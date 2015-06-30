package com.fancymans.thornyball;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * The surface of the game
 *
 * Created by Darrin on 6/26/2015.
 */
public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    // constants
    public static final int WIDTH = 856;
    public static final int HEIGHT = 480;
    public static final int BG_SHIFT = -2; // the amount to shift the background by each update
    public static boolean start = false;
    public static boolean pause = false;

    private MainThread thread;
    private Background bg;

    public GamePanel(Context context) {
        super(context);

        // add callback to surface holder to intercept events
        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);

        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while(retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.bg));

        thread.setRunning(true);
        thread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public void draw(Canvas canvas) {
        // scaling factor to draw to full screen size
        final float scaleFactorX = getWidth()  / (float)WIDTH;
        final float scaleFactorY = getHeight() / (float)HEIGHT;

        // check if canvas is null
        if (canvas != null) {
            final int savedState = canvas.save();       // save state before scaling canvas
            canvas.scale(scaleFactorX, scaleFactorY);   // scale the canvas
            bg.draw(canvas);                            // calling the canvas from Background class
            canvas.restoreToCount(savedState);          /* return back to unscaled state otherwise
                                                           would keep scaling up larger each time */
        }
    }

    public void update() {
        bg.update();
    }
}
