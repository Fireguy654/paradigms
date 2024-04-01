"use strict"

function Const(num) {
    this.num = num;
}
Const.prototype.evaluate = function() { return this.num; };
Const.prototype.toString = function() { return this.num.toString(); };
Const.prototype.prefix = Const.prototype.toString;
Const.prototype.postfix = Const.prototype.toString;
Const.prototype.diff = function() { return ZERO; };
Const.prototype.getCntArgs = () => 0;

const ZERO = new Const(0);
const ONE = new Const(1);
const TWO = new Const(2);

function Variable(name) {
    this.name = name;
}
Variable.prototype.evaluate = function(x, y, z) {
    switch (this.name) {
        case "x":
            return x;
        case "y":
            return y;
        case "z":
            return z;
    }
}
Variable.prototype.toString = function() { return this.name; };
Variable.prototype.prefix = Variable.prototype.toString;
Variable.prototype.postfix = Variable.prototype.toString;
Variable.prototype.diff = function(dName) { return this.name === dName ? ONE : ZERO; };
Variable.prototype.getCntArgs = () => 0;

function Operation(...args) {
    this.operands = args;
}
Operation.prototype.toString = function() { return this.operands.join(" ") + " " + this.getStr(); };
Operation.prototype.prefix = function() {
    return "(" + this.getStr() + " " + this.operands.map(a => a.prefix()).join(" ") + ")";
};
Operation.prototype.postfix = function() {
    return "(" + this.operands.map(a => a.postfix()).join(" ") + " " + this.getStr() + ")";
};
Operation.prototype.evaluate = function(x, y, z) {
    return this.doOper(...this.operands.map(a => a.evaluate(x, y, z)));
};

const createOperation = (str, cntArgs, doOper, doDiff) => {
    const ret = function(...args) {
        Operation.call(this, ...(cntArgs === -1 ? args : args.slice(0, cntArgs)));
    }
    ret.prototype = Object.create(Operation.prototype);
    ret.prototype.constructor = ret;
    ret.prototype.name = "Operation '" + str + "'";
    ret.prototype.getStr = () => str;
    ret.prototype.doOper = doOper;
    ret.prototype.diff = function(dName) {
        return doDiff(dName, ...this.operands);
    };
    ret.prototype.getCntArgs = () => cntArgs;
    return ret;
}

const Add = createOperation("+", 2, (a, b) => a + b,
    (dName, a, b) => new Add(a.diff(dName), b.diff(dName)));
const Subtract = createOperation("-", 2, (a, b) => a - b,
    (dName, a, b) => new Subtract(a.diff(dName), b.diff(dName)));
const Multiply = createOperation("*", 2, (a, b) => a * b,
    (dName, a, b) => new Add(new Multiply(a.diff(dName), b), new Multiply(a, b.diff(dName))));
const Divide = createOperation("/", 2, (a, b) => a / b,
    (dName, a, b) => new Divide(
        new Subtract(new Multiply(a.diff(dName), b), new Multiply(a, b.diff(dName))),
        new Multiply(b, b)
    )
);
const Negate = createOperation("negate", 1, a => -a, (dName, a) => new Negate(a.diff(dName)));

const doSumRec = (...args) => args.reduce((accum, cur) => accum + 1 / cur, 0);
const crSumrec = (cntArgs) => createOperation(
    "sumrec" + cntArgs.toString(),
    cntArgs,
    doSumRec,
    (dName, ...args) => args.reduce((accum, cur) => new Add(accum, new Divide(ONE, cur)), ZERO).diff(dName)
);
const Sumrec2 = crSumrec(2);
const Sumrec3 = crSumrec(3);
const Sumrec4 = crSumrec(4);
const Sumrec5 = crSumrec(5);

const crHMean = (cntArgs) => createOperation(
    "hmean" + cntArgs.toString(),
    cntArgs,
    (...args) => cntArgs / doSumRec(...args),
    (dName, ...args) => (new Divide(new Const(cntArgs), new (crSumrec(cntArgs))(...args))).diff(dName)
);
const HMean2 = crHMean(2);
const HMean3 = crHMean(3);
const HMean4 = crHMean(4);
const HMean5 = crHMean(5);

