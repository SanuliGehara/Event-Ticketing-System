
// I assume that all vendors are releasing tickets to the same ticket pool which is for "Spandana" event
// All the tickets are having the same price - RS. 2000 (No early bird tickets or special seats)

import java.math.BigDecimal;
import java.util.concurrent.CountDownLatch;

public class Vendor extends User {
    private final String vendorId;
    private final TicketPool ticketPool;
    private final int ticketsPerRelease;
    private final int releaseInterval;
    private final CountDownLatch latch;

    public Vendor(String username, String password, String vendorId, TicketPool ticketPool, int ticketsPerRelease, int releaseInterval, CountDownLatch latch) {
        super(username, password, UserType.VENDOR);
        this.vendorId = vendorId;
        this.ticketPool = ticketPool;
        this.ticketsPerRelease = ticketsPerRelease;
        this.releaseInterval = releaseInterval;
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            for (int count = 1; count <= ticketsPerRelease; count++) {
                if (!TicketSystem.isRunning()) return;
                Ticket ticket = new Ticket("Ticket-" + System.currentTimeMillis(), "Spandana", new BigDecimal("2000.00"));
                ticketPool.addTicket(ticket);
                Thread.sleep(releaseInterval);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            latch.countDown(); // Signal that this thread has completed
        }
        System.out.println(getUsername() + " finished adding tickets.");
    }

//    public void run() {
//        for (int count=1; count<=ticketsPerRelease; count++) {
//            // Check if thread is interrupted or system is stopped
//            if (Thread.currentThread().isInterrupted() || !TicketSystem.isRunning()) {
//                break;
//            }
//
//            Ticket ticket = new Ticket("Ticket-" + System.currentTimeMillis(), "Spandana", new BigDecimal("2000.00"));
//            ticketPool.addTicket(ticket);
//
//            try {
//                Thread.sleep(releaseInterval); // milliseconds
//            }
//            catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//                System.out.println(this.getUsername()+" got interrupted");
//                return;
//            }
//        }
//        System.out.println(getUsername() + " finished adding tickets.");
//    }

    public String getVendorId() {
        return vendorId;
    }

    public TicketPool getTicketPool() {
        return ticketPool;
    }

    public int getTicketsPerRelease() {
        return ticketsPerRelease;
    }

    public int getReleaseInterval() {
        return releaseInterval;
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
