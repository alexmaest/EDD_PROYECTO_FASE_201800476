package proyecto_01.Structures.Nodes;

import proyecto_01.Ventanilla;

/**
 *
 * @author Alexis
 */
public class NodeV {

    public Ventanilla value;
    public NodeV next;

    public NodeV(Ventanilla value) {
        this.value = value;
        this.next = null;
    }

    
}
