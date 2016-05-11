package spark.game01.core;

import static playn.core.PlayN.*;

import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import playn.core.*;
import playn.core.ImageLayer;
import playn.core.Mouse;
import react.UnitSlot;
import spark.game01.core.character.*;
import tripleplay.game.*;
import java.lang.Override;
import playn.core.util.Clock;


public class Gameplay00 extends Screen{

    public static  float M_PER_PIXEL = 1 / 26.666667f;
    private static float width = 24;
    private static float height = 18;
    private World world;
    private final ScreenStack ss;
    private final ImageLayer bg;
    private final ImageLayer gndfight;
    private final ImageLayer backbutton;
    private Scott scott;
    private Tom tom;
    private DebugDrawBox2D debugDraw;
    private Boolean showDebugDraw = true;

  public Gameplay00(final ScreenStack ss) {

      Vec2 gravity = new Vec2(0.0f,10.0f);
      world = new World(gravity);
      world.setWarmStarting(true);
      world.setAutoClearForces(true);

      Body ground = world.createBody(new BodyDef());
      EdgeShape groundShape = new EdgeShape();
      groundShape.set(new Vec2(2,height-2.5f),new Vec2(width,height-2.5f));
      ground.createFixture(groundShape,0.0f);


      this.scott = new Scott(world,250f,0f);
      this.tom = new Tom(world,350f,0f);
      this.ss = ss;
      Image bgImage = assets().getImage("images/screen00.png");
      this.bg = graphics().createImageLayer(bgImage);


      Image gndfightImage = assets().getImage("images/gndfight.png");
      this.gndfight = graphics().createImageLayer(gndfightImage);
      gndfight.setTranslation(0,360);

      Image backImage = assets().getImage("images/backbutton.png");
      this.backbutton = graphics().createImageLayer(backImage);
      backbutton.setTranslation(10,10);

      backbutton.addListener(new Mouse.LayerAdapter(){
          @Override
          public void onMouseUp(Mouse.ButtonEvent event){
              ss.remove(ss.top());
              ss.push(new HomeScreen(ss));
              System.out.println("1 = "+ss.size());
          }


      });

  }

  @Override
  public void wasShown(){
    super.wasShown();
      this.layer.add(bg);
      this.layer.add(backbutton);
      this.layer.add(gndfight);


      ///////////////////////////////////////// SpriteLayer
      this.layer.add(scott.layer());
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

      mouse().setListener(new Mouse.Adapter(){
          public void onMouseUp(Mouse.ButtonEvent event){

              BodyDef bodyDef = new BodyDef();
              bodyDef.type = BodyType.DYNAMIC;
              bodyDef.position = new Vec2(
                      event.x() * M_PER_PIXEL,
                      event.y() * M_PER_PIXEL);

              Body body = world.createBody(bodyDef);
              CircleShape shape = new CircleShape();
              shape.setRadius(0.4f);

              FixtureDef fixtureDef = new FixtureDef();
              fixtureDef.shape = shape;
              fixtureDef.density = 1f;
              fixtureDef.friction = 0.1f;
              fixtureDef.restitution = 1f;

              body.createFixture(fixtureDef);
              body.setLinearDamping(0.2f);
          }});

  }

    @Override
    public void update(int delta) {
        super.update(delta);
        world.step(0.033f,10,10);
        this.scott.update(delta);
        this.tom.update(delta);
        }

    @Override
    public void paint(Clock clock) {
        super.paint(clock);
        if(showDebugDraw){
            debugDraw.getCanvas().clear();
            world.drawDebugData();
        }
        this.scott.paint(clock);
        this.tom.paint(clock);
    }



  }
