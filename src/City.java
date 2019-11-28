
import java.util.LinkedList;
import java.util.List;

/*This is the driver function, which receives the input,
stores the tasks in the LinkedList and calls the method taskExecutor of class ‘City’.*/
public class City {

    private MinHeap min_heap = new MinHeap();
    private RedBlackTreeStructure red_black = new RedBlackTreeStructure();
    private LinkedList<PlanStructure> object_list;
    private BuildingStructure current_building;
    private int global_counter;
    private int current_building_executed_time;

    public City(LinkedList<PlanStructure> object_list) {
        this.object_list = object_list;
        this.global_counter = -1;
        this.current_building_executed_time = 0;
        this.current_building = null;
    }

    private boolean isComplete(BuildingStructure b) {
        if (b.getExecuted_time() == b.getTotal_time()) {
            return true;
        } else
            return false;
    }

    public MinHeap getMin_heap() {
        return min_heap;
    }

    public void setMin_heap(MinHeap min_heap) {
        this.min_heap = min_heap;
    }

    public RedBlackTreeStructure getRed_black() {
        return red_black;
    }


    public void setRed_black(RedBlackTreeStructure red_black) {
        this.red_black = red_black;
    }

    public LinkedList<PlanStructure> getObject_list() {
        return object_list;
    }

    public void setObject_list(LinkedList<PlanStructure> object_list) {
        this.object_list = object_list;
    }

    public BuildingStructure getCurrent_building() {
        return current_building;
    }

    public void setCurrent_building(BuildingStructure current_building) {
        this.current_building = current_building;
    }

    public int getGlobal_counter() {
        return global_counter;
    }

    public void setGlobal_counter(int global_counter) {
        this.global_counter = global_counter;
    }

    public void insertData() {
        if (object_list.size() > 0) {
            if (object_list.peekFirst().getGlobal_time() == global_counter) {

                if (object_list.peekFirst().getPlan_type() == PlanStructure.PlanType.INSERT) {
                    InsertPlanStructure insert_plan_object = (InsertPlanStructure) object_list.pollFirst();
                    BuildingStructure build = insert_plan_object.getBuilding_data();
                    min_heap.insert(build);
                    RbtNode rb = new RbtNode(build.getBuildingNum(), build);
                    red_black.addElementToRbTree(build.getBuildingNum(), build);
                }
            }
        }
    }


    public void printData() {
        if (object_list.size() > 0) {
            if (object_list.peekFirst().getGlobal_time() == global_counter) {
                if (object_list.peekFirst().getPlan_type() == PlanStructure.PlanType.PRINT) {
                    PrintPlanStructure print_plan_object = (PrintPlanStructure) object_list.pollFirst();
                    BuildingStructure building = red_black.searchElement(print_plan_object.getBuildingNum1());
                    if (building == null) {
                        System.out.println("(0,0,0)");
                    } else {
                        System.out.println("(" + building.getBuildingNum() + "," + building.getExecuted_time() + "," + building.getTotal_time() + ")");
                    }
                } else if (object_list.peekFirst().getPlan_type() == PlanStructure.PlanType.PRINT_RANGE) {
                    PrintRangePlanStructure print_range_object = (PrintRangePlanStructure) object_list.pollFirst();
                    int buildingNumber1 = print_range_object.getBuildingNum1();
                    int buildingNumber2 = print_range_object.getBuildingNum2();

                    List<BuildingStructure> buildings = red_black.getElementsBetweenRange(buildingNumber1, buildingNumber2);
                    if (buildings.size() > 0) {
                        for (int i = 0; i < buildings.size(); i++) {
                            BuildingStructure building = buildings.get(i);
                            System.out.print("(" + building.getBuildingNum() + "," + building.getExecuted_time() + "," + building.getTotal_time() + ")");
                            if (i < buildings.size() - 1) {
                                System.out.print(",");
                            }
                        }
                        System.out.println();
                    } else {
                        System.out.println("(0,0,0)");
                    }
                }
            }
        }
    }

    public void taskExecutor() {
        global_counter += 1;
        insertData();
        printData();
        if (current_building != null && current_building.getExecuted_time() == current_building.getTotal_time()) {
            System.out.println("(" + current_building.getBuildingNum() + "," + (global_counter) + ")");
            RbtNode rb = new RbtNode(current_building.getBuildingNum(), current_building);
            red_black.deleteElementAtPivot(current_building.getBuildingNum());
            current_building = null;
        }
        if (current_building != null && current_building_executed_time == 5) {
            current_building_executed_time = 0;
            min_heap.insert(current_building);
            current_building = min_heap.removeMin();

        }

        if (current_building == null) {
            current_building_executed_time = 0;
            current_building = min_heap.removeMin();

        }
        if (current_building != null) {
            current_building_executed_time++;
            current_building.setExecuted_time(current_building.getExecuted_time() + 1);
        }
    }

    public boolean isComplete() {

        if (min_heap.isComplete() && red_black.root == null && object_list.size() == 0 && current_building == null) {
            return true;
        } else
            return false;
    }


}
