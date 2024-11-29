
// I assume that all vendors are releasing tickets to the same ticket pool which is for "Spandana" event
// All the tickets are having the same price - RS. 2000 (No early bird tickets or special seats)

import java.math.BigDecimal;

public class Vendor extends User{
    private final String vendorId;
    private TicketPool ticketPool;
    private int ticketsPerRelease;  // total tickets which will be released by the vendor at the time
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
        for (int count=1; count<=ticketsPerRelease; count++) {
            // Check if thread is interrupted
            if (Thread.currentThread().isInterrupted()) break;

            Ticket ticket = new Ticket(Integer.toString(count), "Spandana", new BigDecimal("2000.00"));
            ticketPool.addTicket(ticket);
            System.out.println(getUsername() + " added " + ticket);

            // wait for a time period before releasing next ticket
            try {
                Thread.sleep(releaseInterval); // milliseconds
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Vendor Thread got interrupted");
                break;
            }
        }
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
