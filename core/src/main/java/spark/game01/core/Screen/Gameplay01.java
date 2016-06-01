package spark.game01.core.Screen;

import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import playn.core.*;
import playn.core.util.Clock;
import spark.game01.core.ContactObject.ContactMotion2;
import spark.game01.core.HP.Hpbar;
import spark.game01.core.PauseText.Pauselayer;
import spark.game01.core.Toolsgx.ToolsG;
import spark.game01.core.character.Matt_char.Matt;
import spark.game01.core.character.Scott_char.Scott;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;
import tripleplay.util.Colors;

import java.util.HashMap;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;

public class Gameplay01 extends Screen{

    private Clock.Source stoptime = new Clock.Source(0);
    public static  float M_PER_PIXEL = 1 / 26.666667f;
    private static float width = 24;
    private static float height = 18;
    private World world;
    private ScreenStack ss;
    private ImageLayer bg;
    private ImageLayer gndfight;
    private ImageLayer tagew2;
    private ImageLayer scotthead;
    private ImageLayer matthead;
    private Hpbar hpscott = new Hpbar(135f,55f);
    private Scott scott;
    private Hpbar hpmatt = new Hpbar(490f,55f);
    private Matt matt;
    private DebugDrawBox2D debugDraw;
    public  static HashMap<Body, String> bodies;
    public static String debugSring = "";
    public static String debugSring1 = "";
    public static String debugSring2 = "";
    public static String debugSring3 = "";
    public static int scorem;
    public static int spmatt;
    private float x = -180;
    private float y =0;
    private float xg = 0;
    private float yg = 0;
    private float xr = -127;
    private float yr = 280;
    private ToolsG toolg = new ToolsG();
    private Layer hp1;
    private Layer hp2;
    private Layer sp1;
    private Layer sp2;
    private Layer gameover;
    private float alphaStart = 0.00f;
    private float alphaEnd = 0.00f;
    private Layer nextw;
    private Body ground;

    public Gameplay01(final ScreenStack ss){
        bodies = new HashMap<Body,String>();
        Vec2 gravity = new Vec2(0.0f,10.0f);
        world = new World(gravity);
        world.setWarmStarting(true);
        world.setAutoClearForces(true);

        ground = world.createBody(new BodyDef());
        EdgeShape groundShape = new EdgeShape();
        groundShape.set(new Vec2(0,height-2.5f),new Vec2(width-1,height-2.5f));

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.friction = 0.65f;
        fixtureDef.shape = groundShape;
        ground.createFixture(fixtureDef);
        bodies.put(ground,"ground");

        this.ss = ss;
        Image bgImage = assets().getImage("images/Screen_bg/screen07.png");
        bg = graphics().createImageLayer(bgImage);
        bg.setTranslation(x,y);

        scorem=100;
        spmatt=50;

        Image gndfightImage = assets().getImage("images/rope.png");
        gndfight = graphics().createImageLayer(gndfightImage);
        gndfight.setTranslation(xr,yr);

        Image tagw2Image = assets().getImage("images/w2.png");
        tagew2 = graphics().createImageLayer(tagw2Image);
        tagew2.setTranslation(170,25);


        scott = new Scott(ss,world,250f,350f);
        matt = new Matt(world,450f,350f);

        Image hscottImage = assets().getImage("images/scott_all/scott_h.png");
        scotthead = graphics().createImageLayer(hscottImage);
        scotthead.setTranslation(20,20);

        Image hmattImage = assets().getImage("images/matt_all/matt_h.png");
        matthead = graphics().createImageLayer(hmattImage);
        matthead.setTranslation(530,20);

        new ContactMotion2(world,bodies,scott,matt);

        hp1 = toolg.genText("HP : "+Gameplay00.score,30, Colors.CYAN,30,90);
        hp2 = toolg.genText("HP : "+scorem,30, Colors.CYAN,530,90);
        sp1 = toolg.genText("SP : "+Gameplay00.spscott,22, Colors.WHITE,30,110);
        sp2 = toolg.genText("SP : "+spmatt,22, Colors.WHITE,530,110);

        debugSring = "HpScore matt = "+Gameplay01.scorem;
        debugSring1 = "HpScore scott = "+Gameplay00.score;
        debugSring2 = "SP matt = "+spmatt;
        debugSring3 = "SP scott = "+Gameplay00.spscott;


        nextw = toolg.genText("SO COOL !",60, Colors.BLUE,180,200);
        nextw.setVisible(false);
        gameover = toolg.genText("GAME OVER",70, Colors.BLUE,120,200);
        gameover.setVisible(false);
    }

