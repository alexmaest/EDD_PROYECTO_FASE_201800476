package proyecto_02.Structures;

import proyecto_02.Structures.SubStructure.NodeMatrix;

/**
 *
 * @author Alexis
 */
public class SparceMatrix {

    NodeMatrix root;

    public SparceMatrix() {
        this.root = new NodeMatrix(-1, -1, "root");
    }

    private NodeMatrix searchColumn(int x) {
        NodeMatrix aux = this.root;
        while (aux != null) {
            if (aux.x == x) {
                return aux;
            }
            aux = aux.next;
        }
        return null;
    }

    private NodeMatrix searchRow(int y) {
        NodeMatrix aux = this.root;
        while (aux != null) {
            if (aux.y == y) {
                return aux;
            }
            aux = aux.down;
        }
        return null;
    }

    private NodeMatrix createColumn(int x) {
        NodeMatrix aux = this.root;
        NodeMatrix newValue = new NodeMatrix(x, -1, "column");
        NodeMatrix column = this.sortAddColumn(newValue, aux);
        return column;
    }

    private NodeMatrix sortAddColumn(NodeMatrix newValue, NodeMatrix headerColumn) {
        NodeMatrix aux = headerColumn;
        boolean Added = false;
        while (true) {
            if (newValue.x == aux.x) {
                aux.y = newValue.y;
                aux.value = newValue.value;
                return aux;
            } else if (aux.x > newValue.x) {
                Added = true;
                break;
            }
            if (aux.next != null) {
                aux = aux.next;
            } else {
                Added = false;
                break;
            }
        }
        if (Added) {
            newValue.next = aux;
            aux.previous.next = newValue;
            newValue.previous = aux.previous;
            aux.previous = newValue;
        } else {
            aux.next = newValue;
            newValue.previous = aux;
        }
        return newValue;
    }

    private NodeMatrix createRow(int y) {
        NodeMatrix nodeRow = this.root;
        NodeMatrix newValue = new NodeMatrix(-1, y, "row");
        NodeMatrix column = this.sortAddRow(newValue, nodeRow);
        return column;
    }

    private NodeMatrix sortAddRow(NodeMatrix newValue, NodeMatrix headerRow) {
        NodeMatrix aux = headerRow;
        boolean Added = false;
        while (true) {
            if (newValue.y == aux.y) {
                aux.x = newValue.x;
                aux.value = newValue.value;
                return aux;
            } else if (aux.y > newValue.y) {
                Added = true;
                break;
            }
            if (aux.down != null) {
                aux = aux.down;
            } else {
                Added = false;
                break;
            }
        }
        if (Added) {
            newValue.down = aux;
            aux.up.down = newValue;
            newValue.up = aux.up;
            aux.up = newValue;
        } else {
            aux.down = newValue;
            newValue.up = aux;
        }
        return newValue;
    }

    public NodeMatrix addNode(int x, int y, String value) {
        NodeMatrix newValue = new NodeMatrix(x, y, value);
        NodeMatrix columnNode = this.searchColumn(x);
        NodeMatrix rowNode = this.searchRow(y);
        if (rowNode == null && columnNode == null) {
            columnNode = this.createColumn(x);
            rowNode = this.createRow(y);
            newValue = this.sortAddColumn(newValue, rowNode);
            newValue = this.sortAddRow(newValue, columnNode);
            return null;
        } else if (rowNode == null && columnNode != null) {
            rowNode = this.createRow(y);
            newValue = this.sortAddColumn(newValue, rowNode);
            newValue = this.sortAddRow(newValue, columnNode);
            return null;
        } else if (rowNode != null && columnNode == null) {
            columnNode = this.createColumn(x);
            newValue = this.sortAddColumn(newValue, rowNode);
            newValue = this.sortAddRow(newValue, columnNode);
            return null;
        } else if (rowNode != null && columnNode != null) {
            newValue = this.sortAddColumn(newValue, rowNode);
            newValue = this.sortAddRow(newValue, columnNode);
            return null;
        }
        return null;
    }

    public void printMatrixContent(){
        NodeMatrix aux = this.root;
        while (aux != null){
            String text = "";
            NodeMatrix aux2 = aux;
            while(aux2 != null){
                text += "[" + aux2.x + aux2.y + "]";
                aux2 = aux2.next;
            }
            System.out.println(text);
            aux = aux.down;
        }
    }

}
