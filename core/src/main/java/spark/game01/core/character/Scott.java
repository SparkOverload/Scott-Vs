package spark.game01.core.character;


import playn.core.Key;
import playn.core.*;
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
        IDLE,RUN,WALK,JUMP,DODGE,ATTK1,ATTK2,ATTK3
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
                    case A:
                        state = State.ATTK1;
                        break;
                    case S:
                        state = State.ATTK2;
                        break;
                    case D:
                        state = State.ATTK3;
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
                    }
                    System.out.println("Scott Idle = "+spriteIndex);
                    break;
                case WALK:
                    if(!(spriteIndex>=16 && spriteIndex<=21)){
                        spriteIndex=16;
                    }
                    System.out.println("Scott Walk = "+spriteIndex);
                    break;
                case RUN:
                    if(!(spriteIndex>=8 && spriteIndex<=15)){
                        spriteIndex=8;
                    }
                    System.out.println("Scott Run = "+spriteIndex);
                    break;
                case JUMP:
                    if(!(spriteIndex>=22 && spriteIndex<=34)){
                        spriteIndex=22;
                    }
                    if(spriteIndex==34){
                        state = State.IDLE;
                    }
                    System.out.println("Scott Jump = "+spriteIndex);
                    break;
                case DODGE:
                    if(!(spriteIndex>=35 && spriteIndex<=40)){
                        spriteIndex=35;
                    }
                    if(spriteIndex==40){
                        state = State.IDLE;
                    }
                    System.out.println("Scott DODGE = "+spriteIndex);
                    break;
                case ATTK1:
                    if(!(spriteIndex>=41 && spriteIndex<=44)){
                        spriteIndex=41;
                    }
                    if(spriteIndex==44){
                        state = State.IDLE;
                    }
                    System.out.println("Scott ATTK1 = "+spriteIndex);
                    break;
                case ATTK2:
                    if(!(spriteIndex>=45 && spriteIndex<=50)){
                        spriteIndex=45;
                    }
                    if(spriteIndex==50){
                        state = State.IDLE;
                    }
                    System.out.println("Scott ATTK2 = "+spriteIndex);
                    break;
                case ATTK3:
                    if(!(spriteIndex>=51 && spriteIndex<=57)){
                        spriteIndex=51;
                    }
                    if(spriteIndex==57){
                        state = State.IDLE;
                    }
                    System.out.println("Scott ATTK3 = "+spriteIndex);
                    break;

            }
            sprite.setSprite(spriteIndex);
            spriteIndex++;
            e=0;
        }
    }

}
