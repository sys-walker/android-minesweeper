package com.eps.buscamines2.util;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.Objects;

public class MSGeneratorMap implements Parcelable {
    private String[] board;
    private Boolean[] nonCovered;
    private final int SIZE;
    private final int NUMBOMBS;
    private double entropy;

    public MSGeneratorMap(int size, double entropy) {
        this.SIZE=size;
        this.NUMBOMBS = (int)Math.floor(entropy*SIZE*SIZE);
        this.entropy=entropy;
    }

    public double get_percentage_mines() {

        return (this.entropy*100);
    }

    public int get_num_bombs() {
        return NUMBOMBS;
    }

    public int getSize() {
        return SIZE;
    }

    public int getFullSize() {
        return SIZE*SIZE;
    }

    public String[] getBoard() {
        return board;
    }

    public Boolean[] getNonCovered() {
        return nonCovered;
    }

    private Boolean[] initNonCovered() {
        Boolean[] nonCovered = new Boolean[this.SIZE*this.SIZE];
        for (int i = 0; i < this.SIZE*this.SIZE; i++) {
            nonCovered[i]=false;
        }
        return nonCovered;
    }

    private String[] initBoard() {
        String[] b = new String[this.SIZE*this.SIZE];
        int j=0;
        for (int i = 0; i < this.SIZE*this.SIZE; i++) {
            b[i]=" ";
            j++;
        }
        return b;
    }
    public MSGeneratorMap generate() {

        this.board=initBoard();
        this.nonCovered=initNonCovered();
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

                            board[(this.SIZE*i)+j]="B";
                        }else {
                            if (!board[(this.SIZE*i)+j].equals("B") && board[(this.SIZE*i)+j].equals(" ")){
                                board[(this.SIZE*i)+j] = "1";
                            }else if (!board[(this.SIZE*i)+j].equals("B") && !board[(this.SIZE*i)+j].equals(" ")){
                                int n = Integer.parseInt(board[(this.SIZE*i)+j]);
                                n=n+1;
                                board[(this.SIZE*i)+j] = String.valueOf(n);
                            }
                        }

                    }
                }
            }
        }
        return this;
    }
    public static class Point<T,U>{
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
            return "(" +x + "," + y + ')';
        }
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(this.board);
        dest.writeArray(this.nonCovered);
        dest.writeInt(this.SIZE);
        dest.writeInt(this.NUMBOMBS);
    }

    protected MSGeneratorMap(Parcel in) {
        this.board = in.createStringArray();
        this.nonCovered = (Boolean[]) in.readArray(Boolean[].class.getClassLoader());
        this.SIZE = in.readInt();
        this.NUMBOMBS = in.readInt();
    }

    public static final Creator<MSGeneratorMap> CREATOR = new Creator<MSGeneratorMap>() {
        @Override
        public MSGeneratorMap createFromParcel(Parcel source) {
            return new MSGeneratorMap(source);
        }

        @Override
        public MSGeneratorMap[] newArray(int size) {
            return new MSGeneratorMap[size];
        }
    };
}
