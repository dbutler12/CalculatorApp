package com.example.testapplication;

//This does all the calculations to return to the calculator
//It checks which states the calculator are in and which operation is sent to it
//Then calculates and returns.
public class OperationHandler {

    public static double singleOpps(double num1, double num2, int num_state, char operation) {
        double calc;
        if(num_state == 1) {
            calc = num1;
        }else {
            calc = num2;
        }

        switch(operation) {
            case 's': //Sin
                return Math.sin(Math.toRadians(calc));
            case 'S': //arcSin
                return Math.toDegrees(Math.asin(calc));
            case 'c': //Cos
                return Math.cos(Math.toRadians(calc));
            case 'C': //arcCos
                return Math.toDegrees(Math.acos(calc));
            case 't': //Tan
                return Math.tan(Math.toRadians(calc));
            case 'T': //arcTan
                return Math.toDegrees(Math.atan(calc));
            case 'f': //Floor
                return Math.floor(calc);
            case 'e': //Ceiling
                return Math.ceil(calc);
            case 'r': //Square Root
                return Math.sqrt(calc);
            case '^': //Power
                return Math.pow(num1, num2);
            default:
                return calc;

        }

    }

    //This is the basic two number operation calculator
    //It checks the operation then calculates num1 and num2 accordingly
    public static double twoOpps(double num1, char opp, double num2) {
        double number = num1;

        switch(opp) {
            case '0': //Do nothing
                break;
            case '+': //Add
                number = num1 + num2;
                break;
            case '-': //Subtract
                number = num1 - num2;
                break;
            case 'x': //Multiply
                number = num1 * num2;
                break;
            case 'd':  //Divide
                if(num2 == 0) {
                    number = Double.NaN;
                }else {
                    number = num1 / num2;
                }
                break;
        }

        return number;
    }
}
