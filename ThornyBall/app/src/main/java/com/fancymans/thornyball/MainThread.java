package com.fancymans.thornyball;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * The main thread that runs the game
 *
 * Created by Darrin on 6/26/2015.
 */
public class MainThread extends Thread {
    private int FPS;
    private double averageFPS;
    private final SurfaceHolder surfaceHolder;
    private GamePanel gamePanel;
    private boolean running;
    public static Canvas canvas;

    // Constructor
    public MainThread(SurfaceHolder surfaceHolder, GamePanel gamePanel) {
        super();
        FPS = 60;
        averageFPS = 0;

        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }

    @Override
    public void run() {
        // 5093193
        long startTime;
        long timeMS;
        long waitTime;
        long totalTime = 0;
        int frameCount = 0;
        long targetTime = 1000/FPS;

        while(running) {
            startTime = System.nanoTime();
            canvas = null;

            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    this.gamePanel.update();
                    this.gamePanel.draw(canvas);
                }
            } catch (Exception e) {
                // System.out.println(e.getMessage());
            }
            finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            timeMS = (System.nanoTime()-startTime)/1000000;
            waitTime = targetTime-timeMS;

            try {
                this.sleep(waitTime);
            } catch (Exception e) {
                // System.out.println(e.getMessage());
            }

            totalTime += System.nanoTime()-startTime;
            frameCount++;

            if (frameCount == FPS) {
                averageFPS = 1000/((totalTime / frameCount)/1000000);
                frameCount = 0;
                totalTime = 0;
                System.out.println("Average FPS: " + averageFPS);
            }
        }
    }

    public void setRunning(boolean b) {
        this.running = b;
    }
}