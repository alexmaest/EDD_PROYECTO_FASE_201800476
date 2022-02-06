package proyecto_01;

/**
 *
 * @author Alexis
 */
public class Image {

    private int idClient;
    private String type;

    public Image(int idClient, String type) {
        this.idClient = idClient;
        this.type = type;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