const getMeansq = (...args) => args.reduce((accum, cur) => accum + cur * cur, 0) / args.length;
const getDiffMeansq = (dName, ...args) => {
    return new Divide(
        args.reduce((accum, cur) => new Add(accum, new Multiply(new Multiply(TWO, cur), cur.diff(dName))), ZERO),
        new Const(args.length)
    );
}
const Meansq = createOperation(
    "meansq",
    -1,
    getMeansq,
    getDiffMeansq
);

const RMS = createOperation(
    "rms",
    -1,
    (...args) => Math.sqrt(getMeansq(...args))
);
RMS.prototype.diff = function(dName) {
    return new Divide(getDiffMeansq(dName, ...this.operands), new Multiply(TWO, this))
}

const CONSTRUCTORS = new Map([
    ["+", Add], ["-", Subtract], ["*", Multiply], ["/", Divide], ["negate", Negate],
    ["sumrec2", Sumrec2], ["sumrec3", Sumrec3], ["sumrec4", Sumrec4], ["sumrec5", Sumrec5],
    ["hmean2", HMean2], ["hmean3", HMean3], ["hmean4", HMean4], ["hmean5", HMean5],
    ["meansq", Meansq], ["rms", RMS]
]);

const revCurry = (accumulator, constructor) => {
    if (constructor.prototype.getCntArgs() === -1) {
        throw ParseError("Trying to get parse Polish sentence with function with unlimited argument cnt");
    }
    return function curried(...args) {
        if (args.length >= constructor.prototype.getCntArgs()) {
            return accumulator(new constructor(...args.reverse()));
        } else {
            return function(...nArgs) {
                return curried.apply(this, args.concat(nArgs));
            }
        }
    }
}
const accumulateTwoOpers = (accumulator, strOper) => {
    if (!CONSTRUCTORS.has(strOper)) {
        switch (strOper) {
            case "x":
            case "y":
            case "z":
                return accumulator(new Variable(strOper));
            default:
                return accumulator(new Const(parseInt(strOper)));
        }
    } else {
        return revCurry(accumulator, CONSTRUCTORS.get(strOper));
    }
}
const parse = com => com.trim().split(/\s+/).reduceRight(accumulateTwoOpers, x => x);

class ParseError extends Error {
    constructor(message) {
        super(message);
    }
}
class IncorrectBracketSeqError extends ParseError {
    constructor(message) {
        super(message);
    }
}
class WrongNumberOfArg extends ParseError {
    constructor(message) {
        super(message);
    }
}
class ParseTokenError extends ParseError {
    constructor(message) {
        super(message);
    }
}
class ParseOperandError extends ParseTokenError {
    constructor(message) {
        super(message);
    }
}
class ParseOperationError extends ParseTokenError {
    constructor(message) {
        super(message);
    }
}

class StringSource {
    static END = null;

    constructor(str) {
        this.str = str;
        this.ind = 0;
    }
    next() {
        if (this.ind !== this.str.length) {
            return this.str[this.ind++]
        } else {
            return StringSource.END;
        }
    }
    getInd() { return this.ind; }
}


class StringParser {
    static checkNumber(token) {
        // :NOTE: в js у нас все числа даблы, а ты парсишь только целые
        // и есть еще всякие 1e-9
        return token !== "" && !isNaN(Number(token));
    }
    static isOperandPart(ch) {
        return !StringParser.isWhitespace(ch) && ch !== ')' && ch !== '(';
    }
    static isWhitespace(ch) {
        return /\s/.test(ch);
    }

