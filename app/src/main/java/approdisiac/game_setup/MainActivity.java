package approdisiac.game_setup;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import approdisiac.game_setup.Game.Game;

public class MainActivity extends AppCompatActivity
{
    SharedPreferences mSharedPreferences;
    int highScore;

    /** APP STARTS */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        /** SET THE GAME CLASS AS THE MAIN ACTIVITY */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(new Game(this));

        /** YOU CAN USE THIS TO LOAD HIGH SCORES OR WHATEVER */
        mSharedPreferences = getSharedPreferences("highScoreTable", 0);
        highScore = mSharedPreferences.getInt("highScore", -1);
        if (highScore >= 0) Game.highScore = highScore;
    }

    /** APP STOPS */
    @Override
    protected void onStop()
    {
        /** YOU CAN USE THIS TO SAVE HIGH SCORES OR WHATEVER */
        mSharedPreferences.edit().putInt("highScore", Game.highScore).apply();
        super.onStop();
    }
}