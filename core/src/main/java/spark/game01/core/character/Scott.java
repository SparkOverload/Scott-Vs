package spark.game01.core.character;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.contacts.Contact;
import playn.core.Key;
import playn.core.*;
import playn.core.Keyboard;
import playn.core.Layer;
import playn.core.PlayN;
import playn.core.util.Callback;
import playn.core.util.Clock;
import spark.game01.core.Gameplay00;
import spark.game01.core.sprite.Sprite;
import spark.game01.core.sprite.SpriteLoader;


import static playn.core.PlayN.keyboard;

public class Scott {

    private Sprite sprite;
    public int spriteIndex = 0;
    private boolean hasLoaded = false;
    private  int eventstate;
    private  int eventwarp=0;
    private  int rcount=10;
    private  int lcount=10;
    private float x;
    private float y;
    private float z = 24f;
    private int move;
    public Body body;
    private Body other;
    public Boolean contacted = false;
    private int contactCheck;




    public enum State{
        IDLE,LIDLE,RUN,LRUN,WALK,LWALK,JUMP,LJUMP,DODGE,LDODGE,ATTK1,ATTK2,ATTK3,
        LATTK1,LATTK2,LATTK3,KICK1,LKICK1,KICK2,LKICK2,JKICK,LJKICK,ULTIK,LULTIK,
        ULTIB1,LULTIB1,ULTIB2,LULTIB2,CHARGE,LCHARGE,
        DEF,LDEF,CEL1,CEL2,CEL3,GUITAR,HEADBUTT,LHEADBUTT,LOSE,COMEBACK,LCOMEBACK,
        WASATK1,LWASATK1,WASATK2,LWASATK2,WASATK3,LWASATK3,SLEEP,LSLEEP
    };

    public State state = State.IDLE;

    private int e = 0;

