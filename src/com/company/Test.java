package com.company;


import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Test {

    public static void main(String args[]) throws FileNotFoundException {
        // command args ignored
        Parser parser = new Parser(new Scanner(new DataInputStream(System.in)));
        parser.run();
        System.out.println("done");
    } // main

} // class Test