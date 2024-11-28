import java.io.IOException;
import java.util.Scanner;

public class TicketSystem {
    public static void main(String[] args) {
        //Load configuration or start with the default configuration
        System.out.println("\nLoading default configuration...");
        Configuration config = Configuration.loadConfiguration();

        boolean choice = true;

        while (choice) {
            // configure system
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
                    choice = false;
                    break;
                case "2":
                    System.out.println("Exiting from the application... \nThank you! Have a nice day");
                    choice = false;
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option! Please enter 1 to start or 2 to stop.\n");
            }
        }
    }
}