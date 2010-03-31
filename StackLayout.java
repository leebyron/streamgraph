import java.util.*;

/**
 * StackLayout
 * Standard stacked graph layout, with a straight baseline
 *
 * @author Lee Byron
 * @author Martin Wattenberg
 */
public class StackLayout extends LayerLayout {

  public String getName() {
    return "Stacked Layout";
  }

  public void layout(Layer[] layers) {
    int n = layers[0].size.length;

    // lay out layers, top to bottom.
    float[] baseline = new float[n];
    Arrays.fill(baseline, 0);

    // Put layers on top of the baseline.
    stackOnBaseline(layers, baseline);
  }

}
