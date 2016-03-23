package spark.game01.core;

import static playn.core.PlayN.*;

import playn.core.*;
import playn.core.ImageLayer;
import playn.core.Mouse;
import react.UnitSlot;
import tripleplay.game.*;

import java.lang.Override;


public class TestScreen extends Screen{

  private final ScreenStack ss;
  private final ImageLayer bg;
    private final ImageLayer backbutton;

  public TestScreen(final ScreenStack ss) {
    this.ss = ss;
      Image bgImage = assets().getImage("images/screen02.png");
      this.bg = graphics().createImageLayer(bgImage);

      Image backImage = assets().getImage("images/backbutton.png");
      this.backbutton = graphics().createImageLayer(backImage);
      backbutton.setTranslation(280,230);

      backbutton.addListener(new Mouse.LayerAdapter(){
          @Override
          public void onMouseUp(Mouse.ButtonEvent event){
              ss.remove(ss.top());
          }


      });
  }

  @Override
  public void wasShown(){
    super.wasShown();
    this.layer.add(bg);
    this.layer.add(backbutton);



  }


  }
