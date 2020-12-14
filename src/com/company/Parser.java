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
        while (scanner.token == Token.semicolon) {
            scanner.match(Token.semicolon);
            statement();
        }
    }

    private void statement() {
        //    statement → if-stmt | repeat-stmt | assign-stmt | read-stmt | write stmt
        //scanner.getToken();
        switch (scanner.token) {
            case Token.IF_TOKEN:
                ifStmt();
                break;

            case Token.REPEAT_TOKEN:
                repeatStmt();
                break;

            case Token.IDENTIFIER_TOKEN:
                assignStmt();
                break;

            case Token.READ_TOKEN:
                readStmt();
                break;

            case Token.WRITE_TOKEN:
                writeStmt();
                break;

            default:
                scanner.error("Syntax Error at line: ");
                break;

        }

    } // statement

    private void ifStmt() {
        //    if-stmt → if exp then stmt-sequence [else stmt-sequence] end
        scanner.match(Token.IF_TOKEN);
        expression();
        scanner.match(Token.THEN_TOKEN);
        stmtSequence();
        if (scanner.token == Token.ELSE_TOKEN) {
            scanner.match(Token.ELSE_TOKEN);
            stmtSequence();
        }
        scanner.match(Token.END_TOKEN);
    }

    private void repeatStmt() {
        //    repeat-stmt → repeat stmt-sequence until exp
        scanner.match(Token.REPEAT_TOKEN);
        stmtSequence();
        scanner.match(Token.UNTIL_TOKEN);
        expression();
    }

    private void assignStmt() {
        //    assign-stmt → identifier := exp
        scanner.match(Token.IDENTIFIER_TOKEN);
        scanner.match(Token.assignop);
        expression();

    }

    private void readStmt() {
        //    read-stmt → read identifier
        scanner.match(Token.READ_TOKEN);
        scanner.match(Token.IDENTIFIER_TOKEN);
    }

    private void writeStmt() {
        //    write-stmt → write exp
        scanner.match(Token.WRITE_TOKEN);
        expression();
    }


    private void expression() {
        //        exp → simple-exp [comparison-op simple-exp]
        simpleExp();
        if (scanner.token == Token.lessthanop || scanner.token == Token.equalsop) {
            comparisonOp();
        }
    }// expression

    private void comparisonOp() {
        //    comparison-op → < | =
        if(scanner.token == Token.lessthanop){
            scanner.match(Token.lessthanop);
        }else {
            scanner.match(Token.equalsop);
        }
    }

    private void simpleExp() {
        //    simple-exp → term [addop term]
        term();
        if(scanner.token == Token.plusop||scanner.token==Token.minusop){
            addOp();
        }
    }

    private void addOp() {
        //    addop → + | -
        if(scanner.token==Token.plusop){
            scanner.match(Token.plusop);
        }else {
            scanner.match(Token.minusop);
        }
    }


    private void term() {
        //        term → factor [mulop factor]
        factor();
        if(scanner.token==Token.timesop||scanner.token==Token.divideop){
            mulOp();
            factor();
        }
    } // term

    private void mulOp() {
        //    mulop → * | /
        if(scanner.token==Token.timesop){
            scanner.match(Token.timesop);
        }else {
            scanner.match(Token.divideop);
        }
    }


    private void factor() {
        //    factor → ( exp ) | number | identifier
        switch (scanner.token){
            case Token.lparen:
                scanner.match(Token.lparen);
                expression();
                scanner.match(Token.rparen);
                break;

            case Token.number:
                scanner.match(Token.number);
                break;

            case Token.IDENTIFIER_TOKEN:
                scanner.match(Token.IDENTIFIER_TOKEN);
                break;

            default:
                scanner.error("Expected parantheses, a number or an identifier at line: ");
                break;
        }
    } // factor

} // class Parser