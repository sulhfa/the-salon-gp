package dto;

import java.util.Objects;

public class OrderDetails {

    private int id;
    private int orderId;
    private int serviceId;
    private String serviceTitle;
    private double cost;

    public OrderDetails() {
    }

    public OrderDetails(int id) {
        this.id = id;
    }

    public OrderDetails(int id, int orderId, int serviceId, double cost) {
        this.id = id;
        this.orderId = orderId;
        this.serviceId = serviceId;
        this.cost = cost;
    }

    public OrderDetails(int orderId, int serviceId, double cost) {
        this.orderId = orderId;
        this.serviceId = serviceId;
        this.cost = cost;
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
     * @return the serviceId
     */
    public int getServiceId() {
        return serviceId;
    }

    /**
     * @param serviceId the serviceId to set
     */
    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    /**
     * @return the cost
     */
    public double getCost() {
        return cost;
    }

    /**
     * @param cost the cost to set
     */
    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final OrderDetails other = (OrderDetails) obj;
        return Objects.equals(this.getId(), other.getId());
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + this.id;
        hash = 29 * hash + this.orderId;
        hash = 29 * hash + this.serviceId;
        return hash;
    }

    /**
     * @return the serviceTitle
     */
    public String getServiceTitle() {
        return serviceTitle;
    }

    /**
     * @param serviceTitle the serviceTitle to set
     */
    public void setServiceTitle(String serviceTitle) {
        this.serviceTitle = serviceTitle;
    }

}
