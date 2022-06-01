package proyecto_01;

import proyecto_01.Structures.Pile;
import proyecto_01.Structures.Nodes.NodeC;

/**
 *
 * @author Alexis
 */
public class Ventanilla {

    private int id = 0;
    private String name = "";
    private NodeC client;
    private Pile images;

    public Ventanilla(int id, String name, NodeC client, Pile images) {
        this.id = id;
        this.name = name;
        this.client = client;
        this.images = images;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public NodeC getClient() {
        return client;
    }

    public void setClient(NodeC client) {
        this.client = client;
    }

    public Pile getImages() {
        return images;
    }

    public void setImages(Pile images) {
        this.images = images;
    }

}
