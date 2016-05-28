package spark.game01.core.character.Tom_char;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.contacts.Contact;
import playn.core.Layer;
import playn.core.PlayN;
import playn.core.util.Callback;
import playn.core.util.Clock;
import spark.game01.core.Screen.Gameplay00;
import spark.game01.core.character.Scott_char.Scott;
import spark.game01.core.sprite.Sprite;
import spark.game01.core.sprite.SpriteLoader;
import java.util.Random;

public class Tom {
    private Sprite sprite;
    public int spriteIndex = 0;
    private boolean hasLoaded = false;
    public Body body;
    public Boolean contacted = false;
    private Random random = new Random();
    private int i = 0;
    public int i1= 0;
    public Body other;


    public enum State{
        IDLE,LIDLE,WALK,LWALK,JUMP,RUN,ATTK1,KICK1,JPUN,
        LJUMP,LRUN,LATTK1,LKICK1,LJPUN,SS,DEF,WASATTK,
        LSS,LDEF,LWASATTK,WASATTK2,COMBACK,LWASATTK2,LCOMBACK
    };

    public State state = State.LIDLE;

    private int e = 0;

    public Tom(final World world,final float x, final float y){

        sprite = SpriteLoader.getSprite("images/tom_all/tom.json");
        sprite.addCallback(new Callback<Sprite>() {
            @Override
            public void onSuccess(Sprite result) {
                sprite.setSprite(spriteIndex);
                sprite.layer().setOrigin(sprite.width()/2f,sprite.height()/2f+40);
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

    private Body initPhysicsBody(World world, float x, float y){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position = new Vec2(0,0);
        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox((sprite.layer().width()-60)* Gameplay00.M_PER_PIXEL/2,
                (sprite.layer().height()-90)*Gameplay00.M_PER_PIXEL/2);


        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 1f;
        //fixtureDef.restitution = 1f;

        body.createFixture(fixtureDef);
        body.setLinearDamping(0.2f);
        body.setTransform(new Vec2(x,y),0f);
        body.setFixedRotation(true);
        return body;
    }


    public void update(int delta,Scott scott) {
        if(hasLoaded == false) return;
        e = e + delta;
        if(e > 60){
            switch (state) {
                case IDLE:
                    if(!(spriteIndex>=0 && spriteIndex<=3)){
                        spriteIndex=0;
                    }
                    break;
                case WALK:
                    if(!(spriteIndex>=4 && spriteIndex<=11)){
                        spriteIndex=4;
                    }
                    break;
                case LIDLE:
                    if(!(spriteIndex>=12 && spriteIndex<=15)){
                        spriteIndex=12;
                    }

                    break;
                case LWALK:
                    if(!(spriteIndex>=16 && spriteIndex<=23)){
                        spriteIndex=16;
                    }
                    break;
                case JUMP:
                    if(!(spriteIndex>=24 && spriteIndex<=32)){
                        spriteIndex=24;
                    }
                    break;
                case ATTK1:
                    if(!(spriteIndex>=33 && spriteIndex<=37)){
                        spriteIndex=33;
                    }
                    if(spriteIndex==37){
                        state=State.IDLE;
                    }
                    break;
                case KICK1:
                    if(!(spriteIndex>=38 && spriteIndex<=43)){
                        spriteIndex=38;
                    }
                    if(spriteIndex==43){
                        state=State.IDLE;
                    }
                    break;
                case JPUN:
                    if(!(spriteIndex>=44 && spriteIndex<=48)){
                        spriteIndex=44;
                    }
                    if(spriteIndex==48){
                        state=State.IDLE;
                    }
                    break;
                case LJUMP:
                    if(!(spriteIndex>=49 && spriteIndex<=57)){
                        spriteIndex=49;
                    }
                    break;
                case LATTK1:
                    if(!(spriteIndex>=58 && spriteIndex<=62)){
                        spriteIndex=58;
                    }
                    if(spriteIndex==62){
                        state=State.LIDLE;
                    }
                    break;
                case LKICK1:
                    if(!(spriteIndex>=63 && spriteIndex<=68)){
                        spriteIndex=63;
                    }
                    if(spriteIndex==68){
                        state=State.LIDLE;
                    }
                    break;
                case LJPUN:
                    if(!(spriteIndex>=69 && spriteIndex<=73)){
                        spriteIndex=69;
                    }
                    if(spriteIndex==73){
                        state=State.LIDLE;
                    }
                    break;
                case SS:
                    if(!(spriteIndex>=74 && spriteIndex<=87)){
                        spriteIndex=74;
                    }
                    if(spriteIndex==87){
                        state=State.IDLE;
                    }
                    break;
                case DEF:
                    if(!(spriteIndex>=88 && spriteIndex<=90)){
                        spriteIndex=88;
                    }
                    if(spriteIndex==90){
                        state=State.IDLE;
                    }
                    break;
                case WASATTK:
                    if(!(spriteIndex>=91 && spriteIndex<=93)){
                        spriteIndex=91;
                    }
                    if(spriteIndex==93){
                        state=State.IDLE;
                    }
                    break;
                case LSS:
                    if(!(spriteIndex>=94 && spriteIndex<=107)){
                        spriteIndex=94;
                    }
                    if(spriteIndex==107){
                        state=State.LIDLE;
                    }
                    break;
                case LDEF:
                    if(!(spriteIndex>=108 && spriteIndex<=110)){
                        spriteIndex=108;
                    }
                    if(spriteIndex==110){
                        state=State.LIDLE;
                    }
                    break;
                case LWASATTK:
                    if(!(spriteIndex>=111 && spriteIndex<=113)){
                        spriteIndex=111;
                    }
                    if(spriteIndex==113){
                        state=State.LIDLE;
                    }
                    break;
                case WASATTK2:
                    if(!(spriteIndex>=114 && spriteIndex<=129)){
                        spriteIndex=114;
                    }
                    if(spriteIndex>=128&&spriteIndex<=129){
                        spriteIndex=128;
                    }
                    break;
                case COMBACK:
                    if(!(spriteIndex>=130 && spriteIndex<=137)){
                        spriteIndex=130;
                    }
                    if(spriteIndex==137){
                        state=State.IDLE;
                    }
                    break;
                case LWASATTK2:
                    if(!(spriteIndex>=138 && spriteIndex<=153)){
                        spriteIndex=138;
                    }
                    if(spriteIndex>=152&&spriteIndex<=153){
                        spriteIndex=152;
                    }
                    break;
                case LCOMBACK:
                    if(!(spriteIndex>=154 && spriteIndex<=161)){
                        spriteIndex=154;
                    }
                    if(spriteIndex==161){
                        state=State.LIDLE;
                    }
                    break;
            }
            sprite.setSprite(spriteIndex);
            spriteIndex++;
            e=0;
        }
        try{
            i++;
            if((scott.state== Scott.State.SLEEP)||(scott.state== Scott.State.LOSE)){
                state=State.LIDLE;
                body.setActive(true);
            }else if(scott.state== Scott.State.LSLEEP){
                state=State.IDLE;
                body.setActive(true);
            }
            if((body.getPosition().x >= (scott.body.getPosition().x+4))
                    &&(state!=State.LWASATTK2&&state!=State.WASATTK2)){
                state=State.LWALK;
            }else if(((body.getPosition().x <= (scott.body.getPosition().x+3))
                    &&(body.getPosition().x >= (scott.body.getPosition().x)))
                    &&(state!=State.LWASATTK2&&state!=State.WASATTK2)){
                if(i==25) {
                    switch (random.nextInt(3)) {
                        case 0:
                            state = State.LATTK1;
                            break;
                        case 1:
                            state = State.LKICK1;
                            break;
                        case 2:
                            if(Gameplay00.sptom>=80) {
                                state = State.LSS;
                            }
                            break;
                    }
                }

                if(contacted == true && (scott.spriteIndex==189|| scott.spriteIndex==381)){ // lwasattk2
                    state=State.LWASATTK2;
                    Gameplay00.scoret -=15;
                }
                if(contacted==true && (scott.spriteIndex==42
                        ||scott.spriteIndex==46||scott.spriteIndex==49
                        ||scott.spriteIndex==52||scott.spriteIndex==55)){             //lwasattk
                    state=State.LWASATTK;
                    Gameplay00.scoret -=1;
                    Gameplay00.spscott+=1;
                }
                if(contacted==true && (scott.spriteIndex == 196||scott.spriteIndex == 388)){             //lheadbutt
                    state=State.LWASATTK2;
                    Gameplay00.scoret -=10;
                }
                if(contacted==true && (scott.spriteIndex==125
                        || scott.spriteIndex==132 || scott.spriteIndex==159
                        || scott.spriteIndex==162)){
                    state=State.LWASATTK;
                    Gameplay00.scoret -=1;
                    Gameplay00.spscott+=1;
                }
                if(contacted==true && (scott.spriteIndex==147 || scott.spriteIndex==339
                        || scott.spriteIndex == 167)){
                    state=State.LWASATTK2;
                    Gameplay00.scoret -=20;
                }
                if(Gameplay00.scoret<=0){
                    state=State.LWASATTK2;
                    body.applyLinearImpulse(new Vec2(10f,-50f),body.getPosition());
                }
            }
            //##########################  LEFT   ##########################################
            if((body.getPosition().x <= (scott.body.getPosition().x-4))
                    &&(state!=State.WASATTK2)&&(state!=State.LWASATTK2)){
                state=State.WALK;
            }else if(((body.getPosition().x >= (scott.body.getPosition().x-3))&&
                    (body.getPosition().x <= (scott.body.getPosition().x)))
                    &&(state!=State.WASATTK2)&&(state!=State.LWASATTK2)){
                if(i==25) {
                    switch (random.nextInt(3)) {
                        case 0:
                            state = State.ATTK1;
                            break;
                        case 1:
                            state = State.KICK1;
                            break;
                        case 2:
                            if(Gameplay00.sptom>=80) {
                                state = State.SS;
                            }
                            break;
                    }
                }
                if(contacted == true && (scott.spriteIndex==381
                        || scott.spriteIndex==189)){                                    // wasattk2
                    state=State.WASATTK2;
                    Gameplay00.scoret -=15;
                }
                if(contacted==true && (scott.spriteIndex==299
                        ||scott.spriteIndex==303||scott.spriteIndex==306
                        ||scott.spriteIndex==309||scott.spriteIndex==312)){             //lwasattk
                    state=State.WASATTK;
                    Gameplay00.scoret -=1;
                    Gameplay00.spscott+=1;
                }
                if(contacted==true && (scott.spriteIndex == 388||scott.spriteIndex == 196)){             //headbutt
                    state=State.WASATTK2;
                    Gameplay00.scoret -=10;
                }
                if(contacted==true && (scott.spriteIndex==317
                        || scott.spriteIndex==324||scott.spriteIndex==351
                        || scott.spriteIndex==354)){
                    state=State.WASATTK;
                    Gameplay00.scoret -=1;
                    Gameplay00.spscott+=1;
                }
                if(contacted==true && (scott.spriteIndex==147 || scott.spriteIndex==339
                        || scott.spriteIndex==359)){
                    state=State.WASATTK2;
                    Gameplay00.scoret -=20;
                }
                if(Gameplay00.scoret<=0){
                    state=State.WASATTK2;
                    body.applyLinearImpulse(new Vec2(-10f,-50f),body.getPosition());

                }

            }

            switch (state){
                case WALK:
                    body.applyForce(new Vec2(80f,0f),body.getPosition());
                    break;
                case LWALK:
                    body.applyForce(new Vec2(-80f,0f),body.getPosition());
                    break;
                case LATTK1:
                    if(spriteIndex==59){
                        body.applyLinearImpulse(new Vec2(-5f,0f),body.getPosition());
                    }
                    break;
                case LKICK1:
                    if(spriteIndex==65){
                        body.applyLinearImpulse(new Vec2(-5f,0f),body.getPosition());
                    }
                    break;
                case LSS:
                    if(spriteIndex==98){
                        body.applyLinearImpulse(new Vec2(-15f,0f),body.getPosition());
                        if(contacted==true){
                            other.applyLinearImpulse(new Vec2(-20f, -20f), other.getPosition());
                        }
                    }
                    break;
                case ATTK1:
                    if(spriteIndex==34) {
                        body.applyLinearImpulse(new Vec2(5f, 0f), body.getPosition());
                    }
                    break;
                case KICK1:
                    if(spriteIndex==40){
                        body.applyLinearImpulse(new Vec2(5f,0f),body.getPosition());
                    }
                    break;
                case SS:
                    if(spriteIndex==78){
                        body.applyLinearImpulse(new Vec2(15f,0f),body.getPosition());
                        if(contacted==true){
                            other.applyLinearImpulse(new Vec2(20f, -20f), other.getPosition());
                        }
                    }
                    break;
                case WASATTK2:
                    if((i1>=1&&i1<=50)&&(Gameplay00.scoret>0)){
                        i1--;
                        if(i1==1){
                            state=State.COMBACK;
                            body.setActive(true);
                            i1=0;
                        }
                    }
                    break;
                case LWASATTK2:
                    if((i1>=1&&i1<=50)&&(Gameplay00.scoret>0)){
                        i1--;
                        if(i1==1){
                            state=State.LCOMBACK;
                            body.setActive(true);
                            i1=0;
                        }
                    }
                    break;
                case JUMP:
                    body.applyLinearImpulse(new Vec2(0f,-10f),body.getPosition());
                    break;
                case LJUMP:
                    body.applyLinearImpulse(new Vec2(0f,-10f),body.getPosition());
                    break;
            }
        }catch (Exception e){

        }
        if(i>=25){
            i=0;
        }
        if(Gameplay00.sptom>=100){
            Gameplay00.sptom=100;
        }else if(Gameplay00.sptom<=0){
            Gameplay00.sptom=0;
        }
        if(Gameplay00.spscott>=100){
            Gameplay00.spscott=100;
        }else if(Gameplay00.spscott<=0){
            Gameplay00.spscott=0;
        }

        if(Gameplay00.scoret<=0){
            Gameplay00.scoret=0;
        }
        Gameplay00.debugSring1 = "HpScore = "+Gameplay00.scoret;
        Gameplay00.debugSring2 = "SP tom = "+Gameplay00.sptom;
        Gameplay00.debugSring3 = "SP scott = "+Gameplay00.spscott;
    }


    public void paint(Clock clock) {
        if(!hasLoaded) return;
        sprite.layer().setTranslation(
                (body.getPosition().x/Gameplay00.M_PER_PIXEL),
                body.getPosition().y/Gameplay00.M_PER_PIXEL);
    }

}
