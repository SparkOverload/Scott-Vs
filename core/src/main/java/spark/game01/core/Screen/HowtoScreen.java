package spark.game01.core.Screen;

import static playn.core.PlayN.*;
import playn.core.*;
import playn.core.ImageLayer;
import playn.core.Mouse;
import tripleplay.game.*;
import java.lang.Override;


public class HowtoScreen extends Screen{

  private final ScreenStack ss;
  private final ImageLayer bg;
  private final ImageLayer backbutton;
  private ImageLayer key;

  public HowtoScreen(final ScreenStack ss) {
      this.ss = ss;
      Image bgImage = assets().getImage("images/Screen_bg/screen03.png");
      this.bg = graphics().createImageLayer(bgImage);

      Image backImage = assets().getImage("images/backbutton.png");
      this.backbutton = graphics().createImageLayer(backImage);
      backbutton.setTranslation(10,10);

      Image keyImage = assets().getImage("images/key.png");
      key = graphics().createImageLayer(keyImage);
      key.setTranslation(70,100);

      backbutton.addListener(new Mouse.LayerAdapter(){
          @Override
          public void onMouseUp(Mouse.ButtonEvent event){
            ss.remove(ss.top());
            ss.push(new HomeScreen(ss));
            System.out.println("2 = "+ss.size());
          }


      });
  }

  @Override
  public void wasShown(){
    super.wasShown();
    this.layer.add(bg);
    this.layer.add(backbutton);
    this.layer.add(key);



  }


  }
