import processing.core.*;

/**
 * LastFMColorPicker
 * Loads in an image and uses it as a two-dimensional gradient
 * Supply two [0,1) numbers and get the color of the gradient at that point
 *
 * @author Lee Byron
 * @author Martin Wattenberg
 */
public class LastFMColorPicker implements ColorPicker {

  public PImage source;

  public LastFMColorPicker(PApplet parent, String src) {
    source = parent.loadImage(src);
  }

  public String getName() {
    return "Listening History Color Scheme";
  }

  public void colorize(Layer[] layers) {
    // find the largest layer to use as a normalizer
    float maxSum = 0;
    for (int i=0; i<layers.length; i++) {
      maxSum = (float) Math.max(maxSum, layers[i].sum);
    }

    // find the color for each layer
    for (int i = 0; i < layers.length; i++) {
      float normalizedOnset = (float)layers[i].onset / layers[i].size.length;
      float normalizedSum = layers[i].sum / maxSum;
      float shapedSum = (float)(1.0 - Math.sqrt(normalizedSum));

      layers[i].rgb = get(normalizedOnset, shapedSum);
    }
  }

  protected int get(float g1, float g2) {
    // get pixel coordinate based on provided parameters
    int x = PApplet.floor(g1 * source.width);
    int y = PApplet.floor(g2 * source.height);

    // ensure that the pixel is within bounds.
    x = PApplet.constrain(x, 0, source.width - 1);
    y = PApplet.constrain(y, 0, source.height - 1);

    // return the color at the requested pixel
    return source.pixels[x + y * source.width];
  }

}
