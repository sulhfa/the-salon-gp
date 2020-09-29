package rest;

import dao.IOrderDao;
import dao.IReviewDao;
import dao.ReviewDao;
import dto.Review;
import dto.Order;
import dao.OrderDao;
import dto.OrderDetails;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;
import static rest.RestHelper.sendResponse;

@Path("orders")
public class OrderAPI {

    @Context
    private UriInfo context;

    public OrderAPI() {

    }


    @POST
    @Consumes("text/plain")
    @Produces("text/plain")
    public String post(String content) {
        return "Postback: "+content;
    }
    
    @Path("new")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response newOrder(String content) {
        int rowOp = -1;
        Order t = convertJsonStringToOrder(content);
        if (t != null) {
            IOrderDao db = new OrderDao();
            rowOp = db.addNewOrder(t);
        }
        JSONObject response = new JSONObject();
        response.put("update", rowOp);

        return sendResponse(response.toJSONString());
    }
    
    @Path("all")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postGetAllIncompleteOrder(String content) {
        return getOrdersByStatus(content,true);
    }
    
    @Path("activeowner")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postGetAllActiveOrderByOwner(String content) {
        return getAllOrderByOwner(content, true);
    }

    @Path("inactiveowner")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postGetAllInactiveOrderByOwner(String content) {
        return getAllOrderByOwner(content, false);
    }
    
    private Response getAllOrderByOwner(String content, boolean isActive) {
        List<Order> tlist = null;
        JSONParser parser = new JSONParser();
        JSONObject obj;
        try {
            obj = (JSONObject) parser.parse(content);
            int user_id = (((Long) obj.get("user_id")).intValue());
            if (user_id > 0) {
                IOrderDao db = new OrderDao();
                tlist = db.getAllOdersByOwner(user_id, isActive);
            }
        } catch (ParseException ex) {
            Logger.getLogger(ServiceAPI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        JSONArray array = new JSONArray();
        for (Order o : tlist) {
            array.add(convertOrderToJson(o));
        }
        //System.out.println(array.toString());
        return sendResponse(array.toString());
    }
    
    @Path("reported")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response postGetAllReported() {
        List<Order> tlist = null;
        IOrderDao db = new OrderDao();
        tlist = db.getAllOdersReported();

        JSONArray array = new JSONArray();
        for (Order o : tlist) {
            array.add(convertOrderToJson(o));
        }
        //System.out.println(array.toString());
        return sendResponse(array.toString());
    }
    
    @Path("history")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postGetAllHistoryOrder(String content) {
        return getOrdersByStatus(content,false);
    }

    @Path("status")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateStatus(String content) {
        int rowOp = -1;
        try {
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(content);
            IOrderDao db = new OrderDao();
            int order_id = ((Long) obj.get("id")).intValue();
            int status = ((Long) obj.get("status")).intValue();
            rowOp = db.updateOrderStatus(order_id, status);
        } catch (Exception exp) {
            System.out.println(exp);
        }
        JSONObject response = new JSONObject();
        response.put("update", rowOp);
        return sendResponse(response.toJSONString());
    }
   
    private Response getOrdersByStatus(String content, boolean isActive) {
        //String returnMessage;
        List<Order> tlist = null;
        JSONParser parser = new JSONParser();
        JSONObject obj;
        try {
            obj = (JSONObject) parser.parse(content);
            int user_id = (((Long) obj.get("user_id")).intValue());
            if (user_id > 0) {
                IOrderDao db = new OrderDao();
                tlist = db.getAllOdersByCustId(user_id, isActive);
            }
        } catch (ParseException ex) {
            Logger.getLogger(ServiceAPI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        JSONArray array = new JSONArray();
        for (Order o : tlist) {
            array.add(convertOrderToJson(o));
        }
        //System.out.println(array.toString());
        return sendResponse(array.toString());
    }

    private JSONObject convertOrderToJson(Order p) {
        JSONObject jObj = new JSONObject();
        jObj.put("id", p.getId());
        jObj.put("date", p.getOrderDate().toString());
        jObj.put("status", p.getOrderStatus());
        jObj.put("user_id", p.getUserId());
        jObj.put("totalcost", p.getTotalCost());
        jObj.put("paymentType", p.getPaymentType());
        jObj.put("pcount",p.getServiceCount());
        JSONArray parray = new JSONArray();

        for (OrderDetails d : p.getOrderDetails()) {

            JSONObject pi = new JSONObject();
            pi.put("serviceId", d.getServiceId());
            pi.put("serviceTitle", d.getServiceTitle());
            pi.put("cost", d.getCost());
            parray.add(pi);
        }

        jObj.put("orderdetail", parray);

        return jObj;
    }
    
    private Order convertJsonStringToOrder(String jsonString) {
        Order t = null;
        ArrayList<OrderDetails> dt = null;
        try {
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(jsonString);
            t = new Order();
            dt = new ArrayList<OrderDetails>();
            //int OrderId = ((Long) obj.get("OrderId")).intValue();
            //t.setId(OrderId);
            t.setUserId(((Long) obj.get("user_id")).intValue());
            t.setOrderDate(new java.sql.Date(new java.util.Date().getTime()));
            t.setOrderStatus(((Long) obj.get("status")).intValue());
            double z = ((Long) obj.get("totalcost")).doubleValue();
            t.setTotalCost(z);
            t.setPaymentType(((Long) obj.get("paymentType")).intValue());
            //String cust = 

            JSONArray arr = (JSONArray)obj.get("orderdetail");
            for (Object arr1 : arr) {
                JSONObject p = (JSONObject) arr1;
                OrderDetails d= new OrderDetails();
                //d.setOrderId(OrderId);
                d.setServiceId(((Long) p.get("serviceId")).intValue());
                z = ((Long) p.get("cost")).doubleValue();
                d.setCost(z);
                dt.add(d);
            }
            
            t.setOrderDetails(dt);
            
            
        } catch (Exception exp) {
            System.out.println(exp);
            t = null;
        }
        return t;
    }

    
    @Path("review")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addReview(String content) {
        int rowOp = -1;
        try {
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(content);
            IReviewDao db = new ReviewDao();
            Review r = new Review();
            r.setOrderId( ((Long) obj.get("orderId")).intValue() );
            r.setServiceId(((Long) obj.get("serviceId")).intValue() );
            r.setCustId(((Long) obj.get("custId")).intValue());
            r.setSrate(Integer.valueOf((String)obj.get("srate")));
            r.setComments((String)obj.get("comments"));
            
            rowOp = db.addNewReview(r);
        } catch (Exception exp) {
            System.out.println(exp);
        }
        JSONObject response = new JSONObject();
        response.put("update", rowOp);

        return sendResponse(response.toJSONString());
    }
   
}
