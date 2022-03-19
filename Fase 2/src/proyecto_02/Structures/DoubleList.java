package proyecto_02.Structures;

import proyecto_02.Album;
import proyecto_02.Structures.SubStructure.NodeDouble;


/**
 *
 * @author Alexis
 */
public class DoubleList {

    public NodeDouble first;
    public NodeDouble last;
    public int size = 0;

    public DoubleList() {
        this.first = null;
        this.last = null;
        this.size = 0;
    }

    public void add(Album valueToAdd) {
        NodeDouble myNode = new NodeDouble();
        myNode.setValue(valueToAdd);
        if (this.first == null) {
            first = myNode;
            first.setNext(null);
            first.setPrevious(null);
            last = first;
        } else {
            last.setNext(myNode);
            myNode.setPrevious(last);
            myNode.setNext(null);
            last = myNode;
        }
        this.size += 1;
    }

    public void printContent() {
        NodeDouble Current = first;
        if (this.size != 0) {
            while (Current != null) {
                System.out.println(Current.getValue().getName());
                Current = Current.getNext();
            }
        } else {
            System.out.println("La lista esta vacía");
        }
    }

    /*public void remove(int valueToRemove) {
        NodeDouble Current = new NodeDouble();
        NodeDouble Back = new NodeDouble();
        Current = first;
        Back = null;
        if (first != null) {
            while (Current != null) {
                if (Current.getValue().getId() == valueToRemove) {
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
    }*/
}
