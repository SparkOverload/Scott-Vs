package spark.game01.core;

import static playn.core.PlayN.*;

import playn.core.Font;
import react.UnitSlot;
import tripleplay.game.UIScreen;
import tripleplay.game.ScreenStack;
import tripleplay.ui.*;
import tripleplay.ui.layout.*;
import static playn.core.PlayN.graphics;
import playn.core.*;
import playn.core.ImageLayer;
import playn.core.Mouse;
import react.UnitSlot;
import tripleplay.game.*;

import java.lang.Override;

public class HomeScreen extends UIScreen{


  private final HowtoScreen howtoscreen;
  private ScreenStack ss;
  private Root root;

  private final Image bgImage;
  private final ImageLayer bg;
  private final ImageLayer backbutton;
  private final Image backImage;
  private final Image gnameImage;
  private final ImageLayer gname;

  public HomeScreen(final ScreenStack ss) {
    this.ss = ss;
    this.howtoscreen = new HowtoScreen(ss);
    this.bgImage = assets().getImage("images/screen05.png");
    this.bg = graphics().createImageLayer(bgImage);

    this.gnameImage = assets().getImage("images/gamename.png");
    this.gname = graphics().createImageLayer(gnameImage);
    gname.setTranslation(60,50);

    this.backImage = assets().getImage("images/backbutton.png");
    this.backbutton = graphics().createImageLayer(backImage);
    backbutton.setTranslation(10,10);
    backbutton.addListener(new Mouse.LayerAdapter(){
          @Override
          public void onMouseUp(Mouse.ButtonEvent event){
             ss.push(howtoscreen);
          }


      });


    this.layer.add(bg);
    this.layer.add(gname);
	this.layer.add(backbutton);
    
  }

  @Override
  public void wasShown(){
    super.wasShown();
  }


  }
