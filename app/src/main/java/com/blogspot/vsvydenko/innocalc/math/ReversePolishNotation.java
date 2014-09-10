package com.blogspot.vsvydenko.innocalc.math;

/**
 * Created by vsvydenko on 10.09.14.
 * http://www.java2s.com/Code/Java/Collections-Data-Structure/ReversePolishNotation.htm
 */

import java.util.Scanner;

/**
 * ReversePolishNotation is a simple application which will test several RPN
 * equations to make sure the calcRPN method works properly.
 *
 * @author Alex Laird
 * @version 1.0 File: ReversePolishNotation.java Created: Oct 2008
 */
public class ReversePolishNotation {

    /**
     * This method tests to see whether the value of a String is a legal RPN
     * mathematical operator or not.
     *
     * @param next is the value to be tested
     * @return whether the next value is a mathematical operator or not
     */
    public static boolean nextIsOperator(String next) {
        return (next.equals("+") || next.equals("-") || next.equals("*") || next
                .equals("/"));
    }

    /**
     * This method will calculate the answer of the given Reverse Polar Notation
     * equation. All exceptions are thrown to the parent for handling.
     *
     * @param input is the equation entered by the user
     * @return the top item of the stack; the calculated answer of the Reverse
     * Polish Notation equation
     */
    public static double calcRPN(String input) {
        // eliminate any leading or trailing whitespace from input
        input = input.trim();
        // scanner to manipulate input and stack to store double values
        String next;
        StackCalc<Double> stack = new StackCalc<Double>();
        Scanner scan = new Scanner(input);

        // loop while there are tokens left in scan
        while (scan.hasNext()) {
            // retrieve the next token from the input
            next = scan.next();

            // see if token is mathematical operator
            if (nextIsOperator(next)) {
                // ensure there are enough numbers on stack
                if (stack.size() > 1) {
                    if (next.equals("+")) {
                        stack.push(stack.pop() + stack.pop());
                    } else if (next.equals("-")) {
                        stack.push(-stack.pop() + stack.pop());
                    } else if (next.equals("*")) {
                        stack.push(stack.pop() * stack.pop());
                    } else if (next.equals("/")) {
                        double first = stack.pop();
                        double second = stack.pop();

                        if (first == 0) {
                            android.util.Log
                                    .e("rpn", "The RPN equation attempted to divide by zero.");
                        } else {
                            stack.push(second / first);
                        }
                    }
                } else {
                    android.util.Log.e("rpn",
                            "A mathematical operator occured before there were enough numerical values for it to evaluate.");
                }
            } else {
                try {
                    stack.push(Double.parseDouble(next));
                } catch (NumberFormatException c) {
                    android.util.Log.e("rpn", "The string is not a valid RPN equation.");
                }
            }
        }

        if (stack.size() > 1) {
            android.util.Log.e("rpn",
                    "There too many numbers and not enough mathematical operators with which to evaluate them.");
        }

        return stack.pop();
    }
}
