package proyecto_02.Structures.SubStructure;

import proyecto_02.Client;
import proyecto_02.Layer;
import proyecto_02.Photo;
import proyecto_02.Pixel;

/**
 *
 * @author Alexis
 */

public class Node {

    public Client valuec;
    public NodeB values;
    public Pixel valuep;
    public Layer valuel;
    public Photo valuei;


    public Node next;

    public Node(Client value) {
        this.valuec = value;
        this.next = null;
    }

    public Node(Pixel value) {
        this.valuep = value;
        this.next = null;
    }
    
    public Node(NodeB value) {
        this.values = value;
        this.next = null;
    }
    
    public Node(Layer value) {
        this.valuel = value;
        this.next = null;
    }
    
    public Node(Photo value) {
        this.valuei = value;
        this.next = null;
    }
}
