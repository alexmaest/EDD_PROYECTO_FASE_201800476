package proyecto_02;

import proyecto_02.Structures.AvlTree;
import proyecto_02.Structures.BinaryTree;
import proyecto_02.Structures.DoubleList;

/**
 *
 * @author Alexis
 */
public class Client {
    
    private long dpi = 0;
    private String name = "";
    private String password = "";
    private AvlTree images;
    private BinaryTree layers;
    private DoubleList Albums;
    

    public Client(long dpi, String name, String password) {
        this.dpi = dpi;
        this.name = name;
        this.password = password;
    }

    public long getDpi() {
        return dpi;
    }

    public void setDpi(int dpi) {
        this.dpi = dpi;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AvlTree getImages() {
        return images;
    }

    public void setImages(AvlTree images) {
        this.images = images;
    }

    public BinaryTree getLayers() {
        return layers;
    }

    public void setLayers(BinaryTree layers) {
        this.layers = layers;
    }

    public DoubleList getAlbums() {
        return Albums;
    }

    public void setAlbums(DoubleList Albums) {
        this.Albums = Albums;
    }
}