    constructor(str) {
        this.src = new StringSource(str);
        this.cur = this.src.next();
    }
    take() {
        let ret = this.cur;
        this.cur = this.src.next();
        return ret;
    }
    takeIfCh(ch) {
        if (this.cur === ch) {
            this.cur = this.src.next();
            return true;
        }
        return false;
    }
    getCur() {
        return this.cur;
    }
    getInd() {
        return this.src.getInd();
    }
    getSpecifiedToken(isPart) {
        let token = "";
        while (this.cur !== StringSource.END && isPart(this.cur)) {
            token += this.cur;
            this.take();
        }
        return token;
    }
    getOperandToken() {
        // :NOTE: нужно не разрешать какие-то символы, а запрещать
        // если появятся операции с символами в юникоде, ты устанешь их все разрешать
        return this.getSpecifiedToken(StringParser.isOperandPart);
    }
    skipWhitespaces() {
        while (StringParser.isWhitespace(this.cur)) {
            this.take();
        }
    }
    errorCh() { return this.cur === null ? "END" : "'" + this.cur + "'" }
    createErrorCh(constr, message) { return new constr(`${this.src.getInd()}: ${message}. Found: ${this.errorCh()}`)}
    createErrorWithInd(constr, ind, message) { return new constr(`${ind}: ${message}`) }
}

const crParse = operPos => function(com) {
    let sp = new StringParser(com);
    let ret = parseOperand(sp, operPos);
    sp.skipWhitespaces();
    if (sp.getCur() !== null) {
        throw sp.createErrorCh(ParseError, "expected end of expression");
    }
    return ret;
}
const parseOperation = function(sp, operPos) {
    let items = [];
    let exprInd = sp.getInd();
    let realOperPos = -1;
    do {
        items.push(parseOperand(sp, operPos));
        if (typeof items[items.length - 1] === "function") {
            if (realOperPos !== -1) {
                throw sp.createErrorWithInd(ParseOperationError, exprInd,
                                    `Found more than one operation literal in one expression.`);
            }
            realOperPos = items.length - 1;
        }
        sp.skipWhitespaces();
    } while (sp.getCur() !== ")" && sp.getCur() !== StringSource.END)
    operPos = (operPos + items.length) % items.length;
    if (realOperPos === -1) {
        throw sp.createErrorWithInd(ParseOperationError, exprInd, `Didn't found operation literal.`);
    }
    if (realOperPos !== operPos) {
        throw sp.createErrorWithInd(ParseOperationError, exprInd,
            `Expected operation literal as ${operPos} token. Found as ${realOperPos} token.`);
    }
    let operation = items[operPos]
    if (operation.prototype.getCntArgs() !== -1 && operation.prototype.getCntArgs() !== items.length - 1) {
        throw sp.createErrorWithInd(WrongNumberOfArg, exprInd,
            `Invalid number of arguments for operation ${operation.prototype.getStr()}`
        );
    }
    return new operation(...items.filter((val, ind) => ind !== operPos));
}
function parseOperand(sp, operPos) {
    sp.skipWhitespaces();
    let ind = sp.getInd();
    if (sp.takeIfCh('(')) {
        let ret = parseOperation(sp, operPos);
        if (!sp.takeIfCh(')')) {
            throw sp.createErrorCh(IncorrectBracketSeqError, "expected ')'");
        }
        return ret;
    }
    let token = sp.getOperandToken();
    if (CONSTRUCTORS.has(token)) {
        return CONSTRUCTORS.get(token);
    }
    if (StringParser.checkNumber(token)) {
        return new Const(parseFloat(token));
    }
    if (token === "x" || token === "y" || token === "z") {
        return new Variable(token);
    }
    throw sp.createErrorWithInd(ParseOperandError, ind,
                        `Expected operand found '${token === "" ? "{nothing}" : token}'`);
}
// :NOTE: вынесение копипасты получилось очень сложным и не читаемым
// по факту здесь единственная разница - это на какой позиции внутри скобок стоит операция -- либо 0, либо n-1
// и только эту цифру нужно передавать в общий парсер
const parsePrefix = crParse(0);
const parsePostfix = crParse(-1);

// :NOTE: Variable op (0 args)     : org.graalvm.polyglot.PolyglotException: TypeError: Cannot read property 'prototype' of undefined
// Empty op                 : org.graalvm.polyglot.PolyglotException: TypeError: Cannot read property 'prototype' of undefined
// ошибку надо починить

// :NOTE: Unknown operation        : org.graalvm.polyglot.PolyglotException: Error: Expected operand, found ''
// Missing )                : org.graalvm.polyglot.PolyglotException: Error: Expected operand, found ''
// сообщение не имеет ничего общего с проблемой

// :NOTE: указание места с проблемой есть только в части ошибок