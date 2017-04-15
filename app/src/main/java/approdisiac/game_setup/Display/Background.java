package approdisiac.game_setup.Display;

/**
 * Created by Brett on 2017-04-15.
 */

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Background
{
    private Bitmap image;
    private int x;

    public Background(Bitmap image)
    {
        this.image = image;
    }

    /** DRAW THE BACKGROUND */
    public void draw(Canvas canvas)
    {
        /** THIS X STUFF IS TO MAKE A SIDE SCROLLING BACKGROUND */
        x -= 15;
        if (x == -120)
        {
            x = 0;
        }
        canvas.drawBitmap(image, x, 0, null);
    }
}
