package spark.game01.core.Screen;

import playn.core.Game;
import playn.core.Sound;
import playn.core.util.Clock;
import tripleplay.game.ScreenStack;

import static playn.core.PlayN.assets;

public class MyGame extends Game.Default {

  public static final int UPDATE_RATE = 25;
  private ScreenStack ss = new ScreenStack();
  protected final Clock.Source clock = new Clock.Source(UPDATE_RATE);

  public static Sound world01 = assets().getSound("BGM/world01");
  public static Sound world02 = assets().getSound("BGM/world02");
  public static Sound world03 = assets().getSound("BGM/world03");
  public static Sound homebgm = assets().getSound("BGM/homebgm");
  public static Sound topbgm = assets().getSound("BGM/topscorebgm");
  public static Sound howtobgm = assets().getSound("BGM/howtobgm");
  public static Sound victory = assets().getSound("BGM/victory");

  public MyGame() {
    super(UPDATE_RATE); // call update every 33ms (30 times per second)
  }

  @Override
  public void init() {
   ss.push(new HomeScreen(ss));
  }

  @Override
  public void update(int delta) {
    ss.update(delta);
  }

  @Override
  public void paint(float alpha) {
    clock.paint(alpha);
    ss.paint(clock);
  }
}
