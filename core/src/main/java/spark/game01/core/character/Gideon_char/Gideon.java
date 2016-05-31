package spark.game01.core.character.Gideon_char;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import playn.core.Keyboard;
import playn.core.Layer;
import playn.core.PlayN;
import playn.core.util.Callback;
import playn.core.util.Clock;
import spark.game01.core.Screen.Gameplay00;
import spark.game01.core.Screen.Gameplay02;
import spark.game01.core.character.Scott_char.Scott;
import spark.game01.core.sprite.Sprite;
import spark.game01.core.sprite.SpriteLoader;
import java.util.Random;

public class Gideon {
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
        IDLE,WALK,WASATTK2,WASATTK1,LIDLE,LWALK,LWASATTK2,ATTK1,ATTK2,LATTK1,LATTK2,
        ATTK3,LATTK3,ATTK4,LATTK4,ATTK5,CHARGE,LATTK5,LCHARGE,COMEBACK,LCOMEBACK,LWASATTK1
    };

    public State state = State.LIDLE;
    private int e = 0;

    public Gideon(final World world, final float x, final float y){
        sprite = SpriteLoader.getSprite("images/gideon_all/gideon.json");
        sprite.addCallback(new Callback<Sprite>() {
            @Override
            public void onSuccess(Sprite result) {
                sprite.setSprite(spriteIndex);
                sprite.layer().setOrigin(sprite.width()/2f,sprite.height()/2f+60);
                sprite.layer().setTranslation(x,y);

                body = initPhysicsBody(world,
                        Gameplay02.M_PER_PIXEL * x,
                        Gameplay02.M_PER_PIXEL * y);
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

    public void update(int delta,Scott scott){
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
                    if(!(spriteIndex>=4 && spriteIndex<=9)){
                        spriteIndex=4;
                    }
                    break;
                case WASATTK2:
                    if(!(spriteIndex>=10 && spriteIndex<=20)){
                        spriteIndex=10;
                    }
                    if(spriteIndex>=19 && spriteIndex<=20){
                        spriteIndex=19;
                    }
                    break;
                case WASATTK1:
                    if(!(spriteIndex>=21 && spriteIndex<=25)){
                        spriteIndex=21;
                    }
                    if(spriteIndex==25){
                        state=State.IDLE;
                    }
                    break;
                case LWASATTK1:
                    if(!(spriteIndex>=181 && spriteIndex<=185)){
                        spriteIndex=181;
                    }
                    if(spriteIndex==185){
                        state=State.LIDLE;
                    }
                    break;
                case LIDLE:
                    if(!(spriteIndex>=26 && spriteIndex<=29)){
                        spriteIndex=26;
                    }
                    break;
                case LWALK:
                    if(!(spriteIndex>=30 && spriteIndex<=35)){
                        spriteIndex=30;
                    }
                    break;
                case LWASATTK2:
                    if(!(spriteIndex>=36 && spriteIndex<=46)){
                        spriteIndex=36;
                    }
                    if(spriteIndex>=45 && spriteIndex<=46){
                        spriteIndex=45;
                    }
                    break;
                case ATTK1:
                    if(!(spriteIndex>=47 && spriteIndex<=53)){
                        spriteIndex=47;
                    }
                    if(spriteIndex==53){
                        state=State.IDLE;
                    }
                    break;
                case ATTK2:
                    if(!(spriteIndex>=54 && spriteIndex<=61)){
                        spriteIndex=54;
                    }
                    if(spriteIndex==61){
                        state=State.IDLE;
                    }
                    break;
                case LATTK1:
                    if(!(spriteIndex>=62 && spriteIndex<=68)){
                        spriteIndex=62;
                    }
                    if(spriteIndex==68){
                        state=State.LIDLE;
                    }
                    break;
                case LATTK2:
                    if(!(spriteIndex>=69 && spriteIndex<=76)){
                        spriteIndex=69;
                    }
                    if(spriteIndex==76){
                        state=State.LIDLE;
                    }
                    break;
                case ATTK3:
                    if(!(spriteIndex>=77 && spriteIndex<=88)){
                        spriteIndex=77;
                    }
                    if(spriteIndex==88){
                        state=State.IDLE;
                    }
                    break;
                case LATTK3:
                    if(!(spriteIndex>=89 && spriteIndex<=100)){
                        spriteIndex=89;
                    }
                    if(spriteIndex==100){
                        state=State.LIDLE;
                    }
                    break;
                case ATTK4:
                    if(!(spriteIndex>=101 && spriteIndex<=113)){
                        spriteIndex=101;
                    }
                    if(spriteIndex==109){
                        Gameplay02.spgideon -=20;
                    }
                    if(spriteIndex==113){
                        state=State.IDLE;
                    }
                    break;
                case LATTK4:
                    if(!(spriteIndex>=114 && spriteIndex<=126)){
                        spriteIndex=114;
                    }
                    if(spriteIndex==122){
                        Gameplay02.spgideon -=20;
                    }
                    if(spriteIndex==126){
                        state=State.LIDLE;
                    }
                    break;
                case ATTK5:
                    if(!(spriteIndex>=127 && spriteIndex<=134)){
                        spriteIndex=127;
                    }
                    if(spriteIndex==129){
                        Gameplay02.spgideon -=20;
                    }
                    if(spriteIndex==134){
                        state=State.IDLE;
                    }
                    break;
                case CHARGE:
                    if(!(spriteIndex>=135 && spriteIndex<=141)){
                        spriteIndex=135;
                    }
                    if(spriteIndex==138){
                        Gameplay02.spgideon -=20;
                    }
                    if(spriteIndex==141){
                        state=State.IDLE;
                    }
                    break;
                case LATTK5:
                    if(!(spriteIndex>=142 && spriteIndex<=149)){
                        spriteIndex=142;
                    }
                    if(spriteIndex==144){
                        Gameplay02.spgideon -=20;
                    }
                    if(spriteIndex==149){
                        state=State.LIDLE;
                    }
                    break;
                case LCHARGE:
                    if(!(spriteIndex>=150 && spriteIndex<=156)){
                        spriteIndex=150;
                    }
                    if(spriteIndex==153){
                        Gameplay02.spgideon -=20;
                    }
                    if(spriteIndex==156){
                        state=State.LIDLE;
                    }
                    break;
                case COMEBACK:
                    if(!(spriteIndex>=157 && spriteIndex<=168)){
                        spriteIndex=157;
                    }
                    if(spriteIndex==168){
                        state=State.IDLE;
                    }
                    break;
                case LCOMEBACK:
                    if(!(spriteIndex>=169 && spriteIndex<=180)){
                        spriteIndex=169;
                    }
                    if(spriteIndex==180){
                        state=State.LIDLE;
                    }
                    break;
            }
            sprite.setSprite(spriteIndex);
            spriteIndex++;
            e=0;
        }

        try {
            i++;
            if ((scott.spriteIndex>=227&& scott.spriteIndex<=235)||(scott.state == Scott.State.SLEEP) || (scott.state == Scott.State.LOSE)) {
                state = State.IDLE;
                body.setActive(true);
            }else if(state==State.LWALK && contacted==true){
                state=State.LIDLE;
            }else if ((body.getPosition().x >= (scott.body.getPosition().x + 3.1))
                    && (state != State.LWASATTK2 && state != State.WASATTK2)) {
                if (i == 30) {
                    switch (random.nextInt(3)) {
                        case 0:
                            state = State.LWALK;
                            break;
                        case 1:
                            state = State.LWALK;
                            break;
                        case 2:
                            state = State.LIDLE;
                            break;
                    }
                }
            } else if ((body.getPosition().x <= (scott.body.getPosition().x + 3))
                    &&(body.getPosition().x >= (scott.body.getPosition().x))
                    && (state != State.LWASATTK2 && state != State.WASATTK2)) {
                if (i == 30) {
                    switch (random.nextInt(7)) {
                        case 0:
                            state = State.LATTK1;
                            break;
                        case 1:
                            state = State.LATTK2;
                            break;
                        case 2:
                            state = State.LATTK3;
                            break;
                        case 3:
                            if(Gameplay02.spgideon>=70) {
                                state = State.LATTK4;
                            }
                            break;
                        case 4:
                            if(Gameplay02.spgideon>=80) {
                                state = State.LATTK5;
                            }
                            break;
                        case 5:
                            if(Gameplay02.spgideon>=60) {
                                state = State.LCHARGE;
                            }
                            break;
                        case 6:
                            state = State.LIDLE;
                            break;
                    }
                }

                if (contacted == true && (scott.spriteIndex == 189 || scott.spriteIndex == 381)) { // lwasattk2
                    state = State.LWASATTK2;
                    body.applyLinearImpulse(new Vec2(40f, -30f), other.getPosition());
                    Gameplay02.scoreg -= 15;
                }
                if (contacted == true && (scott.spriteIndex == 42
                        || scott.spriteIndex == 46 || scott.spriteIndex == 49
                        || scott.spriteIndex == 52 || scott.spriteIndex == 55)) {             //lwasattk
                    state = State.LWASATTK1;
                    Gameplay02.scoreg -= 1;
                    Gameplay00.spscott+=1;
                }
                if (contacted == true && (scott.spriteIndex == 196 || scott.spriteIndex == 388)) {             //lheadbutt
                    state = State.LWASATTK2;
                    body.applyLinearImpulse(new Vec2(40f, -30f), other.getPosition());
                    Gameplay02.scoreg -= 10;
                }
                if (contacted == true && (scott.spriteIndex == 125
                        || scott.spriteIndex == 132 || scott.spriteIndex == 159
                        || scott.spriteIndex == 162)) {
                    state = State.LWASATTK1;
                    Gameplay02.scoreg -= 1;
                    Gameplay00.spscott+=1;
                }
                if (contacted == true && (scott.spriteIndex == 147 || scott.spriteIndex == 339
                        || scott.spriteIndex == 167)) {
                    state = State.LWASATTK2;
                    body.applyLinearImpulse(new Vec2(0f, -100f), other.getPosition());
                    Gameplay02.scoreg -= 20;
                }
                if (Gameplay02.scoreg <= 0) {
                    state = State.LWASATTK2;
                    body.applyLinearImpulse(new Vec2(10f, -50f), body.getPosition());
                }
            }
            //##########################  LEFT   ##########################################
            if ((scott.spriteIndex>=408&& scott.spriteIndex<=416)||(scott.state == Scott.State.LSLEEP)) {
                state = State.LIDLE;
                body.setActive(true);
            }else if(state==State.WALK && contacted==true){
                state=State.IDLE;
            }else if ((body.getPosition().x <= (scott.body.getPosition().x - 3.1))
                    && (state != State.WASATTK2) && (state != State.LWASATTK2)) {
                if (i == 30) {
                    switch (random.nextInt(3)) {
                        case 0:
                            state = State.WALK;
                            break;
                        case 1:
                            state = State.WALK;
                            break;
                        case 2:
                            state = State.IDLE;
                            break;
                    }
                }
            } else if ((body.getPosition().x >= (scott.body.getPosition().x - 3))
                    &&(body.getPosition().x <= (scott.body.getPosition().x))
                    && (state != State.WASATTK2 && state != State.LWASATTK2)) {
                if (i == 30) {
                    switch (random.nextInt(7)) {
                        case 0:
                            state = State.ATTK1;
                            break;
                        case 1:
                            state = State.ATTK2;
                            break;
                        case 2:
                            state = State.ATTK3;
                            break;
                        case 3:
                            if(Gameplay02.spgideon>=70) {
                                state = State.ATTK4;
                            }
                            break;
                        case 4:
                            if(Gameplay02.spgideon>=80) {
                                state = State.ATTK5;
                            }
                            break;
                        case 5:
                            if(Gameplay02.spgideon>=60) {
                                state = State.CHARGE;
                            }
                            break;
                        case 6:
                            state = State.IDLE;
                            break;
                    }
                }
                if (contacted == true && (scott.spriteIndex == 381
                        || scott.spriteIndex == 189)) {                                    // wasattk2
                    state = State.WASATTK2;
                    body.applyLinearImpulse(new Vec2(-40f, -30f), other.getPosition());
                    Gameplay02.scoreg -= 15;
                }
                if (contacted == true && (scott.spriteIndex == 299
                        || scott.spriteIndex == 303 || scott.spriteIndex == 306
                        || scott.spriteIndex == 309 || scott.spriteIndex == 312)) {             //lwasattk
                    state = State.WASATTK1;
                    Gameplay02.scoreg -= 1f;
                    Gameplay00.spscott+=1;
                }
                if (contacted == true && (scott.spriteIndex == 388 || scott.spriteIndex == 196)) {             //headbutt
                    state = State.WASATTK2;
                    body.applyLinearImpulse(new Vec2(-40f, -30f), other.getPosition());
                    Gameplay02.scoreg -= 10;
                }
                if (contacted == true && (scott.spriteIndex == 317
                        || scott.spriteIndex == 324 || scott.spriteIndex == 351
                        || scott.spriteIndex == 354)) {
                    state = State.WASATTK1;
                    Gameplay02.scoreg -= 1;
                    Gameplay00.spscott+=1;
                }
                if (contacted == true && (scott.spriteIndex == 147 || scott.spriteIndex == 339
                        || scott.spriteIndex == 359)) {
                    state = State.WASATTK2;
                    body.applyLinearImpulse(new Vec2(0f, -100f), other.getPosition());
                    Gameplay02.scoreg -= 20;
                }
                if (Gameplay02.scoreg <= 0) {
                    state = State.WASATTK2;
                    body.applyLinearImpulse(new Vec2(-10f, -50f), body.getPosition());

                }
            }

            switch(state){
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
                case ATTK2:
                    body.applyLinearImpulse(new Vec2(5f,0f),body.getPosition());
                    break;
                case LATTK2:
                    body.applyLinearImpulse(new Vec2(-5f,0f),body.getPosition());
                    break;
                case ATTK3:
                    if(spriteIndex==80 || spriteIndex == 82){
                        body.applyLinearImpulse(new Vec2(5f,0f),body.getPosition());
                    }
                    break;
                case LATTK3:
                    if(spriteIndex==92 || spriteIndex == 94){
                        body.applyLinearImpulse(new Vec2(-5f,0f),body.getPosition());
                    }
                    break;
                case ATTK4:
                    if(spriteIndex==109){
                        body.applyLinearImpulse(new Vec2(15f,0f),body.getPosition());
                    }
                    break;
                case LATTK4:
                    if(spriteIndex==122){
                        body.applyLinearImpulse(new Vec2(-15f,0f),body.getPosition());
                    }
                    break;
                case ATTK5:
                    if(spriteIndex==129){
                        body.applyLinearImpulse(new Vec2(15f,0f),body.getPosition());
                    }
                    break;
                case LATTK5:
                    if(spriteIndex==144){
                        body.applyLinearImpulse(new Vec2(-15f,0f),body.getPosition());
                    }
                    break;
                case CHARGE:
                    if(spriteIndex==138){
                        body.applyLinearImpulse(new Vec2(15f,0f),body.getPosition());
                    }
                    break;
                case LCHARGE:
                    if(spriteIndex==153){
                        body.applyLinearImpulse(new Vec2(-15f,0f),body.getPosition());
                    }
                    break;
                case WASATTK2:
                    if((i1>=1&&i1<=50)&&(Gameplay02.scoreg>0)){
                        i1--;
                        if(i1==1){
                            state=State.COMEBACK;
                            body.setActive(true);
                            i1=0;
                        }
                    }
                    break;
                case LWASATTK2:
                    if((i1>=1&&i1<=50)&&(Gameplay02.scoreg>0)){
                        i1--;
                        if(i1==1){
                            state=State.LCOMEBACK;
                            body.setActive(true);
                            i1=0;
                        }
                    }
                    break;
            }
        }catch (Exception e){

        }
        if(i>=30){
            i=0;
        }
        if(Gameplay02.spgideon>=100){
            Gameplay02.spgideon=100;
        }else if(Gameplay02.spgideon<=0){
            Gameplay02.spgideon=0;
        }
        if(Gameplay00.spscott>=100){
            Gameplay00.spscott=100;
        }else if(Gameplay00.spscott<=0){
            Gameplay00.spscott=0;
        }

        if(Gameplay02.scoreg<=0){
            Gameplay02.scoreg=0;
        }

        Gameplay02.debugSring1 = "HpScore = "+Gameplay02.scoreg;
        Gameplay02.debugSring2 = "SP gideon = "+Gameplay02.spgideon;
        Gameplay02.debugSring2 = "SP scott = "+Gameplay00.spscott;
    }

    public void paint(Clock clock) {
        if(!hasLoaded) return;
        sprite.layer().setTranslation(
                (body.getPosition().x/ Gameplay02.M_PER_PIXEL),
                (body.getPosition().y/Gameplay02.M_PER_PIXEL));
    }
}
