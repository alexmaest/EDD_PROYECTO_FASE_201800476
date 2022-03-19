package proyecto_02;

import proyecto_02.Structures.List;

/**
 *
 * @author Alexis
 */
public class Photo {

    int id;
    List layers;

    public Photo(int id, List layers) {
        this.id = id;
        this.layers = layers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List getLayers() {
        return layers;
    }

    public void setLayers(List layers) {
        this.layers = layers;
    }
}
