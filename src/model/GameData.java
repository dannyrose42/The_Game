package model;

import controller.Main;
import controller.TimerListener;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.swing.Timer;
import view.GamePanel;
import view.MainWindow;

public class GameData {

    private final int SIZE = 50;
    public final List<GameFigure> enemys;
    public final List<GameFigure> deadEnemys;
    public final List<GameFigure> bullets;
    public final List<GameFigure> deadBullets;
    public final Marine marine;
    public Timer mutaTimer, bossTimer;
    public TimerListener timerListener;
    public Thread gameThread;
    public Boss boss;

    public GameData() {
        enemys = Collections.synchronizedList(new ArrayList<GameFigure>());
        deadEnemys = Collections.synchronizedList(new ArrayList<GameFigure>());
        bullets = Collections.synchronizedList(new ArrayList<GameFigure>());
        deadBullets = Collections.synchronizedList(new ArrayList<GameFigure>());
        
        timerListener = new TimerListener();
        mutaTimer = new Timer(10000, timerListener);
        mutaTimer.setInitialDelay(3000);
        
        bossTimer = new Timer(3000, timerListener);
        bossTimer.setRepeats(false);
        
        gameThread = new Thread(Main.animator);
        
        marine = new Marine(GamePanel.PWIDTH / 2, GamePanel.PHEIGHT - 30, 30);
       
    }

    public void addMutalisk(int n) {
        Random r = new Random();
         synchronized (enemys) {
            for (int i = 0; i < n; i++) {
                enemys.add(new Mutalisk(r.nextInt(GamePanel.PWIDTH),
                        r.nextInt(GamePanel.PHEIGHT), SIZE));
            }
        }
    }
    
    public void addBoss(){
        synchronized (enemys){
            boss = new Boss(30, 30, 250, 115);
            enemys.add(boss);
        }
    }
    public void addMarineBullet(double x1, double y1, double x2, double y2, Color color) {
        synchronized (bullets) {
                bullets.add(new MarineBullet(x1, y1, x2, y2, color));
        }
    }
    
    public void addEnemyBullet(double x1, double y1, double x2, double y2, Color color) {
        synchronized (bullets) {
                bullets.add(new EnemyBullet(x1, y1, x2, y2, color));
        }
    }

    public void checkGameCondition(){
        if(enemys.isEmpty() && !mutaTimer.isRunning()){
            addBoss();
            mutaTimer.restart();
        }
        if (marine.health <= 0){
            Main.animator.running = false;
            Main.gameOverWindow.setOutcomeText("loose");
            Main.gameOverWindow.setVisible(true);
        }
        if ((boss != null) && (boss.health <= 0)){
            Main.animator.running = false;
            boss = null;
            Main.gameOverWindow.setOutcomeText("win");
            Main.gameOverWindow.setVisible(true);
        }
            
    }
    
    
    public void update() {
       
        marine.update();
        
        synchronized (bullets) {
            for (GameFigure b : bullets) {
                b.update();
                if(b.hit==true 
                        || b.x < 0
                        || b.x > GamePanel.PWIDTH
                        || b.y > GamePanel.PHEIGHT
                        || b.y <0)deadBullets.add(b);
            }
        }       
        synchronized (enemys) {
            for (GameFigure f : enemys) {
                f.update();
                if(f.health <=0)deadEnemys.add(f);
            }
        }
      
        enemys.removeAll(deadEnemys);
        bullets.removeAll(deadBullets);
        deadBullets.clear();
        deadEnemys.clear();
        int bulletCount = bullets.size();
        MainWindow.message.setText("Bullets:" + bulletCount + mutaTimer.isRunning());
        checkGameCondition();
    }
}
      

