/**
 * Layer
 * Represents a layer in a layered graph, maintaining properties which
 * define it's position, size, color and mathemetical characteristics
 *
 * @author Lee Byron
 * @author Martin Wattenberg
 */
public class Layer {

  public String   name;
  public float[]  size;
  public float[]  yBottom;
  public float[]  yTop;
  public int      rgb;
  public int      onset;
  public int      end;
  public float    sum;
  public float    volatility;

  public Layer(String name, float[] size) {

    // check for reasonable data
    for (int i = 0; i < size.length; i++) {
      if (size[i] < 0) {
        throw new IllegalArgumentException("No negative sizes allowed.");
      }
    }

    this.name   = name;
    this.size   = size;
    yBottom     = new float[size.length];
    yTop        = new float[size.length];
    sum         = 0;
    volatility  = 0;
    onset       = -1;

    for (int i = 0; i < size.length; i++) {

      // sum is the summation of all points
      sum += size[i];

      // onset is the first non-zero point
      // end is the last non-zero point
      if (size[i] > 0) {
        if (onset == -1) {
          onset = i;
        } else {
          end = i;
        }
      }

      // volatility is the maximum change between any two consecutive points
      if (i > 0) {
        volatility = Math.max(
          volatility,
          Math.abs(size[i] - size[i-1])
        );
      }
    }
  }

}
