package proyecto_02.Structures.SubStructure;

import proyecto_02.Client;

/**
 *
 * @author Alexis
 */
public class NodeB {

    //Valores
    Client client;
    
    //Apuntadores
    NodeB previous;
    NodeB next;
    Branch left;
    Branch right;

    public NodeB(Client client) {
        this.client = client;
        this.previous = null;
        this.next = null;
        this.left = null;
        this.right = null;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public NodeB getPrevious() {
        return previous;
    }

    public void setPrevious(NodeB previous) {
        this.previous = previous;
    }

    public NodeB getNext() {
        return next;
    }

    public void setNext(NodeB next) {
        this.next = next;
    }

    public Branch getLeft() {
        return left;
    }

    public void setLeft(Branch left) {
        this.left = left;
    }

    public Branch getRight() {
        return right;
    }

    public void setRight(Branch right) {
        this.right = right;
    }
}
