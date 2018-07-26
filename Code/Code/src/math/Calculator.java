package math;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
/**
 * 计算器,计算一个完整的表达式
 * @author STH
 *
 */
public class Calculator {
    //表达式有错误抛出异常
    class ExpressionException extends Exception {}
    private String expression;
    private List<String> infix = new LinkedList<>();
    private List<String> postfix = new LinkedList<>();
    private Stack<BigDecimal> numberStack = new Stack<>();
    private Stack<Operator> operatorStack = new Stack<>();
    private BigDecimal result = new BigDecimal(0);;

    public Calculator(String expression) {
        this.expression = expression;
    }

    public BigDecimal calculate() throws ExpressionException {
        separate();
        transform();
        operate();
        return result;
    }

    /**
     * 第一步，获取中缀。将原始表达式分隔成数字和符号(原始表必须空格隔开)
     */
    private void separate() {
        infix = Arrays.asList(expression.split(" "));
    }

    /**
     * 第二步，中缀到后缀的转换
     * 
     * @throws ExpressionException
     */
    private void transform() throws ExpressionException {
        operatorStack.clear();
        // 对所有数字和符号进行操作
        for (String str : infix) {
            if (Operator.isOperator(str)) {// 符号
                Operator operator = Operator.getOperator(str);
                if (operatorStack.isEmpty()) {
                    operatorStack.push(operator);
                    continue;
                }
                // 对括号处理,左括号,直接入栈
                if (operator == Operator.BRACKET_LEFT) {
                    operatorStack.push(operator);
                }
                // 对括号处理,右括号，栈中元素弹出直至遇到左括号
                else if (operator == Operator.BRACKET_RIGHT) {
                    Operator top = operatorStack.pop();
                    while (top != Operator.BRACKET_LEFT) {
                        postfix.add(top.getSign());
                        top = operatorStack.pop();
                    }
                }
                // 对于其他符号，栈依次弹出优先级高的符号，再压入当前符号
                else {
                    while (!operatorStack.isEmpty() && !operatorStack.peek().isLower(operator)) {
                        postfix.add(operatorStack.pop().getSign());
                    }
                    operatorStack.push(operator);
                }
            } else {// 数字
                postfix.add(str);
            }
        }
        // 最后将符号栈中所有符号弹出
        while (!operatorStack.isEmpty()) {
            postfix.add(operatorStack.pop().getSign());
        }
    }

    /**
     * 第三步,后缀解析并运算
     */
    private void operate() {
        numberStack.clear();
        // 对所有数字和符号进行操作
        for (String str : postfix) {
            if (Operator.isOperator(str)) {// 符号
                // 操作栈顶两个数字，把结果压入栈
                Operator operator = Operator.getOperator(str);
                BigDecimal number1 = numberStack.pop();
                BigDecimal number2 = numberStack.pop();
                BigDecimal temp = new BigDecimal(0);
                switch (operator) {
                    case PLUS:
                        temp = number2.add(number1);
                        break;
                    case SUB:
                        temp = number2.subtract(number1);
                        break;
                    case MULT:
                        temp = number2.multiply(number1);
                        break;
                    case DIV:
                        temp = number2.divide(number1);
                        break;
                    default:
                        break;
                }
                numberStack.push(temp);
            } else {// 数字
                numberStack.push(new BigDecimal(str));
            }
        }
        result = numberStack.pop();
    }

    public static void main(String[] args) throws ExpressionException {
       /* BigDecimal z=new BigDecimal("0.1");
        BigDecimal xMax=new BigDecimal("9.1");
        BigDecimal yMax=new BigDecimal("27.1");
        for(BigDecimal x=new BigDecimal("8.5");xMax.compareTo(x)==1;x=x.add(z)){
            for(BigDecimal y=new BigDecimal("26");yMax.compareTo(y)==1;y=y.add(z)){
                String rule = "13008 * "+x+" + 3589 * "+y;
                Calculator calculator = new Calculator(rule);
                System.out.println(rule);
                System.out.println(calculator.calculate());
            }
           System.out.println(x+","+xMax);
        }*/
        
        /*Calculator calculator = new Calculator("1 + 2 * 3 + ( 4 / 5 + 6 ) * 7");
        System.out.println(calculator.calculate());*/
        
        for(double x=8.5;x<=9.1;x=x+0.01){
            for(double y=26.0;y<=27.1;y=y+0.01){
                double  result = 13008*x+3589 *y;
                String xBigDecimal =new java.text.DecimalFormat("0.00000000").format( 13008*x);
                String yBigDecimal =new java.text.DecimalFormat("0.00000000").format( 3589 *y);
               // System.out.println(xBigDecimal+","+yBigDecimal+","+new java.text.DecimalFormat("0").format(Double.parseDouble(xBigDecimal)+Double.parseDouble(yBigDecimal)));
                if (new java.text.DecimalFormat("0").format(Double.parseDouble(xBigDecimal)+Double.parseDouble(yBigDecimal)).equals("207414")) {
                    System.out.println(xBigDecimal+","+yBigDecimal+","+new java.text.DecimalFormat("0").format(Double.parseDouble(xBigDecimal)+Double.parseDouble(yBigDecimal)));
                }
               /* System.out.println(new java.text.DecimalFormat("0").format(result)); */
               /* System.out.println(x+","+y+","+result);*/
            }
          /* System.out.println(x+","+xMax);*/
        }
    }
}