    @Override
    public void wasShown(){
        super.wasShown();
        MyGame.world02.play();
        MyGame.world02.setLooping(true);
        //////////////////////////////////////////BGM
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
        /////////////////////////////////////////////
        this.layer.add(hp1);
        this.layer.add(hp2);
        this.layer.add(sp1);
        this.layer.add(sp2);
        this.layer.add(gameover);
        this.layer.add(nextw);

        if(Gameplay00.showDebugDraw){
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
        try {
            if (Gameplay00.pause == false) {
                if(Gameplay00.checkcpause==true){
                    layer.remove(Gameplay00.pauselayer.layer());
                    Gameplay00.checkcpause=false;
                }
                super.update(delta);
                world.step(0.066f, 10, 10);
                hpscott.update(delta);
                hpmatt.update(delta);
                matt.update(delta, scott);
                scott.update(delta, matt);
                hpscott.Hp(Gameplay00.score);
                hpmatt.Hp(scorem);
                if(Gameplay00.score>=0 || scorem>=0) {
                    updatehp();
                }
                if(alphaStart<=1) {
                    cgx();
                }
                if(Gameplay00.score<=0 || scott.body.getPosition().y>20f){
                    Gameplay00.score=0;
                    if(alphaEnd<=1) {
                        gameover.setVisible(true);
                        gameover.setAlpha(alphaEnd);
                    }
                }else if(scorem<=0 || matt.body.getPosition().y>20f){
                    scorem=0;
                    if(alphaEnd<=1) {
                        nextw.setVisible(true);
                        nextw.setAlpha(alphaEnd);
                    }
                }
            } else if (Gameplay00.pause == true) {
                if(Gameplay00.checkcpause==false){
                    Gameplay00.pauselayer = new Pauselayer(310f,240f);
                    layer.add(Gameplay00.pauselayer.layer());
                    Gameplay00.checkcpause=true;
                }
                super.update(0);
                Gameplay00.pauselayer.update(delta);
                world.step(0f, 10, 10);
                hpscott.update(0);
                hpmatt.update(0);
                matt.update(0, scott);
                scott.update(0, matt);
            }
        }catch (Exception e){

        }

    }

    @Override
    public void paint(Clock clock) {
        if(Gameplay00.pause==false) {
            alphaStart = toolg.fade(alphaStart);
            super.paint(clock);
            scott.paint(clock);
            matt.paint(clock);
            controlbg();
            if(Gameplay00.score<=0){
                alphaEnd = toolg.fade(alphaEnd);
            }else if(scorem<=0){
                alphaEnd = toolg.fade(alphaEnd);
            }
        }else if(Gameplay00.pause==true){
            super.paint(stoptime);
            scott.paint(stoptime);
            matt.paint(stoptime);
        }

        if(Gameplay00.showDebugDraw){
            debugDraw.getCanvas().clear();
            debugDraw.getCanvas().drawText(debugSring,200,50);
            debugDraw.getCanvas().drawText(debugSring1,200,100);
            debugDraw.getCanvas().drawText(debugSring2, 200, 300);
            debugDraw.getCanvas().drawText(debugSring3, 200, 320);
            world.drawDebugData();
        }

    }

    public void controlbg(){
        try{
            x = -(143+scott.body.getPosition().x*4);
            xr = -(90+scott.body.getPosition().x*4);
            xg = -(scott.body.getPosition().x/7);
            ground.setTransform(new Vec2(xg+2,yg),ground.getAngle());
            bg.setTranslation(x,y);
            gndfight.setTranslation(xr,yr);
        }catch (Exception e){

        }
    }

    public void updatehp(){
        this.layer.remove(hp1);
        this.layer.remove(hp2);
        this.layer.remove(sp1);
        this.layer.remove(sp2);
        hp1 = toolg.genText("HP : "+Gameplay00.score,22, Colors.CYAN,30,90);
        hp2 = toolg.genText("HP : "+scorem,22, Colors.CYAN,530,90);
        sp1 = toolg.genText("SP : "+Gameplay00.spscott,22, Colors.WHITE,30,110);
        sp2 = toolg.genText("SP : "+spmatt,22, Colors.WHITE,530,110);
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
        matthead.setAlpha(alphaStart);
        tagew2.setAlpha(alphaStart);
        hpmatt.layer().setAlpha(alphaStart);
        hpscott.layer().setAlpha(alphaStart);
    }

}
