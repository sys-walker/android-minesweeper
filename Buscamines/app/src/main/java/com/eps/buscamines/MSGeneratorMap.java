package com.eps.buscamines;

import java.util.*;

/**
 * @author pere
 * @version 0.1
 */
public class MSGeneratorMap{
    private final String[][] board;
    private final int SIZE;
    private final int NUMBOMBS;

    /**
     *  It creates an instance minesweeper map and empty map
     * @param size size of the map size*size
     * @param entropy general probability to find bombs
     */
    public MSGeneratorMap(int size,double entropy){
        this.SIZE=size;
        this.board=initBoard();
        this.NUMBOMBS = (int)Math.floor(entropy*SIZE*SIZE);
    }

    /**
     *  Inits the map with spaces or empty cell bombs
     * example with 3x3 size
     * [ , , ]
     * [ , , ]
     * [ , , ]
     * </pre>
     * @return empty map of bombs
     */
    private String[][] initBoard() {
        String[][] tmp=new String[this.SIZE][this.SIZE];
        for (String[] ae:
                tmp) {
            Arrays.fill(ae, " ");
        }
        return tmp;
    }

    /**
     * Generates a random map of bombs with clue tiles
     *
     * <pre>
     * example with 3x3 size and 0.15 entropy
     * [ , 1, B]
     * [ , 1, 1]
     * [ ,  ,  ]
     * </pre>
     * @return a map with bombs
     */
    void generate() {
        ArrayList<Point<Integer,Integer>> bomb_positions = new ArrayList<>(this.NUMBOMBS);

        int size=0;
        while ( size < this.NUMBOMBS) {
            int x=(int)Math.floor(Math.random()*this.SIZE);
            int y=(int)Math.floor(Math.random()*this.SIZE);
            if (!bomb_positions.contains(new Point<>(x,y))) {
                bomb_positions.add(new Point<>(x,y));
                size++;
            }
        }
      // [    ,     ,    ,    ]
      // [    ,cnrXY,    ,    ]
      // [    ,     ,Bomb,    ]
      // [    ,     ,    ,    ]
        for (Point<Integer,Integer> bomb:
                bomb_positions) {
            int cornerX = bomb.getX()-1;
            int cornerY= bomb.getY()-1;
            for (int i = cornerX; i < cornerX+3 ; i++) {
                for (int j = cornerY; j < cornerY+3; j++) {
                    if(i>=0 && i<SIZE && j>=0 && j< SIZE){
                        if ((new Point<>(i,j)).equals(bomb)){
                            board[i][j]="B";
                        }else {
                            if (!board[i][j].equals("B") && board[i][j].equals(" ")){
                                board[i][j] = "1";
                            }else if (!board[i][j].equals("B") && !board[i][j].equals(" ")){
                                int n = Integer.parseInt(board[i][j]);
                                n=n+1;
                                board[i][j] = String.valueOf(n);
                            }
                        }

                    }
                }
            }
        }

    }

    public String[][] getBoard() {
        return board;
    }

    public int getNUMBOMBS() {
        return NUMBOMBS;
    }

    public int getMapSize() {
        return SIZE*SIZE;
    }

    private static class Point<T,U>{
        private final T x;
        private final U y;
        public Point(T x, U y){
            this.x=x;
            this.y=y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Point)) return false;
            Point<?, ?> pair = (Point<?, ?>) o;
            return x.equals(pair.x) && y.equals(pair.y);
        }

        public T getX() {
            return x;
        }

        public U getY() {
            return y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "(" +
                    x +
                    "," + y +
                    ')';
        }
    }

    @Override
    public String toString() {
        StringBuilder retval= new StringBuilder();
        for(String [] ae: this.board){
            retval.append(Arrays.toString(ae)).append("\n");

        }
        return "MinesweeperMap{\n" +
                "board=\n" +retval.toString() +
                ", SIZE=" + SIZE +"\n"+
                ", NUMBOMBS=" + NUMBOMBS +"\n"+
                '}';
    }

    /**
     * to see an example
     * @param args - Non used

    public static void main(String[] args) {
        int SIZE=4;
        double entropy=0.15;
        MSGeneratorMap map = new MSGeneratorMap(SIZE,entropy);
        String[][] p = map.getBoard();
        System.out.println(map.toString());

    }
     */
}
