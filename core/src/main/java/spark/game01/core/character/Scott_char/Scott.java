package spark.game01.core.character.Scott_char;

import com.sun.javafx.css.StyleCache;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.contacts.Contact;
import playn.core.Keyboard;
import playn.core.Layer;
import playn.core.PlayN;
import playn.core.util.Callback;
import playn.core.util.Clock;
import spark.game01.core.Screen.*;
import spark.game01.core.character.Gideon_char.Gideon;
import spark.game01.core.character.Matt_char.Matt;
import spark.game01.core.character.Tom_char.Tom;
import spark.game01.core.sprite.Sprite;
import spark.game01.core.sprite.SpriteLoader;
import tripleplay.game.ScreenStack;

import java.util.Random;

public class Scott {

    private Sprite sprite;
    public int spriteIndex = 0;
    private boolean hasLoaded = false;
    private  int eventstate;
    private  int rcount=10;
    private  int lcount=10;
    public Body body;
    public Body other;
    public Boolean contacted = false;
    private ScreenStack ss;

    public enum State{
        IDLE,LIDLE,RUN,LRUN,WALK,LWALK,JUMP,LJUMP,DODGE,LDODGE,ATTK1,ATTK2,ATTK3,
        LATTK1,LATTK2,LATTK3,KICK1,LKICK1,KICK2,LKICK2,JKICK,LJKICK,ULTIK,LULTIK,
        ULTIB1,LULTIB1,ULTIB2,LULTIB2,CHARGE,LCHARGE,
        DEF,LDEF,CEL1,CEL2,CEL3,GUITAR,HEADBUTT,LHEADBUTT,LOSE,COMEBACK,LCOMEBACK,
        WASATK1,LWASATK1,WASATK2,LWASATK2,WASATK3,LWASATK3,SLEEP,LSLEEP,COOLDOWN,LCOOLDOWN
    };
    public State state = State.IDLE;
    private int e = 0;

