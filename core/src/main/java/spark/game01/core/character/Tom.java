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
        IDLE,LIDLE,WALK,LWALK
    };

    public State state = State.LIDLE;

    private int e = 0;

    public Tom(final World world,final float x, final float y){


       /* PlayN.keyboard().setListener(new Keyboard.Adapter() {
            @Override
            public void onKeyDown(Keyboard.Event event) {
                switch (event.key()) {
                    case UP:
                        state = State.IDLE;
                        break;
                    case LEFT:
                        state = State.WALK;
                        break;
                }
            }
        });*/
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
        if(e > 100){
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

        if(body==b){
            state=State.WALK;
        }

    }
}
