import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Configuration {
    private static final String CONFIG_FILE = "config.json";

    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maxTicketCapacity;
    private int totalVendors;
    private int totalCustomers;

    //Constructor
    public Configuration() {
        this.totalTickets = 50;
        this.ticketReleaseRate = 10;
        this.customerRetrievalRate = 5;
        this.maxTicketCapacity = 100;
        this.totalVendors = 10;
        this.totalCustomers = 5;
    }

    /**
     * Take input from user for configuration parameters
    * Set non-static fields with the prompt values
    * */
    public void configureParameters() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("_________________ System Configuration __________________");

        // Prompt for config parameters and validate
        this.totalTickets = validateInputs(scanner,"Enter total number of tickets: ",1,Integer.MAX_VALUE);
        this.ticketReleaseRate = validateInputs(scanner,"Enter Ticket Release Rate: ",1, Integer.MAX_VALUE);
        this.customerRetrievalRate = validateInputs(scanner,"Enter Customer Retrieval Rate: ",1,Integer.MAX_VALUE);
        this.maxTicketCapacity = validateInputs(scanner,"Enter maximum ticket capacity: ",1,Integer.MAX_VALUE);
        this.totalVendors = validateInputs(scanner,"Enter total number of vendors: ",1,Integer.MAX_VALUE);
        this.totalCustomers = validateInputs(scanner, "Enter total number of customers: ",1, Integer.MAX_VALUE);
    }

    /**
     *  Validate user inputs to check tje correct data type and range
     * @Param scanner, prompt, minimum acceptable value, maximum acceptable value
     * @return value
     */
    public int validateInputs(Scanner scanner, String prompt, int min, int max) {
        int value = 0;
        boolean valid = false;

        while(!valid) {
            try{
                System.out.print(prompt);
                value = Integer.parseInt(scanner.nextLine());   // Take input as a string and convert to int (Check correct input type)

                if (value < min || value > max) {  // Check if Input within range. Accepted
                    System.out.println("Invalid input. Value must be between "+ min + " and "  + max + ".");
                } else {
                    valid = true;
                }
            }
            catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid integer from 1");
            }
            catch (Exception e) {
                System.out.println("Wrong input. Try again");
            }
        }
        return value;
    }

    /**
     * Save configuration in to JSON file with serialization
    * */
    public void saveConfiguration() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            gson.toJson(this,writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load configuration from the JSON file
     * @return configuration
     */
    public static Configuration loadConfiguration() {
        Gson gson = new Gson();

        try {
            FileReader reader = new FileReader(CONFIG_FILE);
            Configuration config =  gson.fromJson(reader, Configuration.class);
            System.out.println(config);
            return config;
        }
        catch (IOException e) {
            System.out.println("No Configuration file found! Starting with default configuration");
            return new Configuration();
        }
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public void setTicketReleaseRate(int ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    public void setCustomerRetrievalRate(int customerRetrievalRate) {
        this.customerRetrievalRate = customerRetrievalRate;
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public void setMaxTicketCapacity(int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
    }

    public int getTotalVendors() {
        return totalVendors;
    }

    public void setTotalVendors(int totalVendors) {
        this.totalVendors = totalVendors;
    }

    public int getTotalCustomers() {
        return totalCustomers;
    }

    public void setTotalCustomers(int totalCustomers) {
        this.totalCustomers = totalCustomers;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "totalTickets=" + totalTickets +
                ", ticketReleaseRate=" + ticketReleaseRate +
                ", customerRetrievalRate=" + customerRetrievalRate +
                ", maxTicketCapacity=" + maxTicketCapacity +
                ", totalVendors=" + totalVendors +
                ", totalCustomers=" + totalCustomers +
                '}';
    }
}
