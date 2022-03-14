package proyecto_02;

import proyecto_02.Structures.BinaryTree;
import proyecto_02.Structures.List;
import proyecto_02.Structures.SubStructure.NodeBinary;
import proyecto_02.Structures.TreeB;

/**
 *
 * @author Alexis
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Arbol B
        TreeB arbolB = new TreeB();
        arbolB.addValue(new Client(3000L, "Alexis", "123"));
        arbolB.addValue(new Client(3010L, "Alexis", "123"));
        arbolB.addValue(new Client(3020L, "Alexis", "123"));
        arbolB.addValue(new Client(3030L, "Alexis", "123"));
        arbolB.addValue(new Client(3040L, "Alexis", "123"));
        arbolB.addValue(new Client(3050L, "Alexis", "123"));
        arbolB.addValue(new Client(3060L, "Alexis1", "1234"));
        arbolB.addValue(new Client(3061L, "Alexis3", "1236"));
        arbolB.addValue(new Client(3062L, "Alexis3", "1236"));
        arbolB.addValue(new Client(3063L, "Alexis3", "1236"));
        arbolB.addValue(new Client(3070L, "Alexis2", "1235"));
        arbolB.addValue(new Client(3071L, "Alexis2", "1235"));
        arbolB.addValue(new Client(3072L, "Alexis2", "1235"));
        arbolB.addValue(new Client(3073L, "Alexis2", "1235"));
        arbolB.addValue(new Client(3074L, "Alexis2", "1235"));
        arbolB.addValue(new Client(3075L, "Alexis2", "1235"));
        arbolB.addValue(new Client(3076L, "Alexis2", "1235"));
        arbolB.addValue(new Client(3080L, "Alexis3", "1236"));
        arbolB.addValue(new Client(3090L, "Alexis3", "1236"));
        arbolB.addValue(new Client(3091L, "Alexis3", "1236"));
        arbolB.addValue(new Client(3100L, "Alexis3", "1236"));
        arbolB.addValue(new Client(3110L, "Alexis3", "1236"));
        arbolB.addValue(new Client(3120L, "Alexis3", "1236"));
        arbolB.addValue(new Client(3130L, "Alexis3", "1236"));
        arbolB.addValue(new Client(3140L, "Alexis3", "1236"));
        arbolB.addValue(new Client(3150L, "Alexis3", "1236"));
        arbolB.addValue(new Client(3160L, "Alexis3", "1236"));

        //arbolB.addValue(new Client(6007201810101L, "Alexis4", "1237"));
        //arbolB.addValue(new Client(7007201810101L, "Alexis5", "1238"));
        //arbolB.addValue(new Client(7008201810101L, "Alexis5", "1238"));
        //arbolB.searchValue(5007201810101L, arbolB.root);
        //System.out.println(arbolB.printTreeContent(arbolB.root) + " }");
        /*arbolB.removeValue(3006201810101L, arbolB.root);
        arbolB.removeValue(6007201810101L, arbolB.root);
        arbolB.removeValue(7007201810101L, arbolB.root);
         */
        arbolB.removeValue(3100L, arbolB.root);
        arbolB.removeValue(3062L, arbolB.root);
        System.out.println("-------------------------------");
        System.out.println(arbolB.printTreeContent(arbolB.root) + "}");

        //Arbol binario
        List pixeles = new List();
        pixeles.addPixel(new Pixel(0, 0, "rojo"));
        pixeles.addPixel(new Pixel(1, 0, "azul"));
        pixeles.addPixel(new Pixel(1, 1, "verde"));

        Layer capa = new Layer(5, pixeles);
        Layer capa2 = new Layer(4, pixeles);
        Layer capa3 = new Layer(6, pixeles);
        Layer capa4 = new Layer(7, pixeles);
        Layer capa5 = new Layer(1, pixeles);

        BinaryTree arbol = new BinaryTree();
        arbol.add(new NodeBinary(capa));
        arbol.add(new NodeBinary(capa2));
        arbol.add(new NodeBinary(capa3));
        arbol.add(new NodeBinary(capa4));
        arbol.add(new NodeBinary(capa5));

        //arbol.preOrder(arbol.getRoot());
    }
}
