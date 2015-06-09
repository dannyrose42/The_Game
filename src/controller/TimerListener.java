package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimerListener implements ActionListener {

    public int mutaCount;
    
    public TimerListener(){
        mutaCount = 0;
    }
  
    @Override
    public void actionPerformed(ActionEvent ae) {      
        if(mutaCount == 0){
            Main.gameData.addMutalisk(2);
            mutaCount = 2;
        }
        else if(mutaCount == 4){
            Main.gameData.addMutalisk(1);
            Main.gameData.mutaTimer.stop();  
        }
        else{
            Main.gameData.addMutalisk(1);
            mutaCount++;   
            }
    }   
}
