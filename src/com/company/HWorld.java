
import java.io.*;

public class HWorld{
    public static void main(String... args) throws FileNotFoundException {

        /*    change input file name  */
        Parser run_parser = new Parser(new Scanner(new BufferedReader(new FileReader("...filename"))));
        run_parser.run();
    }
}

class Parser {
    private Scanner scanner;
    public Parser(Scanner scanner) {
        this.scanner = scanner;
    }

    public void run() {
        scanner.getToken();
        program();
    }

    private void program() {
        stmt_sequence();
    }

    private void stmt_sequence() {
        statement();
        while (scanner.token == Token.semicolon) {
            scanner.match(Token.semicolon);
            statement();
        }
    }

    private void statement() {
        if (  scanner.token == Token.ifk) {
            if_method();
        } else if (scanner.token == Token.identifier) {
            assign_method();
        } else if (scanner.token == Token.repeat) {
            repeat_method();
        } else if (scanner.token == Token.read) {
            read_method();
        } else if (scanner.token == Token.write) {
            write_method();
        } else {
            scanner.error("Syntax Error on line ");
        }
    }

    private void if_method() {
        scanner.match(Token.ifk);
        expression();
        scanner.match(Token.thenk);
        stmt_sequence();
        if (scanner.token == Token.elsek) {
            scanner.match(Token.elsek);
            stmt_sequence();
        }
        scanner.match(Token.end);
    }

    private void repeat_method() {
        scanner.match(Token.repeat);
        stmt_sequence();
        scanner.match(Token.until);
        expression();
    }

    private void assign_method() {
        scanner.match(Token.identifier);
        scanner.match(Token.assignop);
        expression();
    }

    private void read_method() {
        scanner.match(Token.read);
        scanner.match(Token.identifier);
    }

    private void write_method() {
        scanner.match(Token.write);
        expression();
    }

    private void expression() {
        simple_exp();
        if (scanner.token == Token.lessthan || scanner.token == Token.equalto) {
            comparison_op();
            simple_exp();
        }
    }

    private void comparison_op() {
        if (scanner.token == Token.lessthan) {
            scanner.match(Token.lessthan);
        } else {
            scanner.match(Token.equalto);
        }
    }

    private void simple_exp() {
        term();
        if (scanner.token == Token.plusop || scanner.token == Token.minusop) {
            addition();
            term();
        }
    }

    private void addition() {
        if (scanner.token == Token.plusop) {
            scanner.match(Token.plusop);
        } else {
            scanner.match(Token.minusop);
        }
    }

    private void term() {
        factor();
        if (scanner.token == Token.timesop || scanner.token == Token.divideop) {
            multiply();
            factor();
        }
    }

    private void multiply() {
        if (scanner.token == Token.timesop) {
            scanner.match(Token.timesop);
        } else {
            scanner.match(Token.divideop);
        }
    }


    private void factor() {
        if (scanner.token == Token.lparen) {
            scanner.match(Token.lparen);
            expression();
            scanner.match(Token.rparen);
        } else if (scanner.token == Token.number) {
            scanner.match(Token.number);
        } else if (scanner.token == Token.identifier) {
            scanner.match(Token.identifier);
        } else {
            scanner.error("Syntax error ");
        }
    }
}


class Scanner {

    private char buffer_in = ' ';
    private String compare_string = "";
    private int integer_value = 0;
    private Buffer buffer;

    public int token;

    public Scanner(BufferedReader in) {
        buffer = new Buffer(in);
        token = Token.semicolon;
    }

