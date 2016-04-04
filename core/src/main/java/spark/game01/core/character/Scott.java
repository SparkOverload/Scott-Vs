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
        IDLE,RUN,WALK,JUMP,DODGE,ATTK1,ATTK2,ATTK3,
        KICK1,KICK2,JKICK,ULTIK,ULTIB1,ULTIB2,CHARGE,
        DEF,CEL1,CEL2,CEL3,GUITAR,HEADBUTT,LOSE,COMEBACK,
        WASATK1,WASATK2,WASATK3,SLEEP
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
                    case NP0:
                        state = State.RUN;
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
                    case Z:
                        state = State.ATTK3;
                        break;
                    case D:
                        state = State.DEF;
                        break;
                    case NP1:
                        state = State.CEL1;
                        break;
                    case NP2:
                        state = State.CEL2;
                        break;
                    case NP3:
                        state = State.CEL3;
                        break;
                    case NP5:
                        state = State.GUITAR;
                        break;
                    case K:
                        state = State.ULTIK;
                        break;
                    case L:
                        state = State.KICK1;
                        break;
                    case SEMICOLON:
                        state = State.KICK2;
                        break;
                    case I:
                        state = State.JKICK;
                        break;
                    case O:
                        state = State.ULTIB1;
                        break;
                    case P:
                        state = State.ULTIB2;
                        break;
                    case C:
                        state = State.CHARGE;
                        break;
                    case H:
                        state = State.HEADBUTT;
                        break;
                    case NP7:
                        state = State.LOSE;
                        break;
                    case B:
                        state = State.COMEBACK;
                        break;
                    case NP8:
                        state = State.WASATK1;
                        break;
                    case NP9:
                        state = State.WASATK2;
                        break;
                    case NP6:
                        state = State.WASATK3;
                        break;
                    case T:
                        state = State.SLEEP;
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
        if(e > 60){
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
                case DEF:
                    if(!(spriteIndex>=58 && spriteIndex<=64)){
                        spriteIndex=58;
                    }
                    if(spriteIndex==64){
                        state = State.IDLE;
                    }
                    System.out.println("Scott DEF = "+spriteIndex);
                    break;
                case CEL2:
                    if(!(spriteIndex>=65 && spriteIndex<=79)){
                        spriteIndex=65;
                    }
                    if(spriteIndex==79){
                        state = State.IDLE;
                    }
                    System.out.println("Scott Cel2 = "+spriteIndex);
                    break;
                case GUITAR:
                    if(!(spriteIndex>=80 && spriteIndex<=85)){
                        spriteIndex=80;
                    }
                    System.out.println("Scott Guitar = "+spriteIndex);
                    break;
                case CEL1:
                    if(!(spriteIndex>=86 && spriteIndex<=92)){
                        spriteIndex=86;
                    }
                    if(spriteIndex==92){
                        state = State.IDLE;
                    }
                    System.out.println("Scott CEL1 = "+spriteIndex);
                    break;
                case CEL3:
                    if(!(spriteIndex>=93 && spriteIndex<=110)){
                        spriteIndex=93;
                    }
                    if(spriteIndex==110){
                        state = State.IDLE;
                    }
                    System.out.println("Scott Cel3 = "+spriteIndex);
                    break;
                case ULTIK:
                    if(!(spriteIndex>=111 && spriteIndex<=122)){
                        spriteIndex=111;
                    }
                    if(spriteIndex==122){
                        state = State.IDLE;
                    }
                    System.out.println("Scott Ultikick = "+spriteIndex);
                    break;
                case KICK1:
                    if(!(spriteIndex>=123 && spriteIndex<=129)){
                        spriteIndex=123;
                    }
                    if(spriteIndex==129){
                        state = State.IDLE;
                    }
                    System.out.println("Scott kick1 = "+spriteIndex);
                    break;
                case KICK2:
                    if(!(spriteIndex>=130 && spriteIndex<=136)){
                        spriteIndex=130;
                    }
                    if(spriteIndex==136){
                        state = State.IDLE;
                    }
                    System.out.println("Scott kick2 = "+spriteIndex);
                    break;
                case JKICK:
                    if(!(spriteIndex>=137 && spriteIndex<=143)){
                        spriteIndex=137;
                    }
                    if(spriteIndex==143){
                        state = State.IDLE;
                    }
                    System.out.println("Scott jumpkick = "+spriteIndex);
                    break;
                case ULTIB1:
                    if(!(spriteIndex>=144 && spriteIndex<=155)){
                        spriteIndex=144;
                    }
                    if(spriteIndex==155){
                        state = State.IDLE;
                    }
                    System.out.println("Scott ULTIBOOM1 = "+spriteIndex);
                    break;
                case ULTIB2:
                    if(!(spriteIndex>=156 && spriteIndex<=174)){
                        spriteIndex=156;
                    }
                    if(spriteIndex==174){
                        state = State.IDLE;
                    }
                    System.out.println("Scott ULTIBOOM2 = "+spriteIndex);
                    break;
                case CHARGE:
                    if(!(spriteIndex>=175 && spriteIndex<=191)){
                        spriteIndex=175;
                    }
                    if(spriteIndex==191){
                        state = State.IDLE;
                    }
                    System.out.println("Scott ULTIBOOM2 = "+spriteIndex);
                    break;
                case HEADBUTT:
                    if(!(spriteIndex>=192 && spriteIndex<=201)){
                        spriteIndex=192;
                    }
                    if(spriteIndex==201){
                        state = State.IDLE;
                    }
                    System.out.println("Scott HEADBUTT = "+spriteIndex);
                    break;
                case LOSE:
                    if(!(spriteIndex>=202 && spriteIndex<=205)){
                        spriteIndex=202;
                    }
                    System.out.println("Scott lose = "+spriteIndex);
                    break;
                case COMEBACK:
                    if(!(spriteIndex>=206 && spriteIndex<=212)){
                        spriteIndex=206;
                    }
                    if(spriteIndex==212){
                        state = State.IDLE;
                    }
                    System.out.println("Scott Comeback = "+spriteIndex);
                    break;
                case WASATK1:
                    if(!(spriteIndex>=213 && spriteIndex<=217)){
                        spriteIndex=213;
                    }
                    if(spriteIndex==217){
                        state = State.IDLE;
                    }
                    System.out.println("Scott wasatkf = "+spriteIndex);
                    break;
                case WASATK2:
                    if(!(spriteIndex>=218 && spriteIndex<=221)){
                        spriteIndex=218;
                    }
                    if(spriteIndex==221){
                        state = State.IDLE;
                    }
                    System.out.println("Scott wasatkb = "+spriteIndex);
                    break;
                case WASATK3:
                    if(!(spriteIndex>=222 && spriteIndex<=235)){
                        spriteIndex=222;
                    }
                    if(spriteIndex==235){
                        state = State.SLEEP;
                    }
                    System.out.println("Scott wasatk3 = "+spriteIndex);
                    break;
                case SLEEP:
                    if(!(spriteIndex>=236 && spriteIndex<=237)){
                        spriteIndex=236;
                    }
                    System.out.println("Scott sleep = "+spriteIndex);
                    break;

            }
            sprite.setSprite(spriteIndex);
            spriteIndex++;
            e=0;
        }
    }

}
