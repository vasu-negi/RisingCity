import java.util.LinkedList;
import java.util.Queue;

public class RedBlackTree {
    private RbtNode root;
    //Empty black node

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

    private void rotateLeft(RbtNode node) {
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

    private void rotateRight(RbtNode node) {
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
    /*-------------------------------------------------------------------------------------------------------------------------------------*/

    private void insert(RbtNode node) {
        //System.out.println("\n");
        RbtNode temp_node = root;

        if (root == nil) {
            node.setColor(RbtNode.Color.BLACK);
            node.setLeft_child(nil);
            node.setRight_child(nil);
            node.setParent(nil);
            root = node;
        }
        /*Check for duplicate Insert*/
        else if  ((searchNode(node, root))!=null){

            System.out.println("duplicate");
        }
        else {
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


    /* --------------------------------------------------------------------------------------------------*/
    private void nodeTransplant(RbtNode node1, RbtNode node2){

        if(node1.getParent() == nil){
            root = node2;
        }else if(node1 == node1.getParent().getLeft_child()){
            node1.getParent().setLeft_child(node2);
        }else
            node1.getParent().setRight_child(node2);


        node2.setParent(node1.getParent()) ;
    }

    private RbtNode searchNode(RbtNode node, RbtNode pivot) {
        if (root == nil) {
            return null;
        }
        /* added by me */
        if (node.getKey() == pivot.getKey()) {
            return pivot;
        }
        else if (node.getKey() < pivot.getKey()) {
            if (pivot.getLeft_child() != nil) {
                return searchNode(node, pivot.getLeft_child());
            }
        } else if (node.getKey() > pivot.getKey()) {
            if (pivot.getRight_child() != nil) {
                return searchNode(node, pivot.getRight_child());
            }
        }
        return null;
    }

    private RbtNode getTreeMinimum(RbtNode sub_tree_node){
        while(sub_tree_node.getLeft_child()!=nil){
            sub_tree_node = sub_tree_node.getLeft_child();
        }
        return sub_tree_node;
    }

    public boolean delete(RbtNode delete_node){
        /* changed */
        if((delete_node = searchNode(delete_node, root))==null)
            return false;
        else {
            RbtNode d1;
            RbtNode d2 = delete_node;
            RbtNode.Color y_original_color = d2.getColor();

            if (delete_node.getLeft_child() == nil) {
                d1 = delete_node.getRight_child();
                nodeTransplant(delete_node, delete_node.getRight_child());


            } else if (delete_node.getRight_child() == nil) {
                d1 = delete_node.getLeft_child();
                nodeTransplant(delete_node, delete_node.getLeft_child());
            }

            else {
                d2 = getTreeMinimum(delete_node.getRight_child());
                y_original_color = d2.getColor();
                d1 = d2.getRight_child();
                if (d2.getParent() == delete_node)
                    d1.setParent(d2);
                else {
                    nodeTransplant(d2, d2.getRight_child());
                    d2.setRight_child(delete_node.getRight_child());
                    d2.getRight_child().setParent(d2);
                }
                nodeTransplant(delete_node, d2);
                d2.setLeft_child(delete_node.getLeft_child());
                d2.getLeft_child().setParent(d2);
                d2.setColor(delete_node.getColor());
            }
            if (y_original_color == RbtNode.Color.BLACK)
                deleteFixUp(d1);
            return true;
        }
    }


    /* --------------------------------------------------------------------------------------------------*/
    private void deleteFixUp(RbtNode x){
        while(x!=root && x.getColor() == RbtNode.Color.BLACK){
            if(x == x.getParent().getLeft_child()){
                RbtNode w = x.getParent().getRight_child();


                if(w.getColor() == RbtNode.Color.RED){ // Case1: x's sibling w is red
                    w.setColor(RbtNode.Color.BLACK);
                    x.getParent().setColor(RbtNode.Color.RED);
                    rotateLeft(x.getParent());
                    w = x.getParent().getRight_child();
                }
                //x's sibling w is black, and both of w's children are black
                if(w.getLeft_child().getColor() == RbtNode.Color.BLACK && w.getRight_child().getColor() == RbtNode.Color.BLACK){
                    w.setColor(RbtNode.Color.RED);
                    x = x.getParent();
                    continue;
                }
                //x's sibling w is black, w's left child is red, and w's right child is black
                else if(w.getRight_child().getColor() == RbtNode.Color.BLACK){
                    w.getLeft_child().setColor(RbtNode.Color.BLACK);
                    w.setColor(RbtNode.Color.RED);
                    rotateRight(w);
                    w = x.getParent().getRight_child();
                }
                // x's sibling w is black, and w's right child is red
                if(w.getRight_child().getColor() == RbtNode.Color.RED){
                    w.setColor(x.getParent().getColor());
                    x.getParent().setColor(RbtNode.Color.BLACK);
                    w.getRight_child().setColor(RbtNode.Color.BLACK);
                    rotateLeft(x.getParent());
                    x = root;
                }
            }else{
                RbtNode w = x.getParent().getLeft_child();
                if(w.getColor() == RbtNode.Color.RED){
                    w.setColor(RbtNode.Color.BLACK);
                    x.getParent().setColor(RbtNode.Color.RED);
                    rotateRight(x.getParent());
                    w = x.getParent().getLeft_child();
                }
                if(w.getRight_child().getColor() == RbtNode.Color.BLACK && w.getLeft_child().getColor() == RbtNode.Color.BLACK){
                    w.setColor(RbtNode.Color.RED);
                    x = x.getParent();
                    continue;
                }
                else if(w.getLeft_child().getColor() == RbtNode.Color.BLACK){
                    w.getRight_child().setColor(RbtNode.Color.BLACK);
                    w.setColor(RbtNode.Color.RED);
                    rotateLeft(w);
                    w = x.getParent().getLeft_child();
                }
                if(w.getLeft_child().getColor() == RbtNode.Color.RED){
                    w.setColor(x.getParent().getColor());
                    x.getParent().setColor(RbtNode.Color.BLACK);
                    w.getLeft_child().setColor(RbtNode.Color.BLACK);
                    rotateRight(x.getParent());
                    x = root;
                }
            }
        }
        x.setColor(RbtNode.Color.BLACK);
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
        rbt.delete(rb2);
        rbt.delete(rb3);
        rbt.delete(rb1);
        rbt.delete(rb5);
        rbt.insert(rb6);
        rbt.insert(rb5);
        rbt.insert(rb1);

        rbt.printLevelOrder(rbt.getRoot());


    }
}


