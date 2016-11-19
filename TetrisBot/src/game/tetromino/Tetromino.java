package game.tetromino;

/**
 * Created by Joosep on 06-Oct-16.
 */

public class Tetromino extends Object{

    private int[][] matrix;
    private int x, y;

    protected Tetromino(int[][] matrix, int x, int y){
        this.matrix = matrix;
        setX(x);
        setY(y);
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
    
    public void moveRight(){
    	x++;
    }
    
    public void moveLeft(){
    	x--;
    }
    
    public void moveDown(){
    	y++;
    }
    
    public void moveUp(){
    	y--;
    }
    
    public int getBlock(int x, int y){
        return matrix[x][y];
    }

    // Returns tetromino matrix
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
    
    public int getWidth(){
    	return matrix.length;
    }
    
    public int getHeight(){
    	return matrix[0].length;
    }

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public Tetromino clone(){
		Tetromino tet = new Tetromino(matrix.clone(), x, y);
		tet.setX(x);
		tet.setY(y);
		return tet;
	}
}