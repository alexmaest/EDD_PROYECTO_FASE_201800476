package proyecto_02.Structures;

import proyecto_02.Structures.SubStructure.NodeB;
import proyecto_02.Structures.SubStructure.Node;
import proyecto_02.Client;
import proyecto_02.Pixel;

/**
 *
 * @author Alexis
 */
public class List {

    //Client | Son | Pixel 
    public Node first;
    public int size = 0;

    public List() {
        this.first = null;
        this.size = 0;
    }

    public void addC(Client valueToAdd) {
        Node myNode = new Node(valueToAdd);
        if (this.size == 0) {
            this.first = myNode;
        } else {
            Node Current = this.first;
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
            Node Current = this.first;
            Node Previous = null;
            while (Current.valuec.getDpi() != idClient) {
                Previous = Current;
                Current = Current.next;
                if (Current.next == null && Current.valuec.getDpi() != idClient) {
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
        Node Current = this.first;
        if (this.size != 0) {
            while (Current.next != null) {
                System.out.print(Current.valuec.getName() + "    ");
                Current = Current.next;
            }
            System.out.println(Current.valuec.getName());
        } else {
            System.out.println("La lista esta vacía");
        }
    }

    public void SearchClient(int id) {
        Node Current = this.first;
        boolean Found = false;
        if (this.size != 0) {
            while (Current.next != null) {
                if (Current.valuec.getDpi() == id) {
                    System.out.print("Nombre: " + Current.valuec.getName());
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

    /*public void addSon(NodeB valueToAdd) {
        Node myNode = new Node(valueToAdd);
        if (this.size == 0) {
            this.first = myNode;
        } else {
            Node Current = this.first;
            while (Current.next != null) {
                Current = Current.next;
            }
            Current.next = myNode;
        }
        this.size += 1;
    }*/

    /*public void printContentSon() {
        Node Current = this.first;
        if (this.first != null) {
            while (Current.next != null) {
                List values = Current.values.values;
                Node single = values.first;
                while(single != null){
                    System.out.println(single.valuec.getDpi());
                    single = single.next;
                }
                Current = Current.next;
            }
        } else {
            System.out.println("La lista esta vacía");
        }
    }*/

    public void addPixel(Pixel valueToAdd) {
        Node myNode = new Node(valueToAdd);
        if (this.size == 0) {
            this.first = myNode;
        } else {
            Node Current = this.first;
            while (Current.next != null) {
                Current = Current.next;
            }
            Current.next = myNode;
        }
        this.size += 1;
    }
}
