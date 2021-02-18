package com.company;//com.sampleCode;

class Token {

    public static final int sc = 0;
    public static final int period = 1;
    public static final int plusop = 2;
    public static final int minusop = 3;
    public static final int timesop = 4;
    public static final int divideop = 5;
    public static final int assignop = 6;
    public static final int lparen = 7;
    public static final int rparen = 8;
    public static final int IDENTIFIER_TOKEN = 9;
    public static final int number = 10;
    public static final int lessthanop = 11;
    public static final int equalsop = 12;
    public static final int IF_TOKEN = 13;
    public static final int THEN_TOKEN = 14;
    public static final int ELSE_TOKEN = 15;
    public static final int END_TOKEN = 16;
    public static final int REPEAT_TOKEN = 17;
    public static final int UNTIL_TOKEN = 18;
    public static final int READ_TOKEN = 19;
    public static final int WRITE_TOKEN = 20;


    public static String[] spelling = {";", ".", "+", "-", "*", "/", ":=", "(", ")", "identifier", "number", "<", "=", "if", "then", "else", "end", "repeat", "until", "read", "write"};

    public static String toString(int i) {
        if (i < 0 || i > WRITE_TOKEN)
            return "";
        return spelling[i];
    } // toString

} // Token
