import java.util.LinkedList;
import java.util.Queue;

public class RedBlackTree {
    private RbtNode root;
    BuildingStructure b0 = new BuildingStructure(-1, -1, -1);
    private final RbtNode nil = new RbtNode(-1, b0);

    public RedBlackTree() {
        this.root = nil;
    }

    public RbtNode getRoot() {
        return root;
    }

    public void setRoot(RbtNode root) {
        this.root = root;
    }

    public void printTree(RbtNode node) {
        if (node == nil) {
            return;
        }
        printTree(node.getLeft_child());
        System.out.print("\nColor: " + node.getColor() + "\tKey: " + node.getKey() + "\tvalue: ");
        printTree(node.getRight_child());
    }

    private void insert(RbtNode node) {
        //System.out.println("\n");
        RbtNode temp_node = root;

        if (root == nil) {
            node.setColor(RbtNode.Color.BLACK);
            node.setLeft_child(nil);
            node.setRight_child(nil);
            node.setParent(nil);
            root = node;
        } else {
            node.setColor(RbtNode.Color.RED);
            while (true) {

                if (node.getKey() < temp_node.getKey()) {

                    if (temp_node.getLeft_child() == nil) {
                        //System.out.println("left"+temp_node.getKey());
                        temp_node.setLeft_child(node);
                        node.setParent(temp_node);
                        node.setLeft_child(nil);
                        node.setRight_child(nil);
                        //System.out.println("insert"+node.getKey());
                        break;
                    }


                    else {
                        //System.out.println("left"+temp_node.getKey());
                        temp_node = temp_node.getLeft_child();

                    }


                } else if (node.getKey() >= temp_node.getKey()) {

                    if (temp_node.getRight_child() == nil) {
                        //System.out.println("right"+temp_node.getKey());
                        temp_node.setRight_child(node);
                        node.setParent(temp_node);
                        node.setRight_child(nil);
                        node.setLeft_child(nil);
                        //System.out.println("insert"+node.getKey());
                        break;
                    } else {
                        //System.out.println("right"+temp_node.getKey());
                        temp_node = temp_node.getRight_child();
                    }


                }
            }
            fixTree(node);
        }
    }


    public void printLevelOrder(RbtNode root) {
        // Base Case
        if (root == nil)
            return;

        // Create an empty queue for level order tarversal
        Queue<RbtNode> q = new LinkedList<RbtNode>();

        // Enqueue Root and initialize height
        q.add(root);


        while (true) {

            // nodeCount (queue size) indicates number of nodes
            // at current level.
            int nodeCount = q.size();
            if (nodeCount == 0)
                break;

            // Dequeue all nodes of current level and Enqueue all
            // nodes of next level
            while (nodeCount > 0) {
                RbtNode node = q.peek();
                System.out.print(node.getKey() + " " + node.getColor() + " ");
                q.remove();
                if (node.getLeft_child() != nil)
                    q.add(node.getLeft_child());
                if (node.getRight_child() != nil)
                    q.add(node.getRight_child());
                nodeCount--;
            }
            System.out.println();
        }
    }

    private void fixTree(RbtNode node) {
        while (node.getParent().getColor() == RbtNode.Color.RED) {
            RbtNode aunt = nil;
            if (node.getParent() == node.getParent().getParent().getLeft_child()) {
                aunt = node.getParent().getParent().getRight_child();

                if (aunt != nil && aunt.getColor() == RbtNode.Color.RED) {
                    node.getParent().setColor(RbtNode.Color.BLACK);
                    aunt.setColor(RbtNode.Color.BLACK);
                    node.getParent().getParent().setColor(RbtNode.Color.RED);
                    node = node.getParent().getParent();
                    continue;
                }
                if (node == node.getParent().getRight_child()) {
                    //Double rotation needed
                    node = node.getParent();
                    rotateLeft(node);
                }
                node.getParent().setColor(RbtNode.Color.BLACK);
                node.getParent().getParent().setColor(RbtNode.Color.RED);
                //if the "else if" code hasn't executed, this
                //is a case where we only need a single rotation
                rotateRight(node.getParent().getParent());
            } else {
                aunt = node.getParent().getParent().getLeft_child();
                if (aunt != nil && aunt.getColor() == RbtNode.Color.RED) {
                    node.getParent().setColor(RbtNode.Color.BLACK);
                    aunt.setColor(RbtNode.Color.BLACK);
                    node.getParent().getParent().setColor(RbtNode.Color.RED);
                    node = node.getParent().getParent();
                    continue;
                }
                if (node == node.getParent().getLeft_child()) {
                    //Double rotation needed
                    node = node.getParent();
                    rotateRight(node);
                }
                node.getParent().setColor(RbtNode.Color.BLACK);
                node.getParent().getParent().setColor(RbtNode.Color.RED);
                //if the "else if" code hasn't executed, this
                //is a case where we only need a single rotation
                rotateLeft(node.getParent().getParent());
            }
        }
        root.setColor(RbtNode.Color.BLACK);
    }

