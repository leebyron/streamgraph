/**
 * DataSource
 * Interface for creating a data source
 *
 * @author Lee Byron
 * @author Martin Wattenberg
 */
public interface DataSource {

  public Layer[] make(int numLayers, int sizeArrayLength);

}
