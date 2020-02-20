package com.krikorherlopian.arithmeticoperations;

public class ArithmeticOperations {

    public static double binaryOperation(double operand1, double operand2, String operator) {
        switch (operator) {
            case "+":
                return add(operand1, operand2);
            case "-":
                return sub(operand1, operand2);
            case "*":
                return mul(operand1, operand2);
            case "/":
                return div(operand1, operand2);
        }
        return 0;
    }

    public static double trigonometricOperationRadian(double rad, String function) {
        switch (function.toLowerCase()) {
            case "sin":
                return sin(rad);
            case "cos":
                return cos(rad);
            case "tan":
                return tan(rad);
        }
        return 0;
    }

    public static double trigonometricOperationDegree(double degree, String function) {
        double operandInRad = toRadians(degree);
        switch (function.toLowerCase()) {
            case "sin":
                return sin(operandInRad);
            case "cos":
                return cos(operandInRad);
            case "tan":
                return tan(operandInRad);
        }
        return 0;
    }

    public static double add(double operand1, double operand2) {
        return operand1 + operand2;
    }

    public static double sub(double operand1, double operand2) {
        return operand1 - operand2;
    }

    public static double mul(double operand1, double operand2) {
        return operand1 * operand2;
    }

    public static double div(double operand1, double operand2) {
        return operand1 / operand2;
    }

    public static double sin(double rad) {
        return Math.sin(rad);
    }


    public static double cos(double rad) {
        return Math.cos(rad);
    }

    public static double tan(double rad) {
        return Math.tan(rad);
    }

    public static double toRadians(double degree) {
        return Math.toRadians(degree);
    }
}
