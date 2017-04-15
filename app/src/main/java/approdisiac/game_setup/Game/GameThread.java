package approdisiac.game_setup.Game;

/**
 * Created by Brett on 2017-04-15.
 */
import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameThread extends Thread
{
    private int FPS = 30;
    private double avgFPS;
    private SurfaceHolder surfaceHolder;
    private Game game;
    private boolean running;
    public static Canvas canvas;

    public GameThread(SurfaceHolder surfaceHolder, Game game)
    {
        super();
        this.surfaceHolder = surfaceHolder;
        this.game = game;
    }

    /** THIS SETS THE FRAME RATE FOR THE GAME */
    @Override
    public void run()
    {
        long startTime;
        long timeMillis;
        long waitTime;
        long totalTime = 0;
        int frameCount = 0;
        long targetTime = 1000 / FPS;

        while(running)
        {
            startTime = System.nanoTime();
            canvas = null;

            try
            {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder)
                {
                    this.game.update();
                    this.game.draw(canvas);
                }
            } catch (Exception e) {}
            finally
            {
                if(canvas != null)
                {
                    try
                    {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
            timeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime-timeMillis;
            try
            {
                this.sleep(waitTime);
            } catch(Exception e){}

            totalTime += System.nanoTime() - startTime;
            frameCount++;
            if(frameCount == FPS)
            {
                avgFPS = 1000 / ((totalTime / frameCount) / 1000000);
                frameCount =0;
                totalTime = 0;
                //System.out.println(avgFPS);
            }
        }
    }
    public void setRunning(boolean b)
    {
        running = b;
    }
}

