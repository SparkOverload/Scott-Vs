package spark.game01.core;

import static playn.core.PlayN.*;
import playn.core.*;
import playn.core.ImageLayer;
import playn.core.Mouse;
import react.UnitSlot;
import spark.game01.core.character.*;
import tripleplay.game.*;
import java.lang.Override;


public class Gameplay00 extends Screen{

  private final ScreenStack ss;
  private final ImageLayer bg;
  private final ImageLayer gndfight;
  private final ImageLayer backbutton;
  private Scott scott = new Scott(250f,320f);
    private Tom tom = new Tom(350f,360f);

  public Gameplay00(final ScreenStack ss) {
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

  }

    @Override
    public void update(int delta) {
        this.scott.update(delta);
        this.tom.update(delta);
    }


  }
