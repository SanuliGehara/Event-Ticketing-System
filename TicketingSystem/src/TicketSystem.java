import java.io.IOException;
import java.util.Scanner;

public class TicketSystem {
    public static void main(String[] args) {
        //Load configuration or start with the default configuration
        System.out.println("\nLoading default configuration...");
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
                    // Start new Configuration
                    config.configureParameters();

                    config.saveConfiguration();
                    System.out.println("Configuration saved successfully.");
                    choiceFlag = false;

                    // Initialize ticket pool with to start with default configuration
                    TicketPool ticketPool = new TicketPool(config.getMaxTicketCapacity(), config.getTotalTickets());

                    // Initialize Arrays to manage threads for vendors and customers
                    Vendor[] vendors = new Vendor[config.getTotalVendors()];
                    Customer[] customers = new Customer[config.getTotalCustomers()];

                    // Create and start threads to run the application
                    for (int count = 0; count<vendors.length; count++) {
                        vendors[count] = new Vendor("Vendor-"+(count+1), "password","00V"+(count+1),ticketPool,2,config.getTicketReleaseRate());
                        Thread vendorThread = new Thread(vendors[count], "Vendor-"+(count+1));
                        vendorThread.start();
                    }

                    for (int count = 0; count < customers.length; count++) {
                        customers[count] = new Customer("Customer-" + (count + 1), "password", "10C" + (count + 1), ticketPool, 2, config.getCustomerRetrievalRate());
                        Thread customerThread = new Thread(customers[count], "Customer-" + (count + 1));
                        customerThread.start();
                    }
                    break;
                case "2":
                    System.out.println("Exiting from the application... \nThank you! Have a nice day");
                    choiceFlag = false;
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option! Please enter 1 to start or 2 to stop.\n");
            }
        }
    }
}