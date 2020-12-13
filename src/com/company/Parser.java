package com.company;//com.sampleCode;


/* This program illustrates recursive descent parsing using a
   pure procedural approach.

   The grammar:

    program → stmt-sequence
    stmt-sequence → statement { ;statement }
    statement → if-stmt | repeat-stmt | assign-stmt | read-stmt | write stmt
    if-stmt → if exp then stmt-sequence [else stmt-sequence] end
    repeat-stmt → repeat stmt-sequence until exp
    assign-stmt → identifier := exp
    read-stmt → read identifier
    write-stmt → write exp
    exp → simple-exp [comparison-op simple-exp]
    comparison-op → < | =
    simple-exp → term [addop term]
    addop → + | -
    term → factor [mulop factor]
    mulop → * | /
    factor → ( exp ) | number | identifier

*/

public class Parser {

    private Scanner scanner;


    public Parser(Scanner scanner) {
        this.scanner = scanner;
    } // Parser


    public void run() {
        scanner.getToken();
        program();
    } // run

    private void program() {
        //    program → stmt-sequence
        stmtSequence();
    }

    private void stmtSequence() {
        //    stmt-sequence → statement { ;statement }
        statement();
        scanner.getToken();
        while (scanner.token == Token.semicolon) {
            statement();
        }
    }

    private void statement() {
        //   statement = { expression  ";" } "."
        while (scanner.token != Token.period) {
            int value = expression();
            //System.out.println("=> " + value);
            scanner.getToken();  // flush ";"
        } // while
    } // statement


    private int expression() {
        //    expression = term { ( "+" | "-" ) term }
        int left = term();
        while (scanner.token == Token.plusop ||
                scanner.token == Token.minusop) {
            int saveToken = scanner.token;
            scanner.getToken();
            switch (saveToken) {
                case Token.plusop:
                    left += term();
                    break;
                case Token.minusop:
                    left -= term();
                    break;
            } // switch
        } // while
        return left;
    } // expression


    private int term() {
        //    term = factor { ( "*" | "/" ) factor }
        int left = factor();
        while (scanner.token == Token.timesop ||
                scanner.token == Token.divideop) {
            int saveToken = scanner.token;
            scanner.getToken();
            switch (saveToken) {
                case Token.timesop:
                    left *= factor();
                    break;
                case Token.divideop:
                    left /= factor();
                    break;
            } // switch
        } // while
        return left;
    } // term


    private int factor() {
        //    factor    = number | "(" expression ")"
        int value = 0;
        switch (scanner.token) {
            case Token.number:
                value = scanner.number();
                scanner.getToken();  // flush number
                break;
            case Token.lparen:
                scanner.getToken();
                value = expression();
                if (scanner.token != Token.rparen)
                    scanner.error("Missing ')'");
                scanner.getToken();  // flush ")"
                break;
            default:
                scanner.error("Expecting number or (");
                break;
        } // switch
        return value;
    } // factor

} // class Parser