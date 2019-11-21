

import java.util.*;

class RedBlackTreeStructure{
    RbtNode head;
    public int comparator(int key1, int key2){
        return key1 - key2;
    }

    public void printLevelOrder(RbtNode root) {
       //Sentinel Case
        if (root == null)
            return;


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
                System.out.print(node.getValue().printBuilding() + " " + node.getColor() + " ");
                q.remove();
                if (node.getLeft_child()!= null)
                    q.add(node.getLeft_child());
                if (node.getRight_child() != null)
                    q.add(node.getRight_child());
                nodeCount--;
            }
            System.out.println();
        }
    }

    public void printDriver(){
        printLevelOrder(head);
    }

    boolean addElement(int  key, BuildingStructure value) {

        RbtNode node = new RbtNode(key,value);
        if(head == null) {
            node.setColor(RbtNode.Color.BLACK);

            head = node;
        } else {
            RbtNode current = head;
            RbtNode parent = head;

            while(current != null) {
                parent = current;
                int compareValue = comparator(node.getKey(), current.getKey());
                if(compareValue < 0) {
                    current = current.getLeft_child();
                } else if(compareValue > 0) {
                    current = current.getRight_child();
                } else {
                    return false;
                }
            }
            node.setParent(parent) ;
            if(comparator(node.getKey(), parent.getKey()) < 0) {
                parent.setLeft_child(node);
            } else {
                parent.setRight_child(node);
            }

            setUpTreeAfterInsert(node);
        }
        return true;
    }

    private void setUpTreeAfterInsert(RbtNode node) {
        RbtNode parent = node.getParent();

        if( node.getColor()== RbtNode.Color.RED && parent.getColor()== RbtNode.Color.RED ) {
            RbtNode grandParent = node.getParent().getParent();
            RbtNode.Color gpOtherChildColor;


            if(grandParent.getLeft_child() == parent) {
                gpOtherChildColor = grandParent.getRight_child() == null ? RbtNode.Color.BLACK : grandParent.getRight_child().getColor();
            } else {
                gpOtherChildColor = grandParent.getLeft_child() == null ? RbtNode.Color.BLACK  : grandParent.getLeft_child().getColor();
            }

            if( gpOtherChildColor == RbtNode.Color.RED ) {
                grandParent.getLeft_child().setColor(RbtNode.Color.BLACK);
                if(grandParent.getRight_child() != null) grandParent.getRight_child().setColor(RbtNode.Color.BLACK);
                if(grandParent != head) {
                    grandParent.setColor(RbtNode.Color.RED);
                    setUpTreeAfterInsert(grandParent);
                }
            } else {
                if(grandParent.getLeft_child() == parent) {
                    if(parent.getLeft_child() == node) {
                        LLShift(parent);
                    } else if(parent.getRight_child() == node){
                        RRShift(parent.getRight_child());
                        LLShift(grandParent.getLeft_child());
                    }
                } else if(grandParent.getRight_child() == parent) {
                    if(parent.getRight_child() == node) {
                        RRShift(parent);
                    } else if(parent.getLeft_child() == node) {
                        LLShift(parent.getLeft_child());
                        RRShift(grandParent.getRight_child());
                    }
                }
            }
        }
    }

    private void LLShift(RbtNode node) {
        // System.out.println("LL Shift: " + node.key);
        RbtNode nodeRight = node.getRight_child();
        RbtNode nodeParent = node.getParent();


        node.setRight_child(nodeParent);
        node.setParent(nodeParent.getParent());
        if(node.getParent() == null) {
            head = node;
        } else {
            if(node.getParent().getLeft_child() == nodeParent) {
                node.getParent().setLeft_child(node);
            } else {
                node.getParent().setRight_child(node);
            }
        }
        node.getRight_child().setParent(node);
        node.getRight_child().setLeft_child(nodeRight);
        if(nodeRight != null) nodeRight.setParent(node.getRight_child());

        RbtNode.Color temp = node.getColor();
        node.setColor(nodeParent.getColor());
        nodeParent.setColor(temp);
    }

    private void RRShift(RbtNode node) {
        // System.out.println("RR Shift");

        RbtNode nodeLeft = node.getLeft_child();
        RbtNode nodeParent = node.getParent();


        node.setLeft_child(nodeParent);
        node.setParent(nodeParent.getParent());
        if(node.getParent() == null) {
            head = node;
        } else {
            if(node.getParent().getLeft_child() == nodeParent) {
                node.getParent().setLeft_child(node);
            } else {
                node.getParent().setRight_child(node);
            }
        }
        node.getLeft_child().setParent(node);
        node.getLeft_child().setRight_child(nodeLeft);
        if(nodeLeft != null) nodeLeft.setParent(node.getLeft_child());

        RbtNode.Color temp = node.getColor();
        node.setColor(nodeParent.getColor());
        nodeParent.setColor(temp);
    }

    void printInOrder() {
        if(this.head == null) {
            System.out.print("Head is null");
        }
        printInOrder(this.head);
        System.out.println();
    }

    void printInOrder(RbtNode head) {
        if(head == null) {
            return;
        }
        printInOrder(head.getLeft_child());
        System.out.print(head.getKey() + ":" + head.getColor() + " ");
        printInOrder(head.getRight_child());
    }

    boolean deleteElement(int element) {

        RbtNode nodeToBeDeleted = findElement(head, element);
        if (nodeToBeDeleted == null) return false;

        int degreeOfNodeToBeDeleted = findDegree(nodeToBeDeleted);

        if(degreeOfNodeToBeDeleted == 2) {
            RbtNode inOrderSuccessor = findInOrderSuccessor(nodeToBeDeleted);
            nodeToBeDeleted.setKey(inOrderSuccessor.getKey()) ;
            nodeToBeDeleted = inOrderSuccessor;
        }

        RbtNode  childOfDeletedNode = nodeToBeDeleted.getLeft_child() == null ? nodeToBeDeleted.getRight_child() : nodeToBeDeleted.getLeft_child();
        RbtNode  parentOfDeletedNode = nodeToBeDeleted.getParent();
        if(parentOfDeletedNode == null) {
            if(childOfDeletedNode == null) {
                head = null;
                return true;
            }
            childOfDeletedNode.setColor(RbtNode.Color.BLACK);
            childOfDeletedNode.setParent(null);
            head = childOfDeletedNode;
            return true;
        }
        if (parentOfDeletedNode.getRight_child() == nodeToBeDeleted) {
            parentOfDeletedNode.setRight_child(childOfDeletedNode);
        } else {
            parentOfDeletedNode.setLeft_child(childOfDeletedNode);
        }
        if(childOfDeletedNode != null) childOfDeletedNode.setParent(parentOfDeletedNode);

        if(isRedNode(nodeToBeDeleted) || isRedNode(childOfDeletedNode)) {
            if(childOfDeletedNode != null) childOfDeletedNode.setColor(RbtNode.Color.BLACK);
        } else {
            // printInOrder(head);
            reBalance(childOfDeletedNode, parentOfDeletedNode);
        }
        return true;
    }

    private void reBalance(RbtNode  doubleBlackNode, RbtNode  doubleBlackNodeParent) {

        // Case-1
        if(doubleBlackNode == head) {
            // System.out.println("case-1");
            return;
        }

        RbtNode  doubleBlackNodeSibling = doubleBlackNodeParent.getLeft_child() == doubleBlackNode ? doubleBlackNodeParent.getRight_child() : doubleBlackNodeParent.getLeft_child();
        RbtNode  doubleBlackNodeSiblingLeft = doubleBlackNodeSibling != null? doubleBlackNodeSibling.getLeft_child() : null;
        RbtNode  doubleBlackNodeSiblingRight = doubleBlackNodeSibling != null ? doubleBlackNodeSibling.getRight_child() : null;


        // Case-2
        if(isRedNode(doubleBlackNodeSibling)) {
            if(doubleBlackNodeParent.getLeft_child() == doubleBlackNode) {
                // System.out.println("case-2 L");
                RRShift(doubleBlackNodeSibling);
            } else if(doubleBlackNodeParent.getRight_child() == doubleBlackNode) {
                // System.out.println("case-2 R");
                LLShift(doubleBlackNodeSibling);
            }
            doubleBlackNodeSibling.setColor(RbtNode.Color.BLACK);
            doubleBlackNodeParent.setColor(RbtNode.Color.RED);
            reBalance(doubleBlackNodeSibling, doubleBlackNodeSibling.getParent());
            return;
        }

        // Case-3
        if(isBlackNode(doubleBlackNodeParent) && isBlackNode(doubleBlackNodeSibling) &&
                isBlackNode(doubleBlackNodeSiblingLeft) && isBlackNode(doubleBlackNodeSiblingRight)) {
            // System.out.println("Case-3");
            if(doubleBlackNodeSibling != null) {
                doubleBlackNodeSibling.setColor(RbtNode.Color.RED);
            }
            reBalance(doubleBlackNodeParent, doubleBlackNodeParent.getParent());
            return;
        }

        // Case-4
        if(isRedNode(doubleBlackNodeParent) && isBlackNode(doubleBlackNodeSibling) &&
                isBlackNode(doubleBlackNodeSiblingLeft) && isBlackNode(doubleBlackNodeSiblingRight)) {
            // System.out.println("Case-4: " + doubleBlackNodeSibling.key);
            if(doubleBlackNodeSibling != null) {
                doubleBlackNodeSibling.setColor(RbtNode.Color.RED);
            }

            doubleBlackNodeParent.setColor(RbtNode.Color.BLACK);
            return;
        }

        // Case-5
        if(isBlackNode(doubleBlackNodeParent)) {
            if(doubleBlackNodeParent.getLeft_child() == doubleBlackNode && isRedNode(doubleBlackNodeSiblingLeft) && isBlackNode(doubleBlackNodeSiblingRight)) {
                // System.out.println("Case-5 L");
                LLShift(doubleBlackNodeSiblingLeft);
                reBalance(doubleBlackNode, doubleBlackNodeParent);
                return;
            } else if(doubleBlackNodeParent.getRight_child() == doubleBlackNode && isRedNode(doubleBlackNodeSiblingRight) && isBlackNode(doubleBlackNodeSiblingLeft)) {
                // System.out.println("Case-5 R");
                RRShift(doubleBlackNodeSiblingRight);
                reBalance(doubleBlackNode, doubleBlackNodeParent);
                return;
            }
        }

        // Case-6
        if(doubleBlackNodeParent.getLeft_child() == doubleBlackNode && isBlackNode(doubleBlackNodeSibling) && isRedNode(doubleBlackNodeSiblingRight)) {
            // System.out.println("Case-6 L");
            RRShift(doubleBlackNodeSibling);
            doubleBlackNodeSiblingRight.setColor(RbtNode.Color.BLACK) ;
        } else if(doubleBlackNodeParent.getRight_child() == doubleBlackNode && isBlackNode(doubleBlackNodeSibling) && isRedNode(doubleBlackNodeSiblingLeft)) {
            // System.out.println("Case-6 R");
            LLShift(doubleBlackNodeSibling);
            doubleBlackNodeSiblingLeft.setColor(RbtNode.Color.BLACK);
        }

    }

    BuildingStructure searchElement(int key) {
        RbtNode  element = findElement(head, key);
        return element == null ? null : element.getValue();
    }

    List<BuildingStructure> getElementsBetweenRange(int key1, int key2) {
        List<BuildingStructure> elements = new ArrayList<>();
        getElementsBetweenRange(head, key1, key2, elements);
        return elements;
    }

    private void getElementsBetweenRange(RbtNode  head, int key1, int key2, List<BuildingStructure> elements) {
        if(head == null) {
            return;
        }
        if(comparator(head.getKey(), key1) >= 0 && comparator(head.getKey(), key2) <= 0) {
            getElementsBetweenRange(head.getLeft_child(), key1, key2, elements);
            elements.add(head.getValue());
            getElementsBetweenRange(head.getRight_child(), key1, key2, elements);
            return;
        }
        if(comparator(head.getKey(), key1) <= 0) {
            getElementsBetweenRange(head.getRight_child(), key1, key2, elements);
            return;
        }
        if(comparator(head.getKey(), key2) >= 0) {
            getElementsBetweenRange(head.getLeft_child(), key1, key2, elements);
        }
    }


    private RbtNode findElement(RbtNode head, int element) {
        if(head == null) {
            return null;
        }
        int diff = comparator(head.getKey(), element);
        if(diff == 0) {
            return head;
        } else if(diff < 0) {
            return findElement(head.getRight_child(), element);
        } else {
            return findElement(head.getLeft_child(), element);
        }
    }

    private int findDegree(RbtNode node) {
        int degree = 0;
        if(node.getRight_child() != null) {
            degree++;
        }
        if(node.getLeft_child() != null) {
            degree++;
        }
        return degree;
    }

    private RbtNode findInOrderSuccessor(RbtNode node) {
        node = node.getRight_child();
        RbtNode parent = node;
        while(node != null) {
            parent = node;
            node = node.getLeft_child();
        }
        return parent;
    }

    private boolean isRedNode(RbtNode node) {
        return node != null && node.getColor() == RbtNode.Color.RED ;
    }

    private boolean isBlackNode(RbtNode node) {
        return node == null || node.getColor() == RbtNode.Color.BLACK ;
    }


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

    }
}



