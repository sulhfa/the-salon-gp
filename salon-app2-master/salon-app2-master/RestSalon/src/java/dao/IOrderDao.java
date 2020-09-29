package dao;

import dto.Order;
import java.util.ArrayList;
import java.util.List;

public interface IOrderDao {
    public Order getOderById(int order_id);
    public int addNewOrder(Order a);
    public int delOrder(int order_id);
    public ArrayList<Order> getAllOdersByCustId(int cust_id, boolean active);   
    public ArrayList<Order> getAllOdersByOwner(int owner_id,  boolean active);
    public ArrayList<Order> getAllOdersReported();
    public int updateOrderStatus(int order_id, int new_status);
}
