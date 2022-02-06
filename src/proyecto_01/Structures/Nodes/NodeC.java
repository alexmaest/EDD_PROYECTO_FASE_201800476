package proyecto_01.Structures.Nodes;

import proyecto_01.Cliente;

/**
 *
 * @author Alexis
 */
public class NodeC {
    public Cliente value;
    public NodeC next;
    
    public NodeC(Cliente value) {
        this.value = value;
        this.next = null;
    }
}
