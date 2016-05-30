package spark.game01.core.character.Ramona_char;

import playn.core.Layer;
import playn.core.PlayN;
import playn.core.util.Callback;
import spark.game01.core.sprite.Sprite;
import spark.game01.core.sprite.SpriteLoader;


/**
 * Created by SPARK.O on 5/30/2016.
 */
public class Ramona {

    private Sprite sprite;
    public int spriteIndex = 0;
    private boolean hasLoaded = false;

    public enum State{
        IDLE
    };

    public State state = State.IDLE;
    private int e = 0;

    public Ramona(final float x, final float y){
        sprite = SpriteLoader.getSprite("images/Ramona/ramona.json");
        sprite.addCallback(new Callback<Sprite>() {
            @Override
            public void onSuccess(Sprite result) {
                sprite.setSprite(spriteIndex);
                sprite.layer().setOrigin(sprite.width()/2f,sprite.height()/2f);
                sprite.layer().setTranslation(x,y);
                hasLoaded=true;
            }

            @Override
            public void onFailure(Throwable cause) {
                PlayN.log().error("Error loading image!",cause);
            }
        });

    }

    public Layer layer(){
        return sprite.layer();
    }

    public void update(int delta) {
        if(hasLoaded == false) return;
        e = e + delta;
        if(e > 60){
            switch (state) {
                case IDLE:
                    if(!(spriteIndex>=0&&spriteIndex<=5)){
                        spriteIndex=0;
                    }
                    break;
            }
            sprite.setSprite(spriteIndex);
            spriteIndex++;
            e=0;
        }
    }
}
