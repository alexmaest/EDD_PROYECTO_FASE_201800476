package proyecto_02;

/**
 *
 * @author Alexis
 */
public class Client {
    
    private long dpi = 0;
    private String name = "";
    private String password = "";

    public Client(long dpi, String name, String password) {
        this.dpi = dpi;
        this.name = name;
        this.password = password;
    }

    public long getDpi() {
        return dpi;
    }

    public void setDpi(int dpi) {
        this.dpi = dpi;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
