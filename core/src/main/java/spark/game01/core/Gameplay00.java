package spark.game01.core;

import static playn.core.PlayN.*;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.contacts.Contact;
import playn.core.*;
import playn.core.ImageLayer;
import playn.core.Mouse;
import react.UnitSlot;
import spark.game01.core.character.*;
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
    private final ImageLayer bg;
    private final ImageLayer gndfight;
    private final ImageLayer backbutton;
    private Scott scott;
    private Tom tom;
    private DebugDrawBox2D debugDraw;
    private Boolean showDebugDraw = true;
    public  static HashMap<Body, String> bodies = new HashMap<Body,String>();

  public Gameplay00(final ScreenStack ss) {

      Vec2 gravity = new Vec2(0.0f,10.0f);
      world = new World(gravity);
      world.setWarmStarting(true);
      world.setAutoClearForces(true);

      Body ground = world.createBody(new BodyDef());
      EdgeShape groundShape = new EdgeShape();
      groundShape.set(new Vec2(2,height-2.5f),new Vec2(width,height-2.5f));
      ground.createFixture(groundShape,0.0f);
      bodies.put(ground,"ground");

      scott = new Scott(world,250f,350f);
      tom = new Tom(world,350f,350f);



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



      world.setContactListener(new ContactListener() {
          @Override
          public void beginContact(Contact contact) {

              Body a = contact.getFixtureA().getBody();
              Body b = contact.getFixtureB().getBody();

              System.out.println(bodies.get(a)+"dddddd"+bodies.get(b));

              if(bodies.get(a)=="ground"&&b==scott.body || bodies.get(b)=="ground"&&a==scott.body){
                  scott.contact(contact);
              }

              if(a==scott.body&&b==tom.body || b==scott.body&&a==tom.body){
                  scott.contact(contact);
                  tom.contact(contact);
              }
          }

          @Override
          public void endContact(Contact contact) {



              Body a = contact.getFixtureA().getBody();
              Body b = contact.getFixtureB().getBody();
              if(a==scott.body&&b==tom.body || b==scott.body&&a==tom.body){
                  scott.contacted = false;
                  tom.contacted = false;
              }

          }

          @Override
          public void preSolve(Contact contact, Manifold manifold) {

          }

          @Override
          public void postSolve(Contact contact, ContactImpulse contactImpulse) {

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
