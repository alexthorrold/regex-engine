// Alex Thorrold
// 1578693

public class REcompile {
    static String pattern;
    static int currentPos;

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java REcompile <pattern>");
            System.exit(1);
        }

        pattern = args[0];
        currentPos = 0;

        parseE();
    }

    private static void parseE() {
        parseT();
        while (currentPos < pattern.length()) {
            char c = pattern.charAt(currentPos);
            if (c == '|') {
                currentPos++;
                parseT();
            } else if (isLiteral(c) || c == '(' || c == '[' || c == '.') {
                parseT();
            } else {
                break;
            }
        }
    }

    private static void parseT() {
        parseF();
        while (currentPos < pattern.length()
                && (isLiteral(pattern.charAt(currentPos)) || pattern.charAt(currentPos) == '('
                        || pattern.charAt(currentPos) == '[' || pattern.charAt(currentPos) == '.')) {

            parseF();
        }
    }

    private static void parseF() {
        parseP();
        if (currentPos < pattern.length() && (pattern.charAt(currentPos) == '*' || pattern.charAt(currentPos) == '+'
                || pattern.charAt(currentPos) == '?')) {

            currentPos++;
        }
    }

    private static void parseP() {
        char c = pattern.charAt(currentPos);
        if (c == '(') {
            currentPos++;
            parseE();
            if (currentPos < pattern.length() && pattern.charAt(currentPos) == ')') {
                currentPos++;
            } else {
                throw new RuntimeException("Error: missing ')'");
                // System.exit(1);
            }
        } else if (c == '[') {
            currentPos++;
            parseS();
            if (pattern.charAt(currentPos) == ']') {
                currentPos++;
            } else {
                throw new RuntimeException("Error: missing ']'");
                // System.exit(1);
            }
        } else if (c == '.') {
            currentPos++;
        } else if (isLiteral(c)) {
            currentPos++;
        } else if (c == '\\') {
            currentPos++;
            if (currentPos < pattern.length()) {
                currentPos++;
            } else {
                throw new RuntimeException("Error: Expected a character after the escape character.");
                // System.exit(1);
            }
        } else {
            throw new RuntimeException("Error: invalid character " + c + " at position " + currentPos);
            // System.exit(1);
        }
    }

    private static void parseS() {
        if (isLiteral(pattern.charAt(currentPos))) {
            currentPos++;
            while (currentPos < pattern.length() && isLiteral(pattern.charAt(currentPos))) {
                currentPos++;
            }
        } else {
            throw new RuntimeException("Error: invalid character" + pattern.charAt(currentPos) + " at position " + currentPos);
            // System.exit(1);
        }
    }

    private static boolean isMetaCharacter(char c) {
        return c == '(' || c == ')' || c == '[' || c == ']' || c == '.' || c == '*' || c == '+' || c == '?' || c == '|';
    }

    private static boolean isLiteral(char c) {
        return !isMetaCharacter(c) && c != '\\';
    }
}