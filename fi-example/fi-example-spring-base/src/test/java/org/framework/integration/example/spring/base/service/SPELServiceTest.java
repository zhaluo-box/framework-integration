package org.framework.integration.example.spring.base.service;

import lombok.Data;
import org.framework.integration.example.spring.base.entity.Inventor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelCompilerMode;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.SimpleEvaluationContext;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

class SPELServiceTest {

    @Test
    void javaEntityParse() {
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

    @Test
    void simpleEvaluationContext() {
        var simple = new SPELService.Simple();
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
    @Test
    void parserConfiguration() {
        // Turn on:
        // - auto null reference initialization
        // - auto collection growing
        SpelParserConfiguration config = new SpelParserConfiguration(true, true);
        ExpressionParser parser = new SpelExpressionParser(config);
        Expression expression = parser.parseExpression("list[3]");
        SPELService.Demo demo = new SPELService.Demo();
        Object o = expression.getValue(demo);
        System.out.println("parserConfiguration: o = " + o);
    }

    @Test
    void compilerConfiguration() {
        SpelParserConfiguration config = new SpelParserConfiguration(SpelCompilerMode.IMMEDIATE, this.getClass().getClassLoader());
        SpelExpressionParser parser = new SpelExpressionParser(config);
        Expression expr = parser.parseExpression("payload");
        MyMessage message = new MyMessage();
        Object payload = expr.getValue(message);
        System.out.println("compilerConfiguration: payload = " + payload);
    }

    /**
     * 数字支持使用负号、指数表示法和小数点。默认情况下，使用Double.parseDouble（）解析实数
     */
    @Test
    @DisplayName("字面意义的表达式测试")
    void literalExpressionsTest() {
        ExpressionParser parser = new SpelExpressionParser();

        // evals to "Hello World"
        String helloWorld = (String) parser.parseExpression("'Hello World'").getValue();

        System.out.println("helloWorld = " + helloWorld);

        //6.0221415E+23是科学计数法，表示6.0221415乘以10的23次方，即：
        //6.0221415×10^23 =6.0221415e+23
        double avogadrosNumber = (Double) parser.parseExpression("-6.0221415E+23").getValue();

        //avogadrosNumber = -6.0221415E23  好像还是科学计数法，没有转换为数字
        System.out.println("avogadrosNumber = " + avogadrosNumber);

        // evals to 2147483647
        // 十六进制数 转为 十进制数
        int maxValue = (Integer) parser.parseExpression("0x7FFFFFFF").getValue();
        // maxValue = 2147483647
        System.out.println("maxValue = " + maxValue);

        boolean trueValue = (Boolean) parser.parseExpression("true").getValue();
        System.out.println("trueValue = " + trueValue);

        Object nullValue = parser.parseExpression("null").getValue();
        System.out.println("nullValue = " + nullValue);
    }

    @Data
    static class Simple {
        public List<Boolean> booleanList = new ArrayList<>();
    }

    @Data
    static class Demo {
        public List<String> list;
    }

    @Data
    static class MyMessage {

        private String payload = "12344";
    }
}
