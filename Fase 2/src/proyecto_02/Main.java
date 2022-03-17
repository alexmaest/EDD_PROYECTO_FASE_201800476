package proyecto_02;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Alexis
 */
public class Main {

    public static ArrayList<Client> clients = new ArrayList<Client>();

    public static void main(String[] args) {
        readClientsJson();
        readAlbumJson();
        readLayersJson();
        readImagesJson();
        readAlbumJson();
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
                clients.add(new Client(id, name, pass));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public static void readLayersJson() {
        //JFileChooser file;
        //File open;
        FileReader files = null;
        try {
            //file = new File();
            //file.showOpenDialog(new JFrame());
            //open = file.getSelectedFile();
            //if (open != null) {
            files = new FileReader("C:\\Users\\alexi\\Downloads\\capas.json");
            //}
            JsonParser parser = new JsonParser();
            Object obj = parser.parse(files);
            JsonArray list = (JsonArray) obj;
            for (int i = 0; i < list.size(); i++) {
                int id = list.get(i).getAsJsonObject().get("id_capa").getAsInt();
                int pixelsSize = list.get(i).getAsJsonObject().get("pixeles").getAsJsonArray().size();
                for (int j = 0; j < pixelsSize; j++) {
                    int row = list.get(i).getAsJsonObject().get("pixeles").getAsJsonArray().get(j).getAsJsonObject().get("fila").getAsInt();
                    int column = list.get(i).getAsJsonObject().get("pixeles").getAsJsonArray().get(j).getAsJsonObject().get("columna").getAsInt();
                    String color = list.get(i).getAsJsonObject().get("pixeles").getAsJsonArray().get(j).getAsJsonObject().get("color").getAsString();
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public static void readImagesJson() {
        //JFileChooser file;
        //File open;
        FileReader files = null;
        try {
            //file = new File();
            //file.showOpenDialog(new JFrame());
            //open = file.getSelectedFile();
            //if (open != null) {
            files = new FileReader("C:\\Users\\alexi\\Downloads\\imagenes.json");
            //}
            JsonParser parser = new JsonParser();
            Object obj = parser.parse(files);
            JsonArray list = (JsonArray) obj;
            for (int i = 0; i < list.size(); i++) {
                int id = list.get(i).getAsJsonObject().get("id").getAsInt();
                int layersSize = list.get(i).getAsJsonObject().get("capas").getAsJsonArray().size();
                for (int j = 0; j < layersSize; j++) {
                    int idLayer = list.get(i).getAsJsonObject().get("capas").getAsJsonArray().get(j).getAsInt();
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public static void readAlbumJson() {
        //JFileChooser file;
        //File open;
        FileReader files = null;
        try {
            //file = new File();
            //file.showOpenDialog(new JFrame());
            //open = file.getSelectedFile();
            //if (open != null) {
            files = new FileReader("C:\\Users\\alexi\\Downloads\\albumes.json");
            //}
            JsonParser parser = new JsonParser();
            Object obj = parser.parse(files);
            JsonArray list = (JsonArray) obj;
            for (int i = 0; i < list.size(); i++) {
                String name = list.get(i).getAsJsonObject().get("nombre_album").getAsString();
                int imagesSize = list.get(i).getAsJsonObject().get("imgs").getAsJsonArray().size();
                for (int j = 0; j < imagesSize; j++) {
                    int imageId = list.get(i).getAsJsonObject().get("imgs").getAsJsonArray().get(j).getAsInt();
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
