package com.company;


import java.io.*;

public class Test {

    public static void main(String args[]) throws FileNotFoundException {
        // command args ignored
        Parser parser = new Parser(new Scanner(new BufferedReader(new FileReader("src/com/company/test-input.txt"))));
        parser.run();
        System.out.println("done");
    } // main

} // class Test