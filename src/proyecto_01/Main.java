package proyecto_01;

import proyecto_01.Structures.Pile;
import proyecto_01.Structures.List;
import proyecto_01.Structures.DoubleList;
import proyecto_01.Structures.Nodes.NodeV;
import proyecto_01.Structures.Nodes.NodeC;
import proyecto_01.Structures.Cola;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;
import proyecto_01.Structures.Nodes.NodeImage;

/**
 *
 * @author Alexis
 */
public class Main {

    public static List Ventanillas = new List();
    public static DoubleList Waiting = new DoubleList();
    public static Cola Clientes = new Cola();
    public static Cola PrinterC = new Cola();
    public static Cola PrinterBW = new Cola();

    public static void main(String[] args) {
        menu();
    }

    public static void loadJson() {
        System.out.println("Ingrese la ruta del archivo JSON: ");
        //Scanner s = new Scanner(System.in);
        //String path = s.nextLine();

        File f;
        FileReader fr = null;
        BufferedReader br;
        String fileContent = "";
        String path = "C:\\Users\\alexi\\Downloads\\clientes01.json";
        try {
            f = new File(path);
            fr = new FileReader(f);
            br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                fileContent += line;
            }
            JsonParser parser = new JsonParser();
            JsonObject jObject = (JsonObject) parser.parse(fileContent);
            int cont = 1;
            while (jObject.get("Cliente" + cont) != null) {
                cont += 1;
            }
            cont -= 1;
            for (int i = 0; i < cont; i++) {
                JsonElement client = jObject.get("Cliente" + (i + 1));
                JsonObject data = client.getAsJsonObject();
                int id = data.get("id_cliente").getAsInt();
                String name = data.get("nombre_cliente").getAsString();
                int imgC = data.get("img_color").getAsInt();
                int imgBW = data.get("img_bw").getAsInt();
                int contC = 0;
                List imagesC = new List();
                while (contC == imgC) {
                    imagesC.addImage(new Image(id, "C"));
                    contC++;
                }
                int contBW = 0;
                List imagesBW = new List();
                while (contBW == imgBW) {
                    imagesBW.addImage(new Image(id, "BW"));
                    contBW++;
                }
                Cliente newClient = new Cliente(id, name, imgC, imgBW, imagesC, imagesBW, false, false);
                Clientes.addC(newClient);
            }
            System.out.println("Archivo leido con éxito");
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Entra1");
        } finally {
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
                System.out.println(e2);
                System.out.println("Entra2s");
            }
        }
    }

    public static void vGenerator() {
        //Scanner s = new Scanner(System.in);
        System.out.print("Ingrese la cantidad de ventanillas: ");
        //int num = s.nextInt();
        for (int i = 0; i < 2; i++) {
            Ventanillas.addV(new Ventanilla((i + 1), ("Ventanilla " + (i + 1)), null, new Pile()));
        }
    }

    public static void simulation() {
        boolean Active = true;
        int ejecucion = 0;
        while (Active) {
            System.out.println("\n");
            System.out.println("-----------------SALTO NO." + (ejecucion + 1) + "-----------------");
            System.out.println("-----------CLIENTES HACIENDO COLA-----------");
            Clientes.printContentC();
            System.out.println("-----------------VENTANILLAS----------------");
            Ventanillas.printContentV();

            //Asignar cliente a ventanilla
            NodeV CurrentV = Ventanillas.first;
            while (CurrentV != null) {
                if (CurrentV.value.getClient() == null) {
                    if (Clientes.getSizeC() == 0) {
                        break;
                    }
                    NodeC client = Clientes.getAndRemoveC();
                    CurrentV.value.setClient(client);
                    System.out.println("El cliente " + CurrentV.value.getClient().value.getName() + " ingresa a " + CurrentV.value.getName());
                    CurrentV.value.getClient().value.setgiveImg(true);
                    NodeV CurrentAll = Ventanillas.first;
                    while (CurrentAll.next != null) {
                        if (CurrentV.next != null) {
                            if (CurrentV.next.value.getId() == CurrentAll.next.value.getId()) {
                                if (CurrentV.next.value.getClient() != null) {
                                    CurrentV.next.value.getClient().value.setgiveImg(false);
                                }
                            }
                        }
                        CurrentAll = CurrentAll.next;
                    }
                    break;
                } else {
                    CurrentV.value.getClient().value.setgiveImg(false);
                    CurrentV = CurrentV.next;
                }
            }

            //Clientes en ventanilla dan imagenes
            NodeV CurrentV2 = Ventanillas.first;
            while (CurrentV2 != null) {
                if (CurrentV2.value.getClient() != null) {
                    if (CurrentV2.value.getClient().value.getgiveImg() != true) {
                        Pile images = CurrentV2.value.getImages();
                        int numC = CurrentV2.value.getClient().value.getnumImgC(); //3
                        int numBW = CurrentV2.value.getClient().value.getnumImgBW(); //2
                        //int cantImages = numBW + numC; //5

                        int numPileC = CurrentV2.value.getImages().getCNumber();
                        int numPileBW = CurrentV2.value.getImages().getBWNumber();
                        if (numC != 0) {
                            //2  != 3
                            if (numPileC != numC) {
                                images.add(new Image(CurrentV2.value.getClient().value.getId(), "C"));
                                CurrentV2.value.getClient().value.getImgC().removeImage();
                                System.out.println("La " + CurrentV2.value.getName() + " recibe una imagen a color del cliente "
                                        + CurrentV2.value.getClient().value.getName());
                            } else {
                                if (numBW != 0) {
                                    if (numPileBW != numBW) {
                                        images.add(new Image(CurrentV2.value.getClient().value.getId(), "BW"));
                                        CurrentV2.value.getClient().value.getImgBW().removeImage();
                                        System.out.println("La " + CurrentV2.value.getName() + " recibe una imagen en BW del cliente "
                                                + CurrentV2.value.getClient().value.getName());
                                    } else {
                                        NodeC clientWaiter = CurrentV2.value.getClient();
                                        System.out.println("El cliente " + clientWaiter.value.getName() + " es atendido e ingresa a la lista de espera");
                                        System.out.println("La " + CurrentV2.value.getName() + " envía las imágenes del cliente "
                                                + clientWaiter.value.getName() + " a sus respectivas colas de impresión");
                                        Cliente clientAux = new Cliente(clientWaiter.value.getId(), clientWaiter.value.getName(),
                                                clientWaiter.value.getnumImgC(), clientWaiter.value.getnumImgBW(), clientWaiter.value.getImgC(), clientWaiter.value.getImgBW(), false, true);
                                        if (Clientes.getSizeC() == 0) {
                                            CurrentV2.value.setClient(null);
                                        } else {
                                            NodeC inQueueC = Clientes.getAndRemoveC();
                                            System.out.println("El cliente " + inQueueC.value.getName() + " ingresa a " + CurrentV2.value.getName());
                                            CurrentV2.value.setClient(inQueueC);
                                        }
                                        //Enviar imagenes de ventanilla a cola de impresion
                                        NodeImage CurrentImg = images.first;
                                        while (CurrentImg != null) {
                                            if ("C".equals(CurrentImg.value.getType())) {
                                                PrinterC.addImg(new Image(clientWaiter.value.getId(), "C"));
                                            } else {
                                                PrinterBW.addImg(new Image(clientWaiter.value.getId(), "BW"));
                                            }
                                            CurrentImg = CurrentImg.next;
                                        }
                                        CurrentV2.value.getImages().cleanPile();
                                        Waiting.add(clientAux);
                                    }
                                } else {
                                    NodeC clientWaiter = CurrentV2.value.getClient();
                                    System.out.println("El cliente " + clientWaiter.value.getName() + " es atendido e ingresa a la lista de espera");
                                    System.out.println("La " + CurrentV2.value.getName() + " envía las imágenes del cliente "
                                            + clientWaiter.value.getName() + " a sus respectivas colas de impresión");
                                    Cliente clientAux = new Cliente(clientWaiter.value.getId(), clientWaiter.value.getName(),
                                            clientWaiter.value.getnumImgC(), clientWaiter.value.getnumImgBW(), clientWaiter.value.getImgC(), clientWaiter.value.getImgBW(), false, true);
                                    if (Clientes.getSizeC() == 0) {
                                        CurrentV2.value.setClient(null);
                                    } else {
                                        NodeC inQueueC = Clientes.getAndRemoveC();
                                        System.out.println("El cliente " + inQueueC.value.getName() + " ingresa a " + CurrentV2.value.getName());
                                        CurrentV2.value.setClient(inQueueC);
                                    }
                                    //Enviar imagenes de ventanilla a cola de impresion
                                    NodeImage CurrentImg = images.first;
                                    while (CurrentImg != null) {
                                        if ("C".equals(CurrentImg.value.getType())) {
                                            PrinterC.addImg(new Image(clientWaiter.value.getId(), "C"));
                                        } else {
                                            PrinterBW.addImg(new Image(clientWaiter.value.getId(), "BW"));
                                        }
                                        CurrentImg = CurrentImg.next;
                                    }
                                    CurrentV2.value.getImages().cleanPile();
                                    Waiting.add(clientAux);
                                }
                            }
                        } else if (numBW != 0) {
                            if (numPileBW != numBW) {
                                images.add(new Image(CurrentV2.value.getClient().value.getId(), "BW"));
                                CurrentV2.value.getClient().value.getImgBW().removeImage();
                                System.out.println("La " + CurrentV2.value.getName() + " recibe una imagen en BW del cliente "
                                        + CurrentV2.value.getClient().value.getName());
                            } else {
                                NodeC clientWaiter = CurrentV2.value.getClient();
                                System.out.println("El cliente " + clientWaiter.value.getName() + " es atendido e ingresa a la lista de espera");
                                System.out.println("La " + CurrentV2.value.getName() + " envía las imágenes del cliente "
                                        + clientWaiter.value.getName() + " a sus respectivas colas de impresión");
                                Cliente clientAux = new Cliente(clientWaiter.value.getId(), clientWaiter.value.getName(),
                                        clientWaiter.value.getnumImgC(), clientWaiter.value.getnumImgBW(), clientWaiter.value.getImgC(), clientWaiter.value.getImgBW(), false, true);
                                if (Clientes.getSizeC() == 0) {

                                    CurrentV2.value.setClient(null);
                                } else {
                                    NodeC inQueueC = Clientes.getAndRemoveC();
                                    System.out.println("El cliente " + inQueueC.value.getName() + " ingresa a " + CurrentV2.value.getName());
                                    CurrentV2.value.setClient(inQueueC);
                                }
                                //Enviar imagenes de ventanilla a cola de impresion
                                NodeImage CurrentImg = images.first;
                                while (CurrentImg != null) {
                                    if ("C".equals(CurrentImg.value.getType())) {
                                        PrinterC.addImg(new Image(clientWaiter.value.getId(), "C"));
                                    } else {
                                        PrinterBW.addImg(new Image(clientWaiter.value.getId(), "BW"));
                                    }
                                    CurrentImg = CurrentImg.next;
                                }
                                CurrentV2.value.getImages().cleanPile();
                                Waiting.add(clientAux);
                            }
                        }
                        CurrentV2 = CurrentV2.next;
                    } else {
                        CurrentV2 = CurrentV2.next;
                    }
                } else {
                    CurrentV2 = CurrentV2.next;
                }
            }
            System.out.println("----------LISTA DE ESPERA-----------");
            Waiting.printContent();
            System.out.println("----------COLA DE IMPRESORA A COLOR---------");
            PrinterC.printContentImg();
            System.out.println("----------COLA DE IMPRESORA A BW---------");
            PrinterBW.printContentImg();
            //Impresoras regresarn imagenes a clientes
            /*
            NodeDoubleC CurrentV3 = Waiting.first;
            while (CurrentV3 != null) {
                if (CurrentV3.value.getWait() != true) {
                    //Acción
                } else {
                    CurrentV3.value.setWait(false);
                    CurrentV3 = CurrentV3.next;
                }
                CurrentV3 = CurrentV3.next;
            }
             */
            ejecucion++;
            if (ejecucion == 13) {
                break;
            }
        }
    }

    public static void menu() {
        while (true) {
            System.out.println("\n");
            System.out.println("*******************************************");
            System.out.println("*             MENÚ PRINCIPAL              *");
            System.out.println("*******************************************");
            System.out.println("* 1) Parametros iniciales                 *");
            System.out.println("* 2) Ejecutar paso                        *");
            System.out.println("* 3) Estado en memoria de las estructuras *");
            System.out.println("* 4) Reportes                             *");
            System.out.println("* 5) Acerca de                            *");
            System.out.println("* 6) Salir                                *");
            System.out.println("*******************************************");
            System.out.print("Elige una opcion: ");
            Scanner s = new Scanner(System.in);
            int option = s.nextInt();
            if (option == 1) {
                System.out.println("\n");
                System.out.println("*******************************************");
                System.out.println("*          PARAMETROS INICIALES           *");
                System.out.println("*******************************************");
                System.out.println("* 1) Carga masiva                         *");
                System.out.println("* 2) Cantidad de ventanillas              *");
                System.out.println("* 3) Regresar                             *");
                System.out.println("*******************************************");
                System.out.print("Elige una opcion: ");
                Scanner s2 = new Scanner(System.in);
                int option2 = s.nextInt();
                switch (option2) {
                    case 1:
                        loadJson();
                        break;
                    case 2:
                        vGenerator();
                        break;
                    case 3:
                        break;
                    default:
                        System.out.println("\n");
                        System.out.println("Advertencia: Opción no valida");
                        break;
                }
            } else if (option == 2) {
                simulation();
            } else if (option == 6) {
                System.out.println("\n");
                System.out.println("Has salido del programa");
                System.out.println("\n");
                break;
            } else {
                System.out.println("\n");
                System.out.println("Advertencia: Opción no valida");
            }
        }
    }
}
