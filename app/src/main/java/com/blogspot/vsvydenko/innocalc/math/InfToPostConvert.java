package com.blogspot.vsvydenko.innocalc.math;

/**
 * Created by vsvydenko on 10.09.14.
 * http://www.tutorialspoint.com/javaexamples/data_intopost.htm
 */
public class InfToPostConvert {

    private StackCalc<Character> theStack;
    private String input;
    private StringBuilder output = new StringBuilder("");

    public InfToPostConvert(String in) {
        input = in;
        theStack = new StackCalc<Character>();
    }

    public String doTrans() {
        for (int j = 0; j < input.length(); j++) {
            char ch = input.charAt(j);
            switch (ch) {
                case '+':
                case '-':
                    gotOper(ch, 1);
                    break;
                case '*':
                case '/':
                    gotOper(ch, 2);
                    break;
                case '(':
                    theStack.push(ch);
                    break;
                case ')':
                    gotParen();
                    break;
                default:
                    output.append(ch).append(" ");
                    break;
            }
        }
        while (!theStack.isEmpty()) {
            output.append(" ").append(theStack.pop());
        }
        return output.toString();
    }

    public void gotOper(char opThis, int prec1) {
        while (!theStack.isEmpty()) {
            char opTop = theStack.pop();
            if (opTop == '(') {
                theStack.push(opTop);
                break;
            } else {
                int prec2;
                if (opTop == '+' || opTop == '-')
                    prec2 = 1;
                else
                    prec2 = 2;
                if (prec2 < prec1) {
                    theStack.push(opTop);
                    break;
                } else {
                    output.append(opTop).append(" ");
                }
            }
        }
        theStack.push(opThis);
    }

    public void gotParen() {
        while (!theStack.isEmpty()) {
            char ch = theStack.pop();
            if (ch == '(')
                break;
            else {
                output.append(ch);
            }

        }
    }
}
