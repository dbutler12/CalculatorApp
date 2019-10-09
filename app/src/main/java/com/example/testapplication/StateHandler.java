package com.example.testapplication;

//This function handles all the states and buttons by calling various methods needed for parsing, calculating, etc...
//Stores three states:
//input_state: Which type of input is occurring, labeled below
//num_state: Which number is input. If an operation occurs on two numbers, that is stored in num1 and num_state is set to 1
//pow_state: This is for working with powers, only applies in state 6
//list object is created for the clear stack
public class StateHandler {
    private double num1;
    private double num2;
    private double temp;
    private double power;
    private String display;
    private char button;
    private char operation; //Passed operation
    private char opp; //Stored operation
    private int prev_input_state; //Stores previous input for clears
    private int input_state; //This can be: 0 = Initial or clear state; 1 = Int input; 2 = Fraction input; 3 = Double input; 4 = opps state; 5 = waiting; 6 = power state;
    private int num_state; //This can be 1 or 2 for each number.
    private int pow_state; //For powers can be -1 = no power, 0 = Initialized, 1 = int input, 3 = double input, 4 = performed
    //private boolean equals;
    private SsList list = null; //A new list is created upon the first time something is pushed to the stack.

    //Constructor sets everything to blank slate
    public StateHandler() {
        num1 = 0;
        num2 = 0;
        temp = 0;
        power = 0;
        button = ';';
        opp = '0';
        operation = '0';
        input_state = 0;
        num_state = 1;
        pow_state = -1;
        display = new String();
        display = "0";
        prev_input_state = 0;
    }

    //Accessors
    public double getNum1() {
        return num1;
    }

    public double getNum2() {
        return num2;
    }

    public String getDisplay() {
        return display;
    }

    //Setters
    public void setNum1(double num) {
        num1 = num;
    }

    public void setNum2(double num) {
        num2 = num;
    }

    public void setBut(char but) {
        button = but;
    }

    public void setOpp(char requested_operation) {
        operation = requested_operation;
    }

