package proyecto_02.Structures;

import proyecto_02.AmplitudeObject;
import proyecto_02.Structures.SubStructure.Node;
import proyecto_02.Client;
import proyecto_02.Layer;
import proyecto_02.Photo;
import proyecto_02.Pixel;

/**
 *
 * @author Alexis
 */
public class List {

    //Client | Pixel | Layer | Image | AmplitudeObject
    public Node first;
    public int size = 0;
    public int maxRow = 0;
    public int maxCol = 0;

    public List() {
        this.first = null;
        this.size = 0;
    }

    public void addC(Client valueToAdd) {
        Node myNode = new Node(valueToAdd);
        if (this.size == 0) {
            this.first = myNode;
        } else {
            Node Current = this.first;
            while (Current.next != null) {
                Current = Current.next;
            }
            Current.next = myNode;
        }
        this.size += 1;
    }

    public boolean removeC(int idClient) {
        if (this.size == 0) {
            return false;
        } else {
            Node Current = this.first;
            Node Previous = null;
            while (Current.valuec.getDpi() != idClient) {
                Previous = Current;
                Current = Current.next;
                if (Current.next == null && Current.valuec.getDpi() != idClient) {
                    System.out.println("Id no encontrado");
                    return false;
                }
            }
            if (Previous == null) {
                this.first = Current.next;
                Current.next = null;
            } else if (Current != null) {
                Previous.next = Current.next;
                Current.next = null;
            }
            this.size -= 1;
        }
        return true;
    }

    public void printContentC() {
        Node Current = this.first;
        if (this.size != 0) {
            while (Current.next != null) {
                System.out.print(Current.valuec.getName() + "    ");
                Current = Current.next;
            }
            System.out.println(Current.valuec.getName());
        } else {
            System.out.println("La lista esta vacía");
        }
    }

    public void printContentP() {
        System.out.println("size: " + this.size);
        Node Current = this.first;
        if (this.size != 0) {
            while (Current != null) {
                System.out.println("row: " + Current.valuep.getRow() + ", column: " + Current.valuep.getColumn() + ", color: " + Current.valuep.getColor());
                Current = Current.next;
            }
        } else {
            System.out.println("La lista esta vacía");
        }
    }

    public void printContentL() {
        System.out.println("size: " + this.size);
        Node Current = this.first;
        if (this.size != 0) {
            while (Current != null) {
                System.out.println("id: " + Current.valuel.getId());
                Current = Current.next;
            }
        } else {
            System.out.println("La lista esta vacía");
        }
    }
    
    public void SearchClient(int id) {
        Node Current = this.first;
        boolean Found = false;
        if (this.size != 0) {
            while (Current.next != null) {
                if (Current.valuec.getDpi() == id) {
                    System.out.print("Nombre: " + Current.valuec.getName());
                    Found = true;
                    break;
                } else {
                    Current = Current.next;
                }
            }
            if (Found == false) {
                System.out.println("Información: Cliente no encontrado");
            }
        } else {
            System.out.println("La lista esta vacía");
        }
    }

    public boolean SearchPhoto(int id) {
        Node Current = this.first;
        boolean Found = false;
        if (this.size != 0) {
            while (Current.next != null) {
                if (Current.valuei.getId()== id) {
                    return true;
                } else {
                    Current = Current.next;
                }
            }
            if (Found == false) {
                return false;
            }
        } else {
            System.out.println("La lista esta vacía");
        }
        return false;
    }
    
    public void addPixel(Pixel valueToAdd) {
        Node myNode = new Node(valueToAdd);
        if (this.size == 0) {
            this.first = myNode;
        } else {
            Node Current = this.first;
            while (Current.next != null) {
                Current = Current.next;
            }
            Current.next = myNode;
        }
        this.size += 1;
    }

    public void addLayer(Layer valueToAdd) {
        Node myNode = new Node(valueToAdd);
        if (this.size == 0) {
            this.first = myNode;
        } else {
            Node Current = this.first;
            while (Current.next != null) {
                Current = Current.next;
            }
            Current.next = myNode;
        }
        this.size += 1;
    }

    public void addImage(Photo valueToAdd) {
        Node myNode = new Node(valueToAdd);
        if (this.size == 0) {
            this.first = myNode;
        } else {
            Node Current = this.first;
            while (Current.next != null) {
                Current = Current.next;
            }
            Current.next = myNode;
        }
        this.size += 1;
    }

    public void addAObject(AmplitudeObject valueToAdd) {
        Node myNode = new Node(valueToAdd);
        if (this.size == 0) {
            this.first = myNode;
        } else {
            Node Current = this.first;
            while (Current.next != null) {
                Current = Current.next;
            }
            Current.next = myNode;
        }
        this.size += 1;
    }

    public void sortPixels(Pixel valueToAdd) {
        Node myNode = new Node(valueToAdd);
        if (this.first == null) {
            this.first = myNode;
            if (myNode.valuep.getRow() > maxRow) {
                maxRow = myNode.valuep.getRow();
            }
            if (myNode.valuep.getColumn() > maxCol) {
                maxCol = myNode.valuep.getColumn();
            }
        } else {
            Node Current = this.first;
            Node Next;
            while (Current != null) {
                Next = Current.next;
                if (myNode.valuep.getRow() > maxRow) {
                    maxRow = myNode.valuep.getRow();
                }
                if (myNode.valuep.getColumn() > maxCol) {
                    maxCol = myNode.valuep.getColumn();
                }
                if (myNode.valuep.getRow() <= Current.valuep.getRow() && myNode.valuep.getColumn() <= Current.valuep.getColumn()) {
                    myNode.next = Current;
                    this.first = myNode;
                    break;
                } else if (Next == null) {
                    Current.next = myNode;
                    break;
                } else if (myNode.valuep.getRow() < Next.valuep.getRow() && myNode.valuep.getColumn() > Next.valuep.getColumn()) {
                    Current.next = myNode;
                    myNode.next = Next;
                    break;
                }
                Current = Current.next;
            }
        }
        this.size += 1;
    }

    public void sortHigherPhoto(Photo valueToAdd) {
        Node myNode = new Node(valueToAdd);
        if (this.first == null) {
            this.first = myNode;
        } else {
            Node Current = this.first;
            Node Next;
            while (Current != null) {
                Next = Current.next;
                if (myNode.valuei.getLayers().size > Current.valuei.getLayers().size) {
                    myNode.next = Current;
                    this.first = myNode;
                    break;
                } else if (Next == null) {
                    Current.next = myNode;
                    break;
                } else if (myNode.valuei.getLayers().size > Next.valuei.getLayers().size) {
                    Current.next = myNode;
                    myNode.next = Next;
                    break;
                }
                Current = Current.next;
            }
        }
        this.size += 1;
    }
}
