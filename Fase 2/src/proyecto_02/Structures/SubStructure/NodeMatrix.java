package proyecto_02.Structures.SubStructure;

/**
 *
 * @author Alexis
 */
public class NodeMatrix {

    public NodeMatrix next;
    public NodeMatrix previous;
    public NodeMatrix down;
    public NodeMatrix up;
    public String value;
    public int x;
    public int y;

    public NodeMatrix(int x, int y, String value) {
        this.next = null;
        this.previous = null;
        this.down = null;
        this.up = null;
        this.value = value;
        this.x = x;
        this.y = y;
    }
}
