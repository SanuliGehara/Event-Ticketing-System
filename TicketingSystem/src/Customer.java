public class Customer extends User{
    private final String customerId;
    private TicketPool ticketPool;
    private int ticketsToBuy;
    private int retrievalInterval;  // Customer retrieval rate - frequency which ticket will be retrieved at a time

    public Customer(String username, String password, String customerId, TicketPool ticketPool, int ticketsToBuy, int retrievalInterval) {
        super(username, password, UserType.CUSTOMER);
        this.customerId = customerId;
        this.ticketPool = ticketPool;
        this.ticketsToBuy = ticketsToBuy;
        this.retrievalInterval = retrievalInterval;
    }

    @Override
    public void run() {

    }

    public String getCustomerId() {
        return customerId;
    }

    public TicketPool getTicketPool() {
        return ticketPool;
    }

    public void setTicketPool(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
    }

    public int getTicketsToBuy() {
        return ticketsToBuy;
    }

    public void setTicketsToBuy(int ticketsToBuy) {
        this.ticketsToBuy = ticketsToBuy;
    }

    public int getRetrievalInterval() {
        return retrievalInterval;
    }

    public void setRetrievalInterval(int retrievalInterval) {
        this.retrievalInterval = retrievalInterval;
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
