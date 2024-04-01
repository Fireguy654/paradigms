(defn checkMultiVectorsPredicate [predicate & ms]
  (if (every? number? ms) (every? predicate ms) (every? true? (mapv (partial reduce
                               #(and %1 (if (vector? %2) (checkMultiVectorsPredicate predicate %2) (predicate %2)))
                               true) ms)))
  )
(defn multiVectorsOper [oper & ms]
  (if (vector? (first ms))
    ;(if (empty? (first ms))
    ;  prefRes
    ;  (let [newPref (conj prefRes (multiVectorsOper oper [] (mapv first ms))), newMs (mapv rest ms)] (recur oper newPref newMs))
    ;)
    (apply mapv (partial multiVectorsOper oper) ms)
    (apply oper ms)
    )
  )
(defn multiVectorOper [oper m]
  (if (vector? m)
    (mapv (partial multiVectorOper oper) m)
    (oper m)
    )
  )

(defn getLens
  ([m prefLens]
  (if (vector? m)
    (recur (first m) (conj prefLens (count m)))
    prefLens
  ))
  ([m] (getLens m []))
)
(defn isMultiMatrixWithLens [lens, x]
  (if (vector? x)
    (if (empty? lens) false (and (== (count x) (first lens)) (every? (partial isMultiMatrixWithLens (rest lens)) x)))
    (empty? lens)
  )
)
(defn isNMultiMatrix [n, x]
  (let [lens (getLens x)] (and (or (== n -1) (== (count lens) n)) (isMultiMatrixWithLens lens x))))
(defn isNNumberMultiMatrix [n, x] (and (isNMultiMatrix n x) (checkMultiVectorsPredicate number? x)))
(def isVect (partial isNNumberMultiMatrix 1))
(def isMatrix (partial isNNumberMultiMatrix 2))
(def isTensor (partial isNNumberMultiMatrix -1))
(defn equalLens [& x] (every? true? (apply mapv == (mapv getLens x))))

(defn coordFunc [cntDims pred oper & m]
  {
   :pre [(not-empty m) (every? (partial isNNumberMultiMatrix cntDims) m) (apply equalLens m) (apply pred m)]
   :post [(isNNumberMultiMatrix cntDims %) (equalLens % (first m))]
   }
  (apply multiVectorsOper oper m)
  )
(defn coordFuncNoPred [cntDims oper & m] (apply coordFunc cntDims (constantly true) oper m))
(defn cr+ [cntDims] (partial coordFuncNoPred cntDims +'))
(defn cr- [cntDims] (partial coordFuncNoPred cntDims -'))
(defn cr* [cntDims] (partial coordFuncNoPred cntDims *'))
(defn crd [cntDims] (partial coordFunc cntDims (partial checkMultiVectorsPredicate #(not (= % 0))) /))
(def v+ (cr+ 1))
(def v- (cr- 1))
(def v* (cr* 1))
(def vd (crd 1))
(def m+ (cr+ 2))
(def m- (cr- 2))
(def m* (cr* 2))
(def md (crd 2))
(def t+ (cr+ -1))
(def t- (cr- -1))
(def t* (cr* -1))
(def td (crd -1))

(defn equalSec [seq1 seq2]
  (and (== (count seq1) (count seq2)) (every? true? (mapv == seq1 seq2))))
(defn checkPrefix [v1 v2]
  (let [minSize (min (count v1) (count v2))]
    (equalSec (vec (take minSize v1)) (vec (take minSize v2))))
  )
(defn getMaxLens [len1 len2]
  {
   :pre [(checkPrefix len1 len2)]
   :post [(or (equalSec % len1) (equalSec % len2)) (== (count %) (max (count len1) (count len2)))]
   }
  (if (> (count len1) (count len2)) len1 len2)
  )
(defn broadcastNumber [lens num]
  (if (empty? lens) num (let [elem (broadcastNumber (rest lens) num)] (vec (repeat (first lens) elem)))))
(defn broadcastTensor [lens m]
  (multiVectorOper (partial broadcastNumber (vec (drop (count (getLens m)) lens))) m)
  )
(defn brCoordFunc [pred oper & m]
  {
   :pre [(every? isTensor m)]
   :post [(isTensor %)]
   }
  (apply coordFunc -1 pred oper (mapv (partial broadcastTensor (reduce getMaxLens (mapv getLens m))) m))
  )
(defn brNoPredCoordFunc [oper & m] (apply brCoordFunc (constantly true) oper m))
(def tb+ (partial brNoPredCoordFunc +'))
(def tb- (partial brNoPredCoordFunc -'))
(def tb* (partial brNoPredCoordFunc *'))
(def tbd (partial brCoordFunc (partial checkMultiVectorsPredicate #(not (= % 0))) /))

(defn smth*s [cntDims smth & s]
  {
   :pre [(isNNumberMultiMatrix cntDims smth) (every? number? s)]
   :post [(isNNumberMultiMatrix cntDims %) (equalLens % smth)]
   }
  (let [alls (reduce *' 1 s)] (multiVectorOper #(*' % alls) smth))
  )
(def v*s (partial smth*s 1))
(def m*s (partial smth*s 2))
(defn scalar [& vects]
  {
   :pre [(every? isVect vects) (apply equalLens vects)]
   :post [(number? %)]
   }
  (apply + (apply mapv * vects))
  )
(defn binVect [vect1, vect2]
  (let [x1 (first vect1) y1 (nth vect1 1) z1 (nth vect1 2) x2 (first vect2) y2 (nth vect2 1) z2 (nth vect2 2)]
     (vector (- (* y1 z2) (* y2 z1)) (- (- (* x1 z2) (* x2 z1))) (- (* x1 y2) (* x2 y1))))
  )
(defn vect [& vects]
  {
   :pre [(every? isVect vects) (every? #(== (count %) 3) vects)]
   :post [(isVect %) (== (count %) 3)]
   }
  (reduce binVect vects))
(defn transpose [m]
  {
   :pre [(isMatrix m)]
   :post [(or (zero? (first (rest (getLens m))))
              (and (isMatrix %) (== (count %) (count (first m))) (== (count (first %)) (count m))))]
   }
  (apply mapv vector m))
(defn m*v [m v]
  {
   :pre [(isMatrix m) (isVect v) (== (count (first m)) (count v))]
   :post [(isVect %) (== (count %) (count m))]
   }
  (mapv #(apply + (mapv * % v)) m)
  )
(defn m*m2 [m1 m2] (let [tm2 (transpose m2)] (mapv #(m*v tm2 %) m1)))
(defn m*m [& m] (reduce m*m2 m))
; This string was added for retesting of the system