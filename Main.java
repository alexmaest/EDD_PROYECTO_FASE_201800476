package tarea.pkg4;

import java.util.Arrays;

/**
 *
 * @author Alexis
 */
public class Tarea4 {

    String[] singleArray;
    int singleSize, counter;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    }

    public Tarea4(int size){
        singleSize = size;
        singleArray = new String[size];
        Arrays.fill(singleArray, "-1");
    }
    
    public void hashFunction(String[] textArray, String[] array){
        for (int i = 0; i < textArray.length; i++) {
            String element= textArray[i];
            int arrayIndex = Integer.parseInt(element) % 20;
            System.out.println("El indice es " + arrayIndex + " para el elemento + " + element);
            while(array[arrayIndex] != "-1"){
                arrayIndex++;
                System.out.println("Ocurrio una colisión con el indice " + (arrayIndex - 1) + " cambiar al " + arrayIndex);
                arrayIndex %= singleSize;
            }
            array[arrayIndex] = element;
        }
    }
    
    public void show(){
        int increment = 0;
        for (int i = 0; i < 1; i++) {
            increment += 21;
            for (int j = 0; j < 71; j++) {
                System.out.println("-");
            }
            System.out.println("");
            for (int j = increment - 21; j < increment; j++) {
                System.out.format("| %3s " + " ", j);
            }
            System.out.println("|");
            for (int j = 0; j < 71; j++) {
                System.out.print("-");
            }
            System.out.println("");
            for (int j = increment - 21; j < increment; j++) {
                if (singleArray[j].equals("-1")) {
                    System.out.print("|       ");
                }else{
                    System.out.print(String.format("| %3s " + " ", singleArray));
                }
            }
            System.out.println("|");
            for (int j = 0; j < 71; j++) {
                System.out.println("-");
            }
            System.out.println("");
        }
    }
    
    public String search(String element){
        int index = Integer.parseInt(element)%20;
        int counter = 0;
        while(singleArray[index] != "-1"){
            if (singleArray[index] == element) {
                System.out.println("El elemento " + element + " fue encontrado en el indice " + index);
                return singleArray[index];
            }
            index++;
            index %= singleSize;
            counter++;
            if (counter > 20) {
                break;
            }
        }
        return null;
    }
    
}
