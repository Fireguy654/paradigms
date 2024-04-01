"use strict"

const cnst = num => () => num;
const variable = name => (x, y, z) => {
    switch(name) {
        case "x":
            return x;
        case "y":
            return y;
        case "z":
            return z;
    }
}

const operation = doOper => (...args) => (x, y, z) => doOper(...(args.map(a => a(x, y, z))))
const add = operation((a, b) => a + b);
const subtract = operation((a, b) => a - b);
const multiply = operation((a, b) => a * b);
const divide = operation((a, b) => a / b);
const negate = operation(a => -a);

const indByCmp = cmp => size => (...args) => {
    let accum = 0;
    for (let i = 1; i < size; i++) {
        if (!cmp(args[accum], args[i])) {
            accum = i;
        }
    }
    return accum;
}
const minCmp = (x, y) => (x <= y);
const argMin = indByCmp(minCmp);
const argMin3 = operation(argMin(3))
const argMin5 = operation(argMin(5));
const maxCmp = (x, y) => (x >= y);
const argMax = indByCmp(maxCmp);
const argMax3 = operation(argMax(3));
const argMax5 = operation(argMax(5));

const one = cnst(1);
const two = cnst(2);

const operations = new Map([
    ["+", [add, 2]], ["-", [subtract, 2]], ["*", [multiply, 2]], ["/", [divide, 2]],
    ["negate", [negate, 1]], ["one", [one, 0]], ["two", [two, 0]],
    ["argMin3", [argMin3, 3]], ["argMin5", [argMin5, 5]], ["argMax3", [argMax3, 3]], ["argMax5", [argMax5, 5]]
])

const accumulateTwoOpers = (accumulator, strOper) => {
    let oper;
    let cntArg;
    if (!operations.has(strOper)) {
        switch (strOper) {
            case "x":
            case "y":
            case "z":
                oper = variable(strOper);
                break;
            default:
                oper = cnst(parseInt(strOper));
        }
        cntArg = 0;
    } else {
        oper = operations.get(strOper)[0];
        cntArg = operations.get(strOper)[1];
    }
    switch(cntArg) {
        case 5:
            return f5 => f4 => f3 => f2 => f1 => accumulator(oper(f1, f2, f3, f4, f5));
        case 3:
            return f3 => f2 => f1 => accumulator(oper(f1, f2, f3));
        case 2:
            return f2 => f1 => accumulator(oper(f1, f2));
        case 1:
            return f => accumulator(oper(f));
        case 0:
            return accumulator(oper);
    }
}
const parse = com => (x, y, z) => com.split(" ").filter(elem => elem !== "")
                                    .reduceRight(accumulateTwoOpers, x => x)(x, y, z);
let ans = Array();
for (let i = 0; i < 10; i++) {
    ans.push(parse("x x * 2 x * - 1 +")(i, 0, 0));
}
console.log(ans);



