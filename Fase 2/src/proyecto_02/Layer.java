package proyecto_02;

import proyecto_02.Structures.List;

/**
 *
 * @author Alexis
 */
public class Layer {
    int id = 0;
    List pixels;
    
    public Layer(int id, List pixels){
        this.id = id;
        this.pixels = pixels;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List getPixels() {
        return pixels;
    }

    public void setPixels(List pixels) {
        this.pixels = pixels;
    }
}
