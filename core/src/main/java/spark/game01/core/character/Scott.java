package spark.game01.core.character;


import playn.core.Key;
import playn.core.Keyboard;
import playn.core.Layer;
import playn.core.PlayN;
import playn.core.util.Callback;
import spark.game01.core.sprite.Sprite;
import spark.game01.core.sprite.SpriteLoader;

public class Scott {

    private Sprite sprite;
    private int spriteIndex = 0;
    private boolean hasLoaded = false;
    private int xx;
    private  int yy;


    public enum State{
        IDLE,RUN,WALK,JUMP,DODGE
    };

    private State state = State.IDLE;

    private int e = 0;
    private int offset = 8;

    public Scott(final float x, final float y){


        PlayN.keyboard().setListener(new Keyboard.Adapter() {
            @Override
            public void onKeyDown(Keyboard.Event event) {
                switch (event.key()) {
                    case UP:
                        state = State.IDLE;
                        break;
                    case RIGHT:
                        state = State.WALK;
                        break;
                    case DOWN:
                        state = State.DODGE;
                        break;
                    case SPACE:
                        state = State.JUMP;
                        break;
                }
            }
        });
        sprite = SpriteLoader.getSprite("images/scott.json");
        sprite.addCallback(new Callback<Sprite>() {
            @Override
            public void onSuccess(Sprite result) {
                sprite.setSprite(spriteIndex);
                sprite.layer().setOrigin(sprite.width()/2f,sprite.height()/2f);
                sprite.layer().setTranslation(x,y+13f);
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
        if(e > 70){
            switch (state) {
                case IDLE:
                    if(!(spriteIndex>=0 && spriteIndex<=7)){
                        spriteIndex=0;
                    }else{
                        offset = 0;
                        xx=8;
                        spriteIndex = offset + ((spriteIndex + 1) % xx);
                    }
                    System.out.println("Idle = "+spriteIndex);
                    break;
                case WALK:
                    if(!(spriteIndex>=16 && spriteIndex<=21)){
                        spriteIndex=16;
                    }else{
                        offset = 16;
                        xx=6;
                        yy=16;
                        spriteIndex = offset + (((spriteIndex + 1)%yy) % xx);
                    }
                    System.out.println("Walk = "+spriteIndex);
                    break;
                case RUN:
                    if(!(spriteIndex>=8 && spriteIndex<=15)){
                        spriteIndex=8;
                    }else{
                        offset = 8;
                        xx=8;
                        spriteIndex = offset + ((spriteIndex + 1) % xx);
                    }
                    System.out.println("Run = "+spriteIndex);
                    break;
                case JUMP:
                    if(!(spriteIndex>=22 && spriteIndex<=34)){
                        spriteIndex=22;
                    }else{
                        offset = 22;
                        yy=22;
                        xx=13;
                        spriteIndex = offset + (((spriteIndex + 1)%yy) % xx);
                    }
                    if(spriteIndex==34){
                        state = State.IDLE;
                    }
                    System.out.println("Jump = "+spriteIndex);
                    break;
                case DODGE:
                    if(!(spriteIndex>=35 && spriteIndex<=40)){
                        spriteIndex=35;
                    }else{
                        offset = 35;
                        yy=35;
                        xx=6;
                        spriteIndex = offset + (((spriteIndex + 1)%yy) % xx);
                    }
                    if(spriteIndex==40){
                        state = State.IDLE;
                    }
                    System.out.println("DODGE = "+spriteIndex);
                    break;

            }
            sprite.setSprite(spriteIndex);
            e=0;
        }
    }

}
