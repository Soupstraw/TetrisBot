package game.tetromino;

/**
 * Created by Joosep on 06-Oct-16.
 */
public class TetrominoBuilder {

    public enum TetrominoShape{
        T_4x1,
        T_2x2,
        T_S,
        T_Z,
        T_L,
        T_J,
        T_T
    }

    public Tetromino buildTetromino(TetrominoShape shape) {
        switch (shape) {
            case T_4x1:
                return new Tetromino(new int[][]{
                        {1, 1, 1, 1},				// The shape of a spawned block, make sure it is spawned with the right rotation
                },
                0,				// The coordinates of a spawned block
                0				// Use trial-and error to find the coordinates that match game spawns
                );
            case T_2x2:
                return new Tetromino(new int[][]{
                        {1, 1},
                        {1, 1}
                },
                0,
                0
                );
            case T_S:
                return new Tetromino(new int[][]{
                        {0, 1, 1},
                        {1, 1, 0}
                },
                0,
                0
                );
            case T_Z:
                return new Tetromino(new int[][]{
                        {1, 1, 0},
                        {0, 1, 1}
                },
                0,
                0
                );
            case T_L:
                return new Tetromino(new int[][]{
                        {0, 0, 1},
                        {1, 1, 1}
                },
                0,
                0
                );
            case T_J:
                return new Tetromino(new int[][]{
                        {1, 0, 0},
                        {1, 1, 1}
                },
                0,
                0
                );
            case T_T:
                return new Tetromino(new int[][]{
                        {1, 1, 1},
                        {0, 1, 0}
                },
                0,
                0
                );
        }
        return null;
    }

}
