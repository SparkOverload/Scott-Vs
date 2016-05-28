package spark.game01.core.PauseText;

import playn.core.Layer;
import playn.core.PlayN;
import playn.core.util.Callback;
import spark.game01.core.sprite.Sprite;
import spark.game01.core.sprite.SpriteLoader;


public class Pauselayer {

    private Sprite sprite;
    public int spriteIndex = 0;
    private boolean hasLoaded = false;

    public enum State{
        ACTIVEP
    };

    public State state = State.ACTIVEP;

    private int e = 0;

    public Pauselayer(final float x, final float y){
        sprite = SpriteLoader.getSprite("images/pausel/pause.json");
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
                case ACTIVEP:
                    if(!(spriteIndex>=0&&spriteIndex<=1)){
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
