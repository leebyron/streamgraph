import java.util.*;
import processing.core.*;

/**
 * CSVDataSource
 * Read data from a CSV file.
 * Each Layer corresponds to one line, with the first entry in the line the name of the layer.
 * Assumes every line is the same length.
 * Ignores the parameters given to make.
 *
 * @author Albert Sun
 * @author Lee Byron
 * @author Martin Wattenberg
 */
public class CSVDataSource implements DataSource {

  public String[] data;

  public CSVDataSource(PApplet parent, String filename) {
    data = parent.loadStrings(filename);
  }

  public Layer[] make(int numLayers, int sizeArrayLength) {
    numLayers = data.length;
    Layer[] layers = new Layer[numLayers];

    for (int i = 0; i < numLayers; i++) {
      String[] fields  = data[i].split(",");
      sizeArrayLength  = fields.length - 1;
      String name      = fields[0];
      float[] size     = new float[sizeArrayLength];
      size             = makeDataArray(fields);
      layers[i]        = new Layer(name, size);
    }

    return layers;
  }

  protected float[] makeDataArray(String[] a) {
    float[] x = new float[a.length - 1];
    for (int i = 1; i < a.length; i++) {
      x[i-1] = Float.valueOf(a[i].trim()).floatValue();
    }
    return x;
  }

}
