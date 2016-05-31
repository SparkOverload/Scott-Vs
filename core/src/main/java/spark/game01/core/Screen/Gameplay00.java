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
import spark.game01.core.PauseText.Pauselayer;
import spark.game01.core.Toolsgx.ToolsG;
import spark.game01.core.character.Scott_char.Scott;
import spark.game01.core.character.Tom_char.Tom;
import tripleplay.game.*;
import java.lang.Override;
import java.util.HashMap;

import playn.core.util.Clock;
import tripleplay.util.Colors;


public class Gameplay00 extends Screen{

    public static Boolean pause = false;
    private Clock.Source stoptime = new Clock.Source(0);
    public static  float M_PER_PIXEL = 1 / 26.666667f;
    private static float width = 24;
    private static float height = 18;
    private World world;
    private final ScreenStack ss;
    private ImageLayer bg;
    private ImageLayer gndfight;
    private ImageLayer tagew1;
    private ImageLayer scotthead;
    private ImageLayer tomhead;
    private Hpbar hpscott = new Hpbar(135f,55f);
    private Scott scott;
    private Hpbar hptom = new Hpbar(490f,55f);
    private Tom tom;
    private DebugDrawBox2D debugDraw;
    public static Boolean showDebugDraw = false;
    public  static HashMap<Body, String> bodies;
    public static String debugSring = "";
    public static String debugSring1 = "";
    public static String debugSring2 = "";
    public static String debugSring3 = "";
    public static int score;
    public static int spscott = 50;
    public static int scoret;
    public static int sptom;
    private float x = -175;
    private float y =0;
    private float xg = 0;
    private float yg = 0;
    private float xgf = 20;
    private float ygf = 360;
    private Body ground;
    private Body ground1;
    private ToolsG toolg = new ToolsG();
    private Layer hp1;
    private Layer hp2;
    private Layer sp1;
    private Layer sp2;
    private Layer gameover;
    private float alphaStart = 0.00f;
    private float alphaEnd = 0.00f;
    private Pauselayer pauselayer;
    private Layer nextw;



