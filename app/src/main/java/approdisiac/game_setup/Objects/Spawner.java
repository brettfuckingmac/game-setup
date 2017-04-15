package approdisiac.game_setup.Objects;

/**
 * Created by Brett on 2017-04-15.
 */
import approdisiac.game_setup.Game.Game;

public class Spawner
{
    private Game game;
    private Handler handler;

    public Spawner(Game game, Handler handler){
        this.game = game;
        this.handler = handler;
    }

    public void update()
    {
        if (handler.getSize() == 0)
        {
            handler.addObject(new Character(game, 30, 360));
        }
    }
}