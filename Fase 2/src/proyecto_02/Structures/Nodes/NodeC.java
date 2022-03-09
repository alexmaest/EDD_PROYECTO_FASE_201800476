package proyecto_02.Structures.Nodes;

import proyecto_02.Client;

/**
 *
 * @author Alexis
 */
public class NodeC {
    public Client value;
    public NodeC next;
    
    public NodeC(Client value) {
        this.value = value;
        this.next = null;
    }
}
