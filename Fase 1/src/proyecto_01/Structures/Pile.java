package proyecto_01.Structures;

import proyecto_01.Image;
import proyecto_01.Structures.Nodes.NodeImage;

/**
 *
 * @author Alexis
 */
public class Pile {
    public NodeImage first;
    public int size = 0;

    public Pile(){
        this.first = null;
        this.size = 0;
    }
    public void add(Image valueToAdd){
        NodeImage myNode = new NodeImage(valueToAdd);
        if (this.size == 0) {
            this.first = myNode;
        }
        else{
            myNode.next = this.first;
            this.first = myNode;
        }
        this.size+=1;
    } 

    public boolean remove(){
        if (size != 0) {
            this.first = this.first.next;
            this.size-=1;
        }
        return true;
    }
    public void cleanPile(){
        this.first = null;
        this.size = 0;
    }
    public int getCNumber(){
        int cont = 0;
        NodeImage Current = this.first;
        while(Current != null){
            if ("C".equals(Current.value.getType())) {
                cont++;
            }
            Current = Current.next;
        }
        return cont;
    }
    public int getBWNumber(){
        int cont = 0;
        NodeImage Current = this.first;
        while(Current != null){
            if ("BW".equals(Current.value.getType())) {
                cont++;
            }
            Current = Current.next;
        }
        return cont;
    }
}
