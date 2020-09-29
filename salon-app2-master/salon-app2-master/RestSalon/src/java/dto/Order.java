package dto;

import java.sql.Date;
import java.util.List;
import java.util.Objects;

public class Order {

    private int id;
    private int custId;
    private Date orderDate;
    private int orderStatus; //1.pending 2.accept 3.cancel_cus 4. cancel_own 4.cancel_admin 5.complete
    private int paymentType;
    private double totalCost;
    private List<OrderDetails> orderDetails;
    private int serviceCount;
    
    public Order(){
    }
    
    public Order(int id){
        this.id = id;
    }
    
    public Order(int id, int custId, Date orderDate, 
            int orderStatus, int paymentType, double totalCost, List<OrderDetails> orderDetails){
        this.id = id;
        this.custId = custId;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.paymentType = paymentType;
        this.totalCost = totalCost;
        this.orderDetails = orderDetails;
    }
    
    public Order(int userId, Date orderDate, 
            int orderStatus, int paymentType, double totalCost, List<OrderDetails> orderDetails){
        this.custId = userId;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.paymentType = paymentType;
        this.totalCost = totalCost;
        this.orderDetails = orderDetails;
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
     * @return the custId
     */
    public int getUserId() {
        return custId;
    }

    /**
     * @param userId the custId to set
     */
    public void setUserId(int userId) {
        this.custId = userId;
    }


    /**
     * @return the orderDate
     */
    public Date getOrderDate() {
        return orderDate;
    }

    /**
     * @param orderDate the orderDate to set
     */
    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Order other = (Order) obj;
        return Objects.equals(this.getId(), other.getId());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + this.id;
        hash = 13 * hash + this.custId;
        return hash;
    }

    /**
     * @return the orderStatus
     */
    public int getOrderStatus() {
        return orderStatus;
    }

    /**
     * @param orderStatus the orderStatus to set
     */
    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * @return the orderDetails
     */
    public List<OrderDetails> getOrderDetails() {
        return orderDetails;
    }

    /**
     * @param orderDetails the orderDetails to set
     */
    public void setOrderDetails(List<OrderDetails> orderDetails) {
        this.orderDetails = orderDetails;
    }

    /**
     * @return the totalCost
     */
    public double getTotalCost() {
        return totalCost;
    }

    /**
     * @param totalCost the totalCost to set
     */
    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    /**
     * @return the paymentType
     */
    public int getPaymentType() {
        return paymentType;
    }

    /**
     * @param paymentType the paymentType to set
     */
    public void setPaymentType(int paymentType) {
        this.paymentType = paymentType;
    }

    /**
     * @return the serviceCount
     */
    public int getServiceCount() {
        return serviceCount;
    }

    /**
     * @param serviceCount the serviceCount to set
     */
    public void setServiceCount(int serviceCount) {
        this.serviceCount = serviceCount;
    }

}