  public Gameplay00(final ScreenStack ss) {
      bodies = new HashMap<Body,String>();
      Vec2 gravity = new Vec2(0.0f,10.0f);
      world = new World(gravity);
      world.setWarmStarting(true);
      world.setAutoClearForces(true);

      ground = world.createBody(new BodyDef());
      EdgeShape groundShape = new EdgeShape();
      groundShape.set(new Vec2(1.5f,height-2.5f),new Vec2(width-1.5f,height-2.5f));
      FixtureDef fixtureDef = new FixtureDef();
      fixtureDef.friction = 0.65f;
      fixtureDef.shape = groundShape;
      ground.createFixture(fixtureDef);
      bodies.put(ground,"ground");

      ground1 = world.createBody(new BodyDef());
      EdgeShape groundShape1 = new EdgeShape();
      groundShape1.set(new Vec2(-10,height-1.5f),new Vec2(width+10,height-1.5f));
      FixtureDef fixtureDef1 = new FixtureDef();
      fixtureDef1.friction = 0.65f;
      fixtureDef1.shape = groundShape1;
      ground1.createFixture(fixtureDef1);
      bodies.put(ground1,"ground1");

      score=100;
      scoret=100;
      sptom=50;

      this.ss = ss;
      Image bgImage = assets().getImage("images/Screen_bg/screen00.png");
      bg = graphics().createImageLayer(bgImage);
      bg.setTranslation(x,y);

      Image gndfightImage = assets().getImage("images/gndfight.png");
      gndfight = graphics().createImageLayer(gndfightImage);
      gndfight.setTranslation(xgf,ygf);

      tom = new Tom(world,350f,360f);
      scott = new Scott(ss,world,250f,350f);

      Image tagw1Image = assets().getImage("images/w1.png");
      tagew1 = graphics().createImageLayer(tagw1Image);
      tagew1.setTranslation(170,25);

      tagew1.addListener(new Mouse.LayerAdapter(){
          @Override
          public void onMouseDown(Mouse.ButtonEvent event){
              if(pause==false){
                  pauselayer = new Pauselayer(310f,240f);
                  layer.add(pauselayer.layer());
                  pause = true;
              }else{
                 layer.remove(pauselayer.layer());
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

      hp1 = toolg.genText("HP : "+score,22, Colors.CYAN,30,90);
      hp2 = toolg.genText("HP : "+scoret,22, Colors.CYAN,530,90);
      sp1 = toolg.genText("SP : "+spscott,22, Colors.WHITE,30,110);
      sp2 = toolg.genText("SP : "+sptom,22, Colors.WHITE,530,110);


      debugSring = "HpScore tom = "+score;
      debugSring1 = "HpScore scott = "+scoret;

      debugSring2 = "SP tom = "+sptom;
      debugSring3 = "SP scott = "+spscott;


      nextw = toolg.genText("GOOD JOB !", 60, Colors.MAGENTA, 180, 200);
      nextw.setVisible(false);

      gameover = toolg.genText("GAME OVER", 70, Colors.PINK, 120, 200);
      gameover.setVisible(false);

  }

  @Override
  public void wasShown(){
    super.wasShown();
      MyGame.world01.play();
      MyGame.world01.setLooping(true);
      //////////////////////////////////////////BGM
      this.layer.add(bg);
      this.layer.add(gndfight);
      this.layer.add(scotthead);
      this.layer.add(tomhead);
      this.layer.add(tagew1);
      ///////////////////////////////////////// SpriteLayer
      this.layer.add(hptom.layer());
      this.layer.add(tom.layer());
      this.layer.add(hpscott.layer());
      this.layer.add(scott.layer());
      ///////////////////////////////////////// text
      this.layer.add(hp1);
      this.layer.add(hp2);
      this.layer.add(sp1);
      this.layer.add(sp2);
      this.layer.add(gameover);
      this.layer.add(nextw);
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
                tom.update(delta, scott);
                scott.update(delta, tom);
                hpscott.Hp(score);
                hptom.Hp(scoret);
                if(alphaStart<=1) {
                    cgx();
                }
                if(score>=0 || scoret>=0){
                    updatehp();
                }
                if(score<=0){
                    if(alphaEnd<=1) {
                        gameover.setVisible(true);
                        gameover.setAlpha(alphaEnd);
                    }
                }else if(scoret<=0){
                    if(alphaEnd<=1) {
                        nextw.setVisible(true);
                        nextw.setAlpha(alphaEnd);
                    }
                }
            } else if (pause == true) {
                super.update(0);
                pauselayer.update(delta);
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
            alphaStart = toolg.fade(alphaStart);
            super.paint(clock);
            tom.paint(clock);
            scott.paint(clock);
            controlbg();
            if(score<=0){
                alphaEnd = toolg.fade(alphaEnd);
            }else if(scoret<=0){
                alphaEnd = toolg.fade(alphaEnd);
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

    public void controlbg(){
        try{
                x = -(138 + scott.body.getPosition().x * 4);
                xgf = -((-58) + scott.body.getPosition().x * 4);
                xg = -(scott.body.getPosition().x / 7);
                ground.setTransform(new Vec2(xg + 1.3f, yg), ground.getAngle());
                bg.setTranslation(x, y);
                gndfight.setTranslation(xgf, ygf);
        }catch (Exception e){

        }
    }


    public void updatehp(){
            this.layer.remove(hp1);
            this.layer.remove(hp2);
            this.layer.remove(sp1);
            this.layer.remove(sp2);
            hp1 = toolg.genText("HP : " + score, 22, Colors.CYAN, 30, 90);
            hp2 = toolg.genText("HP : " + scoret, 22, Colors.CYAN, 530, 90);
            sp1 = toolg.genText("SP : " + spscott, 22, Colors.WHITE, 30, 110);
            sp2 = toolg.genText("SP : " + sptom, 22, Colors.WHITE, 530, 110);
            this.layer.add(hp1);
            this.layer.add(hp2);
            this.layer.add(sp1);
            this.layer.add(sp2);
        if(alphaStart<=1) {
            hp1.setAlpha(alphaStart);
            hp2.setAlpha(alphaStart);
            sp1.setAlpha(alphaStart);
            sp2.setAlpha(alphaStart);
        }
    }

    public void cgx(){
        bg.setAlpha(alphaStart);
        gndfight.setAlpha(alphaStart);
        scotthead.setAlpha(alphaStart);
        tomhead.setAlpha(alphaStart);
        tagew1.setAlpha(alphaStart);
        hptom.layer().setAlpha(alphaStart);
        hpscott.layer().setAlpha(alphaStart);
    }


  }
