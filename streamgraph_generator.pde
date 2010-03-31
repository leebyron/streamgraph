/**
 * streamgraph_generator
 * Processing Sketch
 * Explores different stacked graph layout, ordering and coloring methods
 * Used to generate example graphics for the Streamgraph paper
 *
 * Press Enter to save image
 *
 * @author Lee Byron
 * @author Martin Wattenberg
 */

boolean     isGraphCurved = true; // catmull-rom interpolation
int         seed          = 28;   // random seed

float       DPI           = 300;
float       widthInches   = 3.5;
float       heightInches  = 0.7;
int         numLayers     = 50;
int         layerSize     = 100;

DataSource  data;
LayerLayout layout;
LayerSort   ordering;
ColorPicker coloring;

Layer[]     layers;

void setup() {

  size(int(widthInches*DPI), int(heightInches*DPI));
  smooth();
  noLoop();

  // GENERATE DATA
  data     = new LateOnsetDataSource();
  //data     = new BelievableDataSource();

  // ORDER DATA
  ordering = new LateOnsetSort();
  //ordering = new VolatilitySort();
  //ordering = new InverseVolatilitySort();
  //ordering = new BasicLateOnsetSort();
  //ordering = new NoLayerSort();

  // LAYOUT DATA
  layout   = new StreamLayout();
  //layout   = new MinimizedWiggleLayout();
  //layout   = new ThemeRiverLayout();
  //layout   = new StackLayout();

  // COLOR DATA
  coloring = new LastFMColorPicker(this, "layers-nyt.jpg");
  //coloring = new LastFMColorPicker(this, "layers.jpg");
  //coloring = new RandomColorPicker(this);

  //=========================================================================

  // calculate time to generate graph
  long time = System.currentTimeMillis();

  // generate graph
  layers = data.make(numLayers, layerSize);
  layers = ordering.sort(layers);
  layout.layout(layers);
  coloring.colorize(layers);

  // fit graph to viewport
  scaleLayers(layers, 1, height - 1);

  // give report
  long layoutTime = System.currentTimeMillis()-time;
  int numLayers = layers.length;
  int layerSize = layers[0].size.length;
  println("Data has " + numLayers + " layers, each with " +
    layerSize + " datapoints.");
  println("Layout Method: " + layout.getName());
  println("Ordering Method: " + ordering.getName());
  println("Coloring Method: " + layout.getName());
  println("Elapsed Time: " + layoutTime + "ms");
}

// adding a pixel to the top compensate for antialiasing letting
// background through. This is overlapped by following layers, so no
// distortion is made to data.
// detail: a pixel is not added to the top-most layer
// detail: a shape is only drawn between it's non 0 values
void draw() {

  int n = layers.length;
  int m = layers[0].size.length;
  int start;
  int end;
  int lastIndex = m - 1;
  int lastLayer = n - 1;
  int pxl;

  background(255);
  noStroke();

  // calculate time to draw graph
  long time = System.currentTimeMillis();

  // generate graph
  for (int i = 0; i < n; i++) {
    start = max(0, layers[i].onset - 1);
    end   = min(m - 1, layers[i].end);
    pxl   = i == lastLayer ? 0 : 1;

    // set fill color of layer
    fill(layers[i].rgb);

    // draw shape
    beginShape();

    // draw top edge, left to right
    graphVertex(start, layers[i].yTop, isGraphCurved, i == lastLayer);
    for (int j = start; j <= end; j++) {
      graphVertex(j, layers[i].yTop, isGraphCurved, i == lastLayer);
    }
    graphVertex(end, layers[i].yTop, isGraphCurved, i == lastLayer);

    // draw bottom edge, right to left
    graphVertex(end, layers[i].yBottom, isGraphCurved, false);
    for (int j = end; j >= start; j--) {
      graphVertex(j, layers[i].yBottom, isGraphCurved, false);
    }
    graphVertex(start, layers[i].yBottom, isGraphCurved, false);

    endShape(CLOSE);
  }

  // give report
  long layoutTime = System.currentTimeMillis() - time;
  println("Draw Time: " + layoutTime + "ms");
}

void graphVertex(int point, float[] source, boolean curve, boolean pxl) {
  float x = map(point, 0, layerSize - 1, 0, width);
  float y = source[point] - (pxl ? 1 : 0);
  if (curve) {
    curveVertex(x, y);
  } else {
    vertex(x, y);
  }
}

void scaleLayers(Layer[] layers, int screenTop, int screenBottom) {
  // Figure out max and min values of layers.
  float min = Float.MAX_VALUE;
  float max = Float.MIN_VALUE;
  for (int i = 0; i < layers[0].size.length; i++) {
    for (int j = 0; j < layers.length; j++) {
      min = min(min, layers[j].yTop[i]);
      max = max(max, layers[j].yBottom[i]);
    }
  }

  float scale = (screenBottom - screenTop) / (max - min);
  for (int i = 0; i < layers[0].size.length; i++) {
    for (int j = 0; j < layers.length; j++) {
      layers[j].yTop[i] = screenTop + scale * (layers[j].yTop[i] - min);
      layers[j].yBottom[i] = screenTop + scale * (layers[j].yBottom[i] - min);
    }
  }
}

void keyPressed() {
  if (keyCode == ENTER) {
    println();
    println("Rendering image...");
    String fileName = "images/streamgraph-" + dateString() + ".png";
    save(fileName);
    println("Rendered image to: " + fileName);
  }

  // hack for un-responsive non looping p5 sketches
  if (keyCode == ESC) {
    redraw();
  }
}

String dateString() {
  return year() + "-" + nf(month(), 2) + "-" + nf(day(), 2) + "@" +
    nf(hour(), 2) + "-" + nf(minute(), 2) + "-" + nf(second(), 2);
}
