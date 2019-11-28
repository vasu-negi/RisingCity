import java.util.*;

/*Contains the attributes and basic methods for implementing a Red Black Tree in order of buildingNum and performing the desired instructions of the system.*/
class RedBlackTreeStructure {
    /*Root node of the tree*/
    RbtNode root;

    /*
    returns
    value>1 if key1 > key2,
    value 0 if key1 = key2,
    value<1 if key1 < key2
    */
    public int comparator(int key1, int key2) {
        return key1 - key2;
    }

    /*checks whether the node color is RED*/
    private boolean isRedColorNode(RbtNode node) {
        return node != null && node.getColor() == RbtNode.Color.RED;
    }

    /*checks whether the node color is BLACK*/
    private boolean isBlackColorNode(RbtNode node) {
        return node == null || node.getColor() == RbtNode.Color.BLACK;
    }

    /* *****************************************************************
    Description: Inserts the element into the Red Black Tree
    Parameters: int key, BuildingStructure value
    Return Value: boolean
    ********************************************************************/

    boolean addElementToRbTree(int key, BuildingStructure value) {

        RbtNode node = new RbtNode(key, value);
        //if the root is null, set the color of the node to black and make it a root node
        if (root == null) {
            node.setColor(RbtNode.Color.BLACK);
            root = node;
        } else {
            RbtNode current = root;
            RbtNode parent = root;

            while (current != null) {
                parent = current;
                int compareValue = comparator(node.getKey(), current.getKey());
                if (compareValue < 0) {
                    current = current.getLeft_child();
                } else if (compareValue > 0) {
                    current = current.getRight_child();
                } else {
                    return false;
                }
            }
            node.setParent(parent);
            if (comparator(node.getKey(), parent.getKey()) < 0) {
                parent.setLeft_child(node);
            } else {
                parent.setRight_child(node);
            }

            changeTreePostInsert(node);
        }
        return true;
    }

    /* *****************************************************************
    This method fixes the Tree, to satisfy the Red Black Tree property
    Parameters: RbtNode node
    Return Value: void
     ***************************************************************** */
    private void changeTreePostInsert(RbtNode node) {
        RbtNode parent = node.getParent();

        if (node.getColor() == RbtNode.Color.RED && parent.getColor() == RbtNode.Color.RED) {
            RbtNode grandParent = node.getParent().getParent();
            RbtNode.Color gpChildColor;


            if (grandParent.getLeft_child() == parent) {
                gpChildColor = grandParent.getRight_child() == null ? RbtNode.Color.BLACK : grandParent.getRight_child().getColor();
            } else {
                gpChildColor = grandParent.getLeft_child() == null ? RbtNode.Color.BLACK : grandParent.getLeft_child().getColor();
            }

            if (gpChildColor == RbtNode.Color.RED) {
                grandParent.getLeft_child().setColor(RbtNode.Color.BLACK);
                if (grandParent.getRight_child() != null) grandParent.getRight_child().setColor(RbtNode.Color.BLACK);
                if (grandParent != root) {
                    grandParent.setColor(RbtNode.Color.RED);
                    changeTreePostInsert(grandParent);
                }
            } else {
                if (grandParent.getLeft_child() == parent) {
                    if (parent.getLeft_child() == node) {
                        LLShiftAtPivot(parent);
                    } else if (parent.getRight_child() == node) {
                        RRShiftAtPivot(parent.getRight_child());
                        LLShiftAtPivot(grandParent.getLeft_child());
                    }
                } else if (grandParent.getRight_child() == parent) {
                    if (parent.getRight_child() == node) {
                        RRShiftAtPivot(parent);
                    } else if (parent.getLeft_child() == node) {
                        LLShiftAtPivot(parent.getLeft_child());
                        RRShiftAtPivot(grandParent.getRight_child());
                    }
                }
            }
        }
    }

    /* ****************************************************************
    LL Rotation at Node "node"
    Parameters: RbtNode node
    Returns: void
    **************************************************************** */
    private void LLShiftAtPivot(RbtNode node) {

        RbtNode nodeRight = node.getRight_child();
        RbtNode nodeParent = node.getParent();


        node.setRight_child(nodeParent);
        node.setParent(nodeParent.getParent());
        if (node.getParent() == null) {
            root = node;
        } else {
            if (node.getParent().getLeft_child() == nodeParent) {
                node.getParent().setLeft_child(node);
            } else {
                node.getParent().setRight_child(node);
            }
        }
        node.getRight_child().setParent(node);
        node.getRight_child().setLeft_child(nodeRight);
        if (nodeRight != null) nodeRight.setParent(node.getRight_child());

        RbtNode.Color temp = node.getColor();
        node.setColor(nodeParent.getColor());
        nodeParent.setColor(temp);
    }

