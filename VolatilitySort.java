import java.util.*;

/**
 * VolatilitySort
 * Sorts an array of layers by their volatility, placing the most volatile
 * layers along the outsides of the graph, thus minimizing unneccessary
 * distortion.
 *
 * First sort by volatility, then creates a 'top' and 'bottom' collection.
 * Iterating through the sorted list of layers, place each layer in whichever
 * collection has less total mass, arriving at an evenly weighted graph.
 *
 * @author Lee Byron
 * @author Martin Wattenberg
 */
public class VolatilitySort extends LayerSort {

  public String getName() {
    return "Volatility Sorting, Evenly Weighted";
  }

  public Layer[] sort(Layer[] layers) {
    // first sort by volatility
    Arrays.sort(layers, new VolatilityComparator(true));

    return orderToOutside(layers);
  }

}
