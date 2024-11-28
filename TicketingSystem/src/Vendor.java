public class Vendor extends User{
    private final String vendorId;
    private TicketPool ticketPool;
    private int ticketsPerRelease;  // total tickets which will be released at a time
    private int releaseInterval;    // ticket release rate - frequency which ticket will be released at a time

    public Vendor(String username, String password, String vendorId, TicketPool ticketPool, int ticketsPerRelease, int releaseInterval) {
        super(username, password, UserType.VENDOR);
        this.vendorId = vendorId;
        this.ticketPool = ticketPool;
        this.ticketsPerRelease = ticketsPerRelease;
        this.releaseInterval = releaseInterval;
    }

    @Override
    public void run() {

    }

    public String getVendorId() {
        return vendorId;
    }

    public TicketPool getTicketPool() {
        return ticketPool;
    }

    public void setTicketPool(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
    }

    public int getTicketsPerRelease() {
        return ticketsPerRelease;
    }

    public void setTicketsPerRelease(int ticketsPerRelease) {
        this.ticketsPerRelease = ticketsPerRelease;
    }

    public int getReleaseInterval() {
        return releaseInterval;
    }

    public void setReleaseInterval(int releaseInterval) {
        this.releaseInterval = releaseInterval;
    }

    @Override
    public String toString() {
        return "Vendor{" +
                "vendorId='" + vendorId + '\'' +
                ", ticketPool=" + ticketPool +
                ", ticketsPerRelease=" + ticketsPerRelease +
                ", releaseInterval=" + releaseInterval +
                '}';
    }
}
