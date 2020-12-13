package com.company;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Test {

    public static void main(String args[]) throws FileNotFoundException {
        // command args ignored
        Parser parser = new Parser(new Scanner(new BufferedReader(new FileReader("src/com/company/test-input.txt"))));
        parser.run();
        System.out.println("Syntax analysis performed successfully and no errors were found");
    } // main

} // class Test