import java.util.*;

/**
 * BelievableDataSource
 * Create test data for layout engine.
 *
 * @author Lee Byron
 * @author Martin Wattenberg
 */
public class BelievableDataSource implements DataSource {

  public Random rnd;

  public BelievableDataSource() {
    // seeded, so we can reproduce results
    this(2);
  }

  public BelievableDataSource(int seed) {
    rnd = new Random(seed);
  }

  public Layer[] make(int numLayers, int sizeArrayLength) {
    Layer[] layers = new Layer[numLayers];

    for (int i = 0; i < numLayers; i++) {
      String name   = "Layer #" + i;
      float[] size  = new float[sizeArrayLength];
      size          = makeRandomArray(sizeArrayLength);
      layers[i]     = new Layer(name, size);
    }

    return layers;
  }

  protected float[] makeRandomArray(int n) {
    float[] x = new float[n];

    // add a handful of random bumps
    for (int i=0; i<5; i++) {
      addRandomBump(x);
    }

    return x;
  }

  protected void addRandomBump(float[] x) {
    float height  = 1 / rnd.nextFloat();
    float cx      = (float)(2 * rnd.nextFloat() - 0.5);
    float r       = rnd.nextFloat() / 10;

    for (int i = 0; i < x.length; i++) {
      float a = (i / (float)x.length - cx) / r;
      x[i] += height * Math.exp(-a * a);
    }
  }

}
