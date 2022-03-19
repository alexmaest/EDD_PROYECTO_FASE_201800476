package proyecto_02;

import proyecto_02.Structures.List;

/**
 *
 * @author Alexis
 */
public class Album {
    String name;
    List images;

    public Album(String name, List images) {
        this.name = name;
        this.images = images;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List getImages() {
        return images;
    }

    public void setImages(List images) {
        this.images = images;
    }
}
