package spark.game01.core.Screen;

import static playn.core.PlayN.*;

import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import playn.core.*;
import playn.core.ImageLayer;
import spark.game01.core.ContactObject.ContactMotion1;
import spark.game01.core.HP.Hpbar;
import spark.game01.core.character.Scott_char.Scott;
import spark.game01.core.character.Tom_char.Tom;
import tripleplay.game.*;
import java.lang.Override;
import java.util.HashMap;

import playn.core.util.Clock;


public class Gameplay00 extends Screen{

    public static  float M_PER_PIXEL = 1 / 26.666667f;
    private static float width = 24;
    private static float height = 18;
    private World world;
    private final ScreenStack ss;
    public  static ImageLayer bg;
    public  static ImageLayer gndfight;
    private ImageLayer tagew1;
    private ImageLayer scotthead;
    private ImageLayer tomhead;
    private Hpbar hpscott = new Hpbar(135f,55f);
    private Scott scott;
    private Hpbar hptom = new Hpbar(490f,55f);
    private Tom tom;
    private DebugDrawBox2D debugDraw;
    private Boolean showDebugDraw = true;
    public  static HashMap<Body, String> bodies = new HashMap<Body,String>();
    public static String debugSring = "";
    public static String debugSring1 = "";
    public static float score=100;
    public static float scoret=100;
    private float x = 0;
    private float y =0;


  public Gameplay00(final ScreenStack ss) {

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
      Image bgImage = assets().getImage("images/Screen_bg/screen00.png");
      bg = graphics().createImageLayer(bgImage);

      Image gndfightImage = assets().getImage("images/gndfight.png");
      gndfight = graphics().createImageLayer(gndfightImage);
      gndfight.setTranslation(0,360);

      scott = new Scott(ss,world,250f,350f);
      tom = new Tom(world,350f,350f);

      Image tagw1Image = assets().getImage("images/w1.png");
      tagew1 = graphics().createImageLayer(tagw1Image);
      tagew1.setTranslation(170,25);

      Image hscottImage = assets().getImage("images/scott_all/scott_h.png");
      scotthead = graphics().createImageLayer(hscottImage);
      scotthead.setTranslation(20,20);

      Image htomImage = assets().getImage("images/tom_all/tom_h.png");
      tomhead = graphics().createImageLayer(htomImage);
      tomhead.setTranslation(530,20);

      new ContactMotion1(world,bodies,scott,tom);
      debugSring = "HpScore tom = "+Gameplay00.score;
      debugSring1 = "HpScore scott = "+Gameplay00.scoret;

  }

  @Override
  public void wasShown(){
    super.wasShown();
      this.layer.add(bg);
      this.layer.add(gndfight);
      this.layer.add(scotthead);
      this.layer.add(tomhead);
      this.layer.add(tagew1);
      ///////////////////////////////////////// SpriteLayer
      this.layer.add(hpscott.layer());
      this.layer.add(scott.layer());
      this.layer.add(hptom.layer());
      this.layer.add(tom.layer());

      if(showDebugDraw){
          CanvasImage image = graphics().createImage(
                  (int) (width / Gameplay00.M_PER_PIXEL),
                  (int) (height / Gameplay00.M_PER_PIXEL));
          layer.add(graphics().createImageLayer(image));
          debugDraw = new DebugDrawBox2D();
          debugDraw.setCanvas(image);
          debugDraw.setFlipY(false);
          debugDraw.setStrokeAlpha(150);
          debugDraw.setStrokeWidth(2.0f);
          debugDraw.setFlags(DebugDraw.e_shapeBit |
                  DebugDraw.e_jointBit |
                  DebugDraw.e_aabbBit);
          debugDraw.setCamera(0,0,1f / Gameplay00.M_PER_PIXEL);
          world.setDebugDraw(debugDraw);
      }

  }

    @Override
    public void update(int delta) {
        super.update(delta);
        world.step(0.033f,10,10);
        hpscott.update(delta);
        hptom.update(delta);
        scott.update(delta);
        tom.update(delta);
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
        hpscott.paint(clock);
        hptom.paint(clock);
        scott.paint(clock,tom);
        tom.paint(clock,scott);


//        switch(scott.state){
//            case WALK:
//                x -=5;
//               bg.setTranslation(x,y);
//                break;
//        }
    }



  }
