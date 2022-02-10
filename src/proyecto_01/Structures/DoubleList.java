package proyecto_01.Structures;

import proyecto_01.Cliente;
import proyecto_01.Structures.Nodes.NodeDoubleC;

/**
 *
 * @author Alexis
 */
public class DoubleList {

    public NodeDoubleC first;
    public NodeDoubleC last;
    public int size = 0;

    public DoubleList() {
        this.first = null;
        this.last = null;
        this.size = 0;
    }

    public void add(Cliente valueToAdd) {
        NodeDoubleC myNode = new NodeDoubleC();
        myNode.value = valueToAdd;
        if (this.first == null) {
            first = myNode;
            first.next = null;
            first.previous = null;
            last = first;
        } else {
            last.next = myNode;
            myNode.previous = last;
            myNode.next = null;
            last = myNode;
        }
        this.size += 1;
    }

    public void printContent() {
        NodeDoubleC Current = new NodeDoubleC();
        Current = first;
        if (this.size != 0) {
            while (Current != null) {
                System.out.println(Current.value.getName());
                Current = Current.next;
            }
        } else {
            System.out.println("La lista esta vacía");
        }
    }

    public void remove(int valueToRemove) {
        NodeDoubleC Current = new NodeDoubleC();
        NodeDoubleC Back = new NodeDoubleC();
        Current = first;
        Back = null;
        if (first != null) {
            while (Current != null) {
                if (Current.value.getId() == valueToRemove) {
                    if (Current == first) {
                        first = first.next;
                        try {
                            first.previous = null;
                        }catch(Exception e){
                        }
                    } else if (Current == last) {
                        Back.next = null;
                        last = Back;
                    } else {
                        Back.next = Current.next;
                        Current.next.previous = Back;
                    }
                }
                Back = Current;
                Current = Current.next;
            }
            this.size -= 1;
        }
    }

    /*
    public void remove(int valueToRemove) {
        NodeDoubleC Current = new NodeDoubleC();
        NodeDoubleC Back = new NodeDoubleC();
        Current = first;
        Back = null;
        System.out.println("SIZE: " + this.size);
        while (Current != null) {
            if (Current.value.getId() == valueToRemove) {
                if (Current == first) {
                    first = first.next;
                    first.previous = null;
                } else if (Current == last) {
                    Back = Current.previous;
                    Back.next = null;
                    last = Back;
                } else {
                    Back.next = Current.next;
                    Current.next.previous = Current.previous;
                }
            }
            Back = Current;
            Current = Current.next;
        }
        this.size -= 1;
    }*/
}
