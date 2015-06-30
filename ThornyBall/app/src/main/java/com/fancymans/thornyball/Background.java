package com.fancymans.thornyball;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * A class for the background image of the game
 *
 * Created by Darrin on 6/28/2015.
 */
public class Background {
    private Bitmap image;       // the bg image
    private int x, y;           // coords to place bitmap

    // Constructor
    public Background(Bitmap bm) {
        image = bm;
        x = 0;
        y = 0;
    }

    // Update func
    public void update() {
        // check if game is started
        if (GamePanel.start) {
            // check if game is not paused
            if (!GamePanel.pause) {
                // slide background image
                x += GamePanel.BG_SHIFT;
                // if bg shifted off the screen
                if (x < -GamePanel.WIDTH) {
                    x = 0;
                }
            }
        }
    }

    // Draw func
    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, y, null);

        if (x < 0) {
            canvas.drawBitmap(image, x+GamePanel.WIDTH, y, null);
        }

    }
}
