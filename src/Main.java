import com.ua.javaRush.islandSimulation.service.SimulationService;

public class Main {

    public static void main(String[] args) {
        SimulationService ss = new SimulationService();
        ss.printer.print("START");

        if (args.length == 0) {
            for (int i = 1; i < 11; i++) {
                ss.tick(i);
            }
        } else {
            for (int i = 1; i < Integer.parseInt(args[0]) + 1; i++) {
                ss.tick(i);
            }
        }
    }
}