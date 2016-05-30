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
import spark.game01.core.ContactObject.ContactMotion3;
import spark.game01.core.HP.Hpbar;
import spark.game01.core.PauseText.Pauselayer;
import spark.game01.core.Toolsgx.ToolsG;
import spark.game01.core.character.Gideon_char.Gideon;
import spark.game01.core.character.Ramona_char.Ramona;
import spark.game01.core.character.Scott_char.Scott;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;
import tripleplay.util.Colors;

import java.util.HashMap;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;

public class Gameplay02 extends Screen{

    private Boolean pause = false;
    private Clock.Source stoptime = new Clock.Source(0);
    public static  float M_PER_PIXEL = 1 / 26.666667f;
    private static float width = 24;
    private static float height = 18;
    private World world;
    private ScreenStack ss;
    private ImageLayer bg;
    private ImageLayer tagew3;
    private ImageLayer scotthead;
    private ImageLayer gideonhead;
    private Hpbar hpscott = new Hpbar(135f,55f);
    private Scott scott;
    private Hpbar hpgideon = new Hpbar(490f,55f);
    private Gideon gideon;
    private Ramona ramona;
    private DebugDrawBox2D debugDraw;
    public  static HashMap<Body, String> bodies;
    public static String debugSring = "";
    public static String debugSring1 = "";
    public static String debugSring2 = "";
    public static String debugSring3 = "";
    public static int scoreg;
    public static int spgideon;
    private Body ground;
    private Body ground1;
    private Body vground1;
    private Body vground2;
    private float x = -180;
    private float y =0;
    private float xg = 0;
    private float yg =0;
    private ToolsG toolg = new ToolsG();
    private Layer hp1;
    private Layer hp2;
    private Layer sp1;
    private Layer sp2;
    private Pauselayer pauselayer;
    private Layer gameover;
    private float alphaStart = 0.00f;
    private float alphaEnd = 0.00f;
    public static Boolean wingame = false;
    private Layer nextw;

