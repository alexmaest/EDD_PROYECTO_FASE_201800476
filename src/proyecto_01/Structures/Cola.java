package proyecto_01.Structures;

import proyecto_01.Cliente;
import proyecto_01.Image;
import proyecto_01.Structures.Nodes.NodeC;
import proyecto_01.Structures.Nodes.NodeImage;

/**
 *
 * @author Alexis
 */
public class Cola {
    //Clientes
    private NodeC first;
    private NodeC last;
    private int size = 0;
    //Imagenes
    private NodeImage first2;
    private NodeImage last2;
    private int size2 = 0;
    
    public Cola() {
        this.first = null;
        this.last = null;
        this.size = 0;
        this.first2 = null;
        this.last2 = null;
        this.size2 = 0;
    }

    public void addC(Cliente valueToAdd) {
        NodeC myNode = new NodeC(valueToAdd);
        myNode.next = null;
        if (this.first == null & this.last == null) {
            this.first = myNode;
            this.last = myNode;
        }
        this.last.next = myNode;
        this.last = this.last.next;
        this.size += 1;
    }
    
    public void addImg(Image valueToAdd) {
        NodeImage myNode = new NodeImage(valueToAdd);
        myNode.next = null;
        if (this.first2 == null & this.last2 == null) {
            this.first2 = myNode;
            this.last2 = myNode;
        }
        this.last2.next = myNode;
        this.last2 = this.last2.next;
        this.size2 += 1;
    }

    public boolean removeC() {
        this.first = this.first.next;
        NodeC Current = this.first;
        this.size -= 1;
        return true;
    }
    
    public void printContentC(){
        NodeC Current = this.first;
        if (this.size != 0) {
            //System.out.println("----------- Contenido de la cola -----------");
            while (Current.next != null) {
                System.out.print(Current.value.getName() + ", ");
                Current = Current.next;
            }
            System.out.println(Current.value.getName());
        }else{
            System.out.println("La cola esta vacía");
        }
    }
    
    public void printContentImg(){
        NodeImage Current = this.first2;
        if (this.size2 != 0) {
            //System.out.println("----------- Contenido de la cola -----------");
            while (Current.next != null) {
                System.out.print(Current.value.getType()+ ", ");
                Current = Current.next;
            }
            System.out.println(Current.value.getType());
        }else{
            System.out.println("La cola esta vacía");
        }
    }
    
    public NodeC getAndRemoveC(){
        NodeC cliente = this.first;
        this.first = this.first.next;
        NodeC Current = this.first;
        this.size -= 1;
        return cliente;
    }
    
    public NodeImage getAndRemoveImg(){
        NodeImage imagen = this.first2;
        this.first2 = this.first2.next;
        NodeImage Current = this.first2;
        this.size2 -= 1;
        return imagen;
    }
    
    public int getSizeC(){
        return this.size;
    }
    
    public int getSizeImg(){
        return this.size2;
    }
}
