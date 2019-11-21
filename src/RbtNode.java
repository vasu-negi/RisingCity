public class RbtNode {
    enum Color
    {
        RED, BLACK;
    }
    private int key;
    private BuildingStructure value;
    private RbtNode left_child;
    private RbtNode right_child;
    private RbtNode parent;
    private Color color;

    public RbtNode(int key, BuildingStructure value) {
        this.key = key;
        this.value = value;
        this.left_child = null;
        this.right_child = null;
        this.parent = null;
        this.color = Color.RED;
    }

    public BuildingStructure getValue() {
        return value;
    }

    public void setValue(BuildingStructure value) {
        this.value = value;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public RbtNode getLeft_child() {
        return left_child;
    }

    public void setLeft_child(RbtNode left_child) {
        this.left_child = left_child;
    }

    public RbtNode getRight_child() {
        return right_child;
    }

    public void setRight_child(RbtNode right_child) {
        this.right_child = right_child;
    }

    public RbtNode getParent() {
        return parent;
    }

    public void setParent(RbtNode parent) {
        this.parent = parent;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
