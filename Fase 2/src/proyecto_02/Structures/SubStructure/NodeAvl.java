package proyecto_02.Structures.SubStructure;

/**
 *
 * @author Alexis
 */
public class NodeAvl {
    public int dato, fe;
    public NodeAvl left, right;
    
    public NodeAvl(int d){
        this.dato = d;
        this.fe = 0;
        this.left = null;
        this.right = null;
    }
}