    /* ****************************************************************
    RR Rotation at Node "node"
    Parameters: RbtNode node
    Returns: void
    **************************************************************** */
    private void RRShiftAtPivot(RbtNode node) {


        RbtNode nodeLeft = node.getLeft_child();
        RbtNode nodeParent = node.getParent();


        node.setLeft_child(nodeParent);
        node.setParent(nodeParent.getParent());
        if (node.getParent() == null) {
            root = node;
        } else {
            if (node.getParent().getLeft_child() == nodeParent) {
                node.getParent().setLeft_child(node);
            } else {
                node.getParent().setRight_child(node);
            }
        }
        node.getLeft_child().setParent(node);
        node.getLeft_child().setRight_child(nodeLeft);
        if (nodeLeft != null) nodeLeft.setParent(node.getLeft_child());

        RbtNode.Color temp = node.getColor();
        node.setColor(nodeParent.getColor());
        nodeParent.setColor(temp);
    }

    /* ****************************************************************
    delete node with buildingNum = element
    Parameters: RbtNode node
    Returns: void
    ******************************************************************/
    boolean deleteElementAtPivot(int element) {

        RbtNode nodeToBeDeleted = searchElement(root, element);
        if (nodeToBeDeleted == null)
            return false;

        int degreeOfNodeToBeDeleted = degreeOfNode(nodeToBeDeleted);
        /*If degree of the node is two, then find the inorder successor of the node*/
        if (degreeOfNodeToBeDeleted == 2) {
            RbtNode inOrderSuccessor = searchInOrderSuccessor(nodeToBeDeleted);
            nodeToBeDeleted.setKey(inOrderSuccessor.getKey());
            nodeToBeDeleted.setValue(inOrderSuccessor.getValue());
            ;
            nodeToBeDeleted = inOrderSuccessor;
        }

        RbtNode childOfDeletedNode = nodeToBeDeleted.getLeft_child() == null ? nodeToBeDeleted.getRight_child() : nodeToBeDeleted.getLeft_child();
        RbtNode parentOfDeletedNode = nodeToBeDeleted.getParent();
        if (parentOfDeletedNode == null) {
            if (childOfDeletedNode == null) {
                root = null;
                return true;
            }
            childOfDeletedNode.setColor(RbtNode.Color.BLACK);
            childOfDeletedNode.setParent(null);
            root = childOfDeletedNode;
            return true;
        }
        if (parentOfDeletedNode.getRight_child() == nodeToBeDeleted) {
            parentOfDeletedNode.setRight_child(childOfDeletedNode);
        } else {
            parentOfDeletedNode.setLeft_child(childOfDeletedNode);
        }
        if (childOfDeletedNode != null) childOfDeletedNode.setParent(parentOfDeletedNode);

        if (isRedColorNode(nodeToBeDeleted) || isRedColorNode(childOfDeletedNode)) {
            if (childOfDeletedNode != null) childOfDeletedNode.setColor(RbtNode.Color.BLACK);
        } else {
            balanceTreeMethod(childOfDeletedNode, parentOfDeletedNode);
        }
        return true;
    }

    /* ****************************************************************
    This method rebalances the Tree after deletion of the node
    Parameters: RbtNode doubleBlackNode, RbtNode doubleBlackNodeParent
    Returns: void
    *****************************************************************/
    private void balanceTreeMethod(RbtNode doubleBlackNode, RbtNode doubleBlackNodeParent) {

        /*1st Case */
        if (doubleBlackNode == root) {

            return;
        }

        RbtNode doubleBlackNodeSibling = doubleBlackNodeParent.getLeft_child() == doubleBlackNode ? doubleBlackNodeParent.getRight_child() : doubleBlackNodeParent.getLeft_child();
        RbtNode doubleBlackNodeSiblingLeft = doubleBlackNodeSibling != null ? doubleBlackNodeSibling.getLeft_child() : null;
        RbtNode doubleBlackNodeSiblingRight = doubleBlackNodeSibling != null ? doubleBlackNodeSibling.getRight_child() : null;


        /*2nd Case*/
        if (isRedColorNode(doubleBlackNodeSibling)) {
            if (doubleBlackNodeParent.getLeft_child() == doubleBlackNode) {
                /*Apply RR rotation*/
                RRShiftAtPivot(doubleBlackNodeSibling);
            } else if (doubleBlackNodeParent.getRight_child() == doubleBlackNode) {
                /*Apply LL rotation*/
                LLShiftAtPivot(doubleBlackNodeSibling);
            }
            doubleBlackNodeSibling.setColor(RbtNode.Color.BLACK);
            doubleBlackNodeParent.setColor(RbtNode.Color.RED);
            balanceTreeMethod(doubleBlackNodeSibling, doubleBlackNodeSibling.getParent());
            return;
        }

        /*3rd Case*/
        if (isBlackColorNode(doubleBlackNodeParent) && isBlackColorNode(doubleBlackNodeSibling) &&
                isBlackColorNode(doubleBlackNodeSiblingLeft) && isBlackColorNode(doubleBlackNodeSiblingRight)) {
            if (doubleBlackNodeSibling != null) {
                doubleBlackNodeSibling.setColor(RbtNode.Color.RED);
            }
            balanceTreeMethod(doubleBlackNodeParent, doubleBlackNodeParent.getParent());
            return;
        }

        /*4th Case*/
        if (isRedColorNode(doubleBlackNodeParent) && isBlackColorNode(doubleBlackNodeSibling) &&
                isBlackColorNode(doubleBlackNodeSiblingLeft) && isBlackColorNode(doubleBlackNodeSiblingRight)) {
            if (doubleBlackNodeSibling != null) {
                doubleBlackNodeSibling.setColor(RbtNode.Color.RED);
            }

            doubleBlackNodeParent.setColor(RbtNode.Color.BLACK);
            return;
        }

        /*5th Case*/
        if (isBlackColorNode(doubleBlackNodeParent)) {
            if (doubleBlackNodeParent.getLeft_child() == doubleBlackNode && isRedColorNode(doubleBlackNodeSiblingLeft) && isBlackColorNode(doubleBlackNodeSiblingRight)) {
                /*Apply LL rotation*/
                LLShiftAtPivot(doubleBlackNodeSiblingLeft);
                balanceTreeMethod(doubleBlackNode, doubleBlackNodeParent);
                return;
            } else if (doubleBlackNodeParent.getRight_child() == doubleBlackNode && isRedColorNode(doubleBlackNodeSiblingRight) && isBlackColorNode(doubleBlackNodeSiblingLeft)) {
                /*Apply RR Rotation*/
                RRShiftAtPivot(doubleBlackNodeSiblingRight);
                balanceTreeMethod(doubleBlackNode, doubleBlackNodeParent);
                return;
            }
        }

        /*6th Case*/
        if (doubleBlackNodeParent.getLeft_child() == doubleBlackNode && isBlackColorNode(doubleBlackNodeSibling) && isRedColorNode(doubleBlackNodeSiblingRight)) {
            /*Apply RR rotation*/
            RRShiftAtPivot(doubleBlackNodeSibling);
            doubleBlackNodeSiblingRight.setColor(RbtNode.Color.BLACK);
        } else if (doubleBlackNodeParent.getRight_child() == doubleBlackNode && isBlackColorNode(doubleBlackNodeSibling) && isRedColorNode(doubleBlackNodeSiblingLeft)) {
            /*Apply LL rotation*/
            LLShiftAtPivot(doubleBlackNodeSibling);
            doubleBlackNodeSiblingLeft.setColor(RbtNode.Color.BLACK);
        }

    }

