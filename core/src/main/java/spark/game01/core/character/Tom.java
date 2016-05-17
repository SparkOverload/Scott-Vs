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

public class Tom {
    private Sprite sprite;
    public int spriteIndex = 0;
    private boolean hasLoaded = false;
    public Body body;
    public Boolean contacted = false;


    public enum State{
        IDLE,LIDLE,WALK,LWALK,JUMP,RUN,ATTK1,KICK1,JPUN,
        LJUMP,LRUN,LATTK1,LKICK1,LJPUN,SS,DEF,WASATTK,
        LSS,LDEF,LWASATTK,WASATTK2,COMBACK,LWASATTK2,LCOMBACK
    };

    public State state = State.LIDLE;

    private int e = 0;

    public Tom(final World world,final float x, final float y){


        PlayN.keyboard().setListener(new Keyboard.Adapter() {
            @Override
            public void onKeyDown(Keyboard.Event event) {
                switch (event.key()) {
                    case LEFT:
                        state = State.LWALK;
                        break;
                    case RIGHT:
                        state = State.WALK;
                        break;
                    case  SPACE:
                        if(state == State.WALK || state == State.RUN || state == State.IDLE) {
                            state = State.JUMP;
                        }
                        if(state == State.LWALK || state == State.LRUN || state == State.LIDLE) {
                            state = State.LJUMP;
                        }
                        if(state==State.WASATTK2){
                            state=State.COMBACK;
                        }
                        if(state==State.LWASATTK2){
                            state=State.LCOMBACK;
                        }
                        break;
                    case  A:
                        if(state == State.WALK || state == State.RUN || state == State.IDLE) {
                            state = State.ATTK1;
                        }
                        if(state == State.LWALK || state == State.LRUN || state == State.LIDLE) {
                            state = State.LATTK1;
                        }
                        if(state == State.JUMP){
                            state=State.JPUN;
                        }
                        if(state == State.LJUMP){
                            state=State.LJPUN;
                        }
                        break;
                    case  S:
                        if(state == State.WALK || state == State.RUN || state == State.IDLE) {
                            state = State.KICK1;
                        }
                        if(state == State.LWALK || state == State.LRUN || state == State.LIDLE) {
                            state = State.LKICK1;
                        }
                        if(state==State.ATTK1){
                            state=State.SS;
                         }
                        if(state==State.LATTK1){
                            state=State.LSS;
                        }
                        break;
                    case  D:
                        if(state == State.WALK || state == State.RUN || state == State.IDLE) {
                            state = State.DEF;
                        }
                        if(state == State.LWALK || state == State.LRUN || state == State.LIDLE) {
                            state = State.LDEF;
                        }
                        break;
                    case  NP0:
                        if(state == State.WALK || state == State.RUN || state == State.IDLE) {
                            state = State.WASATTK;
                        }
                        if(state == State.LWALK || state == State.LRUN || state == State.LIDLE) {
                            state = State.LWASATTK;
                        }
                        break;
                    case  NP1:
                        if(state == State.WALK || state == State.RUN || state == State.IDLE) {
                            state = State.WASATTK2;
                        }
                        if(state == State.LWALK || state == State.LRUN || state == State.LIDLE) {
                            state = State.LWASATTK2;
                        }
                        break;

                }
            }

        });

        sprite = SpriteLoader.getSprite("images/tom_all/tom.json");
        sprite.addCallback(new Callback<Sprite>() {
            @Override
            public void onSuccess(Sprite result) {
                sprite.setSprite(spriteIndex);
                sprite.layer().setOrigin(sprite.width()/2f,sprite.height()/2f+35);
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
        shape.setAsBox((sprite.layer().width()-35)* Gameplay00.M_PER_PIXEL/2,
                (sprite.layer().height()-65)*Gameplay00.M_PER_PIXEL/2);

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
                    if(spriteIndex==129){
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
                    if(spriteIndex==153){
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
    }


    public void paint(Clock clock) {
        if(!hasLoaded) return;
        sprite.layer().setTranslation(
                (body.getPosition().x/Gameplay00.M_PER_PIXEL),
                body.getPosition().y/Gameplay00.M_PER_PIXEL);
    }

    public void contact(Contact contact) {
        contacted = true;
        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureB().getBody();

        if(contacted==false){
            state=State.WALK;
        }

    }
}
