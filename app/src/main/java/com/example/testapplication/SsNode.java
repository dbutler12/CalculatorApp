package com.example.testapplication;

//The stack nodes
//Stores all the states and numbers to be used when backspace or clear is pressed
public class SsNode {
    public double num1;
    public double num2;
    public double temp;
    public double power;
    public String display;
    public char button;
    public char operation; //Passed operation
    public char opp; //Stored operation
    public int input_state; //This can be: 0 = Initial or clear state; 1 = Int input; 2 = Fraction input; 3 = Double input; 4 = opps state; 5 = waiting; 6 = power state;
    public int num_state; //This can be 1 or 2 for each number.
    public int pow_state; //For powers can be -1 = no power, 0 = Initialized, 1 = int input, 3 = double input, 4 = performed
    public SsNode next = null;

    public SsNode() {
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
    }

    //Create a new saved stack node using an old one (a copy)
    public SsNode(SsNode old) {
        num1 = old.num1;
        num2 = old.num2;
        temp = old.temp;
        power = old.power;
        display = old.display;
        button = old.button;
        operation = old.operation;
        opp = old.opp;
        input_state = old.input_state;
        num_state = old.num_state;
        pow_state = old.pow_state;
        next = old.next;
    }
}