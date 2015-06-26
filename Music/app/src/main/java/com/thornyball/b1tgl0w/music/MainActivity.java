package com.thornyball.b1tgl0w.music;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.media.AudioTrack;
import android.content.res.Resources;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //39030784 is the size in bytes of Disco Medusae.wav
        AudioTrack themeSong = new AudioTrack(AudioManager.STREAM_MUSIC, 44100,
                AudioFormat.CHANNEL_OUT_STEREO, AudioFormat.ENCODING_PCM_16BIT,
                39030784, AudioTrack.MODE_STATIC);
        byte [] themeSongBytes = new byte[39030784];
        try {
            InputStream is = getResources().openRawResource
                    (R.raw.discomedusae);
            int bytesRead = 0;
            int totalBytes = 0;
            while(bytesRead >= 0) {
                if(39030784 - 4096 >= totalBytes)
                    bytesRead = is.read(themeSongBytes, totalBytes, 4096);
                else
                    bytesRead = is.read(themeSongBytes, totalBytes, 39030784 - totalBytes);
                totalBytes += bytesRead;
            }
            themeSong.write(themeSongBytes, 0, totalBytes);
            themeSong.setLoopPoints(0,39030784 / 4,-1);
            themeSong.play();
        }
        catch(IOException e)
        {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
