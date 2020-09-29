package dto;

import java.sql.Date;
import java.util.Objects;

public class Notifications {
    private int id;
    private int orderId;
    private Date eventDate;
    private int eventStatus;
    private String eventDetails;

    public Notifications(int id){
        this.id = id;
    }

    public Notifications(int id,int orderId, Date event_date, int event_status,String event_details){
        this.id = id;
        this.orderId = orderId;
        this.eventDate = event_date;
        this.eventStatus = event_status;
        this.eventDetails = event_details;
    }
    
    public Notifications(int orderId, Date event_date, int event_status,String event_details){
        this.orderId = orderId;
        this.eventDate = event_date;
        this.eventStatus = event_status;
        this.eventDetails = event_details;
    }
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the orderId
     */
    public int getOrderId() {
        return orderId;
    }

    /**
     * @param orderId the orderId to set
     */
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    /**
     * @return the eventDate
     */
    public Date getEventDate() {
        return eventDate;
    }

    /**
     * @param eventDate the eventDate to set
     */
    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    /**
     * @return the eventStatus
     */
    public int getEventStatus() {
        return eventStatus;
    }

    /**
     * @param eventStatus the eventStatus to set
     */
    public void setEventStatus(int eventStatus) {
        this.eventStatus = eventStatus;
    }

    /**
     * @return the eventDetails
     */
    public String getEventDetails() {
        return eventDetails;
    }

    /**
     * @param eventDetails the eventDetails to set
     */
    public void setEventDetails(String eventDetails) {
        this.eventDetails = eventDetails;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Notifications other = (Notifications) obj;
        return Objects.equals(this.getId(), other.getId());
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + this.id;
        hash = 83 * hash + this.orderId;
        return hash;
    }
    
}
