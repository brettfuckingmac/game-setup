package approdisiac.game_setup.Objects;

/**
 * Created by Brett on 2017-04-15.
 */

import android.graphics.Bitmap;

public class Animation {
    private Bitmap[] frames;
    private int currentFrame;

    public void setFrames(Bitmap[] frames)
    {
        this.frames = frames;
        currentFrame = 0;
    }

    public Bitmap getImage()
    {
        if (currentFrame < 0) return frames[currentFrame += frames.length];
        else return frames[currentFrame];
    }

    public void update()
    {
        currentFrame++;
        if(currentFrame == frames.length)
        {
            currentFrame = 0;
        }
    }
}
