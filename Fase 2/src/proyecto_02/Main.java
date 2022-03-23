package proyecto_02;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import proyecto_02.Structures.AvlTree;
import proyecto_02.Structures.BinaryTree;
import proyecto_02.Structures.DoubleList;
import proyecto_02.Structures.List;
import proyecto_02.Structures.SparceMatrix;
import proyecto_02.Structures.SubStructure.NodeAvl;
import proyecto_02.Structures.SubStructure.NodeBinary;
import proyecto_02.Structures.TreeB;

/**
 *
 * @author Alexis
 */
public class Main {
    
    public static TreeB clients = new TreeB();
    //public static BinaryTree layers = new BinaryTree();

    public static void main(String[] args) {
        adminMenu();
        /*readClientsJson();
        readAlbumJson();
        readLayersJson();
        readImagesJson();
        readAlbumJson();*/
    }
    
    public static String openFileChooser() {
        String aux = "";
        String text = "";
        JFileChooser file;
        File open;
        FileReader files = null;
        BufferedReader read = null;
        try {
            file = new JFileChooser();
            file.showOpenDialog(new JFrame());
            open = file.getSelectedFile();
            if (open != null) {
                files = new FileReader(open);
                read = new BufferedReader(files);
                while ((aux = read.readLine()) != null) {
                    //System.out.println(aux);
                    text += aux + "\n";
                }
                files.close();
                read.close();
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex + ""
                    + "\nNo se ha encontrado el archivo",
                    "Información", JOptionPane.WARNING_MESSAGE);
        } finally {
            try {
                files.close();
            } catch (IOException ex) {
                System.out.println(ex);
            }
            try {
                read.close();
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
        return text;
    }
    
    public static void readClientsJson() {
        //JFileChooser file;
        //File open;
        FileReader files = null;
        try {
            //file = new File();
            //file.showOpenDialog(new JFrame());
            //open = file.getSelectedFile();
            //if (open != null) {
            files = new FileReader("C:\\Users\\alexi\\Downloads\\clientes.json");
            //}
            JsonParser parser = new JsonParser();
            Object obj = parser.parse(files);
            JsonArray list = (JsonArray) obj;
            for (int i = 0; i < list.size(); i++) {
                long id = Long.parseLong(list.get(i).getAsJsonObject().get("dpi").getAsString());
                String name = list.get(i).getAsJsonObject().get("nombre_cliente").getAsString();
                String pass = list.get(i).getAsJsonObject().get("password").getAsString();
                clients.addValue(new Client(id, name, pass));
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                files.close();
            } catch (IOException ex) {
            }
        }
    }
    
    public static BinaryTree readLayersJson() {
        BinaryTree tempTree = new BinaryTree();
        try {
            JsonParser parser = new JsonParser();
            System.out.println("Cargando...");
            Object obj = parser.parse(openFileChooser());
            JsonArray list = (JsonArray) obj;
            for (int i = 0; i < list.size(); i++) {
                int id = list.get(i).getAsJsonObject().get("id_capa").getAsInt();
                int pixelsSize = list.get(i).getAsJsonObject().get("pixeles").getAsJsonArray().size();
                List pixels = new List();
                for (int j = 0; j < pixelsSize; j++) {
                    int row = list.get(i).getAsJsonObject().get("pixeles").getAsJsonArray().get(j).getAsJsonObject().get("fila").getAsInt();
                    int column = list.get(i).getAsJsonObject().get("pixeles").getAsJsonArray().get(j).getAsJsonObject().get("columna").getAsInt();
                    String color = list.get(i).getAsJsonObject().get("pixeles").getAsJsonArray().get(j).getAsJsonObject().get("color").getAsString();
                    pixels.addPixel(new Pixel(row, column, color));
                }
                tempTree.add(new NodeBinary(new Layer(id, pixels)));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return tempTree;
    }
    
    public static AvlTree readImagesJson() {
        AvlTree tempTree = new AvlTree();
        try {
            JsonParser parser = new JsonParser();
            Object obj = parser.parse(openFileChooser());
            JsonArray list = (JsonArray) obj;
            for (int i = 0; i < list.size(); i++) {
                int id = list.get(i).getAsJsonObject().get("id").getAsInt();
                int layersSize = list.get(i).getAsJsonObject().get("capas").getAsJsonArray().size();
                List temp = new List();
                for (int j = 0; j < layersSize; j++) {
                    int idLayer = list.get(i).getAsJsonObject().get("capas").getAsJsonArray().get(j).getAsInt();
                    if (Login.currentUser.getLayers().searchNode(idLayer, Login.currentUser.getLayers().getRoot()) != null) {
                        NodeBinary founded = Login.currentUser.getLayers().searchNode(idLayer, Login.currentUser.getLayers().getRoot());
                        temp.addLayer(founded.value);
                    } else {
                        System.out.println("Capa no encontrada");
                    }
                }
                tempTree.insert(new Photo(id, temp));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return tempTree;
    }
    
    public static DoubleList readAlbumJson() {
        DoubleList albums = new DoubleList();
        try {
            JsonParser parser = new JsonParser();
            Object obj = parser.parse(openFileChooser());
            JsonArray list = (JsonArray) obj;
            for (int i = 0; i < list.size(); i++) {
                String name = list.get(i).getAsJsonObject().get("nombre_album").getAsString();
                int imagesSize = list.get(i).getAsJsonObject().get("imgs").getAsJsonArray().size();
                List images = new List();
                for (int j = 0; j < imagesSize; j++) {
                    int imageId = list.get(i).getAsJsonObject().get("imgs").getAsJsonArray().get(j).getAsInt();
                    if (Login.currentUser.getImages().Search(imageId, Login.currentUser.getImages().root) != null) {
                        NodeAvl founded = Login.currentUser.getImages().Search(imageId, Login.currentUser.getImages().root);
                        images.addImage(founded.dato);
                    } else {
                        System.out.println("Capa no encontrada");
                    }
                }
                albums.add(new Album(name, images));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return albums;
    }
    
    public static void menu() {
        boolean play = true;
        while (play) {
            System.out.println("\n");
            System.out.println("*******************************************");
            System.out.println("*             MENÚ PRINCIPAL              *");
            System.out.println("*******************************************");
            System.out.println("* 1) Log in                               *");
            System.out.println("* 2) Salir                                *");
            System.out.println("*******************************************");
            System.out.print("Elige una opcion: ");
            Scanner s = new Scanner(System.in);
            int option = s.nextInt();
            switch (option) {
                case 1: {
                    System.out.println("\n");
                    System.out.println("*******************************************");
                    System.out.println("*                 LOG IN                  *");
                    System.out.println("*******************************************");
                    System.out.print("Ingrese su usuario: ");
                    Scanner sc = new Scanner(System.in);
                    String user = sc.next();
                    System.out.print("Ingrese su usuario: ");
                    Scanner sc2 = new Scanner(System.in);
                    String pass = sc2.next();
                    if (user.equals("admin") && pass.equals("EDD2022")) {
                        System.out.println("Entra paps");
                        adminMenu();
                    } else {
                        System.out.println("Usuario incorrecto");
                    }

                    /*switch (option2) {
                        case 1:
                            //loadJson();
                            break;
                        case 2:
                            //vGenerator();
                            break;
                        case 3:
                            break;
                        default:
                            System.out.println("\n");
                            System.out.println("Advertencia: Opción no valida");
                            break;
                    }*/
                    break;
                }
                case 2:
                    System.out.println("\n");
                    System.out.println("Has salido del programa");
                    System.out.println("\n");
                    play = false;
                    break;
                default:
                    System.out.println("\n");
                    System.out.println("Advertencia: Opción no valida");
                    break;
            }
        }
    }
    
    public static void adminMenu() {
        boolean play = true;
        while (play) {
            System.out.println("\n");
            System.out.println("*******************************************");
            System.out.println("*               MENÚ ADMIN                *");
            System.out.println("*******************************************");
            System.out.println("* 1) Cargar usuarios                      *");
            System.out.println("* 2) Editar usuarios                      *");
            System.out.println("* 3) ArbolB usuarios                      *");
            System.out.println("*******************************************");
            System.out.print("Elige una opcion: ");
            Scanner s = new Scanner(System.in);
            int option = s.nextInt();
            switch (option) {
                case 1:
                    readClientsJson();
                    clients.addValue(new Client(1005, "xd", "123"));
                    clients.addValue(new Client(1006, "xd", "123"));
                    clients.addValue(new Client(1007, "xd", "123"));
                    clients.addValue(new Client(1008, "xd", "123"));
                    clients.addValue(new Client(1009, "xd", "123"));
                    clients.addValue(new Client(1010, "xd", "123"));
                    clients.addValue(new Client(1011, "xd", "123"));
                    clients.addValue(new Client(1012, "xd", "123"));
                    clients.addValue(new Client(1013, "xd", "123"));
                    clients.addValue(new Client(1014, "xd", "123"));
                    clients.addValue(new Client(1015, "xd", "123"));
                    clients.addValue(new Client(1016, "xd", "123"));
                    clients.addValue(new Client(1017, "xd", "123"));
                    clients.addValue(new Client(1018, "xd", "123"));
                    clients.addValue(new Client(1019, "xd", "123"));
                    clients.addValue(new Client(1020, "xd", "123"));
                    clients.addValue(new Client(1021, "xd", "123"));
                    clients.addValue(new Client(1022, "xd", "123"));
                    clients.addValue(new Client(1023, "xd", "123"));
                    clients.addValue(new Client(1024, "xd", "123"));
                    clients.addValue(new Client(1025, "xd", "123"));
                    clients.addValue(new Client(1026, "xd", "123"));
                    clients.addValue(new Client(1027, "xd", "123"));
                    clients.printTreeContent(clients.root, 1);
                    System.out.println(clients.getData() + "}");
                    break;
                case 2:
                    System.out.println("\n");
                    System.out.println("**********************************************");
                    System.out.println("*              EDITAR USUARIOS               *");
                    System.out.println("**********************************************");
                    System.out.println("* 1) Insertar                                *");
                    System.out.println("* 2) Modificar                               *");
                    System.out.println("* 3) Eliminar                                *");
                    System.out.println("* 4) Regresar                                *");
                    System.out.println("**********************************************");
                    System.out.print("Elige una opcion: ");
                    Scanner s3 = new Scanner(System.in);
                    int option3 = s3.nextInt();
                    switch (option3) {
                        case 1:
                            break;
                        case 2:
                            break;
                        case 3:
                            break;
                        default:
                            System.out.println("\n");
                            System.out.println("Advertencia: Opción no valida");
                            break;
                    }
                    break;
                case 3:
                    break;
            }
        }
    }
    
    public static void createFile(String text) {
        FileWriter f = null;
        PrintWriter textG = null;
        try {
            String cType = "";
            cType = "clientsTree.dot";
            f = new FileWriter(cType);
            textG = new PrintWriter(f);
            textG.write(text);
            textG.close();
            f.close();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (textG != null) {
                textG.close();
                try {
                    f.close();
                } catch (IOException ex) {
                }
            }
        }
    }
    
    public static void imageGenerator(String text) {
        createFile(text);
        ProcessBuilder process = null;
        process = new ProcessBuilder("dot", "-Tpng", "-o", "clientsTree.png", "clientsTree.dot");
        process.redirectErrorStream(true);
        try {
            process.start();
            System.out.println("Archivo generado");
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
}
