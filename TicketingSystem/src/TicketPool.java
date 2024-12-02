import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.Queue;

public class TicketPool {
    private final Queue<Ticket> ticketQueue = new LinkedList<>();
    private final int maximumCapacity;

    public TicketPool(int maximumCapacity, int totalTickets) {
        this.maximumCapacity = maximumCapacity;

        //Initialize the queue with current total tickets
        for (int count=0; count<totalTickets; count++) {
            ticketQueue.add(new Ticket("Ticket-" + System.currentTimeMillis(), "Spandana", new BigDecimal("2000.00")));
        }
    }

    /**
     * Adds a new ticket to the ticket queue
     * @param ticket
     */
    public synchronized void addTicket(Ticket ticket) {
        // Wait if the ticket pool is full
        while (ticketQueue.size() >= maximumCapacity) {
            try {
                System.out.println("Dear " + Thread.currentThread().getName() +", Ticket pool is full! Please wait until space available");
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println( Thread.currentThread().getName() + " thread got Interrupted. Ticket release Unsuccessful!");
                return;
            }
        }

        // Adds the ticket into ticketQueue
        ticketQueue.add(ticket);
        System.out.println(Thread.currentThread().getName() + " added a ticket. Current size: " + ticketQueue.size() +" tickets, Remaining size: "+ (maximumCapacity - ticketQueue.size())+", Capacity: " + maximumCapacity);
        notifyAll();
    }

    /**
     * Represents customers buying ticket
     * Retrieves the first element / ticket from the ticket queue
     * @return ticket
     */
    public synchronized Ticket buyTicket() {
        // Wait if the ticketQueue is empty
        while (ticketQueue.isEmpty()) {
            try {
                System.out.println("Dear " + Thread.currentThread().getName() +", Currently no tickets available. Please wait till tickets are available");
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // sets the thread's flag as interrupted
                //System.out.println( Thread.currentThread().getName() + " thread got Interrupted while buying a ticket");
                return null; // Thread will stop executing the loop
            }
        }

        // retrieves and removes the ticket from the ticketQueue
        Ticket ticket = ticketQueue.poll();
        notifyAll();    // Notify producers that there is space available
        System.out.println(Thread.currentThread().getName() + " bought " + ticket.getSeatId() + ". Remaining " + ticketQueue.size() + " tickets");
        return ticket;
    }

    public Queue<Ticket> getTicketQueue() {
        return ticketQueue;
    }

    public int getMaximumCapacity() {
        return maximumCapacity;
    }
}