    void rotateLeft(RbtNode node) {
        if (node.getParent() != nil) {
            if (node == node.getParent().getLeft_child()) {
                node.getParent().setLeft_child(node.getRight_child());
            } else {
                node.getParent().setRight_child(node.getRight_child());
                ;
            }
            node.getRight_child().setParent(node.getParent());
            node.setParent(node.getRight_child());
            if (node.getRight_child().getLeft_child() != nil) {
                node.getRight_child().getLeft_child().setParent(node);
            }
            node.setRight_child(node.getRight_child().getLeft_child());
            node.getParent().setLeft_child(node);
        } else {//Need to rotate root
            RbtNode right = root.getRight_child();
            root.setRight_child(right.getLeft_child());
            right.getLeft_child().setParent(root);
            root.setParent(right);
            right.setLeft_child(root);
            right.setParent(nil);
            root = right;
        }
    }

    void rotateRight(RbtNode node) {
        if (node.getParent() != nil) {
            if (node == node.getParent().getLeft_child()) {
                node.getParent().setLeft_child(node.getRight_child());
            } else {
                node.getParent().setRight_child(node.getLeft_child());
            }

            node.getLeft_child().setParent(node.getParent());
            node.setParent(node.getLeft_child());
            if (node.getLeft_child().getRight_child() != nil) {
                node.getLeft_child().getRight_child().setParent(node);
            }
            node.setLeft_child(node.getLeft_child().getRight_child());
            node.getParent().setRight_child(node);
        } else {//Need to rotate root
            RbtNode left = root.getLeft_child();
            root.setLeft_child(root.getLeft_child().getRight_child());
            System.out.println(left.getRight_child());
            left.getRight_child().setParent(root);
            root.setParent(left);
            left.setRight_child(root);
            left.setParent(nil);
            root = left;
        }
    }

    /* --------------------------------------------------------------------------------------------------*/
    public void delete(RbtNode node) {


    }


    /* --------------------------------------------------------------------------------------------------*/
    public static void main(String[] arg) {
        BuildingStructure b1 = new BuildingStructure(50, 5, 10);
        BuildingStructure b2 = new BuildingStructure(45, 0, 20);
        BuildingStructure b3 = new BuildingStructure(15, 0, 20);
        BuildingStructure b4 = new BuildingStructure(3, 0, 20);
        BuildingStructure b5 = new BuildingStructure(19, 1, 20);
        BuildingStructure b6 = new BuildingStructure(4, 9, 20);
        RedBlackTree rbt = new RedBlackTree();
        RbtNode rb1 = new RbtNode(b1.getBuildingNum(), b1);
        RbtNode rb2 = new RbtNode(b2.getBuildingNum(), b2);
        RbtNode rb3 = new RbtNode(b3.getBuildingNum(), b3);
        RbtNode rb4 = new RbtNode(b4.getBuildingNum(), b4);
        RbtNode rb5 = new RbtNode(b5.getBuildingNum(), b5);
        RbtNode rb6 = new RbtNode(b6.getBuildingNum(), b6);
        rbt.insert(rb1);
        rbt.insert(rb2);
        rbt.insert(rb3);
        rbt.insert(rb4);
        rbt.insert(rb5);
        rbt.insert(rb6);
        rbt.printLevelOrder(rbt.getRoot());


    }
}


