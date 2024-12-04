// MY ASSUMPTIONS //
// SYSTEM AUTOMATICALLY STOPS - IF A VENDOR OR CUSTOMER WAITS MORE THAN 5 SECONDS
// SYSTEM MANUALLY STOPS - BY PRESSING ENTER
// IF NEW CONFIGURATION USED TO RUN THE SYSTEM, IT WILL BE SAVED INTO CINFIG.JSON FILE
// IF THE DEFAULT CONFIGURATION IS USED, IT WON't SAVE IT TO CONFIG FILE AS IT IS ALREADY AVAILABLE IN THE SYSTEM
// MINIMUM VALUE FOR TOTAL TICKETS TO BUY AND TOTAL TICKETS TO RELEASE PER PERSON IS 1
// MAXIMUM IS THE TICKET POOL CAPACITY
// ALL THE CUSTOMERS HAVE A COMMON TICKET RETRIEVAL RATE AND TOTAL NUMBER OF TICKETS TO BUY
// ALL VENDORS HAVE A COMMON TICKET RELEASE RATE AND TOTAL NUMBER OF TICKETS TO RELEASE

import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class TicketSystem {
    private static volatile boolean isRunning = true; // Flag to control thread termination

    public static void main(String[] args) {
        System.out.println("\n****** Welcome to Ticket System! *******");
        //Load configuration
        Configuration config = Configuration.loadConfiguration();
        Scanner input = new Scanner(System.in);
        boolean choiceFlag = true; // flag to check status

        // configure system
        while (choiceFlag) {
            System.out.println("____________ Configuration Settings ______________");
            System.out.println("1. Start \n2. Stop");
            System.out.print("\nSelect an option to start or end the application: ");

            String startInput = input.nextLine();

            switch (startInput) {
                case "1":
                    // Ask to run with default config
                    System.out.print("Do you want to configure the system? (yes/no): ");
                    String choice = input.nextLine().trim().toLowerCase();

                    if (choice.equals("yes")) {
                        config.configureParameters();   // Start new Configuration
                        config.saveConfiguration();
                        System.out.println("\nNew configuration saved successfully!\n");
                    }
                    else {
                        System.out.println("\nLoading default configuration...");
                        System.out.println(config);
                    }
                    choiceFlag = false;

                    // Initialize ticket pool with to start with default configuration
                    TicketPool ticketPool = new TicketPool(config.getMaxTicketCapacity(), config.getTotalTickets());
                    int totalThreads = config.getTotalVendors() + config.getTotalCustomers();
                    CountDownLatch latch = new CountDownLatch(totalThreads);

                    // Initialize Arrays to manage threads for vendors and customers
                    Vendor[] vendors = new Vendor[config.getTotalVendors()];
                    Customer[] customers = new Customer[config.getTotalCustomers()];

                    // Create and start threads to run the application
                    Thread[] vendorThreads = new Thread[vendors.length];
                    for (int count = 0; count<vendors.length; count++) {
                        vendors[count] = new Vendor("Vendor-"+(count+1), "password","V"+(count+1),ticketPool,config.getTicketsPerRelease(), config.getTicketReleaseRate(),latch);
                        vendorThreads[count] = new Thread(vendors[count], "Vendor-" + (count + 1));
                        vendorThreads[count].start();
                    }

                    Thread[] customerThreads = new Thread[customers.length];
                    for (int count = 0; count < customers.length; count++) {
                        customers[count] = new Customer("Customer-" + (count + 1), "password", "C" + (count + 1), ticketPool, config.getTicketsPerPurchase(), config.getCustomerRetrievalRate(),latch);
                        customerThreads[count] = new Thread(customers[count], "Customer-" + (count + 1));
                        customerThreads[count].start();
                    }

                    // Allow manual exit before auto shut down
                    Thread stopper = new Thread(() -> {
                        System.out.println("System is running. Press 'Enter' to stop...");
                        input.nextLine();
                        stopSystem();  // set isRunning to false
                        for (Thread thread : vendorThreads) thread.interrupt();
                        for (Thread thread : customerThreads) thread.interrupt();
                        System.out.println("System is shutting down manually...");
                    });
                    stopper.start();

                    try {
                        latch.await(); // Wait for all threads to finish
                        stopSystem();
                        System.out.println("All operations completed. System shutting down automatically...");
                        System.exit(0);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
//                    if (isRunning() && !Thread.currentThread().isInterrupted()) {
//                        try {
//                            latch.await(); // Wait for all threads to finish
//                            stopSystem();
//                            System.out.println("All operations completed. System shutting down automatically...");
//                            System.exit(0);
//                        } catch (InterruptedException e) {
//                            Thread.currentThread().interrupt();
//                        }
//                    }

//                    // Wait for user input to stop
//                    System.out.println("System is running. Press 'Enter' to stop...");
//                    System.out.println();
//                    input.nextLine();
//                    stopSystem(); // Manually stop the system via user input
//
//                    // Stop all threads
//                    for (Thread thread : vendorThreads) {
//                        thread.interrupt();
//                    }
//                    for (Thread thread : customerThreads) {
//                        thread.interrupt();
//                    }
//
//                    System.out.println("System stopped.");
                    break;

                case "2":
                    choiceFlag = false;
                    stopSystem();
                    System.out.println("Exiting from the application...");
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
//        System.out.println("System is shutting down...");
        Thread.currentThread().interrupt();
    }
}