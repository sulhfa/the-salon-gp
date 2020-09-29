package dao;

import dto.OrderDetails;
import java.util.ArrayList;

public interface IOrderdetailDao {
    public OrderDetails getOrderDetailById(int id);
    public ArrayList<OrderDetails> getAllDetailsByOrderId(int order_id);
    public int addNewOrderDetail(OrderDetails od);
    public int delOrderDetail(int id);
}
