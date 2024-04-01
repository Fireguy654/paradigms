package expression.generic;

public class Main {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Amount of arguments is less than 2");
        }
        StringBuilder mode = new StringBuilder();
        for (int i = 1; i < args[0].length(); i++) {
            mode.append(args[0].charAt(i));
        }
        GenericTabulator tab = new GenericTabulator();
        Object[][][] ans = tab.tabulate(mode.toString(), args[1], -2, 2, -2, 2, -2, 2);
        for (int i = 0; i <= 4; i++) {
            System.out.println("-------Page " + (i - 2) + "-------");
            for (int j = 0; j <= 4; j++) {
                for (int k = 0; k <= 4; k++) {
                    System.out.print(ans[i][j][k] + "\t");
                }
                System.out.println();
            }
        }
    }
}
