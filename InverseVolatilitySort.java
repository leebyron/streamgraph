import java.util.*;

/**
 * InverseVolatilitySort
 * Sorts an array of layers by their volatility, placing the most volatile
 * layers along the insides of the graph, illustrating how disruptive this
 * volatility can be to a stacked graph.
 *
 * @author Lee Byron
 * @author Martin Wattenberg
 */
public class InverseVolatilitySort extends LayerSort {

  public String getName() {
    return "Inverse Volatility Sorting, Evenly Weighted";
  }

  public Layer[] sort(Layer[] layers) {
    // first sort by volatility
    Arrays.sort(layers, new VolatilityComparator(false));

    return orderToOutside(layers);
  }

}
