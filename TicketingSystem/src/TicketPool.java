import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TicketPool {
    private final Queue<Ticket> ticketQueue = new LinkedList<>();
    private final int maximumCapacity;

    private final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();  // Condition for when the pool is full
    private final Condition notEmpty = lock.newCondition(); // Condition for when the pool is empty

    // Constructor
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
    public void addTicket(Ticket ticket) {
        lock.lock();

        try {
            while (ticketQueue.size() >= maximumCapacity) {
                if (!TicketSystem.isRunning()) {
                    System.out.println(Thread.currentThread().getName() + " exiting because system is stopping.");
                    return; // Exit when the system is stopping
                }
                System.out.println(Thread.currentThread().getName() + ", Ticket pool is full! Waiting...");
                if (!notFull.await(5, TimeUnit.SECONDS)) { // Wait up to 5 seconds
                    System.out.println(Thread.currentThread().getName() + " waited too long. This ticket Release cancelled since the pool is full! Exiting...");
                    return; // Exit if timeout occurs
                }
            }

            // Adds the ticket into ticketQueue
            ticketQueue.add(ticket);
            System.out.println(Thread.currentThread().getName() + " added a ticket. Current size: " + ticketQueue.size() + " tickets, Remaining size: " + (maximumCapacity - ticketQueue.size()) + ", Capacity: " + maximumCapacity);
            notEmpty.signalAll(); // Notify consumers that tickets are available
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println(Thread.currentThread().getName() + " interrupted. Ticket release unsuccessful!");
        }
        finally {
            lock.unlock();
        }
    }

    /**
     * Represents customers buying ticket
     * Retrieves the first element / ticket from the ticket queue
     * @return ticket
     */
    public Ticket buyTicket() {
        lock.lock();

        try {
            while(ticketQueue.isEmpty()) {  // wait if ticketQueue is empty
                if (!TicketSystem.isRunning()) {
                    System.out.println(Thread.currentThread().getName() + " exiting because system is stopping.");
                    return null; // Exit when the system is stopping
                }
                System.out.println(Thread.currentThread().getName() + ", No tickets available. Waiting...");
                if (!notEmpty.await(5, TimeUnit.SECONDS)) { // Wait up to 5 seconds
                    System.out.println(Thread.currentThread().getName() + " waited too long. This ticket transaction cancelled due to no ticket availability! Exiting...");
                    return null; // Exit if timeout occurs
                }
            }

            // retrieves and removes the ticket from the ticketQueue
            Ticket ticket = ticketQueue.poll();
            notFull.signalAll();    // Notify producers that there is space available
            System.out.println(Thread.currentThread().getName() + " bought " + ticket.getSeatId() + ". Remaining " + ticketQueue.size() + " tickets");
            return ticket;
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // sets the thread's flag as interrupted
            System.out.println(Thread.currentThread().getName() + " interrupted while buying ticket.");
            return null;
        }
        finally {
            lock.unlock();
        }
    }

    public Queue<Ticket> getTicketQueue() {
        return ticketQueue;
    }

    public int getMaximumCapacity() {
        return maximumCapacity;
    }
}