    public Scott(final ScreenStack ss,final World world,final float x, final float y){
        this.ss =ss;
        PlayN.keyboard().setListener(new Keyboard.Adapter() {
            @Override
            public void onKeyDown(Keyboard.Event event) {
               switch (event.key()) {
                    case UP:
                        if(state == State.SLEEP){
                            state = State.COMEBACK;
                        }
                        if(state == State.LSLEEP){
                            state = State.LCOMEBACK;
                        }
                        if(state == State.ATTK2 && Gameplay00.spscott>=80){
                            state = State.ULTIB1;
                            Gameplay00.spscott-=80;
                        }
                        if(state == State.LATTK2 && Gameplay00.spscott>=80){
                            state = State.LULTIB1;
                            Gameplay00.spscott-=80;
                        }
                        break;
                   case LEFT:
                       if(lcount<3&& state == State.LIDLE){
                           state = State.LRUN;
                       }
                       if(state == State.LIDLE || state == State.IDLE || state == State.WALK){
                           state = State.LWALK;
                           lcount=10;
                           rcount=10;
                       }
                       break;
                    case RIGHT:
                        if(rcount<3 && state == State.IDLE){
                            state = State.RUN;
                        }
                        if(state == State.IDLE || state == State.LIDLE || state == State.LWALK){
                            state = State.WALK;
                            lcount=10;
                            rcount=10;
                        }
                        break;
                    case DOWN:
                        if(state == State.IDLE || state == State.LIDLE){
                            eventstate=1;
                        }
                        break;
                    case SPACE:
                        if(state == State.WALK || state == State.RUN || state == State.IDLE){
                            state = State.JUMP;
                        }
                        if(state == State.JUMP && (spriteIndex >= 26 && spriteIndex <=30) && Gameplay00.spscott>=50){
                            state=State.ULTIK;
                            Gameplay00.spscott-=50;
                        }
                        if(state == State.LJUMP && (spriteIndex >= 264 && spriteIndex <=268) && Gameplay00.spscott>=50){
                            state=State.LULTIK;
                            Gameplay00.spscott-=50;
                        }
                        if(state == State.SLEEP){
                            state = State.COMEBACK;
                        }
                        if(state == State.LSLEEP){
                            state = State.LCOMEBACK;
                        }
                        if(state == State.LWALK || state == State.LRUN || state == State.LIDLE){
                            state = State.LJUMP;
                        }
                        break;
                    case A:
                            if (eventstate == 1 && state == State.IDLE && Gameplay00.spscott>=40) {
                                state = State.HEADBUTT;
                                Gameplay00.spscott-=40;
                                eventstate = 0;
                            }
                            if (eventstate == 1 && state == State.LIDLE && Gameplay00.spscott>=40) {
                                state = State.LHEADBUTT;
                                Gameplay00.spscott-=40;
                                eventstate = 0;
                            }
                            if (state == State.ATTK2 && (spriteIndex >= 48 && spriteIndex <= 50)) {
                                state = State.ATTK3;
                            }
                            if (state == State.ATTK1) {
                                state = State.ATTK2;
                            }
                            if (state == State.IDLE || state == State.WALK || state == State.RUN) {
                                    state = State.ATTK1;
                            }
                            if (state == State.LATTK2 && (spriteIndex >= 305 && spriteIndex <= 307)) {
                            state = State.LATTK3;
                             }
                            if (state == State.LATTK1) {
                            state = State.LATTK2;
                             }
                            if (state == State.LIDLE || state == State.LWALK || state == State.LRUN) {
                            state = State.LATTK1;
                            }
                        break;
                    case S:
                        if(state == State.JUMP && Gameplay00.spscott>=15){
                            state = State.JKICK;
                            Gameplay00.spscott-=15;
                        }
                        if(state == State.LJUMP && Gameplay00.spscott>=15){
                            state = State.LJKICK;
                            Gameplay00.spscott-=15;
                        }
                        if(state == State.KICK1 && (spriteIndex>=127 && spriteIndex<=129)){
                            state = State.KICK2;
                        }
                        if(state == State.IDLE || state == State.WALK || state == State.RUN){
                            state = State.KICK1;
                        }

                        if(state == State.LKICK1 && (spriteIndex>=319 && spriteIndex<=321)){
                            state = State.LKICK2;
                        }
                        if(state == State.LIDLE || state == State.LWALK || state == State.LRUN){
                            state = State.LKICK1;
                        }
                        if(state == State.ATTK2 && Gameplay00.spscott>=80){
                            state=State.ULTIB2;
                            Gameplay00.spscott-=80;
                        }
                        if(state == State.LATTK2 && Gameplay00.spscott>=80){
                            state=State.LULTIB2;
                            Gameplay00.spscott-=80;
                        }
                        if(state == State.ATTK1 && Gameplay00.spscott>=50){
                            state=State.CHARGE;
                            Gameplay00.spscott-=50;
                        }
                        if(state == State.LATTK1 && Gameplay00.spscott>=50){
                            state=State.LCHARGE;
                            Gameplay00.spscott-=50;
                        }
                        break;
                    case D:
                        if(state == State.IDLE || state == State.WALK || state == State.RUN ){
                            state = State.DEF;
                        }
                        if(state == State.LIDLE || state == State.LWALK || state == State.LRUN ){
                            state = State.LDEF;
                        }
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
                    case NP7:
                        state = State.LOSE;
                        break;
                    case NP8:
                        state = State.LWASATK1;
                        break;
                    case NP9:
                        state = State.WASATK3;
                        break;
                    case NP6:
                        state = State.LWASATK3;
                        break;
                    case ENTER:
                        if(state==State.CEL1){
                            MyGame.world01.stop();
                            ss.remove(ss.top());
                            ss.push(new Gameplay01(ss));
                        }else if(state==State.CEL2){
                            MyGame.world02.stop();
                            ss.remove(ss.top());
                            ss.push(new Gameplay02(ss));
                        }else if(state==State.CEL3){
                            MyGame.world03.stop();
                            ss.remove(ss.top());
                            ss.push(new TopScore(ss));
                        }
                        break;
                   case ESCAPE:
                       MyGame.world01.stop();
                       MyGame.world02.stop();
                       MyGame.world03.stop();
                       ss.remove(ss.top());
                       ss.push(new HomeScreen(ss));
                       break;
               }
            }

            @Override
            public void onKeyUp(Keyboard.Event event){
                switch (event.key()) {
                    case RIGHT:
                        if(state == State.WALK){
                            state = State.IDLE;
                            rcount=0;
                        }
                        if(state == State.RUN){
                            state = State.IDLE;
                        }
                        break;
                    case D:
                        if(state == State.DEF){
                            state = State.IDLE;
                        }
                        if(state == State.LDEF){
                            state = State.LIDLE;
                        }
                        break;
                    case LEFT:
                        if(state == State.LWALK){
                            state = State.LIDLE;
                            lcount=0;
                        }
                        if(state == State.LRUN){
                            state = State.LIDLE;
                        }
                        break;
                }
            }
        });


        sprite = SpriteLoader.getSprite("images/scott_all/scott.json");
        sprite.addCallback(new Callback<Sprite>() {
            @Override
            public void onSuccess(Sprite result) {
                sprite.setSprite(spriteIndex);
                sprite.layer().setOrigin(sprite.width()/2f,(sprite.height()/2f) +50);
                sprite.layer().setTranslation(x,y);

                body = initPhysicsBody(world,
                        Gameplay00.M_PER_PIXEL * x,
                        Gameplay00.M_PER_PIXEL * y);
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

    private Body initPhysicsBody(World world,float x,float y){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position = new Vec2(0,0);
        Body body = world.createBody(bodyDef);

        Vec2[] vertices = {
                new Vec2(0.0f, - 2f),
                new Vec2(+ 1.0f, + 1.0f),
                new Vec2(- 1.0f, + 1.0f)
        };

        PolygonShape shape = new PolygonShape();
        shape.set(vertices, vertices.length);
//        shape.setAsBox((sprite.layer().width()-80)* Gameplay00.M_PER_PIXEL/2,
//                (sprite.layer().height()-90)*Gameplay00.M_PER_PIXEL/2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 2.3f;
        fixtureDef.friction = 1f;

        body.createFixture(fixtureDef);
        body.setLinearDamping(0.2f);
        body.setTransform(new Vec2(x,y),0f);
        body.setFixedRotation(true);
        return body;
    }

    public void update(int delta ,Matt matt) {
        if(hasLoaded == false) return;
        e = e + delta;
        if(e > 60){
            switch (state) {
                case IDLE:
                    if(!(spriteIndex>=0 && spriteIndex<=3)){
                        spriteIndex=0;
                    }
                    rcount++;
                    break;
                case WALK:
                    if(!(spriteIndex>=16 && spriteIndex<=21)){
                        spriteIndex=16;
                    }
                    break;
                case RUN:
                    if(!(spriteIndex>=8 && spriteIndex<=15)){
                        spriteIndex=8;
                    }
                    break;
                case JUMP:
                    if(!(spriteIndex>=22 && spriteIndex<=34)){
                        spriteIndex=22;
                    }
                    if(spriteIndex>=32&&spriteIndex<=33){
                        spriteIndex=32;
                    }
                    break;
                case DODGE:
                    if(!(spriteIndex>=35 && spriteIndex<=40)){
                        spriteIndex=35;
                    }
                    if(spriteIndex==40){
                        state = State.IDLE;
                    }
                    break;
                case ATTK1:
                    if(!(spriteIndex>=41 && spriteIndex<=44)){
                        spriteIndex=41;
                    }
                    if(spriteIndex==44){
                        state = State.IDLE;
                    }
                    break;
                case ATTK2:
                    if(!(spriteIndex>=45 && spriteIndex<=50)){
                        spriteIndex=45;
                    }
                    if(spriteIndex==50){
                        state = State.IDLE;
                    }
                    break;
                case ATTK3:
                    if(!(spriteIndex>=51 && spriteIndex<=57)){
                        spriteIndex=51;
                    }
                    if(spriteIndex==57){
                        state = State.COOLDOWN;
                    }
                    break;
                case DEF:
                    if(!(spriteIndex>=58 && spriteIndex<=64)){
                        spriteIndex=58;
                    }
                    if(spriteIndex>=59){
                        spriteIndex=59;
                    }
                    break;
                case CEL2:
                    if(!(spriteIndex>=65 && spriteIndex<=79)){
                        spriteIndex=65;
                    }
                    if(spriteIndex>=76&&spriteIndex<=77){
                        spriteIndex=76;
                    }
                    break;
                case GUITAR:
                    if(!(spriteIndex>=80 && spriteIndex<=85)){
                        spriteIndex=80;
                    }
                    break;
                case CEL1:
                    if(!(spriteIndex>=86 && spriteIndex<=92)){
                        spriteIndex=86;
                    }
                    if(spriteIndex>=91&&spriteIndex<=92){
                        spriteIndex=91;
                    }
                    break;
                case CEL3:
                    if(!(spriteIndex>=93 && spriteIndex<=110)){
                        spriteIndex=93;
                    }
                    if(spriteIndex>=109&&spriteIndex<=110){
                        spriteIndex=109;
                    }
                    break;
                case ULTIK:
                    if(!(spriteIndex>=111 && spriteIndex<=122)){
                        spriteIndex=111;
                    }
                    if(spriteIndex==116){
                        spriteIndex=113;
                    }
                    break;
                case KICK1:
                    if(!(spriteIndex>=123 && spriteIndex<=129)){
                        spriteIndex=123;
                    }
                    if(spriteIndex==129){
                        state = State.IDLE;
                    }
                    break;
                case KICK2:
                    if(!(spriteIndex>=130 && spriteIndex<=136)){
                        spriteIndex=130;
                    }
                    if(spriteIndex==136){
                        state = State.COOLDOWN;
                    }
                    break;
                case JKICK:
                    if(!(spriteIndex>=137 && spriteIndex<=143)){
                        spriteIndex=137;
                    }
                    if(spriteIndex>=141 && spriteIndex<=143){
                        spriteIndex=141;
                    }
                    break;
                case ULTIB1:
                    if(!(spriteIndex>=144 && spriteIndex<=155)){
                        spriteIndex=144;
                    }
                    if(spriteIndex==155){
                        state = State.IDLE;
                    }
                    break;
                case ULTIB2:
                    if(!(spriteIndex>=156 && spriteIndex<=174)){
                        spriteIndex=156;
                    }
                    if(spriteIndex==174){
                        state = State.IDLE;
                    }
                    break;
                case CHARGE:
                    if(!(spriteIndex>=175 && spriteIndex<=191)){
                        spriteIndex=175;
                    }
                    if(spriteIndex==191){
                        state = State.IDLE;
                    }
                    break;
                case HEADBUTT:
                    if(!(spriteIndex>=192 && spriteIndex<=201)){
                        spriteIndex=192;
                    }
                    if(spriteIndex==201){
                        state = State.IDLE;
                    }
                    break;
                case LOSE:
                    if(!(spriteIndex>=202 && spriteIndex<=205)){
                        spriteIndex=202;
                    }
                    break;
                case COMEBACK:
                    if(!(spriteIndex>=206 && spriteIndex<=212)){
                        spriteIndex=206;
                    }
                    if(spriteIndex==212){
                        state = State.IDLE;
                    }
                    break;
                case LCOMEBACK:
                    if(!(spriteIndex>=394 && spriteIndex<=400)){
                        spriteIndex=394;
                    }
                    if(spriteIndex==400){
                        state = State.LIDLE;
                    }
                    break;
                case WASATK1:
                    if(!(spriteIndex>=213 && spriteIndex<=217)){
                        spriteIndex=213;
                    }
                    if(spriteIndex==217){
                        state = State.IDLE;
                    }
                    break;
                case WASATK2:
                    if(!(spriteIndex>=218 && spriteIndex<=221)){
                        spriteIndex=218;
                    }
                    if(spriteIndex==221){
                        state = State.IDLE;
                    }
                    break;
                case WASATK3:
                    if(!(spriteIndex>=222 && spriteIndex<=235)){
                        spriteIndex=222;
                    }
                    if(spriteIndex==235){
                        state = State.SLEEP;
                    }
                    break;
                case LWASATK1:
                    if(!(spriteIndex>=417 && spriteIndex<=421)){
                        spriteIndex=417;
                    }
                    if(spriteIndex==421){
                        state = State.LIDLE;
                    }
                    break;
                case LWASATK2:
                    if(!(spriteIndex>=422 && spriteIndex<=425)){
                        spriteIndex=422;
                    }
                    if(spriteIndex==425){
                        state = State.LIDLE;
                    }
                    break;
                case LWASATK3:
                    if(!(spriteIndex>=403 && spriteIndex<=416)){
                        spriteIndex=403;
                    }
                    if(spriteIndex==416){
                        state = State.LSLEEP;
                    }
                    break;
                case SLEEP:
                    if(!(spriteIndex>=236 && spriteIndex<=237)){
                        spriteIndex=236;
                    }
                    break;
                case LSLEEP:
                    if(!(spriteIndex>=401 && spriteIndex<=402)){
                        spriteIndex=401;
                    }
                    break;
                case LIDLE:
                    if(!(spriteIndex>=238 && spriteIndex<=245)){
                        spriteIndex=238;
                    }
                    lcount++;
                    break;
                case LWALK:
                    if(!(spriteIndex>=246 && spriteIndex<=251)){
                        spriteIndex=246;
                    }
                    break;
                case LRUN:
                    if(!(spriteIndex>=252 && spriteIndex<=259)){
                        spriteIndex=252;
                    }
                    break;
                case LJUMP:
                    if(!(spriteIndex>=260 && spriteIndex<=272)){
                        spriteIndex=260;
                    }
                    if(spriteIndex>=270&&spriteIndex<=271){
                        spriteIndex=270;
                    }
                    break;
                case LDODGE:
                    if(!(spriteIndex>=273 && spriteIndex<=278)){
                        spriteIndex=273;
                    }
                    if(spriteIndex==278){
                        state = State.LIDLE;
                    }
                    break;
                case LDEF:
                    if(!(spriteIndex>=279 && spriteIndex<=285)){
                        spriteIndex=279;
                    }
                    if(spriteIndex>=280){
                        spriteIndex=280;
                    }
                    break;
                case LULTIK:
                    if(!(spriteIndex>=286 && spriteIndex<=297)){
                        spriteIndex=286;
                    }
                    if(spriteIndex==291){
                        spriteIndex=288;
                    }
                    break;
                case LATTK1:
                    if(!(spriteIndex>=298 && spriteIndex<=301)){
                        spriteIndex=298;
                    }
                    if(spriteIndex==301){
                        state = State.LIDLE;
                    }
                    break;
                case LATTK2:
                    if(!(spriteIndex>=302 && spriteIndex<=307)){
                        spriteIndex=302;
                    }
                    if(spriteIndex==307){
                        state = State.LIDLE;
                    }
                    break;
                case LATTK3:
                    if(!(spriteIndex>=308 && spriteIndex<=314)){
                        spriteIndex=308;
                    }
                    if(spriteIndex==314){
                        state = State.LCOOLDOWN;
                    }
                    break;
                case LKICK1:
                    if(!(spriteIndex>=315 && spriteIndex<=321)){
                        spriteIndex=315;
                    }
                    if(spriteIndex==321){
                        state = State.LIDLE;
                    }
                    break;
                case LKICK2:
                    if(!(spriteIndex>=322 && spriteIndex<=328)){
                        spriteIndex=322;
                    }
                    if(spriteIndex==328){
                        state = State.LCOOLDOWN;
                    }
                    break;
                case LJKICK:
                    if(!(spriteIndex>=329 && spriteIndex<=335)){
                        spriteIndex=329;
                    }
                    if(spriteIndex>=333 && spriteIndex<=335){
                        spriteIndex=333;
                    }
                    break;
                case LULTIB1:
                    if(!(spriteIndex>=336 && spriteIndex<=347)){
                        spriteIndex=336;
                    }
                    if(spriteIndex==347){
                        state = State.LIDLE;
                    }
                    break;
                case LULTIB2:
                    if(!(spriteIndex>=348 && spriteIndex<=366)){
                        spriteIndex=348;
                    }
                    if(spriteIndex==366){
                        state = State.LIDLE;
                    }
                    break;
                case LCHARGE:
                    if(!(spriteIndex>=367 && spriteIndex<=383)){
                        spriteIndex=367;
                    }
                    if(spriteIndex==383){
                        state = State.LIDLE;
                    }
                    break;
                case LHEADBUTT:
                    if(!(spriteIndex>=384 && spriteIndex<=393)){
                        spriteIndex=384;
                    }
                    if(spriteIndex==393){
                        state = State.LIDLE;
                    }
                    break;
                case COOLDOWN:
                    if(!(spriteIndex>=0 && spriteIndex<=3)){
                        spriteIndex=0;
                    }
                    if(spriteIndex==3){
                        state=State.IDLE;
                    }
                    break;
                case LCOOLDOWN:
                    if(!(spriteIndex>=238 && spriteIndex<=245)){
                        spriteIndex=238;
                    }
                    if(spriteIndex==245){
                        state=State.LIDLE;
                    }
                    break;

            }
            sprite.setSprite(spriteIndex);
            spriteIndex++;
            e=0;
        }
////////////////////////////////////////////////////////////////////////////////////////////////// add Motion on update method
try{
    //###################### Matt Attk ########################
    if (state != State.DEF && state != State.LDEF) {
        if (contacted == true && (matt.spriteIndex == 36 || matt.spriteIndex == 43)) {
            state = State.LWASATK1;
            Gameplay00.score -= 1;
            Gameplay01.spmatt +=2;
        }
        if (contacted == true && (matt.spriteIndex == 50 || matt.spriteIndex == 57)) {
            state = State.WASATK1;
            Gameplay00.score -= 1;
            Gameplay01.spmatt +=2;
        }
        if (contacted == true && (matt.spriteIndex >= 74 && matt.spriteIndex <= 76)) {
            state = State.WASATK3;
            Gameplay00.score -= 10;
        }
        if (contacted == true && (matt.spriteIndex >= 63 && matt.spriteIndex <= 65)) {
            state = State.LWASATK3;
            Gameplay00.score -= 10;
        }
        if (contacted == true && (matt.spriteIndex >= 148 && matt.spriteIndex <= 153)) {
            state = State.WASATK3;
            Gameplay00.score -= 20;
        }
        if (contacted == true && (matt.spriteIndex >= 136 && matt.spriteIndex <= 141)) {
            state = State.LWASATK3;
            Gameplay00.score -= 20;
        }
        if(Gameplay01.scorem <= 0){
            state = State.CEL2;
        }
    }

    if(Gameplay00.score <= 0){
        state = State.LOSE;
    }

    switch(state){
        case WALK:
            body.applyForce(new Vec2(80f, 0f), body.getPosition());
            break;
        case LWALK:
            body.applyForce(new Vec2(-80f, 0f), body.getPosition());
            break;
        case RUN:
            body.applyForce(new Vec2(100f, 0f), body.getPosition());
            break;
        case LRUN:
            body.applyForce(new Vec2(-100f, 0f), body.getPosition());
            break;
        case JUMP:
            if (spriteIndex == 23) {
                body.applyLinearImpulse(new Vec2(0f, -30f), body.getPosition());
                System.out.println(body);
            }
            break;
        case LJUMP:
            if (spriteIndex == 261) {
                body.applyLinearImpulse(new Vec2(0f, -30f), body.getPosition());
            }
            break;
        case CHARGE:
            if (spriteIndex == 189) {
                body.applyLinearImpulse(new Vec2(15f, 0f), body.getPosition());
            }
            break;
        case LCHARGE:
            if (spriteIndex == 381) {
                body.applyLinearImpulse(new Vec2(-15f, 0f), body.getPosition());
            }
            break;
        case ATTK1:
            if (spriteIndex == 42) {
                body.applyLinearImpulse(new Vec2(5f, 0f), body.getPosition());
            }
            break;
        case ATTK2:
            if (spriteIndex == 46 || spriteIndex == 49) {
                body.applyLinearImpulse(new Vec2(5f, 0f), body.getPosition());
            }
            break;
        case ATTK3:
            if (spriteIndex == 52 || spriteIndex == 55) {
                body.applyLinearImpulse(new Vec2(5f, 0f), body.getPosition());
            }
            break;
        case LATTK1:
            if (spriteIndex == 299) {
                body.applyLinearImpulse(new Vec2(-5f, 0f), body.getPosition());
            }
            break;
        case LATTK2:
            if (spriteIndex == 303 || spriteIndex == 306) {
                body.applyLinearImpulse(new Vec2(-5f, 0f), body.getPosition());
            }
            break;
        case LATTK3:
            if (spriteIndex == 309 || spriteIndex == 312) {
                body.applyLinearImpulse(new Vec2(-5f, 0f), body.getPosition());
            }
            break;
        case HEADBUTT:
            if (spriteIndex == 196) {
                body.applyLinearImpulse(new Vec2(10f, 0f), body.getPosition());
            }
            break;
        case LHEADBUTT:
            if (spriteIndex == 388) {
                body.applyLinearImpulse(new Vec2(-10f, 0f), body.getPosition());
            }
            break;
        case KICK1:
            if (spriteIndex == 125) {
                body.applyLinearImpulse(new Vec2(5f, 0f), body.getPosition());
            }
            break;
        case KICK2:
            if (spriteIndex == 132) {
                body.applyLinearImpulse(new Vec2(5f, 0f), body.getPosition());
            }
            break;
        case LKICK1:
            if (spriteIndex == 317) {
                body.applyLinearImpulse(new Vec2(-5f, 0f), body.getPosition());
            }
            break;
        case LKICK2:
            if (spriteIndex == 324) {
                body.applyLinearImpulse(new Vec2(-5f, 0f), body.getPosition());
            }
            break;
        case JKICK:
            if (spriteIndex == 139) {
                body.applyLinearImpulse(new Vec2(15f, 15f), body.getPosition());
            }
            break;
        case LJKICK:
            if (spriteIndex == 331) {
                body.applyLinearImpulse(new Vec2(-15f, 15f), body.getPosition());
            }
            break;
        case ULTIB2:
            if (spriteIndex == 167) {
                body.applyLinearImpulse(new Vec2(10f, -5f), body.getPosition());
            }
            break;
        case LULTIB2:
            if (spriteIndex == 359) {
                body.applyLinearImpulse(new Vec2(-10f, -5f), body.getPosition());
            }
            break;
        case ULTIK:
            if (spriteIndex == 113) {
                body.applyLinearImpulse(new Vec2(20f, 20f), body.getPosition());
            }
            break;
        case LULTIK:
            if (spriteIndex == 288) {
                body.applyLinearImpulse(new Vec2(-20f, 20f), body.getPosition());
            }
            break;
    }

}catch (Exception e){

}

        if(Gameplay00.score<=0){
            Gameplay00.score=0;
        }

        Gameplay01.debugSring = "HpScore = "+Gameplay00.score;
////////////////////////////////////////////////////////////////////////////////////////////////// add Motion on update method

    }

    public void update(int delta,Tom tom){
        if(hasLoaded == false) return;
        e = e + delta;
        if(e > 60){
            switch (state) {
                case IDLE:
                    if(!(spriteIndex>=0 && spriteIndex<=7)){
                        spriteIndex=0;
                    }
                    rcount++;
                    break;
                case WALK:
                    if(!(spriteIndex>=16 && spriteIndex<=21)){
                        spriteIndex=16;
                    }
                    break;
                case RUN:
                    if(!(spriteIndex>=8 && spriteIndex<=15)){
                        spriteIndex=8;
                    }
                    break;
                case JUMP:
                    if(!(spriteIndex>=22 && spriteIndex<=34)){
                        spriteIndex=22;
                    }
                    if(spriteIndex>=32&&spriteIndex<=33){
                        spriteIndex=32;
                    }
                    break;
                case DODGE:
                    if(!(spriteIndex>=35 && spriteIndex<=40)){
                        spriteIndex=35;
                    }
                    if(spriteIndex==40){
                        state = State.IDLE;
                    }
                    break;
                case ATTK1:
                    if(!(spriteIndex>=41 && spriteIndex<=44)){
                        spriteIndex=41;
                    }
                    if(spriteIndex==44){
                        state = State.IDLE;
                    }
                    break;
                case ATTK2:
                    if(!(spriteIndex>=45 && spriteIndex<=50)){
                        spriteIndex=45;
                    }
                    if(spriteIndex==50){
                        state = State.IDLE;
                    }
                    break;
                case ATTK3:
                    if(!(spriteIndex>=51 && spriteIndex<=57)){
                        spriteIndex=51;
                    }
                    if(spriteIndex==57){
                        state = State.COOLDOWN;
                    }
                    break;
                case DEF:
                    if(!(spriteIndex>=58 && spriteIndex<=64)){
                        spriteIndex=58;
                    }
                    if(spriteIndex>=59){
                        spriteIndex=59;
                    }
                    break;
                case CEL2:
                    if(!(spriteIndex>=65 && spriteIndex<=79)){
                        spriteIndex=65;
                    }
                    if(spriteIndex>=76&&spriteIndex<=77){
                        spriteIndex=76;
                    }
                    break;
                case GUITAR:
                    if(!(spriteIndex>=80 && spriteIndex<=85)){
                        spriteIndex=80;
                    }
                    break;
                case CEL1:
                    if(!(spriteIndex>=86 && spriteIndex<=92)){
                        spriteIndex=86;
                    }
                    if(spriteIndex>=91&&spriteIndex<=92){
                        spriteIndex=91;
                    }
                    break;
                case CEL3:
                    if(!(spriteIndex>=93 && spriteIndex<=110)){
                        spriteIndex=93;
                    }
                    if(spriteIndex>=109&&spriteIndex<=110){
                        spriteIndex=109;
                    }
                    break;
                case ULTIK:
                    if(!(spriteIndex>=111 && spriteIndex<=122)){
                        spriteIndex=111;
                    }
                    if(spriteIndex==116){
                        spriteIndex=113;
                    }
                    break;
                case KICK1:
                    if(!(spriteIndex>=123 && spriteIndex<=129)){
                        spriteIndex=123;
                    }
                    if(spriteIndex==129){
                        state = State.IDLE;
                    }
                    break;
                case KICK2:
                    if(!(spriteIndex>=130 && spriteIndex<=136)){
                        spriteIndex=130;
                    }
                    if(spriteIndex==136){
                        state = State.COOLDOWN;
                    }
                    break;
                case JKICK:
                    if(!(spriteIndex>=137 && spriteIndex<=143)){
                        spriteIndex=137;
                    }
                    if(spriteIndex>=141 && spriteIndex<=143){
                        spriteIndex=141;
                    }
                    break;
                case ULTIB1:
                    if(!(spriteIndex>=144 && spriteIndex<=155)){
                        spriteIndex=144;
                    }
                    if(spriteIndex==155){
                        state = State.IDLE;
                    }
                    break;
                case ULTIB2:
                    if(!(spriteIndex>=156 && spriteIndex<=174)){
                        spriteIndex=156;
                    }
                    if(spriteIndex==174){
                        state = State.IDLE;
                    }
                    break;
                case CHARGE:
                    if(!(spriteIndex>=175 && spriteIndex<=191)){
                        spriteIndex=175;
                    }
                    if(spriteIndex==191){
                        state = State.IDLE;
                    }
                    break;
                case HEADBUTT:
                    if(!(spriteIndex>=192 && spriteIndex<=201)){
                        spriteIndex=192;
                    }
                    if(spriteIndex==201){
                        state = State.IDLE;
                    }
                    break;
                case LOSE:
                    if(!(spriteIndex>=202 && spriteIndex<=205)){
                        spriteIndex=202;
                    }
                    break;
                case COMEBACK:
                    if(!(spriteIndex>=206 && spriteIndex<=212)){
                        spriteIndex=206;
                    }
                    if(spriteIndex==212){
                        state = State.IDLE;
                    }
                    break;
                case LCOMEBACK:
                    if(!(spriteIndex>=394 && spriteIndex<=400)){
                        spriteIndex=394;
                    }
                    if(spriteIndex==400){
                        state = State.LIDLE;
                    }
                    break;
                case WASATK1:
                    if(!(spriteIndex>=213 && spriteIndex<=217)){
                        spriteIndex=213;
                    }
                    if(spriteIndex==217){
                        state = State.IDLE;
                    }
                    break;
                case WASATK2:
                    if(!(spriteIndex>=218 && spriteIndex<=221)){
                        spriteIndex=218;
                    }
                    if(spriteIndex==221){
                        state = State.IDLE;
                    }
                    break;
                case WASATK3:
                    if(!(spriteIndex>=222 && spriteIndex<=235)){
                        spriteIndex=222;
                    }
                    if(spriteIndex==235){
                        state = State.SLEEP;
                    }
                    break;
                case LWASATK1:
                    if(!(spriteIndex>=417 && spriteIndex<=421)){
                        spriteIndex=417;
                    }
                    if(spriteIndex==421){
                        state = State.LIDLE;
                    }
                    break;
                case LWASATK2:
                    if(!(spriteIndex>=422 && spriteIndex<=425)){
                        spriteIndex=422;
                    }
                    if(spriteIndex==425){
                        state = State.LIDLE;
                    }
                    break;
                case LWASATK3:
                    if(!(spriteIndex>=403 && spriteIndex<=416)){
                        spriteIndex=403;
                    }
                    if(spriteIndex==416){
                        state = State.LSLEEP;
                    }
                    break;
                case SLEEP:
                    if(!(spriteIndex>=236 && spriteIndex<=237)){
                        spriteIndex=236;
                    }
                    break;
                case LSLEEP:
                    if(!(spriteIndex>=401 && spriteIndex<=402)){
                        spriteIndex=401;
                    }
                    break;
                case LIDLE:
                    if(!(spriteIndex>=238 && spriteIndex<=245)){
                        spriteIndex=238;
                    }
                    lcount++;
                    break;
                case LWALK:
                    if(!(spriteIndex>=246 && spriteIndex<=251)){
                        spriteIndex=246;
                    }
                    break;
                case LRUN:
                    if(!(spriteIndex>=252 && spriteIndex<=259)){
                        spriteIndex=252;
                    }
                    break;
                case LJUMP:
                    if(!(spriteIndex>=260 && spriteIndex<=272)){
                        spriteIndex=260;
                    }
                    if(spriteIndex>=270&&spriteIndex<=271){
                        spriteIndex=270;
                    }
                    break;
                case LDODGE:
                    if(!(spriteIndex>=273 && spriteIndex<=278)){
                        spriteIndex=273;
                    }
                    if(spriteIndex==278){
                        state = State.LIDLE;
                    }
                    break;
                case LDEF:
                    if(!(spriteIndex>=279 && spriteIndex<=285)){
                        spriteIndex=279;
                    }
                    if(spriteIndex>=280){
                        spriteIndex=280;
                    }
                    break;
                case LULTIK:
                    if(!(spriteIndex>=286 && spriteIndex<=297)){
                        spriteIndex=286;
                    }
                    if(spriteIndex==291){
                        spriteIndex=288;
                    }
                    break;
                case LATTK1:
                    if(!(spriteIndex>=298 && spriteIndex<=301)){
                        spriteIndex=298;
                    }
                    if(spriteIndex==301){
                        state = State.LIDLE;
                    }
                    break;
                case LATTK2:
                    if(!(spriteIndex>=302 && spriteIndex<=307)){
                        spriteIndex=302;
                    }
                    if(spriteIndex==307){
                        state = State.LIDLE;
                    }
                    break;
                case LATTK3:
                    if(!(spriteIndex>=308 && spriteIndex<=314)){
                        spriteIndex=308;
                    }
                    if(spriteIndex==314){
                        state = State.LCOOLDOWN;
                    }
                    break;
                case LKICK1:
                    if(!(spriteIndex>=315 && spriteIndex<=321)){
                        spriteIndex=315;
                    }
                    if(spriteIndex==321){
                        state = State.LIDLE;
                    }
                    break;
                case LKICK2:
                    if(!(spriteIndex>=322 && spriteIndex<=328)){
                        spriteIndex=322;
                    }
                    if(spriteIndex==328){
                        state = State.LCOOLDOWN;
                    }
                    break;
                case LJKICK:
                    if(!(spriteIndex>=329 && spriteIndex<=335)){
                        spriteIndex=329;
                    }
                    if(spriteIndex>=333 && spriteIndex<=335){
                        spriteIndex=333;
                    }
                    break;
                case LULTIB1:
                    if(!(spriteIndex>=336 && spriteIndex<=347)){
                        spriteIndex=336;
                    }
                    if(spriteIndex==347){
                        state = State.LIDLE;
                    }
                    break;
                case LULTIB2:
                    if(!(spriteIndex>=348 && spriteIndex<=366)){
                        spriteIndex=348;
                    }
                    if(spriteIndex==366){
                        state = State.LIDLE;
                    }
                    break;
                case LCHARGE:
                    if(!(spriteIndex>=367 && spriteIndex<=383)){
                        spriteIndex=367;
                    }
                    if(spriteIndex==383){
                        state = State.LIDLE;
                    }
                    break;
                case LHEADBUTT:
                    if(!(spriteIndex>=384 && spriteIndex<=393)){
                        spriteIndex=384;
                    }
                    if(spriteIndex==393){
                        state = State.LIDLE;
                    }
                    break;
                case COOLDOWN:
                    if(!(spriteIndex>=0 && spriteIndex<=3)){
                        spriteIndex=0;
                    }
                    if(spriteIndex==3){
                        state=State.IDLE;
                    }
                    break;
                case LCOOLDOWN:
                    if(!(spriteIndex>=238 && spriteIndex<=245)){
                        spriteIndex=238;
                    }
                    if(spriteIndex==245){
                        state=State.LIDLE;
                    }
                    break;

            }
            sprite.setSprite(spriteIndex);
            spriteIndex++;
            e=0;
        }

        try {
            //###################### Tom Attk ########################
            if (state != State.DEF && state != State.LDEF) {
                if (contacted == true && (tom.spriteIndex == 59 || tom.spriteIndex == 65)) {
                    state = State.WASATK1;
                    Gameplay00.score -= 1;
                    Gameplay00.sptom += 2;
                }
                if (contacted == true && (tom.spriteIndex == 34 || tom.spriteIndex == 40)) {
                    state = State.LWASATK1;
                    Gameplay00.score -= 1;
                    Gameplay00.sptom +=2;
                }
                if (contacted == true && (tom.spriteIndex >= 98 && tom.spriteIndex <= 101)) {
                    state = State.WASATK3;
                    Gameplay00.score -= 10;
                }
                if (contacted == true && (tom.spriteIndex >= 78 && tom.spriteIndex <= 81)) {
                    state = State.LWASATK3;
                    Gameplay00.score -= 10;
                }
                if(Gameplay00.scoret <= 0){
                    state = State.CEL1;
                }
            }

            if(Gameplay00.score <= 0){
                state = State.LOSE;
            }

            switch(state){
                case WALK:
                    body.applyForce(new Vec2(80f, 0f), body.getPosition());
                    break;
                case LWALK:
                    body.applyForce(new Vec2(-80f, 0f), body.getPosition());
                    break;
                case RUN:
                    body.applyForce(new Vec2(100f, 0f), body.getPosition());
                    break;
                case LRUN:
                    body.applyForce(new Vec2(-100f, 0f), body.getPosition());
                    break;
                case JUMP:
                    if (spriteIndex == 23) {
                        body.applyLinearImpulse(new Vec2(0f, -30f), body.getPosition());
                        System.out.println(body);
                    }
                    break;
                case LJUMP:
                    if (spriteIndex == 261) {
                        body.applyLinearImpulse(new Vec2(0f, -30f), body.getPosition());
                    }
                    break;
                case CHARGE:
                    if (spriteIndex == 189) {
                        body.applyLinearImpulse(new Vec2(15f, 0f), body.getPosition());
                    }
                    break;
                case LCHARGE:
                    if (spriteIndex == 381) {
                        body.applyLinearImpulse(new Vec2(-15f, 0f), body.getPosition());
                    }
                    break;
                case ATTK1:
                    if (spriteIndex == 42) {
                        body.applyLinearImpulse(new Vec2(5f, 0f), body.getPosition());
                    }
                    break;
                case ATTK2:
                    if (spriteIndex == 46 || spriteIndex == 49) {
                        body.applyLinearImpulse(new Vec2(5f, 0f), body.getPosition());
                    }
                    break;
                case ATTK3:
                    if (spriteIndex == 52 || spriteIndex == 55) {
                        body.applyLinearImpulse(new Vec2(5f, 0f), body.getPosition());
                    }
                    break;
                case LATTK1:
                    if (spriteIndex == 299) {
                        body.applyLinearImpulse(new Vec2(-5f, 0f), body.getPosition());
                    }
                    break;
                case LATTK2:
                    if (spriteIndex == 303 || spriteIndex == 306) {
                        body.applyLinearImpulse(new Vec2(-5f, 0f), body.getPosition());
                    }
                    break;
                case LATTK3:
                    if (spriteIndex == 309 || spriteIndex == 312) {
                        body.applyLinearImpulse(new Vec2(-5f, 0f), body.getPosition());
                    }
                    break;
                case HEADBUTT:
                    if (spriteIndex == 196) {
                        body.applyLinearImpulse(new Vec2(10f, 0f), body.getPosition());
                    }
                    break;
                case LHEADBUTT:
                    if (spriteIndex == 388) {
                        body.applyLinearImpulse(new Vec2(-10f, 0f), body.getPosition());
                    }
                    break;
                case KICK1:
                    if (spriteIndex == 125) {
                        body.applyLinearImpulse(new Vec2(5f, 0f), body.getPosition());
                    }
                    break;
                case KICK2:
                    if (spriteIndex == 132) {
                        body.applyLinearImpulse(new Vec2(5f, 0f), body.getPosition());
                    }
                    break;
                case LKICK1:
                    if (spriteIndex == 317) {
                        body.applyLinearImpulse(new Vec2(-5f, 0f), body.getPosition());
                    }
                    break;
                case LKICK2:
                    if (spriteIndex == 324) {
                        body.applyLinearImpulse(new Vec2(-5f, 0f), body.getPosition());
                    }
                    break;
                case JKICK:
                    if (spriteIndex == 139) {
                        body.applyLinearImpulse(new Vec2(15f, 15f), body.getPosition());
                    }
                    break;
                case LJKICK:
                    if (spriteIndex == 331) {
                        body.applyLinearImpulse(new Vec2(-15f, 15f), body.getPosition());
                    }
                    break;
                case ULTIB2:
                    if (spriteIndex == 167) {
                        body.applyLinearImpulse(new Vec2(10f, -5f), body.getPosition());
                    }
                    break;
                case LULTIB2:
                    if (spriteIndex == 359) {
                        body.applyLinearImpulse(new Vec2(-10f, -5f), body.getPosition());
                    }
                    break;
                case ULTIK:
                    if (spriteIndex == 113) {
                        body.applyLinearImpulse(new Vec2(20f, 20f), body.getPosition());
                    }
                    break;
                case LULTIK:
                    if (spriteIndex == 288) {
                        body.applyLinearImpulse(new Vec2(-20f, 20f), body.getPosition());
                    }
                    break;
            }
        }catch (Exception e){

        }

        if(Gameplay00.score<=0){
            Gameplay00.score=0;
        }
        Gameplay00.debugSring = "HpScore = "+Gameplay00.score;
    }

    public void update(int delta,Gideon gideon){
        if(hasLoaded == false) return;
        e = e + delta;
        if(e > 60){
            switch (state) {
                case IDLE:
                    if(!(spriteIndex>=0 && spriteIndex<=7)){
                        spriteIndex=0;
                    }
                    rcount++;
                    break;
                case WALK:
                    if(!(spriteIndex>=16 && spriteIndex<=21)){
                        spriteIndex=16;
                    }
                    break;
                case RUN:
                    if(!(spriteIndex>=8 && spriteIndex<=15)){
                        spriteIndex=8;
                    }
                    break;
                case JUMP:
                    if(!(spriteIndex>=22 && spriteIndex<=34)){
                        spriteIndex=22;
                    }
                    if(spriteIndex>=32&&spriteIndex<=33){
                        spriteIndex=32;
                    }
                    break;
                case DODGE:
                    if(!(spriteIndex>=35 && spriteIndex<=40)){
                        spriteIndex=35;
                    }
                    if(spriteIndex==40){
                        state = State.IDLE;
                    }
                    break;
                case ATTK1:
                    if(!(spriteIndex>=41 && spriteIndex<=44)){
                        spriteIndex=41;
                    }
                    if(spriteIndex==44){
                        state = State.IDLE;
                    }
                    break;
                case ATTK2:
                    if(!(spriteIndex>=45 && spriteIndex<=50)){
                        spriteIndex=45;
                    }
                    if(spriteIndex==50){
                        state = State.IDLE;
                    }
                    break;
                case ATTK3:
                    if(!(spriteIndex>=51 && spriteIndex<=57)){
                        spriteIndex=51;
                    }
                    if(spriteIndex==57){
                        state = State.COOLDOWN;
                    }
                    break;
                case DEF:
                    if(!(spriteIndex>=58 && spriteIndex<=64)){
                        spriteIndex=58;
                    }
                    if(spriteIndex>=59){
                        spriteIndex=59;
                    }
                    break;
                case CEL2:
                    if(!(spriteIndex>=65 && spriteIndex<=79)){
                        spriteIndex=65;
                    }
                    if(spriteIndex>=76&&spriteIndex<=77){
                        spriteIndex=76;
                    }
                    break;
                case GUITAR:
                    if(!(spriteIndex>=80 && spriteIndex<=85)){
                        spriteIndex=80;
                    }
                    break;
                case CEL1:
                    if(!(spriteIndex>=86 && spriteIndex<=92)){
                        spriteIndex=86;
                    }
                    if(spriteIndex>=91&&spriteIndex<=92){
                        spriteIndex=91;
                    }
                    break;
                case CEL3:
                    if(!(spriteIndex>=93 && spriteIndex<=110)){
                        spriteIndex=93;
                    }
                    if(spriteIndex>=109&&spriteIndex<=110){
                        spriteIndex=109;
                        MyGame.world03.stop();
                        ss.remove(ss.top());
                        ss.push(new TopScore(ss));
                    }
                    break;
                case ULTIK:
                    if(!(spriteIndex>=111 && spriteIndex<=122)){
                        spriteIndex=111;
                    }
                    if(spriteIndex==116){
                        spriteIndex=113;
                    }
                    break;
                case KICK1:
                    if(!(spriteIndex>=123 && spriteIndex<=129)){
                        spriteIndex=123;
                    }
                    if(spriteIndex==129){
                        state = State.IDLE;
                    }
                    break;
                case KICK2:
                    if(!(spriteIndex>=130 && spriteIndex<=136)){
                        spriteIndex=130;
                    }
                    if(spriteIndex==136){
                        state = State.COOLDOWN;
                    }
                    break;
                case JKICK:
                    if(!(spriteIndex>=137 && spriteIndex<=143)){
                        spriteIndex=137;
                    }
                    if(spriteIndex>=141 && spriteIndex<=143){
                        spriteIndex=141;
                    }
                    break;
                case ULTIB1:
                    if(!(spriteIndex>=144 && spriteIndex<=155)){
                        spriteIndex=144;
                    }
                    if(spriteIndex==155){
                        state = State.IDLE;
                    }
                    break;
                case ULTIB2:
                    if(!(spriteIndex>=156 && spriteIndex<=174)){
                        spriteIndex=156;
                    }
                    if(spriteIndex==174){
                        state = State.IDLE;
                    }
                    break;
                case CHARGE:
                    if(!(spriteIndex>=175 && spriteIndex<=191)){
                        spriteIndex=175;
                    }
                    if(spriteIndex==191){
                        state = State.IDLE;
                    }
                    break;
                case HEADBUTT:
                    if(!(spriteIndex>=192 && spriteIndex<=201)){
                        spriteIndex=192;
                    }
                    if(spriteIndex==201){
                        state = State.IDLE;
                    }
                    break;
                case LOSE:
                    if(!(spriteIndex>=202 && spriteIndex<=205)){
                        spriteIndex=202;
                    }
                    break;
                case COMEBACK:
                    if(!(spriteIndex>=206 && spriteIndex<=212)){
                        spriteIndex=206;
                    }
                    if(spriteIndex==212){
                        state = State.IDLE;
                    }
                    break;
                case LCOMEBACK:
                    if(!(spriteIndex>=394 && spriteIndex<=400)){
                        spriteIndex=394;
                    }
                    if(spriteIndex==400){
                        state = State.LIDLE;
                    }
                    break;
                case WASATK1:
                    if(!(spriteIndex>=213 && spriteIndex<=217)){
                        spriteIndex=213;
                    }
                    if(spriteIndex==217){
                        state = State.IDLE;
                    }
                    break;
                case WASATK2:
                    if(!(spriteIndex>=218 && spriteIndex<=221)){
                        spriteIndex=218;
                    }
                    if(spriteIndex==221){
                        state = State.IDLE;
                    }
                    break;
                case WASATK3:
                    if(!(spriteIndex>=222 && spriteIndex<=235)){
                        spriteIndex=222;
                    }
                    if(spriteIndex==235){
                        state = State.SLEEP;
                    }
                    break;
                case LWASATK1:
                    if(!(spriteIndex>=417 && spriteIndex<=421)){
                        spriteIndex=417;
                    }
                    if(spriteIndex==421){
                        state = State.LIDLE;
                    }
                    break;
                case LWASATK2:
                    if(!(spriteIndex>=422 && spriteIndex<=425)){
                        spriteIndex=422;
                    }
                    if(spriteIndex==425){
                        state = State.LIDLE;
                    }
                    break;
                case LWASATK3:
                    if(!(spriteIndex>=403 && spriteIndex<=416)){
                        spriteIndex=403;
                    }
                    if(spriteIndex==416){
                        state = State.LSLEEP;
                    }
                    break;
                case SLEEP:
                    if(!(spriteIndex>=236 && spriteIndex<=237)){
                        spriteIndex=236;
                    }
                    break;
                case LSLEEP:
                    if(!(spriteIndex>=401 && spriteIndex<=402)){
                        spriteIndex=401;
                    }
                    break;
                case LIDLE:
                    if(!(spriteIndex>=238 && spriteIndex<=245)){
                        spriteIndex=238;
                    }
                    lcount++;
                    break;
                case LWALK:
                    if(!(spriteIndex>=246 && spriteIndex<=251)){
                        spriteIndex=246;
                    }
                    break;
                case LRUN:
                    if(!(spriteIndex>=252 && spriteIndex<=259)){
                        spriteIndex=252;
                    }
                    break;
                case LJUMP:
                    if(!(spriteIndex>=260 && spriteIndex<=272)){
                        spriteIndex=260;
                    }
                    if(spriteIndex>=270&&spriteIndex<=271){
                        spriteIndex=270;
                    }
                    break;
                case LDODGE:
                    if(!(spriteIndex>=273 && spriteIndex<=278)){
                        spriteIndex=273;
                    }
                    if(spriteIndex==278){
                        state = State.LIDLE;
                    }
                    break;
                case LDEF:
                    if(!(spriteIndex>=279 && spriteIndex<=285)){
                        spriteIndex=279;
                    }
                    if(spriteIndex>=280){
                        spriteIndex=280;
                    }
                    break;
                case LULTIK:
                    if(!(spriteIndex>=286 && spriteIndex<=297)){
                        spriteIndex=286;
                    }
                    if(spriteIndex==291){
                        spriteIndex=288;
                    }
                    break;
                case LATTK1:
                    if(!(spriteIndex>=298 && spriteIndex<=301)){
                        spriteIndex=298;
                    }
                    if(spriteIndex==301){
                        state = State.LIDLE;
                    }
                    break;
                case LATTK2:
                    if(!(spriteIndex>=302 && spriteIndex<=307)){
                        spriteIndex=302;
                    }
                    if(spriteIndex==307){
                        state = State.LIDLE;
                    }
                    break;
                case LATTK3:
                    if(!(spriteIndex>=308 && spriteIndex<=314)){
                        spriteIndex=308;
                    }
                    if(spriteIndex==314){
                        state = State.LCOOLDOWN;
                    }
                    break;
                case LKICK1:
                    if(!(spriteIndex>=315 && spriteIndex<=321)){
                        spriteIndex=315;
                    }
                    if(spriteIndex==321){
                        state = State.LIDLE;
                    }
                    break;
                case LKICK2:
                    if(!(spriteIndex>=322 && spriteIndex<=328)){
                        spriteIndex=322;
                    }
                    if(spriteIndex==328){
                        state = State.LCOOLDOWN;
                    }
                    break;
                case LJKICK:
                    if(!(spriteIndex>=329 && spriteIndex<=335)){
                        spriteIndex=329;
                    }
                    if(spriteIndex>=333 && spriteIndex<=335){
                        spriteIndex=333;
                    }
                    break;
                case LULTIB1:
                    if(!(spriteIndex>=336 && spriteIndex<=347)){
                        spriteIndex=336;
                    }
                    if(spriteIndex==347){
                        state = State.LIDLE;
                    }
                    break;
                case LULTIB2:
                    if(!(spriteIndex>=348 && spriteIndex<=366)){
                        spriteIndex=348;
                    }
                    if(spriteIndex==366){
                        state = State.LIDLE;
                    }
                    break;
                case LCHARGE:
                    if(!(spriteIndex>=367 && spriteIndex<=383)){
                        spriteIndex=367;
                    }
                    if(spriteIndex==383){
                        state = State.LIDLE;
                    }
                    break;
                case LHEADBUTT:
                    if(!(spriteIndex>=384 && spriteIndex<=393)){
                        spriteIndex=384;
                    }
                    if(spriteIndex==393){
                        state = State.LIDLE;
                    }
                    break;
                case COOLDOWN:
                    if(!(spriteIndex>=0 && spriteIndex<=3)){
                        spriteIndex=0;
                    }
                    if(spriteIndex==3){
                        state=State.IDLE;
                    }
                    break;
                case LCOOLDOWN:
                    if(!(spriteIndex>=238 && spriteIndex<=245)){
                        spriteIndex=238;
                    }
                    if(spriteIndex==245){
                        state=State.LIDLE;
                    }
                    break;

            }
            sprite.setSprite(spriteIndex);
            spriteIndex++;
            e=0;
        }

        try {
            //###################### Gideon Attk ########################
            if (state != State.DEF && state != State.LDEF) {
                if (contacted == true && (gideon.spriteIndex == 64 || gideon.spriteIndex == 71
                        || gideon.spriteIndex == 92 || gideon.spriteIndex == 94 || gideon.spriteIndex == 117)) {
                    state = State.WASATK1;
                    Gameplay00.score -= 1;
                    Gameplay02.spgideon +=2;
                }
                if (contacted == true && (gideon.spriteIndex == 49 || gideon.spriteIndex == 56
                        || gideon.spriteIndex == 80 || gideon.spriteIndex == 82 || gideon.spriteIndex == 104)) {
                    state = State.LWASATK1;
                    Gameplay00.score -= 1;
                    Gameplay02.spgideon +=2;
                }
                if (contacted == true && ((gideon.spriteIndex >= 122 && gideon.spriteIndex <= 126)
                        || (gideon.spriteIndex >= 144 && gideon.spriteIndex <= 149)
                        || (gideon.spriteIndex >= 153 && gideon.spriteIndex <= 156) )) {
                    state = State.WASATK3;
                    Gameplay00.score -= 15;
                }
                if (contacted == true && ((gideon.spriteIndex >= 109 && gideon.spriteIndex <= 113)
                        || (gideon.spriteIndex >= 129 && gideon.spriteIndex <= 134)
                        || (gideon.spriteIndex >= 138 && gideon.spriteIndex <= 141) )) {
                    state = State.LWASATK3;
                    Gameplay00.score -= 15;
                }
                if(Gameplay02.scoreg <= 0){
                    state = State.CEL3;
                }
            }
            if(Gameplay00.score <= 0){
                state = State.LOSE;
            }

            switch(state){
                case WALK:
                    body.applyForce(new Vec2(80f, 0f), body.getPosition());
                    break;
                case LWALK:
                    body.applyForce(new Vec2(-80f, 0f), body.getPosition());
                    break;
                case RUN:
                    body.applyForce(new Vec2(100f, 0f), body.getPosition());
                    break;
                case LRUN:
                    body.applyForce(new Vec2(-100f, 0f), body.getPosition());
                    break;
                case JUMP:
                    if (spriteIndex == 23) {
                        body.applyLinearImpulse(new Vec2(0f, -30f), body.getPosition());
                        System.out.println(body);
                    }
                    break;
                case LJUMP:
                    if (spriteIndex == 261) {
                        body.applyLinearImpulse(new Vec2(0f, -30f), body.getPosition());
                    }
                    break;
                case CHARGE:
                    if (spriteIndex == 189) {
                        body.applyLinearImpulse(new Vec2(15f, 0f), body.getPosition());
                    }
                    break;
                case LCHARGE:
                    if (spriteIndex == 381) {
                        body.applyLinearImpulse(new Vec2(-15f, 0f), body.getPosition());
                    }
                    break;
                case ATTK1:
                    if (spriteIndex == 42) {
                        body.applyLinearImpulse(new Vec2(5f, 0f), body.getPosition());
                    }
                    break;
                case ATTK2:
                    if (spriteIndex == 46 || spriteIndex == 49) {
                        body.applyLinearImpulse(new Vec2(5f, 0f), body.getPosition());
                    }
                    break;
                case ATTK3:
                    if (spriteIndex == 52 || spriteIndex == 55) {
                        body.applyLinearImpulse(new Vec2(5f, 0f), body.getPosition());
                    }
                    break;
                case LATTK1:
                    if (spriteIndex == 299) {
                        body.applyLinearImpulse(new Vec2(-5f, 0f), body.getPosition());
                    }
                    break;
                case LATTK2:
                    if (spriteIndex == 303 || spriteIndex == 306) {
                        body.applyLinearImpulse(new Vec2(-5f, 0f), body.getPosition());
                    }
                    break;
                case LATTK3:
                    if (spriteIndex == 309 || spriteIndex == 312) {
                        body.applyLinearImpulse(new Vec2(-5f, 0f), body.getPosition());
                    }
                    break;
                case HEADBUTT:
                    if (spriteIndex == 196) {
                        body.applyLinearImpulse(new Vec2(10f, 0f), body.getPosition());
                    }
                    break;
                case LHEADBUTT:
                    if (spriteIndex == 388) {
                        body.applyLinearImpulse(new Vec2(-10f, 0f), body.getPosition());
                    }
                    break;
                case KICK1:
                    if (spriteIndex == 125) {
                        body.applyLinearImpulse(new Vec2(5f, 0f), body.getPosition());
                    }
                    break;
                case KICK2:
                    if (spriteIndex == 132) {
                        body.applyLinearImpulse(new Vec2(5f, 0f), body.getPosition());
                    }
                    break;
                case LKICK1:
                    if (spriteIndex == 317) {
                        body.applyLinearImpulse(new Vec2(-5f, 0f), body.getPosition());
                    }
                    break;
                case LKICK2:
                    if (spriteIndex == 324) {
                        body.applyLinearImpulse(new Vec2(-5f, 0f), body.getPosition());
                    }
                    break;
                case JKICK:
                    if (spriteIndex == 139) {
                        body.applyLinearImpulse(new Vec2(15f, 15f), body.getPosition());
                    }
                    break;
                case LJKICK:
                    if (spriteIndex == 331) {
                        body.applyLinearImpulse(new Vec2(-15f, 15f), body.getPosition());
                    }
                    break;
                case ULTIB2:
                    if (spriteIndex == 167) {
                        body.applyLinearImpulse(new Vec2(10f, -5f), body.getPosition());
                    }
                    break;
                case LULTIB2:
                    if (spriteIndex == 359) {
                        body.applyLinearImpulse(new Vec2(-10f, -5f), body.getPosition());
                    }
                    break;
                case ULTIK:
                    if (spriteIndex == 113) {
                        body.applyLinearImpulse(new Vec2(20f, 20f), body.getPosition());
                    }
                    break;
                case LULTIK:
                    if (spriteIndex == 288) {
                        body.applyLinearImpulse(new Vec2(-20f, 20f), body.getPosition());
                    }
                    break;
            }
        }catch (Exception e){

        }

        if(Gameplay00.score<=0){
            Gameplay00.score=0;
        }
        Gameplay02.debugSring = "HpScore = "+Gameplay00.score;
    }

    public void paint(Clock clock) {
        if(!hasLoaded) return;
        sprite.layer().setTranslation(
                (body.getPosition().x/Gameplay00.M_PER_PIXEL),
                (body.getPosition().y/Gameplay00.M_PER_PIXEL));
    }

}
