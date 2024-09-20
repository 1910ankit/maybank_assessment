
public class ArrArgs {
    public static void main(String[] args) {
            if (args.length == 0) {
                System.err.println("No arguments provided.");
                return;
            }

            for (int k = 0; k < args.length; k++) {
                try {
                    System.out.println("Value of input is " + k + " and arguments " + args[k]);
                } catch (ArrayIndexOutOfBoundsException errorOc) {
                    System.err.println("Error occurred: " + errorOc.toString());
                }
            }
        }
}