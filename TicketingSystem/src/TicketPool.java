import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.Queue;

public class TicketPool {
    private final Queue<Ticket> ticketQueue = new LinkedList<>();
    private final int maximumCapacity;  // maximum capacity of the ticket pool
    private int totalTickets; //current total tickets in the pool

    public TicketPool(int maximumCapacity, int totalTickets) {
        this.maximumCapacity = maximumCapacity;
        this.totalTickets = totalTickets;

        //Initialize the queue with current total tickets
        for (int count=0; count<totalTickets; count++) {
            ticketQueue.add(new Ticket(Integer.toString(count), "Spandana", new BigDecimal("2000.00")));
        }
    }

    /**
     * Adds a new ticket to the ticket queue
     * @param ticket
     */
    public synchronized void addTicket(Ticket ticket) {
        // Wait if the ticket pool is full
        while (ticketQueue.size() >= maximumCapacity || totalTickets >= maximumCapacity) {
            try {
                System.out.println("Dear " + Thread.currentThread().getName() +", Ticket pool is full! Please wait until space available");
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println( Thread.currentThread().getName() + "thread got Interrupted while adding a ticket");
                break;
            }
        }

        // Adds the ticket into ticketQueue
        ticketQueue.add(ticket);
        totalTickets++;
        System.out.println(Thread.currentThread().getName() + " added a ticket. Current size: " + ticketQueue.size() +" tickets, Remaining size: "+ (maximumCapacity - ticketQueue.size())+", Total tickets: " + totalTickets);
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
                System.out.println( Thread.currentThread().getName() + "thread got Interrupted while buying a ticket");
                break; // Thread will stop executing the loop
            }
        }

        // retrieves and removes the ticket from the ticketQueue
        Ticket ticket = ticketQueue.poll();
        totalTickets--;
        notifyAll();    // Notify producers that there is space available
        System.out.println(Thread.currentThread().getName() + " bought a ticket. Remaining " + ticketQueue.size() + " tickets , Total available tickets: " + totalTickets);
        return ticket;
    }

    public Queue<Ticket> getTicketQueue() {
        return ticketQueue;
    }

    public int getMaximumCapacity() {
        return maximumCapacity;
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }
}
