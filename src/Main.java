import it.kibo.fp.lib.RandomDraws;

public class Main {
    private static final int DIM = 5;
    public static void main(String[] args) {
        Elemento c1 = new Elemento();
        int matrix[][] = new int[DIM][DIM];
        c1.creaEquilibrio(matrix);
        System.out.print(matrix.toString());
    }
}