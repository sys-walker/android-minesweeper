package com.eps.buscamines;

public class Cronometer implements Runnable {
    public static boolean running=true;

    @Override
    public void run() {
        while (running){
            if (Minesweeper.isOn){
                try {
                    Thread.sleep(1);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                Minesweeper.milis++;
                if (Minesweeper.milis==999){
                    Minesweeper.seg++;
                    Minesweeper.milis=0;
                }
                if (Minesweeper.seg==59){
                    Minesweeper.minutos++;
                    Minesweeper.seg=0;
                }
                Minesweeper.h.post(() -> {
                    String m="",s="",mi="";
                    if ((Minesweeper.milis<10)){
                        m="00"+ Minesweeper.milis;
                    }else if(Minesweeper.milis<100){
                        m="0"+ Minesweeper.milis;
                    }else{
                        m=""+ Minesweeper.milis;
                    }
                    if ((Minesweeper.seg<10)){
                        s="0"+ Minesweeper.seg;
                    }else{
                        s=""+ Minesweeper.seg;
                    }
                    if ((Minesweeper.minutos<10)){
                        mi="0"+ Minesweeper.minutos;
                    }else{
                        mi=""+ Minesweeper.minutos;
                    }
                    Minesweeper.crono.setText(mi+":"+s+":"+m);
                });
            }
        }
    }

}
