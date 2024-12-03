import java.util.concurrent.CountDownLatch;

public class Customer extends User {
    private final String customerId;
    private final TicketPool ticketPool;
    private final int ticketsToBuy;
    private final int retrievalInterval;
    private final CountDownLatch latch;

    public Customer(String username, String password, String customerId, TicketPool ticketPool, int ticketsToBuy, int retrievalInterval, CountDownLatch latch) {
        super(username, password, UserType.CUSTOMER);
        this.customerId = customerId;
        this.ticketPool = ticketPool;
        this.ticketsToBuy = ticketsToBuy;
        this.retrievalInterval = retrievalInterval;
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            for (int count = 1; count <= ticketsToBuy; count++) {
                if (!TicketSystem.isRunning()) return;
                Ticket ticket = ticketPool.buyTicket();
                if (ticket == null) return;
                Thread.sleep(retrievalInterval);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            latch.countDown(); // Signal that this thread has completed
        }
        System.out.println(Thread.currentThread().getName() + " finished purchasing tickets.");
    }

//    @Override
//    public void run() {
//        for (int count=1; count <= ticketsToBuy; count++) {
//            // Check if thread is interrupted or system is stopped
//            if (Thread.currentThread().isInterrupted() || !TicketSystem.isRunning()) {
//                break;
//            }
//            Ticket ticket = ticketPool.buyTicket();
//            // Check for ticket availability
//            if (ticket == null) {
//                System.out.println(this.getUsername()+": Transaction denied. No tickets available!");
//                break;
//            }
//
//            // Wait before retrieving next ticket
//            try {
//                Thread.sleep(retrievalInterval);
//            }
//            catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//                System.out.println(this.getUsername()+" got interrupted");
//                return;
//            }
//        }
//        System.out.println(Thread.currentThread().getName() + " finished purchasing tickets.");
//    }

    public String getCustomerId() {
        return customerId;
    }

    public TicketPool getTicketPool() {
        return ticketPool;
    }

    public int getTicketsToBuy() {
        return ticketsToBuy;
    }

    public int getRetrievalInterval() {
        return retrievalInterval;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId='" + customerId + '\'' +
                ", ticketPool=" + ticketPool +
                ", ticketsToBuy=" + ticketsToBuy +
                ", retrievalInterval=" + retrievalInterval +
                '}';
    }
}
