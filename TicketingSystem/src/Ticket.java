import java.math.BigDecimal;

public class Ticket {
    private final String seatId;
    private final String eventName;
    private final BigDecimal ticketPrice;

    public Ticket(String seatId, String eventName, BigDecimal ticketPrice) {
        this.seatId = seatId;
        this.eventName = eventName;
        this.ticketPrice = ticketPrice;
    }

    public String getSeatId() {
        return seatId;
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
                "Seat ID='" + seatId + '\'' +
                ", event Name='" + eventName + '\'' +
                ", ticket Price= RS. " + ticketPrice +
                '}';
    }
}