    public int getToken() {
        while (Character.isWhitespace(buffer_in)) {
            buffer_in = buffer.get();
        }
        if (Character.isLetter(buffer_in)) {
            compare_string = "";
            while (Character.isLetter(buffer_in)) {
                compare_string+=buffer_in;
                buffer_in = buffer.get();
            }
            if ("read".equals(compare_string)) {
                buffer_in = buffer.get();
                token = Token.read;
            } else if ("write".equals(compare_string)) {
                buffer_in = buffer.get();
                token = Token.write;
            } else if ("if".equals(compare_string)) {
                buffer_in = buffer.get();
                token = Token.ifk;
            } else if ("then".equals(compare_string)) {
                buffer_in = buffer.get();
                token = Token.thenk;
            } else if ("else".equals(compare_string)) {
                buffer_in = buffer.get();
                token = Token.elsek;
            } else if ("end".equals(compare_string)) {
                buffer_in = buffer.get();
                token = Token.end;
            } else if ("repeat".equals(compare_string)) {
                buffer_in = buffer.get();
                token = Token.repeat;
            } else if ("until".equals(compare_string)) {
                buffer_in = buffer.get();
                token = Token.until;
            } else {
                buffer_in = buffer.get();
                token = Token.identifier;
            }
        } else if (Character.isDigit(buffer_in)) {
            integer_value = get_number();
            token = Token.number;
        }
        else {
            if (buffer_in == ';') {
                buffer_in = buffer.get();
                token = Token.semicolon;
            } else if (buffer_in == '+') {
                buffer_in = buffer.get();
                token = Token.plusop;
            } else if (buffer_in == '-') {
                buffer_in = buffer.get();
                token = Token.minusop;
            } else if (buffer_in == '*') {
                buffer_in = buffer.get();
                token = Token.timesop;
            } else if (buffer_in == '(') {
                buffer_in = buffer.get();
                token = Token.lparen;
            } else if (buffer_in == ')') {
                buffer_in = buffer.get();
                token = Token.rparen;
            } else if (buffer_in == ':') {
                buffer.get();
                buffer_in = buffer.get();
                token = Token.assignop;
            } else if (buffer_in == '<') {
                buffer_in = buffer.get();
                token = Token.lessthan;
            } else if (buffer_in == '=') {
                buffer_in = buffer.get();
                token = Token.equalto;
            } else if (buffer_in == '/') {
                buffer_in = buffer.get();
                token = Token.divideop;
            } else {
                error("Illegal character " + buffer_in + " on line: ");
            }
        }
        return token;
    }

    public String identifier() {
        return compare_string;
    }

    public int number() {
        return integer_value;
    }

    public void match(int which) {
        if (token != which) {
            error("Invalid token " + Token.toString(token) +
                    ". Expecting " + Token.toString(which) + " on line ");
            System.exit(1);
        }
        token = getToken();
    }

    public void error(String msg) {
        System.err.println(msg + this.buffer.curr_line + " at column "+this.buffer.column);
        System.exit(1);
    }


    private int get_number() {
        int result = 0;
        do {
            result = result * 10 + Character.digit(buffer_in, 10);
            buffer_in = buffer.get();
        } while (Character.isDigit(buffer_in));
        return result;
    }
}

class Buffer {
    private String line = "";
    public int curr_line = 0;
    public int column = 0;
    private BufferedReader in;

    public Buffer(BufferedReader in) {
        this.in = in;
    }
    public char get() {
        column++;
        if (column >= line.length()) {
            try {
                line = in.readLine();
            } catch (Exception e) {
                System.err.println("Invalid read operation on line: " );
                System.exit(1);
            }
            if (line == null) {
                System.out.print("No errors found\n");
                System.exit(0);
            }
            column = 0;
            curr_line++;
            System.out.println(line);
            line = line + "\n";
        }
        return line.charAt(column);
    }
}




class Token {
    public static final int semicolon = 0;
    public static final int period    = 1;
    public static final int plusop    = 2;
    public static final int minusop   = 3;
    public static final int timesop   = 4;
    public static final int divideop  = 5;
    public static final int lessthan  = 6;
    public static final int equalto   = 7;
    public static final int lparen    = 8;
    public static final int rparen    = 9;
    public static final int assignop  = 10;
    public static final int identifier= 11;
    public static final int number    = 12;
    public static final int ifk    	  = 13;
    public static final int thenk     = 14;
    public static final int elsek     = 15;
    public static final int end       = 16;
    public static final int repeat    = 17;
    public static final int until     = 18;
    public static final int read      = 19;
    public static final int write     = 20;


    private static String[] spelling = {
            ";", ".", "+", "-", "*", "/", "<","=", "(", ")", ":=",
            "identifier", "number", "if", "then", "else",
            "end", "repeat", "until", "read", "write"};

    public static String toString (int i) {
        if (i < 0 || i > number)
            return "";
        return spelling[i];
    }
}