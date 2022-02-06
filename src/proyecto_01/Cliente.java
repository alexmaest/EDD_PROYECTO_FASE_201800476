package proyecto_01;

import proyecto_01.Structures.List;

/**
 *
 * @author Alexis
 */
public class Cliente {

    private int id = 0;
    private String name = "";
    private int numImgC = 0;
    private int numImgBW = 0;
    private List imgC;
    private List imgBW;
    private boolean giveImg = false;
    private boolean wait = false;

    public Cliente(int id, String name, int numImgC, int numImgBW, List imgC, List imgBW, boolean giveImg, boolean wait) {
        this.id = id;
        this.name = name;
        this.numImgC = numImgC;
        this.numImgBW = numImgBW;
        this.imgC = imgC;
        this.imgBW = imgBW;
        this.giveImg = giveImg;
        this.wait = wait;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public int getnumImgC() {
        return numImgC;
    }

    public void setNumImgC(int numImgC) {
        this.numImgC = numImgC;
    }
    
    public int getnumImgBW() {
        return numImgBW;
    }

    public void setnumImgBW(int numImgBW) {
        this.numImgBW = numImgBW;
    }

    public List getImgC() {
        return imgC;
    }

    public void setImgC(List imgC) {
        this.imgC = imgC;
    }

    public List getImgBW() {
        return imgBW;
    }

    public void setImgBW(List imgBW) {
        this.imgBW = imgBW;
    }

    public boolean getgiveImg() {
        return giveImg;
    }

    public void setgiveImg(boolean giveImg) {
        this.giveImg = giveImg;
    }
    
    public boolean getWait() {
        return wait;
    }

    public void setWait(boolean wait) {
        this.wait = wait;
    }
}
