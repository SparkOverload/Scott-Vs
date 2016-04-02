package spark.game01.core.character;


import playn.core.Key;
import playn.core.Keyboard;
import playn.core.Layer;
import playn.core.PlayN;
import playn.core.util.Callback;
import spark.game01.core.sprite.Sprite;
import spark.game01.core.sprite.SpriteLoader;

public class Scott {

    private Sprite sprite1;
    private int spriteIndex = 0;
    private boolean hasLoaded = false;
    private int xx;
    private  int yy;

    public enum State{
        IDLE,RUN,WALK
    };

    private State state = State.RUN;

    private int e = 0;
    private int offset = 8;

    public Scott(final float x, final float y){


        PlayN.keyboard().setListener(new Keyboard.Adapter() {
            @Override
            public void onKeyUp(Keyboard.Event event) {
                if(event.key() == Key.SPACE) {
                    switch (state) {
                        case IDLE:
                            state = State.WALK;
                            break;
                        case RUN:
                            state = State.IDLE;
                            break;
                        case WALK:
                            state = State.RUN;
                            break;

                    }
                }
            }
        });


        sprite1 = SpriteLoader.getSprite("images/scott.json");
        sprite1.addCallback(new Callback<Sprite>() {
            @Override
            public void onSuccess(Sprite result) {
                sprite1.setSprite(spriteIndex);
                sprite1.layer().setOrigin(sprite1.width()/2f,sprite1.height()/2f);
                sprite1.layer().setTranslation(x,y+13f);
                hasLoaded=true;
            }

            @Override
            public void onFailure(Throwable cause) {
                PlayN.log().error("Error loading image!",cause);
            }
        });
    }


    public Layer layer(){
        return sprite1.layer();
    }

    public void update(int delta) {
        if(hasLoaded == false) return;

        e = e + delta;
        if(e > 100){
            switch (state) {
                case IDLE: offset = 0;
                    xx=8;
                    break;
                case WALK:
                    offset = 16;
                    xx=6;
                    yy=16;
                    break;
                case RUN:
                    offset = 8;
                    xx=8;
                    yy=8;
                    break;


            }
            if(state == State.IDLE){
                spriteIndex = offset + ((spriteIndex + 1) % xx);
            }else{
                spriteIndex = offset + (((spriteIndex + 1)%yy) % xx);
            }

           // System.out.println("= "+spriteIndex);
            sprite1.setSprite(spriteIndex);
            e=0;
        }
    }

}
