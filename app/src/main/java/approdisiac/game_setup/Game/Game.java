package approdisiac.game_setup.Game;

/**
 * Created by Brett on 2017-04-15.
 */
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import approdisiac.game_setup.Display.Background;
import approdisiac.game_setup.Display.HUD;
import approdisiac.game_setup.Display.Menu;
import approdisiac.game_setup.Objects.Handler;
import approdisiac.game_setup.Objects.Character;
import approdisiac.game_setup.Objects.Spawner;
import approdisiac.game_setup.R;

public class Game extends SurfaceView implements SurfaceHolder.Callback
{
    private GameThread thread;
    private Background bg;
    private Handler handler;
    private Spawner spawner;
    private HUD hud;
    private Menu menu;
    private Character character;

    /** USE THESE TO GET THE SCREEN SIZE OF THE CURRENT DEVICE */
    public static final float screenWIDTH = Resources.getSystem().getDisplayMetrics().widthPixels;
    public static final float screenHEIGHT = Resources.getSystem().getDisplayMetrics().heightPixels;

    /** FOR SAVING / LOADING HIGH SCORE */
    public static int highScore;

    /** IF BACKGROUND IS BIG / MOVES, YOU NEED TO REFERENCE A PORTION OF THE BACKGROUND TO SET YOUR DISPLAY */
    private Bitmap backgroundIMG = BitmapFactory.decodeResource(getResources(), R.drawable.bg);
    private Bitmap backgroundSIZE = BitmapFactory.decodeResource(getResources(), R.drawable.screensize);

    /** FOR ANIMATIONS, MAKE A SPRITE SHEET AND THEN CUT IT INTO EASILY ACCESSED FRAMES */
    private Bitmap guyALLIMGS = BitmapFactory.decodeResource(getResources(), R.drawable.guy);
    /** BASED ON THE SPRITESHEET "guy.png" THERE ARE 8 FRAMES WITH THESE DIMENSIONS */
    private int numberOfFrames = 8, frameWidth = 80, frameHeight = 145;
    private Bitmap[] guyIMG = new Bitmap[numberOfFrames];

    /** GAME STATES FOR MENU / GAMEPLAY OR WHATEVER */
    public enum STATE
    {
        Menu,
        Game
    }
    public STATE gameState = STATE.Game;
    public static boolean paused = false, gameover = false;

    public Game(Context context)
    {
        super(context);
        getHolder().addCallback(this);

        thread = new GameThread(getHolder(), this);
        setFocusable(true);
    }

    /** UPDATES STATES OF ALL GAME OBJECTS */
    public void update()
    {
        if (!paused)
        {
            handler.update();
            spawner.update();
            hud.update();
            if (handler.getSize() > 0)
            {
                character = (Character) handler.getObject(0);
            }

            if (gameover)
            {

            }
        }
    }

    /** DRAWS ENTIRE GAME CANVAS */
    @Override
    public void draw(Canvas canvas)
    {
        /** USE THE SIZE OF YOUR BACKGROUND IMAGE TO SET THE SCALE TO FIT YOUR DEVICE SCREEN */
        /** IF YOURE BACKGROUND IS BIGGER THAN ONE SCREEN, MAKE A SEPARATE IMAGE TO REFERENCE */
        final float scaleFactorX = screenWIDTH / backgroundSIZE.getWidth();
        final float scaleFactorY = screenHEIGHT / backgroundSIZE.getHeight();

        if(canvas != null)
        {
            final int state = canvas.save();
            canvas.scale(scaleFactorX, scaleFactorY);
            bg.draw(canvas);
            if (gameState == STATE.Menu)
            {
                menu.draw(canvas);
            }
            else
            {
                handler.draw(canvas);
                hud.draw(canvas);
            }

            canvas.restoreToCount(state);
        }
    }

    /** USE THIS FOR TOUCH-SCREEN CONTROLS */
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        /** BASIC JUMP CONTROL, MAKES GUY JUMP IF YOU TOUCH THE SCREEN AND HES NOT ALREADY JUMPING */
        if (event.getAction() == MotionEvent.ACTION_DOWN && !character.isJumping())
        {
            character.setvely(-50);
            return true;
        }
        return super.onTouchEvent(event);
    }

    /** SET TOUCH LOCATION SOMEWHERE ON THE SCREEN */
    private boolean touchOver(float mx, float my, float x, float y, float width, float height)
    {
        if (mx > x && mx < x + width)
        {
            if (my > y && my < y + height)
            {
                return true;
            } else return false;
        } else return false;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        boolean retry = true;
        while(retry)
        {
            try
            {
                thread.setRunning(false);
                thread.join();
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        /** STARTS THE GAME THREAD */
        thread.setRunning(true);
        thread.start();

        /** INITIALIZE ALL OBJECT AND VIEW CONTROLLERS */
        bg = new Background(backgroundIMG);
        handler = new Handler();
        spawner = new Spawner(this, handler);
        hud = new HUD(this, handler);
        menu = new Menu(this);

        /** CUT THE SPRITESHEET INTO ANIMATION FRAMES FOR THE GUY RUNNING */
        for (int i = 0; i < numberOfFrames; i++)
        {
            guyIMG[i] = Bitmap.createBitmap(guyALLIMGS, i*frameWidth, 0, frameWidth, frameHeight);
        }
    }

    /** ALLOW THE ANIMATION FRAMES TO BE ACCESSED BY OTHER CLASSES */
    public Bitmap[] getIMG()
    {
        return guyIMG;
    }
}