    public Gameplay02(final ScreenStack ss){
        bodies = new HashMap<Body,String>();
        Vec2 gravity = new Vec2(0.0f,10.0f);
        world = new World(gravity);
        world.setWarmStarting(true);
        world.setAutoClearForces(true);

        ground = world.createBody(new BodyDef());
        EdgeShape groundShape = new EdgeShape();
        groundShape.set(new Vec2(width/2+3,height-4.5f),new Vec2(width+5,height-4.5f));
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.friction = 0.65f;
        fixtureDef.shape = groundShape;
        ground.createFixture(fixtureDef);
        bodies.put(ground,"ground");

        ground1 = world.createBody(new BodyDef());
        EdgeShape groundShape1 = new EdgeShape();
        groundShape1.set(new Vec2(-5,height-4.5f),new Vec2(width/2-3,height-4.5f));
        FixtureDef fixtureDef1 = new FixtureDef();
        fixtureDef1.friction = 0.65f;
        fixtureDef1.shape = groundShape1;
        ground1.createFixture(fixtureDef1);
        bodies.put(ground1,"ground");

        vground1 = world.createBody(new BodyDef());
        EdgeShape vgroundShape1 = new EdgeShape();
        vgroundShape1.set(new Vec2(-3,0),new Vec2(-3,height));
        FixtureDef vfixtureDef1 = new FixtureDef();
        vfixtureDef1.friction = 0.65f;
        vfixtureDef1.shape = vgroundShape1;
        vground1.createFixture(vfixtureDef1);
        //bodies.put(vground1,"ground");


        vground2 = world.createBody(new BodyDef());
        EdgeShape vgroundShape2 = new EdgeShape();
        vgroundShape2.set(new Vec2(width+4,0),new Vec2(width+4,height));
        FixtureDef vfixtureDef2 = new FixtureDef();
        vfixtureDef2.friction = 0.65f;
        vfixtureDef2.shape = vgroundShape2;
        vground2.createFixture(vfixtureDef2);
        ///bodies.put(vground2,"ground");


        this.ss = ss;
        Image bgImage = assets().getImage("images/Screen_bg/screen08.png");
        bg = graphics().createImageLayer(bgImage);
        bg.setTranslation(x,y);

        scoreg=100;
        spgideon=100;

        Image tagw3Image = assets().getImage("images/w3.png");
        tagew3 = graphics().createImageLayer(tagw3Image);
        tagew3.setTranslation(170,25);

        tagew3.addListener(new Mouse.LayerAdapter(){
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

        scott = new Scott(ss,world,250f,300f);
        gideon = new Gideon(world,450f,300f);
        ramona = new Ramona(550f,150f);

        Image hscottImage = assets().getImage("images/scott_all/scott_h.png");
        scotthead = graphics().createImageLayer(hscottImage);
        scotthead.setTranslation(20,20);

        Image hgideonImage = assets().getImage("images/gideon_all/gideon_h.png");
        gideonhead = graphics().createImageLayer(hgideonImage);
        gideonhead.setTranslation(530,20);

        new ContactMotion3(world,bodies,scott,gideon);

        hp1 = toolg.genText("HP : "+Gameplay00.score,22, Colors.CYAN,30,90);
        hp2 = toolg.genText("HP : "+scoreg,22, Colors.CYAN,530,90);
        sp1 = toolg.genText("SP : "+Gameplay00.spscott,22, Colors.WHITE,30,110);
        sp2 = toolg.genText("SP : "+spgideon,22, Colors.WHITE,530,110);

        debugSring = "HpScore scott = "+Gameplay00.score;
        debugSring1 = "HpScore gideon = "+Gameplay02.scoreg;
        debugSring2 = "SP gideon = "+spgideon;
        debugSring3 = "SP scott = "+Gameplay00.spscott;

        gameover = toolg.genText("GAME OVER",70, Colors.RED,120,200);
        gameover.setVisible(false);
        nextw = toolg.genText("AWESOME !",60, Colors.RED,180,200);
        nextw.setVisible(false);
    }


    @Override
    public void wasShown(){
        super.wasShown();
        MyGame.world03.play();
        MyGame.world03.setLooping(true);
        //////////////////////////////////////////BGM
        this.layer.add(bg);
        this.layer.add(tagew3);
        this.layer.add(gideonhead);
        this.layer.add(scotthead);
        ///////////////////////////////////////// SpriteLayer
        this.layer.add(hpscott.layer());
        this.layer.add(hpgideon.layer());
        this.layer.add(ramona.layer());
        this.layer.add(scott.layer());
        this.layer.add(gideon.layer());
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
            if (pause == false) {
                super.update(delta);
                world.step(0.066f, 10, 10);
                hpscott.update(delta);
                hpgideon.update(delta);
                ramona.update(delta);
                gideon.update(delta, scott);
                scott.update(delta, gideon);
                hpscott.Hp(Gameplay00.score);
                hpgideon.Hp(scoreg);
                if(Gameplay00.score>=0 || scoreg>=0) {
                    updatehp();
                }
                if(alphaStart<=1) {
                    cgx();
                }
                if(scoreg<=0 || gideon.body.getPosition().y>15f){
                    wingame=true;
                    scoreg=0;
                    if(alphaStart<=1) {
                        nextw.setVisible(true);
                        nextw.setAlpha(alphaEnd);
                    }
                }
                if(Gameplay00.score<=0 || scott.body.getPosition().y>15f){
                    Gameplay00.score=0;
                    if(alphaStart<=1) {
                        gameover.setVisible(true);
                        gameover.setAlpha(alphaEnd);
                    }
                }
            } else if (pause == true) {
                super.update(0);
                pauselayer.update(delta);
                world.step(0f, 10, 10);
                hpscott.update(0);
                hpgideon.update(0);
                gideon.update(0, scott);
                scott.update(0, gideon);
            }
        }catch (Exception e){

        }

    }

    @Override
    public void paint(Clock clock) {
        if(pause==false) {
            alphaStart = toolg.fade(alphaStart);
            super.paint(clock);
            scott.paint(clock);
            gideon.paint(clock);
            controlbg();
            if(Gameplay00.score<=0){
                alphaEnd = toolg.fade(alphaEnd);
            }else if(scoreg<=0){
                alphaEnd = toolg.fade(alphaEnd);
            }
        }else if(pause==true){
            super.paint(stoptime);
            scott.paint(stoptime);
            gideon.paint(stoptime);
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

            x = -(105+scott.body.getPosition().x*8);
            xg = -(scott.body.getPosition().x/3.5f);
            ramona.layer().setTranslation(x+730,150f);
            ground.setTransform(new Vec2(xg+2.7f,yg),ground.getAngle());
            ground1.setTransform(new Vec2(xg+2.7f,yg),ground1.getAngle());
            vground1.setTransform(new Vec2(xg+2.7f,yg),ground.getAngle());
            vground2.setTransform(new Vec2(xg+2.7f,yg),ground1.getAngle());
            bg.setTranslation(x,y);


            if(gideon.body.getPosition().x>=8&&gideon.body.getPosition().x<=15){
                if(gideon.state== Gideon.State.LWALK) {
                    gideon.body.setTransform(new Vec2(0, gideon.body.getPosition().y-0.5f), gideon.body.getAngle());
                }else if(gideon.state== Gideon.State.WALK){
                    gideon.body.setTransform(new Vec2(23, gideon.body.getPosition().y-0.5f), gideon.body.getAngle());
                }
            }
        }catch (Exception e){

        }
    }

    public void updatehp(){
        this.layer.remove(hp1);
        this.layer.remove(hp2);
        this.layer.remove(sp1);
        this.layer.remove(sp2);
        hp1 = toolg.genText("HP : "+Gameplay00.score,22, Colors.CYAN,30,90);
        hp2 = toolg.genText("HP : "+scoreg,22, Colors.CYAN,530,90);
        sp1 = toolg.genText("SP : "+Gameplay00.spscott,22, Colors.WHITE,30,110);
        sp2 = toolg.genText("SP : "+spgideon,22, Colors.WHITE,530,110);
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
        scotthead.setAlpha(alphaStart);
        gideonhead.setAlpha(alphaStart);
        tagew3.setAlpha(alphaStart);
        hpgideon.layer().setAlpha(alphaStart);
        hpscott.layer().setAlpha(alphaStart);
    }
}
