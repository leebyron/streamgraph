import java.util.*;

/**
 * LateOnsetData
 * Creates false data which resembles late onset time-series.
 * Such as band popularity or movie box-office income.
 *
 * @author Lee Byron
 * @author Martin Wattenberg
 */
public class LateOnsetDataSource implements DataSource {

  public Random rnd;

  public LateOnsetDataSource() {
    // seeded, so we can reproduce results
    this(2);
  }

  public LateOnsetDataSource(int seed) {
    rnd = new Random(seed);
  }

  public Layer[] make(int numLayers, int sizeArrayLength) {
    Layer[] layers = new Layer[numLayers];

    for (int i = 0; i < numLayers; i++) {
      String name   = "Layer #" + i;
      int onset     = (int)(sizeArrayLength * (rnd.nextFloat() * 1.25 - 0.25));
      int duration  = (int)(rnd.nextFloat() * 0.75 * sizeArrayLength);
      float[] size  = new float[sizeArrayLength];
      size          = makeRandomArray(sizeArrayLength, onset, duration);
      layers[i]     = new Layer(name, size);
    }

    return layers;
  }

  protected float[] makeRandomArray(int n, int onset, int duration) {
    float[] x = new float[n];

    // add a single random bump
    addRandomBump(x, onset, duration);

    return x;
  }

  protected void addRandomBump(float[] x, int onset, int duration) {
    float height  = rnd.nextFloat();
    int start     = Math.max(0, onset);
    int end       = Math.min(x.length, onset + duration);
    int len       = end - onset;

    for (int i = start; i < x.length && i < onset + duration; i++) {
      float xx = (float)(i - onset) / duration;
      float yy = (float)(xx * Math.exp(-10 * xx));
      x[i] += height * yy;
    }
  }

}
