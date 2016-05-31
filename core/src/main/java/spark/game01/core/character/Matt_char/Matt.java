package spark.game01.core.character.Matt_char;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import playn.core.Keyboard;
import playn.core.Layer;
import playn.core.PlayN;
import playn.core.util.Callback;
import playn.core.util.Clock;
import spark.game01.core.Screen.Gameplay00;
import spark.game01.core.Screen.Gameplay01;
import spark.game01.core.character.Scott_char.Scott;
import spark.game01.core.sprite.Sprite;
import spark.game01.core.sprite.SpriteLoader;

import java.util.Random;

public class Matt {

    private Sprite sprite;
    public int spriteIndex = 0;
    private boolean hasLoaded = false;
    public Body body;
    public Body other;
    public Boolean contacted = false;
    private Random random = new Random();
    private int i = 0;
    public int i1= 0;

    public enum State{
        IDLE,WALK,LIDLE,LWALK,KICK,ATTK1,LKICK,LATTK1,ULTI1,WASATTK1,
        LULTI1,LWASATTK1,WASATTK2,LWASATTK2,COMEBACK,LCOMEBACK,ULTI2,LULTI2
    };

    public State state = State.LIDLE;

    private int e = 0;

    public Matt(final World world, final float x, final float y){

        sprite = SpriteLoader.getSprite("images/matt_all/matt.json");
        sprite.addCallback(new Callback<Sprite>() {
            @Override
            public void onSuccess(Sprite result) {
                sprite.setSprite(spriteIndex);
                sprite.layer().setOrigin(sprite.width()/2f,sprite.height()/2f+60);
                sprite.layer().setTranslation(x,y);

                body = initPhysicsBody(world,
                        Gameplay01.M_PER_PIXEL * x,
                        Gameplay01.M_PER_PIXEL * y);
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

        Vec2[] vertices = {
                new Vec2(0.0f, - 2f),
                new Vec2(+ 1.0f, + 1.0f),
                new Vec2(- 1.0f, + 1.0f)
        };

        PolygonShape shape = new PolygonShape();
        shape.set(vertices, vertices.length);

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

    public void update(int delta,Scott scott) {
        if(hasLoaded == false) return;
        e = e + delta;
        if(e > 60){
            switch (state) {
                case IDLE:
                    if(!(spriteIndex>=0 && spriteIndex<=5)){
                        spriteIndex=0;
                    }
                    break;
                case WALK:
                    if(!(spriteIndex>=6 && spriteIndex<=15)){
                        spriteIndex=6;
                    }
                    break;
                case LIDLE:
                    if(!(spriteIndex>=16 && spriteIndex<=21)){
                        spriteIndex=16;
                    }
                    break;
                case LWALK:
                    if(!(spriteIndex>=22 && spriteIndex<=31)){
                        spriteIndex=22;
                    }
                    break;
                case KICK:
                    if(!(spriteIndex>=32 && spriteIndex<=38)){
                        spriteIndex=32;
                    }
                    if(spriteIndex==38){
                        state=State.IDLE;
                    }
                    break;
                case ATTK1:
                    if(!(spriteIndex>=39 && spriteIndex<=45)){
                        spriteIndex=39;
                    }
                    if(spriteIndex==45){
                        state=State.IDLE;
                    }
                    break;
                case LKICK:
                    if(!(spriteIndex>=46 && spriteIndex<=52)){
                        spriteIndex=46;
                    }
                    if(spriteIndex==52){
                        state=State.LIDLE;
                    }
                    break;
                case LATTK1:
                    if(!(spriteIndex>=53 && spriteIndex<=59)){
                        spriteIndex=53;
                    }
                    if(spriteIndex==59){
                        state=State.LIDLE;
                    }
                    break;
                case ULTI1:
                    if(!(spriteIndex>=60 && spriteIndex<=65)){
                        spriteIndex=60;
                    }
                    if(spriteIndex==63){
                        Gameplay01.spmatt -=40;
                    }
                    if(spriteIndex==65){
                        state=State.IDLE;
                    }
                    break;
                case WASATTK1:
                    if(!(spriteIndex>=66 && spriteIndex<=70)){
                        spriteIndex=66;
                    }
                    if(spriteIndex==70){
                        state=State.IDLE;
                    }
                    break;
                case LULTI1:
                    if(!(spriteIndex>=71 && spriteIndex<=76)){
                        spriteIndex=71;
                    }
                    if(spriteIndex==74){
                        Gameplay01.spmatt -=40;
                    }
                    if(spriteIndex==76){
                        state=State.LIDLE;
                    }
                    break;
                case LWASATTK1:
                    if(!(spriteIndex>=77 && spriteIndex<=81)){
                        spriteIndex=77;
                    }
                    if(spriteIndex==81){
                        state=State.LIDLE;
                    }
                    break;
                case WASATTK2:
                    if(!(spriteIndex>=82 && spriteIndex<=94)){
                        spriteIndex=82;
                    }
                    if(spriteIndex>=93&&spriteIndex<=94){
                        spriteIndex=93;
                    }
                    break;
                case LWASATTK2:
                    if(!(spriteIndex>=95 && spriteIndex<=107)){
                        spriteIndex=95;
                    }
                    if(spriteIndex>=106&&spriteIndex<=107){
                        spriteIndex=106;
                    }
                    break;
                case COMEBACK:
                    if(!(spriteIndex>=108 && spriteIndex<=118)){
                        spriteIndex=108;
                    }
                    if(spriteIndex==118){
                        state=State.IDLE;
                    }
                    break;
                case LCOMEBACK:
                    if(!(spriteIndex>=119 && spriteIndex<=129)){
                        spriteIndex=119;
                    }
                    if(spriteIndex==129){
                        state=State.LIDLE;
                    }
                    break;
                case ULTI2:
                    if(!(spriteIndex>=130 && spriteIndex<=141)){
                        spriteIndex=130;
                    }
                    if(spriteIndex==136){
                        Gameplay01.spmatt -=50;
                    }
                    if(spriteIndex==141){
                        state=State.IDLE;
                    }
                    break;
                case LULTI2:
                    if(!(spriteIndex>=142 && spriteIndex<=153)){
                        spriteIndex=142;
                    }
                    if(spriteIndex == 148){
                        Gameplay01.spmatt -=50;
                    }
                    if(spriteIndex==153){
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
            if((scott.spriteIndex>=227&& scott.spriteIndex<=235)||(scott.state== Scott.State.SLEEP)||(scott.state== Scott.State.LOSE)){
                state=State.LIDLE;
                body.setActive(true);
            }else if(state==State.LWALK && contacted==true){
                state=State.LIDLE;
            }else if((body.getPosition().x >= (scott.body.getPosition().x+3.1))
                    &&(state!=State.LWASATTK2&&state!=State.WASATTK2)){
                state=State.LWALK;
            }else if((body.getPosition().x <= (scott.body.getPosition().x+3))
                    &&(body.getPosition().x >= (scott.body.getPosition().x))
                    &&(state!=State.LWASATTK2&&state!=State.WASATTK2)){
                if(i==20) {
                    switch (random.nextInt(4)) {
                        case 0:
                            state = State.LATTK1;
                            break;
                        case 1:
                            state = State.LKICK;
                            break;
                        case 2:
                            if(Gameplay01.spmatt>=60) {
                                state = State.LULTI1;
                            }
                            break;
                        case 3:
                            if(Gameplay01.spmatt>=80) {
                                state = State.LULTI2;
                            }
                            break;
                    }
                }

                if(contacted == true && (scott.spriteIndex==189|| scott.spriteIndex==381)){ // lwasattk2
                    state=State.LWASATTK2;
                    body.applyLinearImpulse(new Vec2(40f, -30f), other.getPosition());
                    Gameplay01.scorem -=15;
                }
                if(contacted==true && (scott.spriteIndex==42
                        ||scott.spriteIndex==46||scott.spriteIndex==49
                        ||scott.spriteIndex==52||scott.spriteIndex==55)){             //lwasattk
                    state=State.LWASATTK1;
                    Gameplay01.scorem -=1;
                    Gameplay00.spscott+=1;
                }
                if(contacted==true && (scott.spriteIndex == 196||scott.spriteIndex == 388)){             //lheadbutt
                    state=State.LWASATTK2;
                    body.applyLinearImpulse(new Vec2(40f, -30f), other.getPosition());
                    Gameplay01.scorem -=10;
                }
                if(contacted==true && (scott.spriteIndex==125
                        || scott.spriteIndex==132 || scott.spriteIndex==159
                        || scott.spriteIndex==162)){
                    state=State.LWASATTK1;
                    Gameplay01.scorem -=1;
                    Gameplay00.spscott+=1;
                }
                if(contacted==true && (scott.spriteIndex==147 || scott.spriteIndex==339
                        || scott.spriteIndex == 167)){
                    state=State.LWASATTK2;
                    body.applyLinearImpulse(new Vec2(0f, -100f), other.getPosition());
                    Gameplay01.scorem -=20;
                }
                if(Gameplay01.scorem<=0){
                    state=State.LWASATTK2;
                    body.applyLinearImpulse(new Vec2(10f,-50f),body.getPosition());
                }
            }
            //##########################  LEFT   ##########################################
            if((scott.spriteIndex>=408&& scott.spriteIndex<=416)||(scott.state== Scott.State.LSLEEP)){
                state=State.IDLE;
                body.setActive(true);
            }else if(state==State.WALK && contacted==true){
                state=State.IDLE;
            }else if((body.getPosition().x <= (scott.body.getPosition().x-3.1))
                    &&(state!=State.WASATTK2)&&(state!=State.LWASATTK2)){
                state=State.WALK;
            }else if((body.getPosition().x >= (scott.body.getPosition().x-3))
                    &&(body.getPosition().x <= (scott.body.getPosition().x))
                    &&(state!=State.WASATTK2&&state!=State.LWASATTK2)){
                if(i==20) {
                    switch (random.nextInt(4)) {
                        case 0:
                            state = State.ATTK1;
                            break;
                        case 1:
                            state = State.KICK;
                            break;
                        case 2:
                            if(Gameplay01.spmatt>=60) {
                                state = State.ULTI1;
                            }
                            break;
                        case 3:
                            if(Gameplay01.spmatt>=80) {
                                state = State.ULTI2;
                            }
                            break;
                    }
                }
                if(contacted == true && (scott.spriteIndex==381
                        || scott.spriteIndex==189)){                                    // wasattk2
                    state=State.WASATTK2;
                    body.applyLinearImpulse(new Vec2(-40f, -30f), other.getPosition());
                    Gameplay01.scorem -=15;
                }
                if(contacted==true && (scott.spriteIndex==299
                        ||scott.spriteIndex==303||scott.spriteIndex==306
                        ||scott.spriteIndex==309||scott.spriteIndex==312)){             //lwasattk
                    state=State.WASATTK1;
                    Gameplay01.scorem -=1;
                    Gameplay00.spscott+=1;
                }
                if(contacted==true && (scott.spriteIndex == 388||scott.spriteIndex == 196)){             //headbutt
                    state=State.WASATTK2;
                    body.applyLinearImpulse(new Vec2(-40f, -30f), other.getPosition());
                    Gameplay01.scorem -=10;
                }
                if(contacted==true && (scott.spriteIndex==317
                        || scott.spriteIndex==324||scott.spriteIndex==351
                        || scott.spriteIndex==354)){
                    state=State.WASATTK1;
                    Gameplay01.scorem -=1;
                    Gameplay00.spscott+=1;
                }
                if(contacted==true && (scott.spriteIndex==147 || scott.spriteIndex==339
                        || scott.spriteIndex==359)){
                    state=State.WASATTK2;
                    body.applyLinearImpulse(new Vec2(0f, -100f), other.getPosition());
                    Gameplay01.scorem -=20;
                }
                if(Gameplay01.scorem<=0){
                    state=State.WASATTK2;
                    body.applyLinearImpulse(new Vec2(-10f,-50f),body.getPosition());

                }
            }

            switch (state){
                case WALK:
                    body.applyForce(new Vec2(66f,0f),body.getPosition());
                    break;
                case LWALK:
                    body.applyForce(new Vec2(-66f,0f),body.getPosition());
                    break;
                case ATTK1:
                    body.applyLinearImpulse(new Vec2(5f,0f),body.getPosition());
                    break;
                case LATTK1:
                    body.applyLinearImpulse(new Vec2(-5f,0f),body.getPosition());
                    break;
                case KICK:
                    body.applyLinearImpulse(new Vec2(5f,0f),body.getPosition());
                    break;
                case LKICK:
                    body.applyLinearImpulse(new Vec2(-5f,0f),body.getPosition());
                    break;
                case WASATTK2:
                    if((i1>=1&&i1<=50)&&(Gameplay01.scorem>0)){
                        i1--;
                        if(i1==1){
                            state=State.COMEBACK;
                            body.setActive(true);
                            i1=0;
                        }
                    }
                    break;
                case LWASATTK2:
                    if((i1>=1&&i1<=50)&&(Gameplay01.scorem>0)){
                        i1--;
                        if(i1==1){
                            state=State.LCOMEBACK;
                            body.setActive(true);
                            i1=0;
                        }
                    }
                    break;
                case ULTI1:
                    if(spriteIndex==63) {
                        body.applyLinearImpulse(new Vec2(15f, 0f), body.getPosition());
                    }
                    break;
                case LULTI1:
                    if(spriteIndex==74) {
                        body.applyLinearImpulse(new Vec2(-15f, 0f), body.getPosition());
                    }
                    break;
                case ULTI2:
                    if(spriteIndex==136) {
                        body.applyLinearImpulse(new Vec2(15f, 0f), body.getPosition());
                    }
                    break;
                case LULTI2:
                    if(spriteIndex==148) {
                        body.applyLinearImpulse(new Vec2(-15f, 0f), body.getPosition());
                    }
                    break;
            }
        }catch (Exception e){

        }
        if(i>=20){
            i=0;
        }
        if(Gameplay01.spmatt>=100){
            Gameplay01.spmatt=100;
        }else if(Gameplay01.spmatt<=0){
            Gameplay01.spmatt=0;
        }
        if(Gameplay00.spscott>=100){
            Gameplay00.spscott=100;
        }else if(Gameplay00.spscott<=0){
            Gameplay00.spscott=0;
        }

        if(Gameplay01.scorem<=0){
            Gameplay01.scorem=0;
        }
        Gameplay01.debugSring1 = "HpScore = "+Gameplay01.scorem;
        Gameplay01.debugSring2 = "SP tom = "+Gameplay01.spmatt;
        Gameplay01.debugSring3 = "SP scott = "+Gameplay00.spscott;
    }


    public void paint(Clock clock){
        if(!hasLoaded) return;
        sprite.layer().setTranslation(
                (body.getPosition().x/ Gameplay01.M_PER_PIXEL),
                body.getPosition().y/Gameplay01.M_PER_PIXEL);
    }


}
