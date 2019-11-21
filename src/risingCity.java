import java.io.*;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class risingCity {
    public static void main(String[] args) throws IOException {

        String file = args[0];
        BufferedReader br = new BufferedReader(new FileReader(file));
        int time;
        int building_number1 =Integer.MIN_VALUE ;
        int building_number2 =Integer.MIN_VALUE;
        int total_time;
        String st;
        PrintStream o = new PrintStream(new File("output_file.txt"));
        PrintStream console = System.out;
        System.setOut(o);
        LinkedList<Plan> plans = new LinkedList<Plan>();

        int index =0;
        while ((st = br.readLine()) != null) {

            if(st.contains("Insert")) {
                Pattern p = Pattern.compile("\\d+");
                Matcher m = p.matcher(st);
                try{
                    m.find();
                    time = Integer.parseInt(m.group());
                    m.find();
                    building_number1 = Integer.parseInt(m.group());
                    m.find();
                    total_time = Integer.parseInt(m.group());
                   // System.out.println(time+" " + building_number1 + " " + total_time);
                    BuildingStructure build = new BuildingStructure(building_number1,0,total_time);

                    InsertPlan insert_plan = new InsertPlan(time,build);
                    plans.addLast(insert_plan);
                }
                catch(Exception e){
                    System.out.println("error");
                }

            }
            /*Print without range*/
            else if (st.contains("Print") && !st.contains(",")) {
                Pattern p = Pattern.compile("\\d+");
                Matcher m = p.matcher(st);
                try{
                    m.find();
                    time = Integer.parseInt(m.group());
                    m.find();
                    building_number1 = Integer.parseInt(m.group());
                    PrintPlan print_plan = new PrintPlan(time,building_number1);
                    plans.addLast(print_plan);

                }
                catch(Exception e){
                    System.out.println("error");
                }
                /*Print with Range*/
            }
            else if (st.contains("Print") && st.contains(",")) {
                Pattern p = Pattern.compile("\\d+");
                Matcher m = p.matcher(st);
                try{
                    m.find();
                    time = Integer.parseInt(m.group());
                    m.find();
                    building_number1 = Integer.parseInt(m.group());
                    m.find();
                    building_number2 = Integer.parseInt(m.group());
                    PrintRangePlan print_range_plan = new PrintRangePlan(time,building_number1,building_number2);
                    plans.addLast(print_range_plan);
                }
                catch(Exception e){
                    System.out.println("error");
                }

            }

        }

        City build_city = new City(plans);
        while(!build_city.isComplete()){
            build_city.taskExecutor();
        }

    }
}