    public Scott(final World world,final float x, final float y){
        this.x=x;
        this.y=y;
        PlayN.keyboard().setListener(new Keyboard.Adapter() {
            @Override
            public void onKeyDown(Keyboard.Event event) {
               switch (event.key()) {
                    case UP:
                        if(state == State.SLEEP){
                            state = State.COMEBACK;
                        }
                        if(state == State.ATTK2){
                            state = State.ULTIB1;
                        }
                        if(state == State.LATTK2){
                            state = State.LULTIB1;
                        }
                        break;
                   case LEFT:
                       if(lcount<3&& state == State.LIDLE){
                           state = State.LRUN;
                           move = 1;
                       }
                       if(state == State.LIDLE || state == State.IDLE || state == State.WALK){
                           state = State.LWALK;
                           lcount=10;
                           rcount=10;
                           move = 1;
                       }
                       if(state == State.LJUMP){
                           move = 1;
                       }
                       break;
                    case RIGHT:
                        if(rcount<3 && state == State.IDLE){
                            state = State.RUN;
                            move=1;
                        }
                        if(state == State.IDLE || state == State.LIDLE || state == State.LWALK){
                            state = State.WALK;
                            lcount=10;
                            rcount=10;
                            move=1;
                        }
                        if(state == State.JUMP){
                            move = 1;
                        }
                        break;
                    case DOWN:
                        if(state == State.WALK || state == State.RUN){
                            state = State.DODGE;
                        }
                        if(state == State.LWALK || state == State.LRUN){
                            state = State.LDODGE;
                        }
                        if(state == State.IDLE || state == State.LIDLE){
                            eventstate=1;
                        }
                        break;
                    case SPACE:
                        if(state == State.WALK || state == State.RUN || state == State.IDLE){
                            state = State.JUMP;
                        }
                        if(state == State.JUMP && (spriteIndex >= 26 && spriteIndex <=30)){
                            state=State.ULTIK;
                        }
                        if(state == State.LJUMP && (spriteIndex >= 264 && spriteIndex <=268)){
                            state=State.LULTIK;
                        }
                        if(state == State.SLEEP){
                            state = State.COMEBACK;
                        }
                        if(state == State.LWALK || state == State.LRUN || state == State.LIDLE){
                            state = State.LJUMP;
                        }
                        break;
                    case A:
                            if (eventstate == 1 && state == State.IDLE) {
                                state = State.HEADBUTT;
                                eventstate = 0;
                            }
                            if (eventstate == 1 && state == State.LIDLE) {
                                state = State.LHEADBUTT;
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
                        if(state == State.JUMP){
                            state = State.JKICK;
                        }
                        if(state == State.LJUMP){
                            state = State.LJKICK;
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
                        if(state == State.ATTK2){
                            state=State.ULTIB2;
                        }
                        if(state == State.LATTK2){
                            state=State.LULTIB2;
                        }
                        if(state == State.ATTK1){
                            state=State.CHARGE;
                        }
                        if(state == State.LATTK1){
                            state=State.LCHARGE;
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
                        state = State.WASATK1;
                        break;
                    case NP9:
                        state = State.WASATK2;
                        break;
                    case NP6:
                        state = State.WASATK3;
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
                        move=0;
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
                        move=0;
                        break;
                }
            }
        });


        sprite = SpriteLoader.getSprite("images/scott_all/scott.json");
        sprite.addCallback(new Callback<Sprite>() {
            @Override
            public void onSuccess(Sprite result) {
                sprite.setSprite(spriteIndex);
                sprite.layer().setOrigin(sprite.width()/2f,(sprite.height()/2f) +40);
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

        PolygonShape shape = new PolygonShape();
        shape.setAsBox((sprite.layer().width()-60)* Gameplay00.M_PER_PIXEL/2,
                (sprite.layer().height()-70)*Gameplay00.M_PER_PIXEL/2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.1f;
        //fixtureDef.restitution = 1f;

        body.createFixture(fixtureDef);
        body.setLinearDamping(0.2f);
        body.setTransform(new Vec2(x,y),0f);
        return body;
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
                    rcount++;
                    break;
                case WALK:
                    if(!(spriteIndex>=16 && spriteIndex<=21)){
                        spriteIndex=16;
                    }
                    if(eventwarp>=1){
                        x-=51f;
                        eventwarp=0;
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
                    System.out.println("Scott Jump = "+spriteIndex);
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
                    if(spriteIndex>=59){
                        spriteIndex=59;
                    }
                    break;
                case CEL2:
                    if(!(spriteIndex>=65 && spriteIndex<=79)){
                        spriteIndex=65;
                    }
                    if(spriteIndex==79){
                        state = State.IDLE;
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
                    if(spriteIndex==92){
                        state = State.IDLE;
                    }
                    break;
                case CEL3:
                    if(!(spriteIndex>=93 && spriteIndex<=110)){
                        spriteIndex=93;
                    }
                    if(spriteIndex==110){
                        state = State.IDLE;
                    }
                    break;
                case ULTIK:
                    if(!(spriteIndex>=111 && spriteIndex<=122)){
                        spriteIndex=111;
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
                    if(spriteIndex>=141 && spriteIndex<=143){
                        spriteIndex=141;
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
                    System.out.println("Scott Charge = "+spriteIndex);
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
                    break;
                case COMEBACK:
                    if(!(spriteIndex>=206 && spriteIndex<=212)){
                        spriteIndex=206;
                    }
                    if(spriteIndex==212){
                        state = State.IDLE;
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
                case SLEEP:
                    if(!(spriteIndex>=236 && spriteIndex<=237)){
                        spriteIndex=236;
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
                    if(eventwarp==0){
                        x+=51f;
                        eventwarp++;
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
                    if(spriteIndex==272){
                        state=State.LIDLE;
                    }
                    System.out.println("Scott LJump = "+spriteIndex);
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
                    System.out.println("Scott LUltikick = "+spriteIndex);
                    break;
                case LATTK1:
                    if(!(spriteIndex>=298 && spriteIndex<=301)){
                        spriteIndex=298;
                    }
                    if(spriteIndex==301){
                        state = State.LIDLE;
                    }
                    System.out.println("Scott LATK1 = "+spriteIndex);
                    break;
                case LATTK2:
                    if(!(spriteIndex>=302 && spriteIndex<=307)){
                        spriteIndex=302;
                    }
                    if(spriteIndex==307){
                        state = State.LIDLE;
                    }
                    System.out.println("Scott LATK2 = "+spriteIndex);
                    break;
                case LATTK3:
                    if(!(spriteIndex>=308 && spriteIndex<=314)){
                        spriteIndex=308;
                    }
                    if(spriteIndex==314){
                        state = State.LIDLE;
                    }
                    System.out.println("Scott LATK3 = "+spriteIndex);
                    break;
                case LKICK1:
                    if(!(spriteIndex>=315 && spriteIndex<=321)){
                        spriteIndex=315;
                    }
                    if(spriteIndex==321){
                        state = State.LIDLE;
                    }
                    System.out.println("Scott lkick1 = "+spriteIndex);
                    break;
                case LKICK2:
                    if(!(spriteIndex>=322 && spriteIndex<=328)){
                        spriteIndex=322;
                    }
                    if(spriteIndex==328){
                        state = State.LIDLE;
                    }
                    System.out.println("Scott lkick2 = "+spriteIndex);
                    break;
                case LJKICK:
                    if(!(spriteIndex>=329 && spriteIndex<=335)){
                        spriteIndex=329;
                    }
                    if(spriteIndex>=333 && spriteIndex<=335){
                        spriteIndex=333;
                    }
                    System.out.println("Scott ljumpkick = "+spriteIndex);
                    break;
                case LULTIB1:
                    if(!(spriteIndex>=336 && spriteIndex<=347)){
                        spriteIndex=336;
                    }
                    if(spriteIndex==347){
                        state = State.LIDLE;
                    }
                    System.out.println("Scott LULTIBOOM1 = "+spriteIndex);
                    break;
                case LULTIB2:
                    if(!(spriteIndex>=348 && spriteIndex<=366)){
                        spriteIndex=348;
                    }
                    if(spriteIndex==366){
                        state = State.LIDLE;
                    }
                    System.out.println("Scott LULTIBOOM2 = "+spriteIndex);
                    break;
                case LCHARGE:
                    if(!(spriteIndex>=367 && spriteIndex<=383)){
                        spriteIndex=367;
                    }
                    if(spriteIndex==383){
                        state = State.LIDLE;
                    }
                    System.out.println("Scott lcharge = "+spriteIndex);
                    break;
                case LHEADBUTT:
                    if(!(spriteIndex>=384 && spriteIndex<=393)){
                        spriteIndex=384;
                    }
                    if(spriteIndex==393){
                        state = State.LIDLE;
                    }
                    System.out.println("Scott LHEADBUTT = "+spriteIndex);
                    break;

            }
            sprite.setSprite(spriteIndex);
            spriteIndex++;
            e=0;
        }
////////////////////////////////////////////////////////////////////////////////////////////////// add Motion on update method



////////////////////////////////////////////////////////////////////////////////////////////////// add Motion on update method

    }


    public void paint(Clock clock) {
        if(!hasLoaded) return;
        switch (state) {
            case WALK:
                body.applyForce(new Vec2(80f,0f),body.getPosition());
                break;
            case LWALK:
                body.applyForce(new Vec2(-80f,0f),body.getPosition());
                break;
            case JUMP:
                if(spriteIndex==23){
                    body.applyLinearImpulse(new Vec2(0f,-30f),body.getPosition());
                    System.out.println(body);
                }
                break;
            case LJUMP:
                if(spriteIndex==261){
                    body.applyLinearImpulse(new Vec2(0f,-30f),body.getPosition());
                }
                break;
            case CHARGE:
                if(spriteIndex==189){
                        body.applyLinearImpulse(new Vec2(15f,0f),body.getPosition());
                    if(contacted==true){
                        other.applyLinearImpulse(new Vec2(50f, -20f), other.getPosition());
                    }
                }
                break;
       }
        sprite.layer().setTranslation(
                (body.getPosition().x/Gameplay00.M_PER_PIXEL),
                (body.getPosition().y/Gameplay00.M_PER_PIXEL));
        //sprite.layer().setRotation(body.getAngle());

        //System.out.println(contacted);
    }

    public void contact(Contact contact) {
        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureB().getBody();

        if(body==a && (Gameplay00.bodies.get(b))!= "ground"){
            contacted = true;
            other = contact.getFixtureB().getBody();
        }else if(body==b && (Gameplay00.bodies.get(a))!= "ground"){
            contacted = true;
            other = contact.getFixtureA().getBody();
        }

        switch(state){
            case JUMP:
                state=State.IDLE;
                break;
            case LJUMP:
                state=State.LIDLE;
                break;
            case CHARGE:
                if (spriteIndex>=189&&spriteIndex<=191) {
                    if (a == body) {
                        b.applyLinearImpulse(new Vec2(100f, -50f), b.getPosition());
                    } else {
                        a.applyLinearImpulse(new Vec2(100f, -50f), a.getPosition());
                    }
                }
                break;
        }
    }


}
