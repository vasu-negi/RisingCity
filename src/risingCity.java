import java.io.*;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* ******************************************************************************
Reads the input file, stores the data in a LinkedList and then calls a method taskExecutor of class City.
****************************************************************************** */

public class risingCity {

    public static void main(String[] args) throws IOException {


        String file = args[0];
        BufferedReader br = new BufferedReader(new FileReader(file));
        int time;
        int building_number1 = Integer.MIN_VALUE;
        int building_number2 = Integer.MIN_VALUE;
        int total_time;
        String st;
        PrintStream o = new PrintStream(new File("output_file.txt"));
        PrintStream console = System.out;
        System.setOut(o);
        LinkedList<PlanStructure> planStructures = new LinkedList<PlanStructure>();

        int index = 0;
        while ((st = br.readLine()) != null) {
            /* ******************************************************************************
            Print without Insert
            ****************************************************************************** */
            if (st.contains("Insert")) {
                Pattern p = Pattern.compile("\\d+");
                Matcher m = p.matcher(st);
                try {
                    m.find();
                    time = Integer.parseInt(m.group());
                    m.find();
                    building_number1 = Integer.parseInt(m.group());
                    m.find();
                    total_time = Integer.parseInt(m.group());
                    // System.out.println(time+" " + building_number1 + " " + total_time);
                    BuildingStructure build = new BuildingStructure(building_number1, 0, total_time);

                    InsertPlanStructure insert_plan = new InsertPlanStructure(time, build);
                    planStructures.addLast(insert_plan);
                } catch (Exception e) {
                    System.out.println("error");
                }

            }
            /* ******************************************************************************
            Print without range
            ****************************************************************************** */
            else if (st.contains("Print") && !st.contains(",")) {
                Pattern p = Pattern.compile("\\d+");
                Matcher m = p.matcher(st);
                try {
                    m.find();
                    time = Integer.parseInt(m.group());
                    m.find();
                    building_number1 = Integer.parseInt(m.group());
                    PrintPlanStructure print_plan = new PrintPlanStructure(time, building_number1);
                    planStructures.addLast(print_plan);

                } catch (Exception e) {
                    System.out.println("error");
                }
                /* ******************************************************************************
                Print with Range
                ****************************************************************************** */
            } else if (st.contains("Print") && st.contains(",")) {
                Pattern p = Pattern.compile("\\d+");
                Matcher m = p.matcher(st);
                try {
                    m.find();
                    time = Integer.parseInt(m.group());
                    m.find();
                    building_number1 = Integer.parseInt(m.group());
                    m.find();
                    building_number2 = Integer.parseInt(m.group());
                    PrintRangePlanStructure print_range_plan = new PrintRangePlanStructure(time, building_number1, building_number2);
                    planStructures.addLast(print_range_plan);
                } catch (Exception e) {
                    System.out.println("error");
                }

            }

        }
                /* ******************************************************************************
                Execute the taskExecutor method of the City class
                ****************************************************************************** */
        City build_city = new City(planStructures);
        while (!build_city.isComplete()) {
            build_city.taskExecutor();
        }

    }
}
