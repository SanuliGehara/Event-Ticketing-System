import java.math.BigDecimal;

public class Ticket {
    private final String seatName;
    private final String eventName;
    private final BigDecimal ticketPrice;

    public Ticket(String seatName, String eventName, BigDecimal ticketPrice) {
        this.seatName = seatName;
        this.eventName = eventName;
        this.ticketPrice = ticketPrice;
    }

    public String getSeatName() {
        return seatName;
    }

    public String getEventName() {
        return eventName;
    }

    public BigDecimal getTicketPrice() {
        return ticketPrice;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "seatName='" + seatName + '\'' +
                ", eventName='" + eventName + '\'' +
                ", ticketPrice= RS. " + ticketPrice +
                '}';
    }
}
