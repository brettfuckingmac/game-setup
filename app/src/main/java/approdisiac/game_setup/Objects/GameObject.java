package approdisiac.game_setup.Objects;

/**
 * Created by Brett on 2017-04-15.
 */

import android.graphics.Canvas;
import android.graphics.Rect;

public abstract class GameObject {
    protected float x, y, velx, vely;

    /** ALL GAMEOBJECTS BOUND, TICK, AND DRAW DIFFERENTLY */
    public abstract Rect getBounds();
    public abstract void update();
    public abstract void draw(Canvas canvas);
    /** POSITION, VELOCITY, AND ID   SETTERS & GETTERS */
    public float getx() {return x;}
    public float gety() {return y;}
    public void setvelx(float velx) {this.velx = velx;}
    public void setvely(float vely) {this.vely = vely;}
    public float getvelx() {return velx;}
    public float getvely() {return vely;}
}