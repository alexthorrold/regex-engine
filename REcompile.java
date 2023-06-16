// Alex Thorrold
// 1578693

public class REcompile {
    static char[] pattern;
    
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java REcompile <pattern>");
            System.exit(1);
        }

        pattern = args[0].toCharArray();
    }
}