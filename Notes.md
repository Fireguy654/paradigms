Не прошли тесты дз-8

```
Exception in thread "main" jstest.EngineException: No error expected in (rms ): org.graalvm.polyglot.PolyglotException: Error: Expected operand, found ''
        at jstest.JSEngine.eval(Unknown Source)
        at jstest.JSExpressionEngine.parse(Unknown Source)
        at jstest.JSExpressionEngine.parse(Unknown Source)
        at jstest.prefix.ParserTester.test(Unknown Source)
        at jstest.expression.BaseTester.test(Unknown Source)
        at jstest.expression.BaseTester.testRandom(Unknown Source)
        at jstest.expression.BaseTester.lambda$test$0(Unknown Source)
        at base.Log.lambda$action$0(Unknown Source)
        at base.Log.silentScope(Unknown Source)
        at base.Log.scope(Unknown Source)
        at base.Log.scope(Unknown Source)
        at jstest.expression.BaseTester.test(Unknown Source)
        at jstest.expression.Builder.lambda$selector$4(Unknown Source)
        at base.Selector$Composite.lambda$v$0(Unknown Source)
        at base.Selector.lambda$test$2(Unknown Source)
        at base.Log.lambda$action$0(Unknown Source)
        at base.Log.silentScope(Unknown Source)
        at base.Log.scope(Unknown Source)
        at base.Log.scope(Unknown Source)
        at base.Selector.lambda$test$3(Unknown Source)
        at java.base/java.lang.Iterable.forEach(Iterable.java:75)
        at base.Selector.test(Unknown Source)
        at base.Selector.main(Unknown Source)
        at jstest.prefix.FullPrefixTest.main(Unknown Source)
        at jstest.prefix.FullPostfixTest.main(Unknown Source)
Caused by: javax.script.ScriptException: org.graalvm.polyglot.PolyglotException: Error: Expected operand, found ''
        at org.graalvm.js.scriptengine/com.oracle.truffle.js.scriptengine.GraalJSScriptEngine.toScriptException(GraalJSScriptEngine.java:503)
        at org.graalvm.js.scriptengine/com.oracle.truffle.js.scriptengine.GraalJSScriptEngine.eval(GraalJSScriptEngine.java:480)
        at org.graalvm.js.scriptengine/com.oracle.truffle.js.scriptengine.GraalJSScriptEngine.eval(GraalJSScriptEngine.java:446)
        at java.scripting/javax.script.AbstractScriptEngine.eval(AbstractScriptEngine.java:262)
        ... 25 more
Caused by: org.graalvm.polyglot.PolyglotException: Error: Expected operand, found ''
        at <js>.ParseError(<eval>:156)
        at <js>.ParseTokenError(<eval>:171)
        at <js>.ParseOperandError(<eval>:176)
        at <js>.parseOperand(<eval>:347)
        at <js>.prefWhileBody(<eval>:299)
        at <js>.ret(<eval>:287)
        at <js>.parseOperand(<eval>:333)
        at <js>.crParse(<eval>:272)
        at <js>.:program(<eval>:1)
        at org.graalvm.sdk/org.graalvm.polyglot.Context.eval(Context.java:399)
        at org.graalvm.js.scriptengine/com.oracle.truffle.js.scriptengine.GraalJSScriptEngine.eval(GraalJSScriptEngine.java:478)
        ... 27 more

```