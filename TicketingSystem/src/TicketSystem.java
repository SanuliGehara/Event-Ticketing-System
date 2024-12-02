import java.io.IOException;
import java.util.Scanner;

public class TicketSystem {
    private static volatile boolean isRunning = true; // Flag to control thread termination

    public static void main(String[] args) {
        //Load configuration
        Configuration config = Configuration.loadConfiguration();

        boolean choiceFlag = true; // flag to check status

        // configure system
        while (choiceFlag) {
            System.out.println("\n****** Welcome to Ticket System! *******");
            System.out.println("____________ Configuration Settings ______________");
            System.out.println("1. Start \n2.Stop");
            System.out.print("\nSelect an option to start or end the application: ");
            Scanner input = new Scanner(System.in);
            String startInput = input.nextLine();

            switch (startInput) {
                case "1":
                    // Ask to run with default config
                    System.out.print("Do you want to configure the system? (yes/no): ");
                    String choice = input.nextLine().trim().toLowerCase();

                    if (choice.equals("yes")) {
                        config.configureParameters();   // Start new Configuration
                        config.saveConfiguration();
                        System.out.println("New configuration saved successfully!");
                    }
                    else {
                        System.out.println("\nLoading default configuration...");
                        System.out.println(config);
                    }
                    choiceFlag = false;

                    // Initialize ticket pool with to start with default configuration
                    TicketPool ticketPool = new TicketPool(config.getMaxTicketCapacity(), config.getTotalTickets());

                    // Initialize Arrays to manage threads for vendors and customers
                    Vendor[] vendors = new Vendor[config.getTotalVendors()];
                    Customer[] customers = new Customer[config.getTotalCustomers()];

                    // Create and start threads to run the application
                    Thread[] vendorThreads = new Thread[vendors.length];
                    for (int count = 0; count<vendors.length; count++) {
                        vendors[count] = new Vendor("Vendor-"+(count+1), "password","V"+(count+1),ticketPool,config.getTicketsPerRelease(), config.getTicketReleaseRate());
                        vendorThreads[count] = new Thread(vendors[count], "Vendor-" + (count + 1));
                        vendorThreads[count].start();
                    }

                    Thread[] customerThreads = new Thread[customers.length];
                    for (int count = 0; count < customers.length; count++) {
                        customers[count] = new Customer("Customer-" + (count + 1), "password", "C" + (count + 1), ticketPool, config.getTicketsPerPurchase(), config.getCustomerRetrievalRate());
                        customerThreads[count] = new Thread(customers[count], "Customer-" + (count + 1));
                        customerThreads[count].start();
                    }

                    // Wait for user input to stop
                    System.out.println("\nSystem is running. Press 'Enter' to stop...\n");
                    input.nextLine();
                    stopSystem(); // Manually stop the system via user input

                    // Stop all threads
                    for (Thread thread : vendorThreads) {
                        thread.interrupt();
                    }
                    for (Thread thread : customerThreads) {
                        thread.interrupt();
                    }

                    System.out.println("System stopped.");
                    break;

                case "2":
                    System.out.println("Exiting from the application...");
                    choiceFlag = false;
                    stopSystem();
                    break;
                default:
                    System.out.println("Invalid option! Please enter 1 to start or 2 to stop.\n");
            }
        }
    }

    public static boolean isRunning() {
        return isRunning;
    }

    public static void stopSystem() {
        isRunning = false;
        System.out.println("System is shutting down...");
        System.exit(0);
    }
}