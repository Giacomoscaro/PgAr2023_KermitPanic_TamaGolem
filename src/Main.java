import it.kibo.fp.lib.RandomDraws;

public class Main {
    public static void main(String[] args) {
        Elemento c1 = new Elemento();
        int matrix[][] = new int[5][5];
        c1.creaEquilibrio(matrix);
        System.out.print(matrix.toString());
    }
}