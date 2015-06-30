package com.fancymans.thornyball;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;


public class Game extends Activity {

    // buttons in the game
    private Button startBtn;
    private Button pauseBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // get rid of the title
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // set the game to be full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        FrameLayout game = new FrameLayout(this);       // the game
        GamePanel gameView = new GamePanel(this);       // view of the game
        LinearLayout widgets = new LinearLayout(this);  // widget handler

        // Button params
        LinearLayout.LayoutParams startBtnLayout = new LinearLayout.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams pauseBtnLayout = new LinearLayout.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);

        // Display
        Display d = getWindowManager().getDefaultDisplay();
        Point res = new Point();

        // get the resolution of the display and store it in res
        d.getSize(res);

        // create start button
        startBtn = new Button(this);
        startBtn.setText("Start");
        startBtnLayout.leftMargin = res.x / 2 - 150;
        startBtnLayout.topMargin = res.y / 2 + 100;
        startBtnLayout.width = 300;
        startBtnLayout.height = 100;
        startBtn.setLayoutParams(startBtnLayout);

        // create pause button
        pauseBtn = new Button(this);
        pauseBtn.setText("||");
        pauseBtnLayout.leftMargin = res.x - 100;
        pauseBtnLayout.topMargin = 0;
        pauseBtnLayout.width = 100;
        pauseBtnLayout.height = 100;
        pauseBtn.setLayoutParams(pauseBtnLayout);
        pauseBtn.setVisibility(View.GONE);              // hide it on start

        widgets.addView(startBtn);                      // add start button to widgets
        widgets.addView(pauseBtn);                      // add pause button to widgets
        game.addView(gameView);                         // add widgets to the game
        game.addView(widgets);                          // add the gameView to the game

        // create a listener for the startButton
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GamePanel.start = true;
                startBtn.setVisibility(View.GONE);
                System.out.println("Game started");
                pauseBtn.setVisibility(View.VISIBLE);   // show pause button once game starts
            }
        });

        // create a listener for the pauseButton
        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (GamePanel.pause) {
                    System.out.println("Game resumed");
                    GamePanel.pause = false;    // resume the game
                } else {
                    System.out.println("Game paused");
                    GamePanel.pause = true;     // pause the game
                }
            }
        });

        setContentView(game);                           // update view
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
