package proyecto_02.Structures.SubStructure;

/**
 *
 * @author Alexis
 */
public class Branch {

    int size;//identificar la cantidad de elementos que tiene la rama
    NodeB first;
    boolean leaf;//identificar si es una hoja

    public Branch() {
        this.first = null;
        this.leaf = true;
        this.size = 0;
    }

    public void addNode(NodeB valueToAdd) {
        if (this.first == null) {
            this.first = valueToAdd;
            this.size += 1;
        } else {
            //recorrer e insertar
            NodeB Current = this.first;
            while (Current != null) {
                if (Current.client.getDpi() == valueToAdd.client.getDpi()) {//------------->ya existe en el arbol
                    System.out.println("El cliente " + valueToAdd.client.getDpi() + " ya existe");
                    break;
                } else {
                    if (Current.client.getDpi() > valueToAdd.client.getDpi()) {
                        if (Current == this.first) {//------------->insertar al inicio
                            Current.previous = valueToAdd;
                            valueToAdd.next = Current;
                            //ramas del nodo
                            Current.left = valueToAdd.right;
                            valueToAdd.right = null;

                            this.first = valueToAdd;
                            this.size += 1;
                            break;
                        } else {//------------->insertar en medio;
                            valueToAdd.next = Current;
                            //ramas del nodo
                            Current.left = valueToAdd.right;
                            valueToAdd.right = null;

                            valueToAdd.previous = Current.previous;
                            Current.previous.next = valueToAdd;
                            Current.previous = valueToAdd;
                            this.size += 1;
                            break;
                        }
                    } else if (Current.next == null) {//------------->insertar al final
                        Current.next = valueToAdd;
                        valueToAdd.previous = Current;
                        this.size += 1;
                        break;
                    }
                }
                Current = Current.next;
            }
        }
    }

    public void branchPrintContent(Branch branch) {
        System.out.println("-----------Contenido----------");
        NodeB current = branch.getFirst();
        while (current != null) {
            System.out.println("-> " + current.getClient().getDpi() + ", size: "+ branch.getSize());
            if (current.getLeft() != null) {
                System.out.println("Empieza left");
                branchPrintContent(current.getLeft());
                System.out.println("Termina left");
            }
            if (current.getRight() != null) {
                System.out.println("Empieza right");
                branchPrintContent(current.getRight());
                System.out.println("Termina right");
            }
            current = current.getNext();
        }
        System.out.println("------------------------------");
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public NodeB getFirst() {
        return first;
    }

    public void setFirst(NodeB first) {
        this.first = first;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }
}
