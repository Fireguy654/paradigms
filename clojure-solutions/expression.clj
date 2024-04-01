(load-file "parser.clj")

(defn isOneArg [args] (empty? (rest args)))
(defn operCr [pred oper & operands]
  {
   :pre [(pred operands)]
   }
  (let
    [doOper (fn [nums] (apply oper nums))]
    (fn [values] (doOper (mapv #(% values) operands)))
    )
  )
(def operCrNoPred (partial operCr (constantly true)))
(defn constant [num] (fn [values] num))
(defn variable [name] (fn [values] (get values name)))
(def add (partial operCrNoPred +))
(def subtract (partial operCrNoPred -))
(def multiply (partial operCrNoPred *))
(defn doAntiBugDivide
  ([first] (/ 1 (double first)))
  ([first & oth] (/ (double first) (double (apply * oth))))
  )
(def divide (partial operCrNoPred doAntiBugDivide))
(def negate (partial operCr isOneArg -))
(defn doMeansq [& nums] (/ (apply + (mapv #(* % %) nums)) (count nums)))
(def meansq (partial operCrNoPred doMeansq))
(defn doRMS [& nums] (Math/sqrt (apply doMeansq nums)))
(def rms (partial operCrNoPred doRMS))
(def OPERATIONS {'+ add, '- subtract, '* multiply, '/ divide, 'negate negate, 'meansq meansq, 'rms rms})
(defn crParse [variableC constantC operationsC func]
  (letfn
    [
     (parseOperand [token]
       (cond
         (list? token) (parseOperation token),
         (number? token) (constantC token),
         :else (variableC (name token))
         )
       ),
     (parseOperation [tokens]
       (let
         [operation (first tokens), operands (rest tokens)]
         (apply (get operationsC operation) (mapv parseOperand operands))
         )
       )
     ]
    (parseOperand (read-string func))
    )
  )
(def parseFunction (partial crParse variable constant OPERATIONS))

(definterface ExprObject
  (^String toString []),
  (^String toStringInfix []),
  (^Number evaluate [vars]),
  (diff [varName]))
(deftype Operation [sign doOper doDiff args]
  ExprObject
  (toString [this] (let [strArgs (mapv #(.toString %) args)]
                     (apply str "(" sign " " (clojure.string/join " " strArgs) ")")))
  (toStringInfix [this] (let [strArgs (mapv #(.toStringInfix %) args)]
                                 (if (isOneArg args)
                                   (str sign " " (first strArgs))
                                   (str "(" (clojure.string/join (str " " sign " ") strArgs) ")"))))
  (evaluate [this vars] (let [nums (mapv #(.evaluate % vars) args)] (apply doOper nums)))
  (diff [this varName] (doDiff this varName))
  )
(defn crOper
  ([sign doOper doDiff] (fn [& args] (Operation. sign doOper doDiff args)))
  ([sign doOper] (crOper sign doOper (constantly nil))))
(declare ZERO)
(deftype MyConstant [number]
  ExprObject
  (toString [this] (str number))
  (toStringInfix [this] (.toString this))
  (evaluate [this vars] number)
  (diff [this varName] ZERO))
(defn Constant [num] (MyConstant. num))
(def ZERO (Constant 0))
(def ONE (Constant 1))
(deftype MyVariable [varN]
  ExprObject
  (toString [this] (str varN))
  (toStringInfix [this] (.toString this))
  (evaluate [this vars] (get vars (str (first (clojure.string/lower-case varN)))))
  (diff [this varName] (if (= varN varName) ONE ZERO)))
(defn Variable [varN] (MyVariable. varN))
(defn getDiffedArgs [this varName] (mapv #(.diff % varName) (.-args this)))
(def Negate (crOper "negate" - (fn [this varName] (Negate (first (getDiffedArgs this varName))))))
(def Add (crOper "+" + (fn [this varName] (apply Add (getDiffedArgs this varName)))))
(def Subtract (crOper "-" - (fn [this varName] (apply Subtract (getDiffedArgs this varName)))))
(defn getVecDiffOne
  ([prefAns pref cur varName]
  (if (empty? cur)
    prefAns
    (let [elem (first cur), curDiff (concat pref [(.diff elem varName)] (rest cur))]
      (recur (conj prefAns curDiff) (conj pref elem) (rest cur) varName))
     )
   ),
  ([cur varName] (getVecDiffOne [] [] cur varName))
  )
(def Multiply (crOper "*" * (fn [this varName] (apply Add (mapv #(apply Multiply %)
                                                                 (getVecDiffOne (.-args this) varName))))))
(def Divide (crOper "/" doAntiBugDivide
                    (fn [this varName]
                      (let [args (.-args this),
                            fir (if (isOneArg args) ONE (first args)),
                            sec (if (isOneArg args) (first args) (apply Multiply (rest args)))]
                        (Divide (Subtract (Multiply (.diff fir varName) sec) (Multiply fir (.diff sec varName))),
                                (Multiply sec sec))))))
(defn doSumExp [& nums] (apply + (mapv #(Math/exp %) nums)))
(defn doDiffSumexp [sumexpImpl this varName] (let [args (.-args this), diffArgs (getDiffedArgs this varName)]
                                               (apply Add (mapv #(Multiply (sumexpImpl %1) %2) args diffArgs))))
(def Sumexp (crOper "sumexp" doSumExp (fn [this varName] (doDiffSumexp Sumexp this varName))))
(def LSE (crOper "lse"
                 (fn [& nums] (Math/log (apply doSumExp nums)))
                 (fn [this varName] (Divide (doDiffSumexp Sumexp this varName) (apply Sumexp (.-args this))))))
(defn toString [this] (.toString this))
(defn toStringInfix [this] (.toStringInfix this))
(defn evaluate [this vars] (.evaluate this vars))
(defn diff [this varName] (.diff this varName))

(defn getBoolean [operation & nums] (apply operation (mapv #(if (> % 0) 1 0) nums)))
(defn myNot [num] (if (== 1 num) 0 1))
(defn getNum [bool] (if bool 1 0))
(def And (crOper "&&" (partial getBoolean bit-and)))
(def Or (crOper "||" (partial getBoolean bit-or)))
(def Xor (crOper "^^" (partial getBoolean bit-xor)))
(def Not (crOper "!" (partial getBoolean myNot)))
(def Impl (crOper "->" (partial getBoolean (fn [fir sec] (bit-or (myNot fir) sec)))))
(def Iff (crOper "<->" (partial getBoolean (fn [fir & oth] (getNum (every? (partial == fir) oth))))))

(def OPERATION_CLASSES {'+ Add, '- Subtract, '* Multiply, '/ Divide, 'negate Negate, 'sumexp Sumexp, 'lse LSE,
                        '&& And, '|| Or, (symbol "^^") Xor, '! Not, '-> Impl, '<-> Iff})
(def parseObject (partial crParse Variable Constant OPERATION_CLASSES))

(defn takeOperByStr [string] (get OPERATION_CLASSES (symbol string)))
(defn operFromFnPLeft [fir oth] (reduce #((takeOperByStr (str (first %2))) %1 (first (rest %2))) fir oth))
(defn operFromFnPRight [fir oth] (let
                                   [oper (takeOperByStr (str (first (first oth))))
                                    sec (second (first oth))
                                    rest (rest oth)]
                                   (if (empty? oth) fir (oper fir (operFromFnPRight sec rest)))))
(defn operFromFuncs [funcs operand] (letfn [(foldr [cur] (if (empty? cur)
                                                           operand
                                                           ((takeOperByStr (str (first cur))) (foldr (rest cur)))))]
                                        (foldr funcs)))
(def UNARY_OPS  ["!", "negate"])
(def BIN_PRIOR_OPS [[["*", "/"], false],
                    [["+", "-"], false],
                    [["&&"], false],
                    [["||"], false],
                    [["^^"], false],
                    [["->"], true],
                    [["<->"], false]])
(defparser parseObjectInfix
           $all-chars (mapv char (range 0 128))
           ($chars [p] (+char (apply str (filter p $all-chars))))
           $letter (+char "xyzXYZ")
           $digit ($chars #(Character/isDigit %))
           $space ($chars #(Character/isWhitespace %))
           $ws (+ignore (+star $space))
           $constant (+map #(Constant (read-string %)) (+str (+seq (+opt \-) (+str (+plus $digit)) (+opt \.)
                                                                   (+str (+star $digit)))))
           $variable (+map #(Variable %) (+str (+seqf cons $letter (+star $letter))))
           $token (+or $constant $variable (delay $br-expr))
           ($get-parser-by-str [curStr] (+str (apply +seq (map (fn [ch] (+char (str ch))) curStr))))
           ($get-oper-parser [operStrs] (apply +or (map (fn [oneStr] ($get-parser-by-str oneStr)) operStrs)))
           $br-unary (+seqf (fn [unary operand] ((takeOperByStr unary) operand)) ($get-oper-parser UNARY_OPS) (delay $br-expr))
           $unary (+seqf operFromFuncs (+star (+seqn 0 ($get-oper-parser UNARY_OPS) (+ignore $space) $ws)) (+or $br-unary $token))
           ($binary-op [assocFunc] (fn [higher-prior names]
                                     (+seqf assocFunc higher-prior (+star (+seq $ws names $ws higher-prior)))))
           ($get-parser-by-info [info, prev] (let [operStrs (first info),
                                                   assocFunc (if (first (rest info)) operFromFnPRight operFromFnPLeft)]
                                               (($binary-op assocFunc) prev ($get-oper-parser operStrs))))
           $exp-parser (reduce (fn [accum operInfo] ($get-parser-by-info operInfo accum))
                               $unary BIN_PRIOR_OPS)
           $br-expr (+seqn 1 \( $ws $exp-parser $ws \))
           $parse (+seqn 0 $ws $exp-parser $ws)
           )