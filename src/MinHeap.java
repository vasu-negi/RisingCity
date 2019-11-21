public class MinHeap {

    private BuildingStructure[] min_heap;
    public int size;
    private int heap_size;

    //constructor -- Initializes the Min Heap
    public MinHeap() {
        this.heap_size = 2000;
        this.size = 0;
        min_heap = new BuildingStructure[this.heap_size + 1];
        min_heap[0] = new BuildingStructure(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
    }


    // Function to swap two elements of the heap
    private void exchangeElements(int current_node, int parent_node) {
        BuildingStructure temp;
        temp = min_heap[current_node];
        min_heap[current_node] = min_heap[parent_node];
        min_heap[parent_node] = temp;
    }
    private boolean compareEle(int index1, int index2){
        if(min_heap[index1].getExecuted_time() < min_heap[index2].getExecuted_time() ||
                (min_heap[index1].getExecuted_time() == min_heap[index2].getExecuted_time() &&
                        min_heap[index1].getBuildingNum() < min_heap[index2].getBuildingNum())){
            return true;//index1 is less than index2
        }
        else{
            return false;//index2 is less than index1
        }

    }
    // Function to heapify at index
 /*   private void minHeapify(int index) {

        if (index <= size / 2 && index>=1) {
            int smallest = Integer.MIN_VALUE;
            int l = 2 * index;
            int r = 2*index + 1;
            if (l <= heap_size && (min_heap[2 * index].getExecuted_time() < min_heap[index].getExecuted_time())) {
                smallest = l;
            } else {
                smallest = index;
            }
            if (r <= heap_size && (min_heap[r].getExecuted_time() < min_heap[smallest].getExecuted_time())) {
                smallest = r;
            }
            if (smallest != index) {
                exchangeElements(index, smallest);
                minHeapify(smallest);
            }
        }
    }*/
    private void minHeapify(int index) {

        if (index <= size / 2 && index>=1) {
            int smallest = Integer.MIN_VALUE;
            int l = 2 * index;
            int r = 2*index + 1;
            if (l <= heap_size && compareEle(l, index)) {
                smallest = l;
            } else {
                smallest = index;
            }
            if (r <= heap_size && compareEle(r,smallest)) {
                smallest = r;
            }
            if (smallest != index) {
                exchangeElements(index, smallest);
                minHeapify(smallest);
            }
        }
    }
    // insert an element in the heap
    public void insert(BuildingStructure var) {

        if (size >= heap_size) {
            return;
        }
        size = size + 1;
        min_heap[size] = var;
        int index = size;

        while (min_heap[index].getExecuted_time() < min_heap[index/2].getExecuted_time() ||
                (min_heap[index/2].getExecuted_time() == min_heap[index].getExecuted_time() &&
                        min_heap[index].getBuildingNum() < min_heap[index/2].getBuildingNum())) {

            exchangeElements(index, index/2);
            index = index/2;
        }
    }

   // Printing the Heap
    public void print() {

        for (int i = 1; i <= size; i++) {
            System.out.println(min_heap[i].printBuilding());
            System.out.println();
        }

    }

    public void minHeap() {
        for (int i = (size / 2); i >= 1; i--) {
            minHeapify(i);
        }
    }

    // Function to remove minimum element
    public BuildingStructure removeMin() {
        if (size>1) {
            BuildingStructure mini = min_heap[1];
            min_heap[1] = min_heap[size];
            size -= 1;
            minHeapify(1);
            return mini;
        }
        else if (size == 1){
            BuildingStructure mini = min_heap[1];
            size -= 1;
            return mini;
        }
        else{
            return null;
        }
    }
public boolean isComplete(){
        if(size <= 1){
            return true;
        }
        else
            return false;
}

    public static void main(String[] arg) {

        MinHeap minHeap = new MinHeap();
        BuildingStructure b1 = new BuildingStructure(50, 1, 10);
        BuildingStructure b2 = new BuildingStructure(45, 2, 20);
        BuildingStructure b3 = new BuildingStructure(15, 2, 20);
        BuildingStructure b4 = new BuildingStructure(3, 3, 20);
        BuildingStructure b5 = new BuildingStructure(19, 1, 20);
        BuildingStructure b6 = new BuildingStructure(4, 9, 20);


        minHeap.insert(b1);
        minHeap.insert(b2);
        minHeap.insert(b3);
        minHeap.insert(b4);
        minHeap.insert(b5);
        minHeap.insert(b6);


        System.out.println("size "+minHeap.size);
        minHeap.print();
        System.out.println("The Min val is " + minHeap.removeMin().printBuilding());
        System.out.println("The Min val is " + minHeap.removeMin().printBuilding());
        System.out.println("The Min val is " + minHeap.removeMin().printBuilding());
        System.out.println("The Min val is " + minHeap.removeMin().printBuilding());
        System.out.println("The Min val is " + minHeap.removeMin().printBuilding());
        minHeap.print();
    }
}
