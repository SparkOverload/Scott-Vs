package spark.game01.core.Screen;

import static playn.core.PlayN.*;
import playn.core.*;
import playn.core.ImageLayer;
import playn.core.Mouse;
import playn.core.util.Clock;
import spark.game01.core.Toolsgx.ToolsG;
import tripleplay.game.*;
import tripleplay.util.Colors;

import java.lang.Override;
import java.util.ArrayList;
import java.util.List;


public class TopScore extends Screen{

  private final ScreenStack ss;
  private final ImageLayer bg;
  private ImageLayer tagsb;
  private ImageLayer board;
  private static List<Integer> hff = new ArrayList<Integer>();
  private int[] savescore;
  private ToolsG toolg = new ToolsG();
  private float alphaStart = 0.00f;
  private Layer[] scorehp;
  private int y = 90;
  private boolean you = true;
  private boolean swapped = true;
  private int j = 0;
  private int tmp;



  public TopScore(final ScreenStack ss) {
    this.ss = ss;
      Image bgImage = assets().getImage("images/Screen_bg/screen06.png");
      this.bg = graphics().createImageLayer(bgImage);

      Image scoreboardImage = assets().getImage("images/sb.png");
      tagsb = graphics().createImageLayer(scoreboardImage);
      tagsb.setTranslation(120,5);

      Image boardImage = assets().getImage("images/board.png");
      board = graphics().createImageLayer(boardImage);
      board.setTranslation(130,65);

    keyboard().setListener(new Keyboard.Adapter(){
      @Override
      public void onKeyDown(Keyboard.Event event) {
        if(event.key()==Key.ESCAPE){
          MyGame.topbgm.stop();
          Gameplay02.wingame=false;
          ss.remove(ss.top());
          ss.push(new HomeScreen(ss));
        }
      }
    });



    if(Gameplay02.wingame==true) {
      hff.add(Gameplay00.score);
    }
      savescore = new int[hff.size()];
      for (int i = 0; i < hff.size(); i++) {
        savescore[i] = hff.get(i);
      }





    while (swapped) {
      swapped = false;
      j++;
      for (int i = 0; i < savescore.length - j; i++) {
        if (savescore[i] < savescore[i + 1]) {
          tmp = savescore[i];
          savescore[i] = savescore[i + 1];
          savescore[i + 1] = tmp;
          swapped = true;
        }
      }
    }



    scorehp = new Layer[hff.size()];
    for(int i = 0; i < hff.size(); i++){
      if(savescore[i]>=90) {
        if((savescore[i]==Gameplay00.score)&& you==true){
          scorehp[i] = toolg.genText("RANK S : " + savescore[i]+" --- YOU", 30, Colors.RED, 165, y);
          you=false;
        }else {
          scorehp[i] = toolg.genText("RANK S : " + savescore[i], 30, Colors.RED, 165, y);
        }
      }
      if(savescore[i]>=80 && savescore[i]<=89){
        if((savescore[i]==Gameplay00.score)&& you==true){
          scorehp[i] = toolg.genText("RANK A : " + savescore[i]+" --- YOU", 30, Colors.ORANGE, 165, y);
          you=false;
        }else {
          scorehp[i] = toolg.genText("RANK A : " + savescore[i], 30, Colors.ORANGE, 165, y);
        }
      }
      if(savescore[i]>=60 && savescore[i]<=79){
        if((savescore[i]==Gameplay00.score)&& you==true){
          scorehp[i] = toolg.genText("RANK B : " + savescore[i]+" --- YOU", 30, Colors.PINK, 165, y);
          you=false;
        }else {
          scorehp[i] = toolg.genText("RANK B : " + savescore[i], 30, Colors.PINK, 165, y);
        }
      }
      if(savescore[i]>=50 && savescore[i]<=69){
        if((savescore[i]==Gameplay00.score)&& you==true){
          scorehp[i] = toolg.genText("RANK C : " + savescore[i]+" --- YOU", 30, Colors.DARK_GRAY, 165, y);
          you=false;
        }else {
          scorehp[i] = toolg.genText("RANK C : " + savescore[i], 30, Colors.DARK_GRAY, 165, y);
        }
      }
      if(savescore[i]<50){
        if((savescore[i]==Gameplay00.score)&& you==true){
          scorehp[i] = toolg.genText("RANK D : " + savescore[i]+" --- YOU", 30, Colors.BLACK, 165, y);
          you=false;
        }else {
          scorehp[i] = toolg.genText("RANK D : " + savescore[i], 30, Colors.BLACK, 165, y);
        }
      }
      y+=35;
    }


    System.out.println(hff.size());
  }

  @Override
  public void wasShown(){
    super.wasShown();
    MyGame.topbgm.play();
    MyGame.topbgm.setLooping(true);
    //////////////////////////////////////////////bgm
    this.layer.add(bg);
    this.layer.add(tagsb);
    this.layer.add(board);
    for (int i = 0; i < scorehp.length; i++){
      if(i>9){
        break;
      }
      this.layer.add(scorehp[i]);
    }
  }

  public void update(int delta){
    super.update(delta);
    bg.setAlpha(alphaStart);
    tagsb.setAlpha(alphaStart);
    board.setAlpha(alphaStart);
  }

  public void paint(Clock clock){
    super.paint(clock);
    alphaStart = toolg.fade(alphaStart);
  }




  }
