package com.example.testapplication;

//This is the list that runs the stack
//Runs like a normal stack by putting a node on top of the stack when pushed and destroying that node when popped.
//Only the top of the stack can be read.
public class SsList {
    public SsNode top = null;

    public SsList() {
        top = new SsNode();

    }

    //Place top of stack on next node, make new top of stack
    public void push(double num1, double num2, double temp, double power, String display, char button, char operation, char opp, int input_state, int num_state, int pow_state) {
        SsNode prev = new SsNode(top);

        top.num1 = num1;
        top.num2 = num2;
        top.temp = temp;
        top.power = power;
        top.display = display;
        top.button = button;
        top.operation = operation;
        top.opp = opp;
        top.input_state = input_state;
        top.num_state = num_state;
        top.pow_state = pow_state;

        top.next = prev; //Point at next in stack.

    }

    public void pop() {
        //SsNode popped = top;
        top = top.next;

        if(top == null) {
            top = new SsNode();
        }

        //return popped;
    }
}