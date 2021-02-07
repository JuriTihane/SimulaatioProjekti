package simu.model;

public class Bus {

    private int capacity;
    private int passengers;
    private double arrivalTime;
    private double departureTime;
    private static int totalAmountOfBusses = 0;


    public Bus (int capacity){
        this.capacity = capacity;
        newBus();
    }

    public static void newBus(){
        totalAmountOfBusses++;
    }
}
