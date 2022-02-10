package proyecto_01;

/**
 *
 * @author Alexis
 */
public class Image {

    private int idClient;
    private String type;
    private int step;

    public Image(int idClient, String type, int step) {
        this.idClient = idClient;
        this.type = type;
        this.step = step;
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
    
    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }
}
