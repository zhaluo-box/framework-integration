package org.framework.integration.example.spring.base.service;

import org.framework.integration.example.spring.base.entity.Inventor;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
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
        return message;
    }

    private String simpleParse1() {
        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression("'Hello World'.concat('!')");
        String message = (String) exp.getValue();
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

    static class Simple {

        public List<Boolean> booleanList = new ArrayList<>();
    }
}
