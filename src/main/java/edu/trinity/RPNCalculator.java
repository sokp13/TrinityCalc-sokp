package edu.trinity;
import java.util.EmptyStackException;
import java.util.Stack;

public class RPNCalculator {

    public static double evaluate(String expr) {
        String[] splitTokens = expr.split("\\s+");
        Stack<Double> nums = new Stack<>();

        for (String token : splitTokens) {
            // Check if the token is an operand or operator //
            if (checkOp(token)) {

                // Not enough numbers to operate with //
                if (nums.size() < 2) {
                    throw new EmptyStackException();
                }

                // Otherwise, pop the two operands, and execute //
                double opr2 = nums.pop();
                double opr1 = nums.pop();
                double result = runArith(opr1, opr2, token);

                // Put the result back into the stack //
                nums.push(result);
            } else {
                /* If the token is a number, parse it and push it
                 Note: ChatGPT recommended the usage of a Try/Catch
                 block for extra caution, if the token ends up being
                 invalid.
                 */
                try {
                    double number = Double.parseDouble(token);
                    nums.push(number);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid Operator.");
                }
            }
        }

        // Pops the irrelevant/excessive operands //
        while (nums.size() != 1) {
            nums.pop();
        }

        return nums.pop();
    }

    // Detects if the token is an operator //
    private static boolean checkOp(String token) {
        return (token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/"));
    }

    // Application of the appropriate function //
    private static double runArith(double op1, double op2, String opr) {

        // The switch case here was "enhanced" by IntelliJ, explaining the usage of 'yield' //
        return switch (opr) {
            case "+" -> op1 + op2;
            case "-" -> op1 - op2;
            case "*" -> op1 * op2;
            case "/" -> {
                if (op2 == 0) {
                    throw new IllegalArgumentException("Division by zero");
                }
                yield op1 / op2;
            }
            default -> throw new IllegalArgumentException("Invalid operator: " + opr);
        };
    }

//    public static void main(String[] args){
//        System.out.println(evaluate("3 4 + 5 7 8 9"));
//    }
}
