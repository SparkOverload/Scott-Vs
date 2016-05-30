package spark.game01.core.Screen;

import static playn.core.PlayN.*;

import playn.core.util.Clock;
import spark.game01.core.ButtonC.ButtonC0;
import spark.game01.core.Toolsgx.ToolsG;
import spark.game01.core.character.Scott_char.Scott;
import spark.game01.core.character.Tom_char.Tom;
import tripleplay.game.UIScreen;
import tripleplay.game.ScreenStack;

import static playn.core.PlayN.graphics;
import playn.core.*;
import playn.core.ImageLayer;
import playn.core.Mouse;

import java.lang.Override;

public class HomeScreen extends UIScreen{

  private ScreenStack ss;

  private final Image bgImage;
  private final ImageLayer bg;
  private final Image gnameImage;
  private final ImageLayer gname;
    private ToolsG toolg = new ToolsG();
    private float alphaStart = 0.00f;
    private ButtonC0 bt1 = new ButtonC0(190f,160f,"b1");
    private ButtonC0 bt2 = new ButtonC0(190f,250f,"b2");
    private ButtonC0 bt3 = new ButtonC0(190f,340f,"b3");

  public HomeScreen(final ScreenStack ss) {

    this.ss = ss;
    this.bgImage = assets().getImage("images/Screen_bg/screen05.png");
    this.bg = graphics().createImageLayer(bgImage);

    this.gnameImage = assets().getImage("images/gamename.png");
    this.gname = graphics().createImageLayer(gnameImage);
    gname.setTranslation(60,50);

      bt1.layer().addListener(new Mouse.LayerAdapter() {
          @Override
          public void onMouseDown(Mouse.ButtonEvent event) {
              MyGame.homebgm.stop();
              ss.remove(ss.top());
              ss.push(new Gameplay00(ss));
          }
      });
      bt2.layer().addListener(new Mouse.LayerAdapter() {
          @Override
          public void onMouseDown(Mouse.ButtonEvent event) {
              MyGame.homebgm.stop();
              ss.remove(ss.top());
              ss.push(new HowtoScreen(ss));
          }
      });
      bt3.layer().addListener(new Mouse.LayerAdapter() {
          @Override
          public void onMouseDown(Mouse.ButtonEvent event) {
              MyGame.homebgm.stop();
              ss.remove(ss.top());
              ss.push(new TopScore(ss));
          }
      });


    
  }

  @Override
  public void wasShown(){
    super.wasShown();
      MyGame.homebgm.play();
      MyGame.homebgm.setLooping(true);
      //////////////////////////////////////////BGM
      this.layer.add(bg);
      this.layer.add(gname);
      this.layer.add(bt1.layer());
      this.layer.add(bt2.layer());
      this.layer.add(bt3.layer());

  }

    public void update(int delta){
        super.update(delta);
        bg.setAlpha(alphaStart);
        gname.setAlpha(alphaStart);
        bt1.layer().setAlpha(alphaStart);
        bt2.layer().setAlpha(alphaStart);
        bt3.layer().setAlpha(alphaStart);
    }

    public void paint(Clock clock){
        super.paint(clock);
        alphaStart = toolg.fade(alphaStart);
    }


  }
