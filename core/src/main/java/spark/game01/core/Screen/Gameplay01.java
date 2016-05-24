package spark.game01.core.Screen;

import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import playn.core.CanvasImage;
import playn.core.DebugDrawBox2D;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.util.Clock;
import spark.game01.core.ContactObject.ContactMotion1;
import spark.game01.core.ContactObject.ContactMotion2;
import spark.game01.core.HP.Hpbar;
import spark.game01.core.character.Matt_char.Matt;
import spark.game01.core.character.Scott_char.Scott;
import spark.game01.core.character.Tom_char.Tom;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;

import java.util.HashMap;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;

public class Gameplay01 extends Screen{

    public static  float M_PER_PIXEL = 1 / 26.666667f;
    private static float width = 24;
    private static float height = 18;
    private World world;
    private ScreenStack ss;
    public  static ImageLayer bg;
    public  static ImageLayer gndfight;
    private ImageLayer tagew2;
    private ImageLayer scotthead;
    private ImageLayer matthead;
    private Hpbar hpscott = new Hpbar(135f,55f);
    private Scott scott;
    private Hpbar hpmatt = new Hpbar(490f,55f);
    private Matt matt;
    private DebugDrawBox2D debugDraw;
    private Boolean showDebugDraw = true;
    public  static HashMap<Body, String> bodies = new HashMap<Body,String>();
    public static String debugSring = "";
    public static String debugSring1 = "";
    public static float score=100;
    public static float scorem=100;

    public Gameplay01(final ScreenStack ss){
        Vec2 gravity = new Vec2(0.0f,10.0f);
        world = new World(gravity);
        world.setWarmStarting(true);
        world.setAutoClearForces(true);

        Body ground = world.createBody(new BodyDef());
        EdgeShape groundShape = new EdgeShape();
        groundShape.set(new Vec2(2,height-2.5f),new Vec2(width,height-2.5f));
        //ground.createFixture(groundShape,0.0f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.friction = 0.65f;
        fixtureDef.shape = groundShape;
        ground.createFixture(fixtureDef);
        bodies.put(ground,"ground");

        this.ss = ss;
        Image bgImage = assets().getImage("images/Screen_bg/screen07.png");
        bg = graphics().createImageLayer(bgImage);

        Image gndfightImage = assets().getImage("images/gndfight.png");
        gndfight = graphics().createImageLayer(gndfightImage);
        gndfight.setTranslation(0,360);

        Image tagw2Image = assets().getImage("images/w2.png");
        tagew2 = graphics().createImageLayer(tagw2Image);
        tagew2.setTranslation(170,25);

        scott = new Scott(ss,world,250f,350f);
        matt = new Matt(world,350f,350f);

        Image hscottImage = assets().getImage("images/scott_all/scott_h.png");
        scotthead = graphics().createImageLayer(hscottImage);
        scotthead.setTranslation(20,20);

        Image hmattImage = assets().getImage("images/matt_all/matt_h.png");
        matthead = graphics().createImageLayer(hmattImage);
        matthead.setTranslation(530,20);

        new ContactMotion2(world,bodies,scott,matt);
        debugSring = "HpScore tom = "+Gameplay01.score;
        debugSring1 = "HpScore scott = "+Gameplay01.scorem;
    }

    @Override
    public void wasShown(){
        super.wasShown();
        this.layer.add(bg);
        this.layer.add(gndfight);
        this.layer.add(tagew2);
        this.layer.add(matthead);
        this.layer.add(scotthead);
        ///////////////////////////////////////// SpriteLayer
        this.layer.add(hpscott.layer());
        this.layer.add(hpmatt.layer());
        this.layer.add(matt.layer());
        this.layer.add(scott.layer());

        if(showDebugDraw){
            CanvasImage image = graphics().createImage(
                    (int) (width / Gameplay01.M_PER_PIXEL),
                    (int) (height / Gameplay01.M_PER_PIXEL));
            layer.add(graphics().createImageLayer(image));
            debugDraw = new DebugDrawBox2D();
            debugDraw.setCanvas(image);
            debugDraw.setFlipY(false);
            debugDraw.setStrokeAlpha(150);
            debugDraw.setStrokeWidth(2.0f);
            debugDraw.setFlags(DebugDraw.e_shapeBit |
                    DebugDraw.e_jointBit |
                    DebugDraw.e_aabbBit);
            debugDraw.setCamera(0,0,1f / Gameplay01.M_PER_PIXEL);
            world.setDebugDraw(debugDraw);
        }

    }

    @Override
    public void update(int delta) {
        super.update(delta);
        world.step(0.066f,10,10);
        hpscott.update(delta);
        hpmatt.update(delta);
        matt.update(delta,scott);
        scott.update(delta,matt);

    }

    @Override
    public void paint(Clock clock) {
        super.paint(clock);
        if(showDebugDraw){
            debugDraw.getCanvas().clear();
            debugDraw.getCanvas().drawText(debugSring,200,50);
            debugDraw.getCanvas().drawText(debugSring1,200,100);
            world.drawDebugData();
        }
        scott.paint(clock);
        matt.paint(clock);

    }

}
