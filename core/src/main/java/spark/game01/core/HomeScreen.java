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

  private final Gameplay00 gameplay00;
  private final HowtoScreen howtoscreen;
  private final TopScore topscore;
  private ScreenStack ss;
  private Root root;

  private final Image bgImage;
  private final ImageLayer bg;
  private final Image gnameImage;
  private final ImageLayer gname;
  private final Image startImage;
  private final ImageLayer startbutton;
  private final Image howtoImage;
  private final ImageLayer howbutton;
  private final Image topImage;
  private final ImageLayer topbutton;

  public HomeScreen(final ScreenStack ss) {
    this.ss = ss;
    this.gameplay00 = new Gameplay00(ss);
    this.howtoscreen = new HowtoScreen(ss);
    this.topscore = new TopScore(ss);

    this.bgImage = assets().getImage("images/screen05.png");
    this.bg = graphics().createImageLayer(bgImage);

    this.gnameImage = assets().getImage("images/gamename.png");
    this.gname = graphics().createImageLayer(gnameImage);
    gname.setTranslation(60,50);

    this.startImage = assets().getImage("images/startbutton.png");
    this.startbutton = graphics().createImageLayer(startImage);
    startbutton.setTranslation(185,170);
    startbutton.addListener(new Mouse.LayerAdapter(){
          @Override
          public void onMouseUp(Mouse.ButtonEvent event){
             ss.push(gameplay00);
          }


      });


    this.howtoImage = assets().getImage("images/howbutton.png");
    this.howbutton = graphics().createImageLayer(howtoImage);
    howbutton.setTranslation(185,250);
    howbutton.addListener(new Mouse.LayerAdapter(){
          @Override
          public void onMouseUp(Mouse.ButtonEvent event){
             ss.push(howtoscreen);
          }


      });

    this.topImage = assets().getImage("images/topscorebutton.png");
    this.topbutton = graphics().createImageLayer(topImage);
    topbutton.setTranslation(185,330);
    topbutton.addListener(new Mouse.LayerAdapter(){
          @Override
          public void onMouseUp(Mouse.ButtonEvent event){
             ss.push(topscore);
          }


      });

    
    this.layer.add(bg);
    this.layer.add(gname);
	this.layer.add(startbutton);
	this.layer.add(howbutton);
	this.layer.add(topbutton);
    
  }

  @Override
  public void wasShown(){
    super.wasShown();
  }


  }
