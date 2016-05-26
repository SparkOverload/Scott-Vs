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
import spark.game01.core.ToolsG;
import spark.game01.core.character.Gideon_char.Gideon;
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
    public  static ImageLayer bg;
    private ImageLayer tagew3;
    private ImageLayer scotthead;
    private ImageLayer gideonhead;
    private Hpbar hpscott = new Hpbar(135f,55f);
    private Scott scott;
    private Hpbar hpgideon = new Hpbar(490f,55f);
    private Gideon gideon;
    private DebugDrawBox2D debugDraw;
    private Boolean showDebugDraw = true;
    public  static HashMap<Body, String> bodies = new HashMap<Body,String>();
    public static String debugSring = "";
    public static String debugSring1 = "";
    public static String debugSring2 = "";
    public static String debugSring3 = "";
    public static int scoreg;
    public static int spgideon;
    private float x = -180;
    private float y =0;
    private ToolsG toolg = new ToolsG();
    private Layer hp1;
    private Layer hp2;

    public Gameplay02(final ScreenStack ss){

        Vec2 gravity = new Vec2(0.0f,10.0f);
        world = new World(gravity);
        world.setWarmStarting(true);
        world.setAutoClearForces(true);

        Body ground = world.createBody(new BodyDef());
        EdgeShape groundShape = new EdgeShape();
        groundShape.set(new Vec2(2,height-4.5f),new Vec2(width,height-4.5f));

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.friction = 0.65f;
        fixtureDef.shape = groundShape;
        ground.createFixture(fixtureDef);
        bodies.put(ground,"ground");

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
                    pause = true;
                }else{
                    pause = false;
                }

            }

        });

        scott = new Scott(ss,world,250f,300f);
        gideon = new Gideon(world,350f,300f);

        Image hscottImage = assets().getImage("images/scott_all/scott_h.png");
        scotthead = graphics().createImageLayer(hscottImage);
        scotthead.setTranslation(20,20);

        Image hgideonImage = assets().getImage("images/gideon_all/gideon_h.png");
        gideonhead = graphics().createImageLayer(hgideonImage);
        gideonhead.setTranslation(530,20);

        new ContactMotion3(world,bodies,scott,gideon);

        hp1 = toolg.genText("HP : "+Gameplay00.score,30, Colors.WHITE,30,90);
        hp2 = toolg.genText("HP : "+scoreg,30, Colors.WHITE,460,90);

        debugSring = "HpScore scott = "+Gameplay00.score;
        debugSring1 = "HpScore gideon = "+Gameplay02.scoreg;
        debugSring2 = "SP gideon = "+spgideon;
        debugSring3 = "SP scott = "+Gameplay00.spscott;

    }


    @Override
    public void wasShown(){
        super.wasShown();
        this.layer.add(bg);
        this.layer.add(tagew3);
        this.layer.add(gideonhead);
        this.layer.add(scotthead);
        ///////////////////////////////////////// SpriteLayer
        this.layer.add(hpscott.layer());
        this.layer.add(hpgideon.layer());
        this.layer.add(scott.layer());
        this.layer.add(gideon.layer());
        /////////////////////////////////////////////
        this.layer.add(hp1);
        this.layer.add(hp2);


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
        try {
            if (pause == false) {
                super.update(delta);
                world.step(0.066f, 10, 10);
                hpscott.update(delta);
                hpgideon.update(delta);
                gideon.update(delta, scott);
                scott.update(delta, gideon);
                hpscott.Hp(Gameplay00.score);
                hpgideon.Hp(scoreg);
                updatehp();
            } else if (pause == true) {
                super.update(0);
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
            super.paint(clock);
            scott.paint(clock);
            gideon.paint(clock);
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
            gideon.paint(stoptime);
        }

        if(showDebugDraw){
            debugDraw.getCanvas().clear();
            debugDraw.getCanvas().drawText(debugSring,200,50);
            debugDraw.getCanvas().drawText(debugSring1,200,100);
            debugDraw.getCanvas().drawText(debugSring2, 200, 300);
            debugDraw.getCanvas().drawText(debugSring3, 200, 320);
            world.drawDebugData();
        }
    }

    public void updatehp(){
        this.layer.remove(hp1);
        this.layer.remove(hp2);
        hp1 = toolg.genText("HP : "+Gameplay00.score,30, Colors.WHITE,30,90);
        hp2 = toolg.genText("HP : "+scoreg,30, Colors.WHITE,460,90);
        this.layer.add(hp1);
        this.layer.add(hp2);
    }
}
