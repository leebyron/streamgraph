import java.util.*;

/**
 * VolatilityComparator
 * Compares two Layers based on their volatility
 *
 * @author Lee Byron
 * @author Martin Wattenberg
 */
public class VolatilityComparator implements Comparator {

  public boolean ascending;

  public VolatilityComparator(boolean ascending) {
    this.ascending = ascending;
  }

  public int compare(Object p, Object q) {
    Layer pL = (Layer)p;
    Layer qL = (Layer)q;
    float volatilityDifference = pL.volatility - qL.volatility;
    return (ascending ? 1 : -1) * (int)(10000000 * volatilityDifference);
  }

  public boolean equals(Object p, Object q) {
    Layer pL = (Layer)p;
    Layer qL = (Layer)q;
    return pL.volatility == qL.volatility;
  }

}
