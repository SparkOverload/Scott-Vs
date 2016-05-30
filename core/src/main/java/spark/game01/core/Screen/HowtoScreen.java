package spark.game01.core.Screen;

import static playn.core.PlayN.*;
import playn.core.*;
import playn.core.ImageLayer;
import playn.core.Mouse;
import playn.core.util.Clock;
import spark.game01.core.Toolsgx.ToolsG;
import tripleplay.game.*;
import java.lang.Override;


public class HowtoScreen extends Screen{

  private final ScreenStack ss;
  private final ImageLayer bg;
  private ImageLayer key;
    private ImageLayer htc;
    private ImageLayer board1;
    private ToolsG toolg = new ToolsG();
    private float alphaStart = 0.00f;



  public HowtoScreen(final ScreenStack ss) {

      this.ss = ss;
      Image bgImage = assets().getImage("images/Screen_bg/screen03.png");
      this.bg = graphics().createImageLayer(bgImage);

      Image keyImage = assets().getImage("images/key.png");
      key = graphics().createImageLayer(keyImage);
      key.setTranslation(70,100);

      Image htcImage = assets().getImage("images/htc.png");
      htc = graphics().createImageLayer(htcImage);
      htc.setTranslation(120,10);

      Image board1Image = assets().getImage("images/board1.png");
      board1 = graphics().createImageLayer(board1Image);
      board1.setTranslation(50,300);

      keyboard().setListener(new Keyboard.Adapter(){
          @Override
          public void onKeyDown(Keyboard.Event event) {
              if(event.key()==Key.ESCAPE){
                  MyGame.howtobgm.stop();
                  Gameplay02.wingame=false;
                  ss.remove(ss.top());
                  ss.push(new HomeScreen(ss));
              }
          }
      });


  }

  @Override
  public void wasShown(){
    super.wasShown();
      MyGame.howtobgm.play();
      MyGame.howtobgm.setLooping(true);
      //////////////////////////////////////////////bgm
    this.layer.add(bg);
    this.layer.add(key);
      this.layer.add(htc);
      this.layer.add(board1);

  }

    public void update(int delta){
        super.update(delta);
        bg.setAlpha(alphaStart);
        key.setAlpha(alphaStart);
        htc.setAlpha(alphaStart);
        board1.setAlpha(alphaStart);
    }

    public void paint(Clock clock){
        super.paint(clock);
        alphaStart = toolg.fade(alphaStart);
    }


  }
