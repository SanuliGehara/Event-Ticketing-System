import java.math.BigDecimal;

public class Ticket {
    private final int ticketId;
    private final String ticketName;
    private final BigDecimal ticketPrice;

    public Ticket(int ticketId, String ticketName, BigDecimal ticketPrice) {
        this.ticketId = ticketId;
        this.ticketName = ticketName;
        this.ticketPrice = ticketPrice;
    }

    public int getTicketId() {
        return ticketId;
    }

    public String getTicketName() {
        return ticketName;
    }

    public BigDecimal getTicketPrice() {
        return ticketPrice;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId=" + ticketId +
                ", eventName='" + ticketName + '\'' +
                ", ticketPrice=" + ticketPrice +
                '}';
    }
}
