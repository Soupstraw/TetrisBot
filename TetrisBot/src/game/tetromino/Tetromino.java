package game.tetromino;

/**
 * Created by Joosep on 06-Oct-16.
 */

public class Tetromino {

    private int[][] matrix;

    Tetromino(int[][] matrix){
        this.matrix = matrix;
    }

    public final void rotateClockwise(){
        int[][] newMatrix = new int[matrix[0].length][matrix.length];

        for(int y = 0; y < matrix[0].length; y++){
            for(int x = 0; x < matrix.length; x++){
                newMatrix[newMatrix.length - y - 1][x] = matrix[x][y];
            }
        }

        matrix = newMatrix;
    }

    public int getBlock(int x, int y){
        return matrix[x][y];
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(int y = 0; y < matrix[0].length; y++){
            for (int[] aMatrix : matrix) {
                sb.append((aMatrix[y]) + " ");
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}