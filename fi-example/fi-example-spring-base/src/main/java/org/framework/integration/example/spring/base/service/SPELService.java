package org.framework.integration.example.spring.base.service;

import lombok.Data;
import org.framework.integration.example.spring.base.entity.Inventor;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelCompilerMode;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.SimpleEvaluationContext;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created  on 2023/8/16 16:16:34
 *
 * @author zl
 */
@Service
public class SPELService {

    public String simpleParse() {

        String message = simpleParse1();
        javaEntityParse();
        //        myTest();
        simpleEvaluationContext();
        parserConfiguration();
        compilerConfiguration();
        return message;
    }

    private String simpleParse1() {
        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression("'Hello World'.concat('!')");
        Expression exp2 = parser.parseExpression("T(java.lang.Math).random() * 100.0");
        String message = (String) exp.getValue();
        System.out.println("exp2.getValue() = " + exp2.getValue());
        System.out.println("message = " + message);
        return message;
    }

    public void javaEntityParse() {

        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression("new String('hello world').toUpperCase()");
        String message = exp.getValue(String.class);

        System.out.println("javaEntityParse : message = " + message);

        // 通过·来访问属性
        Expression exp1 = parser.parseExpression("'Hello World'.bytes");
        byte[] bytes = (byte[]) exp1.getValue();
        System.out.println("bytes.length = " + bytes.length);

        Expression exp2 = parser.parseExpression("'Hello World'.bytes.length");
        int length = (Integer) exp2.getValue();
        System.out.println("length = " + length);

        GregorianCalendar c = new GregorianCalendar();
        c.set(1856, 7, 9);

        // The constructor arguments are name, birthday, and nationality.
        Inventor tesla = new Inventor("Nikola Tesla", c.getTime(), "Serbian");

        Expression exp3 = parser.parseExpression("name"); // Parse name as an expression
        String name = (String) exp3.getValue(tesla);
        // name == "Nikola Tesla"
        System.out.println("name = " + name);

        Expression exp4 = parser.parseExpression("name == 'Nikola Tesla'");
        boolean result = exp4.getValue(tesla, Boolean.class);
        System.out.println("result = " + result);
    }

    public void myTest() {

        var map = new HashMap<String, Object>();

        map.put("1", 123);
        map.put("2", 123332);
        map.put("3", "xxxx");
        map.put("name", "name---");

        var evaluationContext = new StandardEvaluationContext();

        evaluationContext.setVariables(map);

        ExpressionParser parser = new SpelExpressionParser();

        var expression = parser.parseExpression("name");
        var value = (String) expression.getValue(evaluationContext);
        System.out.println("value = " + value);

    }

    public void simpleEvaluationContext() {
        var simple = new Simple();
        simple.booleanList.add(Boolean.TRUE);

        var context = SimpleEvaluationContext.forReadOnlyDataBinding().build();

        ExpressionParser parser = new SpelExpressionParser();
        // set 具备修改 simple 的能力
        parser.parseExpression("booleanList[0]").setValue(context, simple, "false");
        var result = simple.booleanList.get(0);

        System.out.println("simpleEvaluationContext: result = " + result);

    }

    /**
     * 如果超出了，讲自动扩展list或者数组，用来适应匹配索引，避免越界异常
     *
     * @see <a href='https://docs.spring.io/spring-framework/docs/5.2.25.RELEASE/spring-framework-reference/core.html#expressions-parser-configuration'>4.1.2. Parser Configuration</>
     */
    public void parserConfiguration() {
        // Turn on:
        // - auto null reference initialization
        // - auto collection growing
        SpelParserConfiguration config = new SpelParserConfiguration(true, true);
        ExpressionParser parser = new SpelExpressionParser(config);
        Expression expression = parser.parseExpression("list[3]");
        Demo demo = new Demo();
        Object o = expression.getValue(demo);
        System.out.println("parserConfiguration: o = " + o);
    }

    public void compilerConfiguration() {
        SpelParserConfiguration config = new SpelParserConfiguration(SpelCompilerMode.IMMEDIATE, this.getClass().getClassLoader());
        SpelExpressionParser parser = new SpelExpressionParser(config);
        Expression expr = parser.parseExpression("payload");
        MyMessage message = new MyMessage();
        Object payload = expr.getValue(message);
        System.out.println("compilerConfiguration: payload = " + payload);
    }

    static class Simple {
        public List<Boolean> booleanList = new ArrayList<>();
    }

    static class Demo {
        public List<String> list;
    }

    @Data
    private class MyMessage {

        private String payload = "12344";
    }
}
