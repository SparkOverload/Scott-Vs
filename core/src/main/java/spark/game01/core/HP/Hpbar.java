package spark.game01.core.HP;


import playn.core.Layer;
import playn.core.PlayN;
import playn.core.util.Callback;
import playn.core.util.Clock;
import spark.game01.core.Screen.Gameplay00;
import spark.game01.core.sprite.Sprite;
import spark.game01.core.sprite.SpriteLoader;

public class Hpbar {

    private Sprite sprite;
    public int spriteIndex = 0;
    private boolean hasLoaded = false;

    public enum State{
        HP100,HP90,HP70,HP50,HP30,HP10,HP0
    };

    public State state = State.HP100;

    private int e = 0;

    public Hpbar(final float x, final float y){
        sprite = SpriteLoader.getSprite("images/hp/hp.json");
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
                case HP100:
                    if(spriteIndex!=0){
                      spriteIndex=0;
                    }
                    break;
                case HP90:
                    if(spriteIndex!=1){
                        spriteIndex=1;
                    }
                    break;
                case HP70:
                    if(spriteIndex!=2){
                        spriteIndex=2;
                    }
                    break;
                case HP50:
                    if(spriteIndex!=3){
                        spriteIndex=3;
                    }
                    break;
                case HP30:
                    if(spriteIndex!=4){
                        spriteIndex=4;
                    }
                    break;
                case HP10:
                    if(spriteIndex!=5){
                        spriteIndex=5;
                    }
                    break;
                case HP0:
                    if(spriteIndex!=6){
                        spriteIndex=6;
                    }
                    break;
            }
            sprite.setSprite(spriteIndex);
            spriteIndex++;
            e=0;
        }

        if(Gameplay00.score <= 90f){
            state=State.HP90;
        }
        if(Gameplay00.score <= 70f){
            state=State.HP70;
        }
        if(Gameplay00.score <= 50f){
            state=State.HP50;
        }
        if(Gameplay00.score <= 30f){
            state=State.HP30;
        }
        if(Gameplay00.score <= 10f){
            state=State.HP10;
        }
        if(Gameplay00.score <= 0f){
            state=State.HP0;
        }
    }

}
