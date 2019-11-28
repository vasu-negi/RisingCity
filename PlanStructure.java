/*Plan Structure contains the attributes and constructors to create the node to store each instruction obtained
 from the input file. This is the parent class of InsertPlanStructure, PrintPlanStructure, and PrintRangeStructure. */

class PlanStructure {

    enum PlanType {
        INSERT, PRINT, PRINT_RANGE;
    }

    private int global_time;
    private PlanType plan_type;

    /*Constructor*/
    public PlanStructure(int global_time, PlanType plan_type) {
        this.global_time = global_time;
        this.plan_type = plan_type;

    }

    /*Getters and Setters */
    public int getGlobal_time() {
        return global_time;
    }

    public void setGlobal_time(int global_time) {
        this.global_time = global_time;
    }

    public PlanType getPlan_type() {
        return plan_type;
    }

    public void setPlan_type(PlanType plan_type) {
        this.plan_type = plan_type;
    }
}

/*Contains the attributes and constructors to implement the additional attributes particular to the INSERT instruction.
This class is the child class of the PlanStructure and hence inherits the attributes and functionality of the PlanStructure class.*/
class InsertPlanStructure extends PlanStructure {
    private BuildingStructure building_data;

    public InsertPlanStructure(int global_time, BuildingStructure building_data) {
        super(global_time, PlanType.INSERT);
        this.building_data = building_data;

    }

    public BuildingStructure getBuilding_data() {
        return building_data;
    }

    public void setBuilding_data(BuildingStructure building_data) {
        this.building_data = building_data;
    }
}

/*Contains the attributes and constructors to implement the additional attributes particular to the PRINT instruction.
This class is the child class of the PlanStructure and hence inherits the attributes and functionality of the PlanStructure class.*/
class PrintPlanStructure extends PlanStructure {

    private int buildingNum1 = Integer.MAX_VALUE;

    /*Constructor*/
    public PrintPlanStructure(int global_time, int buildingNum1) {
        super(global_time, PlanType.PRINT);
        this.buildingNum1 = buildingNum1;
    }

    /*Getters and Setters */
    public int getBuildingNum1() {
        return buildingNum1;
    }

    public void setBuildingNum1(int buildingNum1) {
        this.buildingNum1 = buildingNum1;
    }
}

/*Contains the attributes and constructors to implement the additional attributes particular to the PRINT_RANGE instruction.
 This class is the child class of the PlanStructure and hence inherits the attributes and functionality of the PlanStructure class.*/
class PrintRangePlanStructure extends PlanStructure {
    private int buildingNum1 = Integer.MAX_VALUE;
    private int buildingNum2 = Integer.MAX_VALUE;

    /*Constructor*/
    public PrintRangePlanStructure(int global_time, int buildingNum1, int buildingNum2) {
        super(global_time, PlanType.PRINT_RANGE);
        this.buildingNum1 = buildingNum1;
        this.buildingNum2 = buildingNum2;
    }

    public int getBuildingNum1() {
        return buildingNum1;
    }

    public void setBuildingNum1(int buildingNum1) {
        this.buildingNum1 = buildingNum1;
    }

    public int getBuildingNum2() {
        return buildingNum2;
    }

    public void setBuildingNum2(int buildingNum2) {
        this.buildingNum2 = buildingNum2;
    }
}