    /* ****************************************************************
    Searches for the node with the buildingNum  = key
    Parameters: int key
    Returns: BuildingStructure
    **************************************************************** */
    BuildingStructure searchElement(int key) {
        RbtNode element = searchElement(root, key);
        return element == null ? null : element.getValue();
    }

    /* ****************************************************************
    Creates a list and passes it to the ElementsBetweenRange(RbtNode  head, int key1, int key2, List<BuildingStructure> elements) method
    Parameters: int key1, int key2
    Returns: List<BuildingStructure>
    */
    List<BuildingStructure> getElementsBetweenRange(int key1, int key2) {
        List<BuildingStructure> elements = new ArrayList<>();
        getElementsBetweenRange(root, key1, key2, elements);
        return elements;
    }

    /* ****************************************************************
    prints all the elements between the range
    Parameters: RbtNode head, int key1, int key2, List<BuildingStructure> elements
    Returns: void
    *****************************************************************/
    private void getElementsBetweenRange(RbtNode head, int key1, int key2, List<BuildingStructure> elements) {
        if (head == null) {
            return;
        }
        if (comparator(head.getKey(), key1) >= 0 && comparator(head.getKey(), key2) <= 0) {
            getElementsBetweenRange(head.getLeft_child(), key1, key2, elements);
            elements.add(head.getValue());
            getElementsBetweenRange(head.getRight_child(), key1, key2, elements);
            return;
        }
        if (comparator(head.getKey(), key1) <= 0) {
            getElementsBetweenRange(head.getRight_child(), key1, key2, elements);
            return;
        }
        if (comparator(head.getKey(), key2) >= 0) {
            getElementsBetweenRange(head.getLeft_child(), key1, key2, elements);
        }
    }

    /* ****************************************************************
    Returns the element with buildingNum = element
    Parameters: RbtNode head, int element
    Returns: RbtNode
    **************************************************************** */
    private RbtNode searchElement(RbtNode head, int element) {
        if (head == null) {
            return null;
        }
        int diff = comparator(head.getKey(), element);
        if (diff == 0) {
            return head;
        } else if (diff < 0) {
            return searchElement(head.getRight_child(), element);
        } else {
            return searchElement(head.getLeft_child(), element);
        }
    }

    /* ****************************************************************
    Returns the degree of the node
    Parameters: RbtNode node
    Returns: int
    **************************************************************** */
    private int degreeOfNode(RbtNode node) {
        int degree = 0;
        if (node.getRight_child() != null) {
            degree++;
        }
        if (node.getLeft_child() != null) {
            degree++;
        }
        return degree;
    }

    /* ****************************************************************
    searches for inorder successor
    Parameters: RbtNode
    Returns: RbtNode
    **************************************************************** */
    private RbtNode searchInOrderSuccessor(RbtNode node) {
        node = node.getRight_child();
        RbtNode parent = node;
        while (node != null) {
            parent = node;
            node = node.getLeft_child();
        }
        return parent;
    }


}



