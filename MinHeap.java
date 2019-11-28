    /*This is the class that implements Min Heap functionality and stores the building data in order of executed_time.
    When we have duplicate executed_time in the Min Heap, then the node with the smaller building node is considered to be the smaller node to break the conflict.*/
public class MinHeap {

    private BuildingStructure[] min_heap;
    public int size;
    private int heap_size;

    /*constructor -- Initializes the Min Heap*/
    public MinHeap() {
        this.heap_size = 2000;
        this.size = 0;
        min_heap = new BuildingStructure[this.heap_size + 1];
        min_heap[0] = new BuildingStructure(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
    }


    /*  ****************************************************************
      Function to swap two elements of the heap
      Parameters: int current_node, int parent_node
      Return: void
      **************************************************************** */
    private void exchangeElements(int current_node, int parent_node) {
        BuildingStructure temp;
        temp = min_heap[current_node];
        min_heap[current_node] = min_heap[parent_node];
        min_heap[parent_node] = temp;
    }

    /* ****************************************************************
    Compare two elements at index1 and index2
    Parameters: int index1, int index2
    Return: boolean
    **************************************************************** */
    private boolean compareEle(int index1, int index2) {
        if (min_heap[index1].getExecuted_time() < min_heap[index2].getExecuted_time() ||
                (min_heap[index1].getExecuted_time() == min_heap[index2].getExecuted_time() &&
                        min_heap[index1].getBuildingNum() < min_heap[index2].getBuildingNum())) {
            return true;//element at index1 is less than index2
        } else {
            return false;//element at index2 is less than index1
        }

    }

    /*Description: Applies the fundamental task min-heapify of the Min Heap at the node stored at index “index”. minHeapify(int index)
    assumes that the binary trees rooted at left child and right child of itself are min heaps. If the value of the parent (index) is greater than the children,
    then the method swaps the parent and the smallest child. The running time of minheapify is O(lg n).
    Parameters: int index
    Return: void
    */
    private void minHeapify(int index) {

        if (index <= size / 2 && index >= 1) {
            int smallest = Integer.MIN_VALUE;
            int l = 2 * index;
            int r = 2 * index + 1;
            if (l <= heap_size && compareEle(l, index)) {
                smallest = l;
            } else {
                smallest = index;
            }
            if (r <= heap_size && compareEle(r, smallest)) {
                smallest = r;
            }
            if (smallest != index) {
                exchangeElements(index, smallest);
                minHeapify(smallest);
            }
        }
    }

    /* ****************************************************************
    insert an element in the heap
    Parameters: BuildingStructure var
    Returns: void
    **************************************************************** */
    public void insert(BuildingStructure var) {

        if (size >= heap_size) {
            return;
        }
        size = size + 1;
        min_heap[size] = var;
        int index = size;

        while (min_heap[index].getExecuted_time() < min_heap[index / 2].getExecuted_time() ||
                (min_heap[index / 2].getExecuted_time() == min_heap[index].getExecuted_time() &&
                        min_heap[index].getBuildingNum() < min_heap[index / 2].getBuildingNum())) {

            exchangeElements(index, index / 2);
            index = index / 2;
        }
    }

    /*  ****************************************************************
    Printing the Heap
    Parameters: None
    Returns: void
    **************************************************************** */
    public void print() {

        for (int i = 1; i <= size; i++) {
            System.out.println(min_heap[i].printBuilding());
            System.out.println();
        }

    }

    /* ****************************************************************
    building a min heap
    Parameters: None
    Returns: void
    *****************************************************************/
    public void minHeap() {
        for (int i = (size / 2); i >= 1; i--) {
            minHeapify(i);
        }
    }
    /* ****************************************************************
    Function to remove minimum element
    Description: Removes the minimum element from the min-heap by first storing the last element of
    the minheap array at the root and then reducing the size of the min heap array. After this,
    we apply minHeapify at the root node to make sure the heap follows the min heap property. The running time of minheapify is O(lg n).
    Parameters: none
    Return Value: BuildingStructure
    **************************************************************** */

    public BuildingStructure removeMin() {
        if (size > 1) {
            BuildingStructure mini = min_heap[1];
            min_heap[1] = min_heap[size];
            size -= 1;
            minHeapify(1);
            return mini;
        } else if (size == 1) {
            BuildingStructure mini = min_heap[1];
            size -= 1;
            return mini;
        } else {
            return null;
        }
    }

    /* ****************************************************************
    returns true if minheap is empty
    Parameters: none
    Return: boolean
    **************************************************************** */
    public boolean isComplete() {
        if (size <= 1) {
            return true;
        } else
            return false;
    }


}
