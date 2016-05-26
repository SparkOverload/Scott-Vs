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
import spark.game01.core.ToolsG;
import spark.game01.core.character.Scott_char.Scott;
import spark.game01.core.character.Tom_char.Tom;
import tripleplay.game.*;
import java.lang.Override;
import java.util.HashMap;

import playn.core.util.Clock;
import tripleplay.util.Colors;


public class Gameplay00 extends Screen{

    private Boolean pause = false;
    private Clock.Source stoptime = new Clock.Source(0);
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
    public static String debugSring2 = "";
    public static String debugSring3 = "";
    public static int score;
    public static int spscott = 50;
    public static int scoret;
    public static int sptom;
    private float x = -140;
    private float y =0;
    private Body ground;
    private ToolsG toolg = new ToolsG();
    private Layer hp1;
    private Layer hp2;
    private float alphaTest = 0.00f;


  public Gameplay00(final ScreenStack ss) {
      Vec2 gravity = new Vec2(0.0f,10.0f);
      world = new World(gravity);
      world.setWarmStarting(true);
      world.setAutoClearForces(true);

      ground = world.createBody(new BodyDef());
      EdgeShape groundShape = new EdgeShape();
      groundShape.set(new Vec2(2,height-2.5f),new Vec2(width,height-2.5f));
      //ground.createFixture(groundShape,0.0f);

      FixtureDef fixtureDef = new FixtureDef();
      fixtureDef.friction = 0.65f;
      fixtureDef.shape = groundShape;
      ground.createFixture(fixtureDef);
      bodies.put(ground,"ground");

      score=100;
      scoret=100;
      sptom=50;

      this.ss = ss;
      Image bgImage = assets().getImage("images/Screen_bg/screen00.png");
      bg = graphics().createImageLayer(bgImage);
      bg.setTranslation(x,y);

      Image gndfightImage = assets().getImage("images/gndfight.png");
      gndfight = graphics().createImageLayer(gndfightImage);
      gndfight.setTranslation(0,360);

      scott = new Scott(ss,world,250f,350f);
      tom = new Tom(world,350f,350f);

      Image tagw1Image = assets().getImage("images/w1.png");
      tagew1 = graphics().createImageLayer(tagw1Image);
      tagew1.setTranslation(170,25);

      tagew1.addListener(new Mouse.LayerAdapter(){
          @Override
          public void onMouseDown(Mouse.ButtonEvent event){
              if(pause==false){
                  pause = true;
              }else{
                  pause = false;
              }

          }

      });

      Image hscottImage = assets().getImage("images/scott_all/scott_h.png");
      scotthead = graphics().createImageLayer(hscottImage);
      scotthead.setTranslation(20,20);

      Image htomImage = assets().getImage("images/tom_all/tom_h.png");
      tomhead = graphics().createImageLayer(htomImage);
      tomhead.setTranslation(530,20);

      new ContactMotion1(world,bodies,scott,tom);

      hp1 = toolg.genText("HP : "+score,30, Colors.WHITE,30,90);
      hp2 = toolg.genText("HP : "+scoret,30, Colors.WHITE,460,90);

      debugSring = "HpScore tom = "+score;
      debugSring1 = "HpScore scott = "+scoret;

      debugSring2 = "SP tom = "+sptom;
      debugSring3 = "SP scott = "+spscott;

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
      ///////////////////////////////////////// text
      this.layer.add(hp1);
      this.layer.add(hp2);

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
        try {
            if (pause == false) {
                super.update(delta);
                world.step(0.066f, 10, 10);
                hpscott.update(delta);
                hptom.update(delta);
                scott.update(delta, tom);
                tom.update(delta, scott);
                hpscott.Hp(score);
                hptom.Hp(scoret);
                bg.setAlpha(alphaTest);
                updatehp();
            } else if (pause == true) {
                super.update(0);
                world.step(0f, 10, 10);
                hpscott.update(0);
                hptom.update(0);
                scott.update(0, tom);
                tom.update(0, scott);
            }
        }catch (Exception e){

        }
        }

    @Override
    public void paint(Clock clock) {
        if(pause==false) {
            alphaTest = toolg.fade(alphaTest);
            super.paint(clock);
            scott.paint(clock);
            tom.paint(clock);
            switch (scott.state) {
                case WALK:
                    if (x >= -350 && x <= 350) {
                        if (x == -350) {
                            x = -345;
                        }
                        x -= 5;
                        bg.setTranslation(x, y);
                    }
                    break;
                case LWALK:
                    if (x >= -350 && x <= -10) {
                        x += 5;
                        bg.setTranslation(x, y);
                    }
                    break;
            }
        }else if(pause==true){
            super.paint(stoptime);
            scott.paint(stoptime);
            tom.paint(stoptime);
        }
        if (showDebugDraw) {
            debugDraw.getCanvas().clear();
            debugDraw.getCanvas().drawText(debugSring, 200, 50);
            debugDraw.getCanvas().drawText(debugSring1, 200, 100);
            debugDraw.getCanvas().drawText(debugSring2, 200, 300);
            debugDraw.getCanvas().drawText(debugSring3, 200, 320);
            world.drawDebugData();
        }
    }

    public void updatehp(){
        this.layer.remove(hp1);
        this.layer.remove(hp2);
        hp1 = toolg.genText("HP : "+score,30, Colors.WHITE,30,90);
        hp2 = toolg.genText("HP : "+scoret,30, Colors.WHITE,460,90);
        this.layer.add(hp1);
        this.layer.add(hp2);
    }



  }
