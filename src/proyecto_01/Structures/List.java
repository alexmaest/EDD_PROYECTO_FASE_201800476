package proyecto_01.Structures;

import proyecto_01.Cliente;
import proyecto_01.Image;
import proyecto_01.Structures.Nodes.NodeC;
import proyecto_01.Structures.Nodes.NodeImage;
import proyecto_01.Structures.Nodes.NodeV;
import proyecto_01.Ventanilla;

/**
 *
 * @author Alexis
 */
public class List {

    //Ventanilla
    public NodeV first;
    public int size = 0;
    //Cliente
    public NodeC first2;
    public int size2 = 0;
    //Imagen
    public NodeImage first3;
    public int size3 = 0;

    public List() {
        this.first = null;
        this.size = 0;
        this.first2 = null;
        this.size2 = 0;
        this.first3 = null;
        this.size3 = 0;
    }

    public void addV(Ventanilla valueToAdd) {
        NodeV myNode = new NodeV(valueToAdd);
        if (this.size == 0) {
            this.first = myNode;
        } else {
            NodeV Current = this.first;
            while (Current.next != null) {
                Current = Current.next;
            }
            Current.next = myNode;
        }
        this.size += 1;
    }

    public void addC(Cliente valueToAdd) {
        NodeC myNode = new NodeC(valueToAdd);
        if (this.size2 == 0) {
            this.first2 = myNode;
        } else {
            NodeC Current = this.first2;
            while (Current.next != null) {
                Current = Current.next;
            }
            Current.next = myNode;
        }
        this.size2 += 1;
    }

    public void addImage(Image valueToAdd) {
        NodeImage myNode = new NodeImage(valueToAdd);
        if (this.size3 == 0) {
            this.first3 = myNode;
        } else {
            NodeImage Current = this.first3;
            while (Current.next != null) {
                Current = Current.next;
            }
            Current.next = myNode;
        }
        this.size3 += 1;
    }

    public boolean removeV(int idVentanilla) {
        if (this.size == 0) {
            return false;
        } else {
            NodeV Current = this.first;
            NodeV Previous = null;
            while (Current.value.getId() != idVentanilla) {
                Previous = Current;
                Current = Current.next;
                if (Current.next == null && Current.value.getId() != idVentanilla) {
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
        }
        this.size -= 1;
        return true;
    }

    public boolean removeC(int idClient) {
        if (this.size2 == 0) {
            return false;
        } else {
            NodeC Current = this.first2;
            NodeC Previous = null;
            while (Current.value.getId() != idClient) {
                Previous = Current;
                Current = Current.next;
                if (Current.next == null && Current.value.getId() != idClient) {
                    System.out.println("Id no encontrado");
                    return false;
                }
            }
            if (Previous == null) {
                this.first2 = Current.next;
                Current.next = null;
            } else if (Current != null) {
                Previous.next = Current.next;
                Current.next = null;
            }
        }
        this.size2 -= 1;
        return true;
    }

    public boolean removeImage() {
        if (this.size3 == 0) {
            return false;
        } else {
            NodeImage Current = this.first3;
            NodeImage Previous = null;
            Previous = Current;
            Current = Current.next;
            if (Previous == null) {
                this.first3 = Current.next;
                Current.next = null;
            } else if (Current != null) {
                Previous.next = Current.next;
                Current.next = null;
            }
        }
        this.size3 -= 1;
        return true;
    }

    /*public int getImageById(int idImage) {
        boolean Found = false;
        if (this.size3 == 0) {
        } else {
            NodeImage Current = this.first3;
            while (Current.next != null) {
                if (Current.value.getIdImage() == idImage){
                    Found = true;
                    return Current.value.getIdImage();
                }
                Current = Current.next;
            }
        }
        if (Found =! true) {
            System.out.println("Imagen no encontrada");
            return -1;
        }
        return -1;
    }*/
    public void printContentV() {
        NodeV Current = this.first;
        if (this.size != 0) {
            //System.out.println("----------- Contenido de la cola -----------");
            while (Current.next != null) {
                System.out.print(Current.value.getName() + ", ");
                Current = Current.next;
            }
            System.out.println(Current.value.getName());
        } else {
            System.out.println("La lista esta vacía");
        }
    }

    public void printContentC() {
        NodeC Current = this.first2;
        if (this.size2 != 0) {
            //System.out.println("----------- Contenido de la cola -----------");
            while (Current.next != null) {
                System.out.print(Current.value.getName() + ", ");
                Current = Current.next;
            }
            System.out.println(Current.value.getName());
        } else {
            System.out.println("La lista esta vacía");
        }
    }
}
