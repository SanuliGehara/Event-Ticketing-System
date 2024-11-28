import java.util.LinkedList;
import java.util.Queue;

public class TicketPool {
    private final Queue<Ticket> ticketQueue;
    private int maximumCapacity;

    public TicketPool(int maximumCapacity) {
        this.ticketQueue = new LinkedList<>();
        this.maximumCapacity = maximumCapacity;
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
                System.out.println( Thread.currentThread().getName() + "thread got Interrupted");
                break;
            }
        }

        // Adds the ticket into ticketQueue
        ticketQueue.add(ticket);
        notifyAll();
        System.out.println("Ticket added to the pool. Current size: " + this.ticketQueue.size());
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
                System.out.println( Thread.currentThread().getName() + "thread got Interrupted");
                break; // Thread will stop executing the loop
            }
        }

        // retrieves the first ticket from the ticketQueue
        Ticket ticket = ticketQueue.poll();
        notifyAll();
        System.out.println("Ticket bought from the ticket pool. Current size: " + this.ticketQueue.size());
        return ticket;
    }

    public Queue<Ticket> getTicketQueue() {
        return ticketQueue;
    }

    public int getMaximumCapacity() {
        return maximumCapacity;
    }

    public void setMaximumCapacity(int maximumCapacity) {
        this.maximumCapacity = maximumCapacity;
    }
}
