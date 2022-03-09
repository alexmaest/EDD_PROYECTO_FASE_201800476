package proyecto_02.Structures;

import proyecto_02.Structures.Nodes.NodeS;
import proyecto_02.Structures.Nodes.NodeTreeB;
import proyecto_02.Structures.Nodes.NodeC;
import proyecto_02.Client;

/**
 *
 * @author Alexis
 */
public class List {

    //Client
    public NodeC first;
    public int size = 0;

    //Son Node
    public NodeS first2;
    public int size2 = 0;

    public List() {
        this.first = null;
        this.size = 0;
        this.first2 = null;
        this.size2 = 0;
    }

    public void addC(Client valueToAdd) {
        NodeC myNode = new NodeC(valueToAdd);
        if (this.size == 0) {
            this.first = myNode;
        } else {
            NodeC Current = this.first;
            while (Current.next != null) {
                Current = Current.next;
            }
            Current.next = myNode;
        }
        this.size += 1;
    }

    public boolean removeC(int idClient) {
        if (this.size == 0) {
            return false;
        } else {
            NodeC Current = this.first;
            NodeC Previous = null;
            while (Current.value.getDpi() != idClient) {
                Previous = Current;
                Current = Current.next;
                if (Current.next == null && Current.value.getDpi() != idClient) {
                    System.out.println("Id no encontrado");
                    return false;
                }
            }
            if (Previous == null) {
                this.first = Current.next;
                Current.next = null;
            } else if (Current != null) {
                Previous.next = Current.next;
                Current.next = null;
            }
            this.size -= 1;
        }
        return true;
    }

    public void printContentC() {
        NodeC Current = this.first;
        if (this.size != 0) {
            while (Current.next != null) {
                System.out.print(Current.value.getName() + "    ");
                Current = Current.next;
            }
            System.out.println(Current.value.getName());
        } else {
            System.out.println("La lista esta vacía");
        }
    }

    public void SearchClient(int id) {
        NodeC Current = this.first;
        boolean Found = false;
        if (this.size != 0) {
            while (Current.next != null) {
                if (Current.value.getDpi() == id) {
                    System.out.print("Nombre: " + Current.value.getName());
                    Found = true;
                    break;
                } else {
                    Current = Current.next;
                }
            }
            if (Found == false) {
                System.out.println("Información: Cliente no encontrado");
            }
        } else {
            System.out.println("La lista esta vacía");
        }
    }

    public void addSon(NodeTreeB valueToAdd) {
        NodeS myNode = new NodeS(valueToAdd);
        if (this.size2 == 0) {
            this.first2 = myNode;
        } else {
            NodeS Current = this.first2;
            while (Current.next != null) {
                Current = Current.next;
            }
            Current.next = myNode;
        }
        this.size2 += 1;
    }

    public void printContentSon() {
        NodeS Current = this.first2;
        if (this.first2 != null) {
            while (Current.next != null) {
                List values = Current.value.values;
                NodeC single = values.first;
                while(single != null){
                    System.out.println(single.value.getDpi());
                    single = single.next;
                }
                Current = Current.next;
            }
        } else {
            System.out.println("La lista esta vacía");
        }
    }

}
