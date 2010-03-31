import processing.core.*;
import java.util.*;

/**
 * RandomColorPicker
 * Chooses random colors within an acceptable HSB color spectrum
 *
 * @author Lee Byron
 * @author Martin Wattenberg
 */
public class RandomColorPicker implements ColorPicker {

  public Random rnd;
  public PApplet parent;

  public RandomColorPicker(PApplet parent) {
    this(parent, 2);
  }

  public RandomColorPicker(PApplet parent, int seed) {
    this.parent = parent;
    parent.colorMode(PApplet.RGB, 255);

    // seeded, so we can reproduce results
    rnd = new Random(seed);
  }

  public String getName() {
    return "Random Colors";
  }

  public void colorize(Layer[] layers) {
    for (int i = 0; i < layers.length; i++) {
      float h = PApplet.lerp(0.6f, 0.65f, rnd.nextFloat());
      float s = PApplet.lerp(0.2f, 0.25f, rnd.nextFloat());
      float b = PApplet.lerp(0.4f, 0.95f, rnd.nextFloat());

      layers[i].rgb = hsb2rgb(h, s, b);
    }
  }

  protected int hsb2rgb(float x, float y, float z) {
    float calcR = 0;
    float calcG = 0;
    float calcB = 0;
    float calcA = 1;

    if (y == 0) { // saturation == 0
      calcR = calcG = calcB = z;
    } else {
      float which = (x - (int)x) * 6.0f;
      float f = which - (int)which;
      float p = z * (1.0f - y);
      float q = z * (1.0f - y * f);
      float t = z * (1.0f - (y * (1.0f - f)));

      switch ((int)which) {
        case 0:
          calcR = z;
          calcG = t;
          calcB = p;
          break;
        case 1:
          calcR = q;
          calcG = z;
          calcB = p;
          break;
        case 2:
          calcR = p;
          calcG = z;
          calcB = t;
          break;
        case 3:
          calcR = p;
          calcG = q;
          calcB = z;
          break;
        case 4:
          calcR = t;
          calcG = p;
          calcB = z;
          break;
        case 5:
          calcR = z;
          calcG = p;
          calcB = q;
          break;
      }
    }

    int calcRi = (int)(255 * calcR);
    int calcGi = (int)(255 * calcG);
    int calcBi = (int)(255 * calcB);
    int calcAi = (int)(255 * calcA);
    int calcColor = (calcAi << 24) | (calcRi << 16) | (calcGi << 8) | calcBi;

    return calcColor;
  }

}
