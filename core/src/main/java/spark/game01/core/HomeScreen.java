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

public class HomeScreen extends Screen{

  

  private final HowtoScreen howtoscreen;
  private ScreenStack ss;
  private Root root;
  private final Image bgImage;
  private final ImageLayer bg;
  private final ImageLayer backbutton;
  private final Image backImage;

  public HomeScreen(final ScreenStack ss) {
    this.ss = ss;
    this.howtoscreen = new HowtoScreen(ss);
    this.bgImage = assets().getImage("images/screen01.png");
    this.bg = graphics().createImageLayer(bgImage);

    this.backImage = assets().getImage("images/backbutton.png");
    this.backbutton = graphics().createImageLayer(backImage);
    backbutton.setTranslation(280,230);
    backbutton.addListener(new Mouse.LayerAdapter(){
          @Override
          public void onMouseUp(Mouse.ButtonEvent event){
             ss.push(howtoscreen);
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
