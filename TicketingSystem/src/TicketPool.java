import java.util.LinkedList;
import java.util.Queue;

public class TicketPool {
    private final Queue<Ticket> ticketQueue;
    private final int maximumCapacity;  // maximum capacity of the ticket pool
    private int totalTicketsDistributed; //how many tickets are already added to the ticket pool

    public TicketPool(int maximumCapacity, int totalTicketsDistributed) {
        this.ticketQueue = new LinkedList<>();
        this.maximumCapacity = maximumCapacity;
        this.totalTicketsDistributed = totalTicketsDistributed;
    }

    /**
     * Adds a new ticket to the ticket queue
     * @param ticket
     */
    public synchronized void addTicket(Ticket ticket) {
        // Wait if the ticket pool is full
        while (ticketQueue.size() >= maximumCapacity) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println( Thread.currentThread().getName() + "thread got Interrupted while adding a ticket");
                break;
            }
        }

        // Adds the ticket into ticketQueue
        ticketQueue.add(ticket);
        notifyAll();
        System.out.println(Thread.currentThread().getName() + " has added a ticket to the ticket pool. Current size is " + this.ticketQueue.size());
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
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // sets the thread's flag as interrupted
                System.out.println( Thread.currentThread().getName() + "thread got Interrupted while buying a ticket");
                break; // Thread will stop executing the loop
            }
        }

        // retrieves and removes the ticket from the ticketQueue
        Ticket ticket = ticketQueue.poll();
        notifyAll();    // Notify producers that there is space available
        System.out.println(Thread.currentThread().getName() + " has bought a ticket from the pool. Current size is " + this.ticketQueue.size());
        return ticket;
    }

    public Queue<Ticket> getTicketQueue() {
        return ticketQueue;
    }

    public int getMaximumCapacity() {
        return maximumCapacity;
    }
}
