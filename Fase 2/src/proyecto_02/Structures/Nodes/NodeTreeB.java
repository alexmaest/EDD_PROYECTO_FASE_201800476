package proyecto_02.Structures.Nodes;

import proyecto_02.Structures.List;

/**
 *
 * @author Alexis
 */
public class NodeTreeB {

    public int size; //numero de claves almacenadas en el nodo
    public boolean leaf; //Si el nodo es hoja (nodo hoja=true; nodo interno=false)
    public List values;  //almacena las claves en el nodo
    public List sons; //arreglo con referencias a los hijos

    //Constructores
    public NodeTreeB() {
        size = 0;
        leaf = true;
        values = new List();//5->3 (2*3)-1=5
        sons = new List();//(2*3)=6
    }

    public void printNodeContent() {
        System.out.print("[");
        NodeC Current = values.first;
        while (Current != null) {
            if (Current.next == null) {
                System.out.println(Current.value.getDpi());
            } else {
                System.out.println(Current.value.getDpi() + "|");
            }
            Current = Current.next;
        }
        System.out.print("]");
    }

    public NodeC searchAValueInNode(int dpi) {
        NodeC Current = values.first;
        while (Current != null) {
            if (Current.value.getDpi() == dpi) {
                return Current;
            } else {
                Current = Current.next;
            }
        }
        return null;
    }
}
