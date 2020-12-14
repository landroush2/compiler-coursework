package com.company;//com.sampleCode;

import java.io.BufferedReader;

public class Scanner {
    public int token;
    private char character = ' ';
    private char ident = ' ';
    private String word = "";
    private int intValue = 0;
    private Buffer buffer;

    public Scanner(BufferedReader in) {
        buffer = new Buffer(in);
        token = Token.semicolon;
    } // Scanner


    public int getToken() {
        while (Character.isWhitespace(character)) {
            character = buffer.get();
        }
        if (Character.isLetter(character)) {
            word = "";
            while (Character.isLetter(character)) {
//            ident = Character.toLowerCase(character);
                word.concat(Character.toString(character));
                character = buffer.get();
            }
            switch (word){
                case "if":
                    character = buffer.get();
                    token = Token.IF_TOKEN;
                    break;

                case "then":
                    character = buffer.get();
                    token = Token.THEN_TOKEN;
                    break;

                case "else":
                    character = buffer.get();
                    token = Token.ELSE_TOKEN;
                    break;

                case "end":
                    character = buffer.get();
                    token = Token.END_TOKEN;
                    break;

                case "repeat":
                    character = buffer.get();
                    token = Token.REPEAT_TOKEN;
                    break;

                case "until":
                    character = buffer.get();
                    token = Token.UNTIL_TOKEN;
                    break;

                case "read":
                    character = buffer.get();
                    token = Token.READ_TOKEN;
                    break;

                case "write":
                    character = buffer.get();
                    token = Token.WRITE_TOKEN;
                    break;

                default:
                    character = buffer.get();
                    token = Token.IDENTIFIER_TOKEN;
                    break;

            }
//            token = Token.letter;
        } else if (Character.isDigit(character)) {
            intValue = getNumber();
            token = Token.number;
        } else {
            switch (character) {
                case ';':
                    character = buffer.get();
                    token = Token.semicolon;
                    break;

                case '.':
                    character = buffer.get();
                    token = Token.period;
                    break;

                case '+':
                    character = buffer.get();
                    token = Token.plusop;
                    break;

                case '-':
                    character = buffer.get();
                    token = Token.minusop;
                    break;

                case '*':
                    character = buffer.get();
                    token = Token.timesop;
                    break;

                case '/':
                    character = buffer.get();
                    token = Token.divideop;
                    break;

                case '=':
                    character = buffer.get();
                    token = Token.assignop;
                    break;

                case '(':
                    character = buffer.get();
                    token = Token.lparen;
                    break;

                case ')':
                    character = buffer.get();
                    token = Token.rparen;
                    break;

                default:
                    error("Illegal character " + character + "at line: ");
                    break;
            } // switch
        } // if
        return token;
    } // getToken


    public int number() {
        return intValue;
    } // number


    public String identifier() {
        return word;
    } // letter


    public void match(int which) {
//        if (token != which) {
//            error("Invalid token " + Token.toString(token) +
//                    "-- expecting " + Token.toString(which) + " at line: ");
//            System.exit(1);
//        } // if
        token = getToken();

    } // match


    public void error(String msg) {
        System.err.println(msg + this.buffer.lineNo);
        System.exit(1);
    } // error


    private int getNumber() {
        int result = 0;
        do {
            result = result * 10 + Character.digit(character, 10);
            character = buffer.get();
        } while (Character.isDigit(character));
        return result;
    } // getNumber

} // Scanner

class Buffer {
    private String line = "";
    private int column = 0;
    public int lineNo = 0;
    private BufferedReader in;

    public Buffer(BufferedReader in) {
        this.in = in;
    } // Buffer


    public char get() {
        column++;
        if (column >= line.length()) {
            try {
                line = in.readLine();
            } catch (Exception e) {
                System.err.println("Invalid read operation on line: " );
                System.exit(1);
            } // try
            if (line == null) {
                System.out.println("Syntax analysis performed successfully and no errors were found");
                System.exit(0);
            }
            column = 0;
            lineNo++;
            System.out.println(line);
            line = line + "\n";
        } // if column
        return line.charAt(column);
    } // get

} // class Buffer