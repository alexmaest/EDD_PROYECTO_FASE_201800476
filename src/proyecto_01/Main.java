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
import java.util.Random;
import java.util.Scanner;
import proyecto_01.Structures.Nodes.NodeDoubleC;
import proyecto_01.Structures.Nodes.NodeImage;

/**
 *
 * @author Alexis
 */
public class Main {

    public static List Ventanillas = new List();
    public static List Attended = new List();
    public static DoubleList Waiting = new DoubleList();
    public static Cola Clientes = new Cola();
    public static Cola PrinterC = new Cola();
    public static Cola PrinterBW = new Cola();
    public static int globalStep = 0;
    public static int lastId = 0;

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
                    imagesC.addImage(new Image(id, "C", 0));
                    contC++;
                }
                int contBW = 0;
                List imagesBW = new List();
                while (contBW == imgBW) {
                    imagesBW.addImage(new Image(id, "BW", 0));
                    contBW++;
                }
                Cliente newClient = new Cliente(id, name, imgC, imgBW, imagesC, imagesBW, false, false, false, 0, "");
                lastId = id;
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
        while (Active) {
            System.out.println("\n");
            System.out.println("------------------------ SALTO NO." + (globalStep + 1) + " ------------------------");
            System.out.println("......................... ACCIONES .........................");

            //Generar clientes random
            cGenerator();

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
                        //Cantidad de imagenes que tiene el cliente en ventanilla
                        int numC = CurrentV2.value.getClient().value.getnumImgC(); //0
                        int numBW = CurrentV2.value.getClient().value.getnumImgBW(); //1
                        //int cantImages = numBW + numC; //1

                        //Cantidad de imagenes que tiene la ventanilla
                        int numPileC = CurrentV2.value.getImages().getCNumber();//0
                        int numPileBW = CurrentV2.value.getImages().getBWNumber();//0
                        if (numC != 0) {
                            //2  != 3
                            if (numPileC != numC) {
                                images.add(new Image(CurrentV2.value.getClient().value.getId(), "C", 0));
                                CurrentV2.value.getClient().value.getImgC().removeImage();
                                System.out.println("La " + CurrentV2.value.getName() + " recibe una imagen a color del cliente "
                                        + CurrentV2.value.getClient().value.getName());
                            } else {
                                if (numBW != 0) {
                                    if (numPileBW != numBW) {
                                        images.add(new Image(CurrentV2.value.getClient().value.getId(), "BW", 0));
                                        CurrentV2.value.getClient().value.getImgBW().removeImage();
                                        System.out.println("La " + CurrentV2.value.getName() + " recibe una imagen en BW del cliente "
                                                + CurrentV2.value.getClient().value.getName());
                                    } else {
                                        NodeC clientWaiter = CurrentV2.value.getClient();
                                        System.out.println("El cliente " + clientWaiter.value.getName() + " es atendido e ingresa a la lista de espera");
                                        System.out.println("La " + CurrentV2.value.getName() + " envía las imágenes del cliente "
                                                + clientWaiter.value.getName() + " a sus respectivas colas de impresión");
                                        Cliente clientAux = new Cliente(clientWaiter.value.getId(), clientWaiter.value.getName(),
                                                clientWaiter.value.getnumImgC(), clientWaiter.value.getnumImgBW(), new List(),
                                                new List(), false, true, true, 0, CurrentV2.value.getName());
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
                                                PrinterC.addImg(new Image(clientWaiter.value.getId(), "C", 0));
                                            } else {
                                                PrinterBW.addImg(new Image(clientWaiter.value.getId(), "BW", 0));
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
                                            clientWaiter.value.getnumImgC(), clientWaiter.value.getnumImgBW(), new List(),
                                            new List(), false, true, true, 0, CurrentV2.value.getName());
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
                                            PrinterC.addImg(new Image(clientWaiter.value.getId(), "C", 0));
                                        } else {
                                            PrinterBW.addImg(new Image(clientWaiter.value.getId(), "BW", 0));
                                        }
                                        CurrentImg = CurrentImg.next;
                                    }
                                    CurrentV2.value.getImages().cleanPile();
                                    Waiting.add(clientAux);
                                }
                            }
                        } else if (numBW != 0) {
                            if (numPileBW != numBW) {
                                images.add(new Image(CurrentV2.value.getClient().value.getId(), "BW", 0));
                                CurrentV2.value.getClient().value.getImgBW().removeImage();
                                System.out.println("La " + CurrentV2.value.getName() + " recibe una imagen en BW del cliente "
                                        + CurrentV2.value.getClient().value.getName());
                            } else {
                                NodeC clientWaiter = CurrentV2.value.getClient();
                                System.out.println("El cliente " + clientWaiter.value.getName() + " es atendido e ingresa a la lista de espera");
                                System.out.println("La " + CurrentV2.value.getName() + " envía las imágenes del cliente "
                                        + clientWaiter.value.getName() + " a sus respectivas colas de impresión");
                                Cliente clientAux = new Cliente(clientWaiter.value.getId(), clientWaiter.value.getName(),
                                        clientWaiter.value.getnumImgC(), clientWaiter.value.getnumImgBW(), new List(),
                                        new List(), false, true, true, 0, CurrentV2.value.getName());
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
                                        PrinterC.addImg(new Image(clientWaiter.value.getId(), "C", 0));
                                    } else {
                                        PrinterBW.addImg(new Image(clientWaiter.value.getId(), "BW", 0));
                                    }
                                    CurrentImg = CurrentImg.next;
                                }
                                CurrentV2.value.getImages().cleanPile();
                                Waiting.add(clientAux);
                            }
                        }
                    }
                }
                CurrentV2 = CurrentV2.next;
            }

            //Agregar un paso al contador de los clientes
            NodeDoubleC CurrentStep = Waiting.first;
            while (CurrentStep != null) {
                if (CurrentStep.value.getWait() != true) {
                    int auxStepCont = CurrentStep.value.getStepCont();
                    CurrentStep.value.setStepCont(auxStepCont += 1);
                }
                CurrentStep = CurrentStep.next;
            }

            //Impresoras regresan imagenes a clientes
            boolean sameStepC = false;
            boolean sameStepBW = false;
            NodeDoubleC CurrentV3 = Waiting.first;
            while (CurrentV3 != null) {
                if (CurrentV3.value.getWait() != true) {
                    //Recorre cada cola de impresion en busca del mismo id
                    //COLOR
                    NodeImage CurrentC = PrinterC.first2;
                    if (CurrentC != null) {
                        if (CurrentV3.value.getId() == CurrentC.value.getIdClient()) {
                            if (sameStepC == false) {
                                if (CurrentC.value.getStep() == 2) {
                                    System.out.println("Se completa la impresión de una imagen a color y se le"
                                            + " entrega al cliente " + CurrentV3.value.getName());
                                    List imgListC = CurrentV3.value.getImgC();
                                    imgListC.addImage(new Image(CurrentC.value.getIdClient(), CurrentC.value.getType(), CurrentC.value.getStep()));
                                    CurrentV3.value.setImgC(imgListC);
                                    PrinterC.removeImage();
                                    sameStepC = true;
                                } else {
                                    int stepNum = CurrentC.value.getStep();
                                    CurrentC.value.setStep(stepNum += 1);
                                    if (CurrentC.value.getStep() == 2) {
                                        System.out.println("Se completa la impresión de una imagen a color y se le"
                                                + " entrega al cliente " + CurrentV3.value.getName());
                                        List imgListC = CurrentV3.value.getImgC();
                                        imgListC.addImage(new Image(CurrentC.value.getIdClient(), CurrentC.value.getType(), CurrentC.value.getStep()));
                                        CurrentV3.value.setImgC(imgListC);
                                        PrinterC.removeImage();
                                        sameStepC = true;
                                    }
                                }
                            }
                        }
                    }

                    //BW
                    NodeImage CurrentC2 = PrinterBW.first2;
                    if (CurrentC2 != null) {
                        if (CurrentV3.value.getId() == CurrentC2.value.getIdClient()) {
                            if (sameStepBW == false) {
                                if (CurrentC2.value.getStep() == 1) {
                                    System.out.println("Se completa la impresión de una imagen en blanco y negro y se le"
                                            + " entrega al cliente " + CurrentV3.value.getName());
                                    List imgListBW = CurrentV3.value.getImgBW();
                                    imgListBW.addImage(new Image(CurrentC2.value.getIdClient(), CurrentC2.value.getType(), CurrentC2.value.getStep()));
                                    CurrentV3.value.setImgBW(imgListBW);
                                    PrinterBW.removeImage();
                                    sameStepBW = true;
                                } else {
                                    int stepNum = CurrentC2.value.getStep();
                                    CurrentC2.value.setStep(stepNum += 1);
                                    if (CurrentC2.value.getStep() == 1) {
                                        System.out.println("Se completa la impresión de una imagen en blanco y negro y se le"
                                                + " entrega al cliente " + CurrentV3.value.getName());
                                        List imgListBW = CurrentV3.value.getImgBW();
                                        imgListBW.addImage(new Image(CurrentC2.value.getIdClient(), CurrentC2.value.getType(), CurrentC2.value.getStep()));
                                        CurrentV3.value.setImgBW(imgListBW);
                                        PrinterBW.removeImage();
                                        sameStepBW = true;
                                    }
                                }
                            }
                        }
                    }
                } else {
                    CurrentV3.value.setWait(false);
                }
                CurrentV3 = CurrentV3.next;
            }

            //Cliente sale de la empresa
            NodeDoubleC CurrentV4 = Waiting.first;
            while (CurrentV4 != null) {
                if (CurrentV4.value.getnumImgC() == CurrentV4.value.getImgC().size3 && CurrentV4.value.getnumImgBW() == CurrentV4.value.getImgBW().size3) {
                    if (CurrentV4.value.getExit() != true) {
                        System.out.println("El cliente " + CurrentV4.value.getName() + " ya posee todas sus imagenes impresas y sale de la empresa");
                        Attended.addC(new Cliente(CurrentV4.value.getId(), CurrentV4.value.getName(), CurrentV4.value.getnumImgC(), CurrentV4.value.getnumImgBW(), CurrentV4.value.getImgC(),
                                CurrentV4.value.getImgBW(), CurrentV4.value.getgiveImg(), CurrentV4.value.getWait(), CurrentV4.value.getExit(), (CurrentV4.value.getStepCont() - 1), CurrentV4.value.getvAttended()));
                        Waiting.remove(CurrentV4.value.getId());
                    } else {
                        CurrentV4.value.setExit(false);
                    }
                }
                CurrentV4 = CurrentV4.next;
            }

            System.out.println("\n");
            System.out.println(".................. CLIENTES HACIENDO COLA ..................");
            Clientes.printContentC();
            System.out.println("........................ VENTANILLAS .......................");
            Ventanillas.printContentV();
            Ventanillas.printContentVClients();
            System.out.println("...................... LISTA DE ESPERA .....................");
            Waiting.printContent();
            System.out.println("................ COLA DE IMPRESORA A COLOR .................");
            PrinterC.printContentImg();
            System.out.println(".................. COLA DE IMPRESORA A BW ..................");
            PrinterBW.printContentImg();
            System.out.println(".................... CLIENTES ATENDIDOS ....................");
            Attended.printContentC();
            globalStep++;
            break;
        }
    }

    public static void cGenerator() {
        String[] names = {"Alvaro", "Felipe", "Juan", "Carlos", "Alberto", "Sara", "Maria", "Jorge", "Isabel", "Fernanda", "Lucia", "Lourdes", "Vanessa", "Carol",
            "Sofia", "Andrea", "Abner", "Alejandra", "Gabriela", "Manuel", "Hugo", "Francisco", "Jaime", "Ivan", "Michelle"};
        String[] lastnames = {"Gonzalez", "Gomez", "Diaz", "Rodriguez", "Fernandez", "Lopez", "Garcia", "Romero", "Sanchez", "Muñoz", "Flores", "Rojas", "Morales",
            "Torres", "Espinoza", "Fuentes", "Soto", "Alvarez", "Castro", "Cortes", "Rivera", "Figueroa", "Campos", "Ortiz", "Guzman"};

        Random r = new Random();

        int clients = r.nextInt(4);
        System.out.println(clients + "----------------");
        if (clients != 0) {
            for (int i = 0; i < clients; i++) {
                int rNames = r.nextInt(25);
                int rLastNames = r.nextInt(25);
                int imagesC = r.nextInt(5);
                int imagesBW = r.nextInt(5);
                Clientes.addC(new Cliente((lastId + 1), (names[rNames] + " " + lastnames[rLastNames]), imagesC, imagesBW,
                        new List(), new List(), false, false, false, 0, ""));
            }
        }
    }

    public static void liveStructures() {
        System.out.println("\n");
        System.out.println(".................. CLIENTES HACIENDO COLA ..................");
        Clientes.printContentC();
        System.out.println("........................ VENTANILLAS .......................");
        Ventanillas.printContentV();
        Ventanillas.printContentVClients();
        System.out.println("...................... LISTA DE ESPERA .....................");
        Waiting.printContent();
        System.out.println("................ COLA DE IMPRESORA A COLOR .................");
        PrinterC.printContentImg();
        System.out.println(".................. COLA DE IMPRESORA A BW ..................");
        PrinterBW.printContentImg();
        System.out.println(".................... CLIENTES ATENDIDOS ....................");
        Attended.printContentC();
        System.out.println("\n");
    }

    public static void studentData() {
        System.out.println("\n");
        System.out.println("...................... DATOS ESTUDIANTE ....................");
        System.out.println("               Estructuras de datos Sección B");
        System.out.println("                Marvin Alexis Estrada Florian");
        System.out.println("                         201800476");
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
                    default:
                        System.out.println("\n");
                        System.out.println("Advertencia: Opción no valida");
                        break;
                }
            } else if (option == 2) {
                simulation();
            } else if (option == 3) {
                liveStructures();
            } else if (option == 5) {
                studentData();
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