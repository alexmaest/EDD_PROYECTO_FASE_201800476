package proyecto_02.Structures.SubStructure;

import proyecto_02.Album;
import proyecto_02.Client;

/**
 *
 * @author Alexis
 */
public class NodeDouble {

    private Album value;
    private NodeDouble next;
    private NodeDouble previous;

    public Album getValue() {
        return value;
    }

    public void setValue(Album value) {
        this.value = value;
    }

    public NodeDouble getNext() {
        return next;
    }

    public void setNext(NodeDouble next) {
        this.next = next;
    }

    public NodeDouble getPrevious() {
        return previous;
    }

    public void setPrevious(NodeDouble previous) {
        this.previous = previous;
    }
}