    //Main handler function
    //Parses the given button to know what type of input it is using oppsParser
    //If a clear button was pressed, handles that by settings states or going back in the stack
    //Otherwise runs through the state machine utilizing the num_state, input_state, pow_state, button, and operation
    //Most of the time after the state machine changes the display, a small display handler at the end assigns the display to the correct number
    public void handler() {
        oppsParser();

        //System.out.println("IS: " + input_state + " NS: " + num_state + " B: " + button + " O: " + operation + " num1: " + num1 + " num2: " + num2 + " pow: " + power);

        //Clear buttons
        switch(button) {
            case 'C': //Full reset
                num1 = num2 = power = temp = 0;
                button = ';';
                display = "0";
                input_state = 0;
                num_state = 1;
                opp = operation = '0';
                pow_state = -1;
                list = new SsList();
                return;
            case 'B':
                goBack();
                return;
            case 'E': //Clear current number, reset input state and display
                switch(input_state) {
                    case 0:
                    case 1:
                    case 3:
                        display = "0";
                        input_state = 0;
                        button = 0;
                        return;
                    case 4:
                        if(num1 == (int) num1) {
                            display = "" + (int) num1;
                        }else {
                            display = "" + num1;
                        }
                        opp = '0';
                        input_state = prev_input_state;
                        num_state = 1;
                        button = 0;
                        return;
                    case 5:
                        num1 = num2 = power = temp = 0;
                        button = ';';
                        display = "0";
                        input_state = 0;
                        num_state = 1;
                        opp = operation = '0';
                        pow_state = -1;
                        list = new SsList();
                        return;
                    case 6:
                        display = "0";
                        pow_state = 0;
                        button = 0;
                        power = 0;
                        return;
                }
            default: //If not cleared, push to the stack.
                if(list == null) {
                    list = new SsList();
                }else {
                    list.push(num1, num2, temp, power, display, button, operation, opp, input_state, num_state, pow_state);
                }
                break;
        }

        switch(input_state) {
            case 0: //Initial or Zero state
                switch(button) {
                    case '0': //Keep display = 0
                        display = "0";
                        break;
                    case '.': //Switch to decimal state and concatenate string
                        display = "0.";
                        input_state = 3; //Switch to decimal input state
                        break;
                    case 'o': //Two numbers operation case. Store operation, move to second input, and opps state (4)

                        if(num_state == 2) { //Set second number to 0 in this case.
                            num2 = 0;
                        }

                        oppsCase();

                        return;
                    case 'O': //Single number operation case
                        temp = OperationHandler.singleOpps(num1, num2, num_state, operation);
                        display = "" + temp;
                        input_state = 5;
                        operation = '0';
                        break;
                    case 'n': //Can't negate 0, so do nothing
                        break;
                    case '=':  //Stay here if num_state is 1, otherwise perform operation
                        if(num_state == 2) {
                            num2 = 0;
                            equalCase();
                            return;
                        }
                        break;
                    case '^': //0 to the power of any real number is 0, so do nothing
                        break;
                    default: //Number was pressed
                        display = "" + button;
                        input_state = 1;
                        break;
                }
                break;
            case 1: //Int input state
                switch(button) {
                    case '.': //Switch to decimal state and concatenate string
                        display = display + ".";
                        input_state = 3; //Switch to decimal input state
                        break;
                    case 'o': //Operation case: set number, switch number state

                        oppsCase();

                        return;
                    case 'O': //Single operation case
                        temp = OperationHandler.singleOpps(num1, num2, num_state, operation);
                        display = "" + temp;
                        input_state = 5;
                        operation = '0';
                        break;
                    case 'n': //Negate: make negative or positive.
                        display = "-" + display;
                        break;
                    case '=':  //Do nothing if pressed without a second number
                        if(num_state == 2) {
                            equalCase();
                            return;
                        }
                        break;
                    case '^': //Switch to power mode
                        display = "0";
                        input_state = 6; //Power state
                        pow_state = 0;
                        power = 0;
                        return;
                    default: //Number was pressed, so concatenate.
                        display = display + button;
                        break;
                }
                break;
            case 2: //Fraction input state
                break;
            case 3: //Double input state
                switch(button) {
                    case '.': //Already in decimal state, so ignore.
                        break;
                    case 'o': //Operation case: set number, switch number state

                        oppsCase();

                        return;
                    case 'O': //Single operation case
                        temp = OperationHandler.singleOpps(num1, num2, num_state, operation);
                        display = "" + temp;
                        input_state = 5;
                        operation = '0';
                        break;
                    case 'n': //Negate: make negative or positive.
                        display = "-" + display;
                        break;
                    case '=': //Do nothing if pressed without a second number
                        if(num_state == 2) {
                            equalCase();
                            return;
                        }
                        break;
                    case '^': //Switch to power mode
                        display = "0";
                        input_state = 6; //Power state
                        pow_state = 0;
                        power = 0;
                        return;
                    default: //Number was pressed, so concatenate.
                        display = display + button;
                        break;
                }
                break;
            case 4: //New operation state
                switch(button) {
                    case '0':
                        display = "0";
                        input_state = 0;
                        break;
                    case '.': //Accept previous operation and switch to double state
                        display = "0.";
                        input_state = 3;
                        break;
                    case 'o': //Switch operation, stay in operation state.
                        opp = operation;
                        operation = '0';
                        return;
                    case 'O': //Perform operation on 0
                        num2 = 0;
                        temp = OperationHandler.singleOpps(num1, num2, num_state, operation);
                        display = "" + temp;
                        input_state = 5;
                        operation = '0';
                        break;
                    case 'n': //Do nothing because not in a number accepting state
                        break;
                    case '=': //Perform operation with same number.
                        num2 = num1;
                        equalCase();
                        return;
                    case '^': //Switch to power mode, remove old operation from memory
                        display = "0";
                        input_state = 6; //Power state
                        pow_state = 0;
                        power = 0;
                        return;
                    default: //Number was pressed, so concatenate.
                        display = "" + button;
                        input_state = 1;
                        break;
                }
                break;
            case 5: //Waiting state. Num1 is calculated and in display
                //Only options are new operations. If a number is pressed, acts like state 0 and restarts.
                switch(button) {
                    case '.': //Reset and start with 0.
                        display = "0.";
                        input_state = 3;
                        num_state = 1;
                        num1 = num2 = 0;
                        return;
                    case 'o': //Use previous answer and start a new operation.
                        opp = operation;
                        operation = '0';
                        input_state = 4;
                        num_state = 2;
                        num2 = 0;
                        return;
                    case 'O': //Perform operation on answer (num1)
                        temp = OperationHandler.singleOpps(num1, num2, num_state, operation);
                        num1 = temp;
                        display = "" + temp;
                        input_state = 5;
                        operation = '0';
                        return;
                    case 'n': //Negate answer
                        num1 = -num1;
                        display = "" + num1;
                        return;
                    case '=': //Repeat previous operation
                        if(opp != '0') {
                            num_state = 2;
                            equalCase();
                        }
                        return;
                    case '^': //Switch to power mode
                        display = "0";
                        input_state = 6; //Power state
                        pow_state = 0;
                        power = 0;
                        return;
                    case '0': //Zero was pressed
                        display = "0";
                        input_state = 0;
                        num_state = 1;
                        num1 = num2 = 0;
                        break;
                    default: //Number was pressed
                        display = "" + button;
                        input_state = 1;
                        num_state = 1;
                        num1 = num2 = 0;
                        break;
                }
            case 6: //Power case. Can create a number for the power here.
                switch(button) {
                    case '.': //Make decimal if not already one
                        display = (pow_state == 3) ? display : display + ".";
                        pow_state = 3;
                        break;
                    case 'o': //Do nothing. No operations with powers right now! (Probably need to redesign for it)

                        return;
                    case 'O': //Could probably implement this eventually without a redesign

                        return;
                    case 'n': //Negate answer
                        display = "-" + display;
                        break;
                    case '=': //Finish power operation
                        display = "" + calcPower();
                        input_state = 5; //Return to waiting state
                        pow_state = 4;
                        operation = '0';
                        return;
                    case '^': //No powers to powers here!
                        break;
                    case '0': //Zero was pressed
                        if(power != 0) {
                            display = display + "0";
                        }

                        break;
                    default: //Number was pressed
                        display = (power == 0 && pow_state != 3) ? "" + button : display + button;
                        break;
                }
                break;
        }


        if(display.charAt(0) == '-' && display.charAt(1) == '-') { //If two - signs have been concatenated, make it positive
            display = display.substring(2);
        }

        //Check if flag was set earlier to switch state, or this will break!!
        //Assign number based on which number state we are in.
        if(input_state != 6) {
            if(num_state == 1) {
                num1 = Double.parseDouble(display);

            }else {
                num2 = Double.parseDouble(display);

            }
        }else {
            power = Double.parseDouble(display);
        }



        //System.out.println("Output. Num1: " + num1 + " Num2: " + num2 + " Display: " + display);
    }

