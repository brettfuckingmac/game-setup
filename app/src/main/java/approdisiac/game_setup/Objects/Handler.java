package approdisiac.game_setup.Objects;

/**
 * Created by Brett on 2017-04-15.
 */

import android.graphics.Canvas;
import java.util.LinkedList;

public class Handler
{
    LinkedList<GameObject> object = new LinkedList<>();
    /** UPDATES GAME STATES AND DRAWS CANVAS FOR ALL OBJECTS */
    public void update()
    {
        for(int i = 0; i < object.size(); i++){
            GameObject tempObject = object.get(i);
            tempObject.update();
        }
    }

    public void draw(Canvas canvas)
    {
        for(int i = 0; i < object.size(); i++){
            GameObject tempObject = object.get(i);
            tempObject.draw(canvas);
        }
    }

    public int getSize()
    {
        return object.size();
    }
    public GameObject getObject(int i)
    {
        return object.get(i);
    }

    /** ADD/REMOVE SPECIFIC GAME OBJECT */
    public void addObject(GameObject object) {this.object.add(object);}
    public void removeObject(GameObject object) {this.object.remove(object);}
}
