/*Contains the attributes and constructors to create a node of building.
Each building node comprises of the following three attributes: buildingNum,
executed_time, and total_time*/

public class BuildingStructure {
    private int buildingNum;
    private int executed_time;
    private int total_time;

    /*Constructor*/
    public BuildingStructure(int buildingNum, int executed_time, int total_time) {
        this.buildingNum = buildingNum;
        this.executed_time = executed_time;
        this.total_time = total_time;
    }

    /*Setters and Getters*/
    public int getBuildingNum() {
        return buildingNum;
    }

    public void setBuildingNum(int buildingNum) {
        this.buildingNum = buildingNum;
    }

    public int getExecuted_time() {
        return executed_time;
    }

    public void setExecuted_time(int executed_time) {
        this.executed_time = executed_time;
    }

    public int getTotal_time() {
        return total_time;
    }

    public void setTotal_time(int total_time) {
        this.total_time = total_time;
    }

    /*prints the building data*/
    public String printBuilding() {
        return ("Building Number: " + this.buildingNum + "\tExecuted Time: " + this.executed_time + "\tTotal Time:" + this.total_time);
    }
}
