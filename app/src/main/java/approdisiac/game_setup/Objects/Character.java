package approdisiac.game_setup.Objects;

/**
 * Created by Brett on 2017-04-15.
 */

import android.graphics.Canvas;
import android.graphics.Rect;

import approdisiac.game_setup.Game.Game;

public class Character extends GameObject {
    private Game game;
    private Animation animation = new Animation();

    private int count;
    private boolean jumped = false;

    public Character(Game game, float x, float y) {
        this.x = x;
        this.y = y;
        this.game = game;

        animation.setFrames(this.game.getIMG());
    }

    public void update() {
        count++;
        if (count == 2) {
            animation.update();
            count = 0;
        }

        if (vely < 0 && !jumped) {
            jumped = true;
        }
        if (jumped) {
            y += vely;
            vely += 5;
        }

        if (y >= 360 && jumped) {
            jumped = false;
            y = 360;
        }
    }

    public boolean isJumping() {
        return jumped;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(animation.getImage().createScaledBitmap(animation.getImage(), 160, 290, false), x, y, null);
    }

    public Rect getBounds() {
        return new Rect(0, 0, 1, 1);
    }
}

