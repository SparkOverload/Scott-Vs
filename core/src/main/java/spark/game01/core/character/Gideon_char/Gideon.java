package spark.game01.core.character.Gideon_char;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import playn.core.Keyboard;
import playn.core.Layer;
import playn.core.PlayN;
import playn.core.util.Callback;
import spark.game01.core.Screen.Gameplay01;
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
        ATTK3,LATTK3,ATTK4,LATTK4
    };

    public State state = State.IDLE;
    private int e = 0;

    public Gideon(final World world, final float x, final float y){
        sprite = SpriteLoader.getSprite("images/gideon_all/gideon.json");
        sprite.addCallback(new Callback<Sprite>() {
            @Override
            public void onSuccess(Sprite result) {
                sprite.setSprite(spriteIndex);
                sprite.layer().setOrigin(sprite.width()/2f,sprite.height()/2f+50);
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

        PlayN.keyboard().setListener(new Keyboard.Adapter() {
            @Override
            public void onKeyDown(Keyboard.Event event) {
                switch (event.key()) {
                    case RIGHT:
                        state=State.WALK;
                        break;
                    case LEFT:
                        state=State.LWALK;
                        break;
                    case UP:
                        state=State.LIDLE;
                        break;
                    case A:
                        state=State.ATTK4;
                        break;
                    case S:
                        state=State.LATTK4;
                        break;
                }
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
        shape.setAsBox((sprite.layer().width())* Gameplay01.M_PER_PIXEL/2,
                (sprite.layer().height())*Gameplay01.M_PER_PIXEL/2);


        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 1f;

        body.createFixture(fixtureDef);
        body.setLinearDamping(0.2f);
        body.setTransform(new Vec2(x,y),0f);
        return body;
    }

    public void update(int delta){
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
                    if(spriteIndex==113){
                        state=State.IDLE;
                    }
                    break;
                case LATTK4:
                    if(!(spriteIndex>=114 && spriteIndex<=126)){
                        spriteIndex=114;
                    }
                    if(spriteIndex==126){
                        state=State.LIDLE;
                    }
                    break;
            }
            sprite.setSprite(spriteIndex);
            spriteIndex++;
            e=0;
        }
    }
}
