package spark.game01.core;

import static playn.core.PlayN.*;
import playn.core.*;
import playn.core.ImageLayer;
import playn.core.Mouse;
import react.UnitSlot;
import spark.game01.core.character.Zento;
import spark.game01.core.character.Scott;
import tripleplay.game.*;
import java.lang.Override;


public class Gameplay00 extends Screen{

  private final ScreenStack ss;
  private final ImageLayer bg;
    private final ImageLayer backbutton;
    private Zento z;
    private Scott scott;

  public Gameplay00(final ScreenStack ss) {
    this.ss = ss;
      Image bgImage = assets().getImage("images/screen00.png");
      this.bg = graphics().createImageLayer(bgImage);

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

      z = new Zento(540f,400f);
      this.layer.add(z.layer());

      scott = new Scott(320f,400f);
      this.layer.add(scott.layer());


  }

    @Override
    public void update(int delta) {
        this.z.update(delta);
        this.scott.update(delta);
    }


  }
