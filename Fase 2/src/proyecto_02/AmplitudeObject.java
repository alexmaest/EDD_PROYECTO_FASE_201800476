package proyecto_02;

import proyecto_02.Structures.List;

/**
 *
 * @author Alexis
 */
public class AmplitudeObject {

    int index;
    List layers;

    public AmplitudeObject(int index, List layers) {
        this.index = index;
        this.layers = layers;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public List getLayers() {
        return layers;
    }

    public void setLayers(List layers) {
        this.layers = layers;
    }
}