    //Checks which button was pressed and sets the operation to the correct operation
    //The button is set to 'o' if it is a two number operation and 'O' if it's a single number operation
    private void oppsParser() {
        switch(button) {
            //Two number operations set button to 'o' and operation to given operation
            case '+':
                operation = '+';
                button = 'o';
                break;
            case '-':
                operation = '-';
                button = 'o';
                break;
            case 'x':
                operation = 'x';
                button = 'o';
                break;
            case 'd':
                operation = 'd';
                button = 'o';
                break;
            case 'E': //Clear entry case
                operation = 'E';
                break;
            case 'C': //Clear all case
                operation = 'C';
                break;
            case 's': //Sin
                operation = 's';
                button = 'O';
                break;
            case 'c': //Cos
                operation = 'c';
                button = 'O';
                break;
            case 't': //Tan
                operation = 't';
                button = 'O';
                break;
            case 'S': //arcSin
                operation = 'S';
                button = 'O';
                break;
            case 'v': //arcCos
                operation = 'v';
                button = 'O';
                break;
            case 'T': //arcTan
                operation = 'T';
                button = 'O';
                break;
            case 'f': //Floor
                operation = 'f';
                button = 'O';
                break;
            case 'e': //Ceiling
                operation = 'e';
                button = 'O';
                break;
            case 'r': //Square Root
                operation = 'r';
                button = 'O';
                break;
            case '^': //Power
                operation = '^';
                button = '^';
                break;
            default:
                break;
        }
    }

    private void oppsCase() { //Operation button pressed
        if(num_state == 2) { //If we have two numbers, then equal their operation at the end and set the new operation for later
            num1 = OperationHandler.twoOpps(num1, opp, num2);
            opp = operation;
            operation = '0';
            display = "" + num1;
            prev_input_state = input_state;
            input_state = 4; //Switch to opps state
            return;
        }else { //If we have one number, or are in num1 state, save the operation for later
            opp = operation;
            operation = '0';
            num_state = 2;
            prev_input_state = input_state;
            input_state = 4;
            return;
        }
    }

    //When equals is pressed
    private void equalCase() {
        if(num_state == 2) {
            num1 = OperationHandler.twoOpps(num1, opp, num2);
            display = "" + num1;
            input_state = 5; //Switch to waiting state after a calculation
            num_state = 1; //Answers are in num1
            return;
        }

    }

    //When it is time to calculate a power (state 6)
    private double calcPower() {
        if(num_state == 1) { //Calculate the power
            num1 = OperationHandler.singleOpps(num1,  power, num_state, '^');
            //System.out.println("Power Numbers: " + num1);
            return num1;
        }else {
            num2 = OperationHandler.singleOpps(num2,  power, num_state, '^');
            //System.out.println("Power Numbers: " + num2);
            return num2;
        }
    }

    //Function to work with the clear stack
    private void goBack() {

        if(list.top != null) {
            num1 = list.top.num1;
            num2 = list.top.num2;
            temp = list.top.temp;
            power = list.top.power;
            display = list.top.display;
            button = list.top.button;
            operation = list.top.operation; //Passed operation
            opp = list.top.opp; //Stored operation
            input_state = list.top.input_state; //This can be: 0 = Initial or clear state; 1 = Int input; 2 = Fraction input; 3 = Double input; 4 = opps state; 5 = waiting; 6 = power state;
            num_state = list.top.num_state; //This can be 1 or 2 for each number.
            pow_state = list.top.pow_state; //For powers can be -1 = no power, 0 = Initialized, 1 = int input, 3 = double input, 4 = performed
            list.pop();
        }

    }

}