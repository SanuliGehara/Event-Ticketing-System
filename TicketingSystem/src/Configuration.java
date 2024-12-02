import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Configuration {
    private static final String CONFIG_FILE = "config.json";

    private int totalTickets;   // Total number of tickets which are already added to the ticket pool
    private int ticketReleaseRate;  // in milliseconds
    private int customerRetrievalRate; // in milliseconds
    private int maxTicketCapacity;
    private int totalVendors;
    private int totalCustomers;
    private int ticketsPerRelease; //total tickets which a vendor will release
    private int ticketsPerPurchase;

    //Constructor
    public Configuration() {
        this.totalTickets = 10;
        this.ticketReleaseRate = 1000; //  1 second
        this.customerRetrievalRate = 1000;
        this.maxTicketCapacity = 20;
        this.totalVendors = 5;
        this.totalCustomers = 5;
        this.ticketsPerRelease = 2;
        this.ticketsPerPurchase = 2;
    }

    /**
     * Take input from user for configuration parameters
    * Set non-static fields with the prompt values
    * */
    public void configureParameters() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("_________________ System Configuration __________________");

        // Prompt for config parameters and validate
        this.maxTicketCapacity = validateInputs(scanner,"Enter maximum ticket capacity: ",1,Integer.MAX_VALUE);
        this.totalTickets = validateInputs(scanner,"Enter total number of tickets in the ticket pool: ",0,maxTicketCapacity);
        this.ticketReleaseRate = validateInputs(scanner,"Enter Ticket Release Rate in Seconds(S): ",1, Integer.MAX_VALUE)*1000;
        this.customerRetrievalRate = validateInputs(scanner,"Enter Customer Retrieval in Second(S): ",1,Integer.MAX_VALUE)*1000;
        this.totalVendors = validateInputs(scanner,"Enter total number of vendors: ",1,Integer.MAX_VALUE);
        this.ticketsPerRelease = validateInputs(scanner,"Enter total tickets per release for a vendor: ",1,maxTicketCapacity);
        this.totalCustomers = validateInputs(scanner, "Enter total number of customers: ",1, Integer.MAX_VALUE);
        this.ticketsPerPurchase = validateInputs(scanner, "Enter total tickets per a purchase for a customer: ",1, maxTicketCapacity);
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
                value = Integer.parseInt(scanner.nextLine());   // Take as a string and convert to int (Check correct input type)

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
            return new Gson().fromJson(reader, Configuration.class);
        }
        catch (IOException e) {
            System.out.println("No Configuration file found! Starting with default configuration");
            return new Configuration();
        }
    }

    public int getTicketsPerPurchase() {
        return ticketsPerPurchase;
    }

    public void setTicketsPerPurchase(int ticketsPerPurchase) {
        this.ticketsPerPurchase = ticketsPerPurchase;
    }

    public int getTotalCustomers() {
        return totalCustomers;
    }

    public void setTotalCustomers(int totalCustomers) {
        this.totalCustomers = totalCustomers;
    }

    public int getTicketsPerRelease() {
        return ticketsPerRelease;
    }

    public void setTicketsPerRelease(int ticketsPerRelease) {
        this.ticketsPerRelease = ticketsPerRelease;
    }

    public int getTotalVendors() {
        return totalVendors;
    }

    public void setTotalVendors(int totalVendors) {
        this.totalVendors = totalVendors;
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public void setMaxTicketCapacity(int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
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

    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "totalTickets=" + totalTickets +
                ", ticketReleaseRate=" + ticketReleaseRate +
                " (ms), customerRetrievalRate=" + customerRetrievalRate +
                " (ms), maxTicketCapacity=" + maxTicketCapacity +
                ", totalVendors=" + totalVendors +
                ", totalCustomers=" + totalCustomers +
                ", ticketsPerRelease=" + ticketsPerRelease +
                ", ticketsPerPurchase=" + ticketsPerPurchase +
                '}';
    }
}
