package rest;

import dto.Service;
import dao.IServiceDao;
import dao.ServiceDao;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;
import static rest.RestHelper.sendResponse;

@Path("services")
public class ServiceAPI {
    @Context
    private UriInfo context;

    public ServiceAPI() {

    }

    @POST
    @Consumes("text/plain")
    @Produces("text/plain")
    public String postTest(String content) {
        return "Postback: " + content;
    }

    @Path("update")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateService(String content) {
        int rowOp = -1;
        Service t = convertJsonStringToService(content, false, false, true);
        if (t != null) {
            IServiceDao db = new ServiceDao();
            rowOp = db.updateService(t);
        }
        JSONObject response = new JSONObject();
        response.put("update", rowOp);

        return sendResponse(response.toJSONString());
    }

    @Path("add")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addService(String content) {
        int rowOp = -1;
        Service t = convertJsonStringToService(content, true, false, false);
        if (t != null) {
            IServiceDao db = new ServiceDao();
            rowOp = db.addNewService(t);
        }
        JSONObject response = new JSONObject();
        response.put("update", rowOp);

        return sendResponse(response.toJSONString());
    }

    @Path("del")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response delService(String content) {
        int rowOp = -1;
        Service t = convertJsonStringToService(content, false, true, false);
        if (t != null) {
            IServiceDao db = new ServiceDao();
            rowOp = db.delService(t.getId());
        }
        JSONObject response = new JSONObject();
        response.put("update", rowOp);

        return sendResponse(response.toJSONString());
    }

    @Path("byowner")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postGetAllByOwner(String content) {
        //String returnMessage;
        List<Service> tlist = null;
        JSONParser parser = new JSONParser();
        JSONObject obj;
        try {
            obj = (JSONObject) parser.parse(content);
            int user_id = (((Long) obj.get("user_id")).intValue());
            if (user_id > 0) {
                IServiceDao db = new ServiceDao();
                tlist = db.getAllServiceByOwnerId(user_id);
            }

        } catch (ParseException ex) {
            Logger.getLogger(ServiceAPI.class.getName()).log(Level.SEVERE, null, ex);
        }
        JSONArray array = new JSONArray();
        for (Service t : tlist) {
            array.add(convertSerivceToJson(t));
        }
        return sendResponse(array.toString());
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response postGetAllPublic() {
        //String returnMessage;
        List<Service> tlist = null;
        JSONParser parser = new JSONParser();
        JSONObject obj;
        IServiceDao db = new ServiceDao();
        tlist = db.getAllService(true);

        JSONArray array = new JSONArray();
        for (Service t : tlist) {
            array.add(convertSerivceToJson(t));
        }
        return sendResponse(array.toString());
    }

    @Path("manage")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response postGetAll() {
        //String returnMessage;
        List<Service> tlist = null;
        JSONParser parser = new JSONParser();
        JSONObject obj;
        IServiceDao db = new ServiceDao();
        tlist = db.getAllService(false);

        JSONArray array = new JSONArray();
        for (Service t : tlist) {
            array.add(convertSerivceToJson(t));
        }
        return sendResponse(array.toString());
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
            IServiceDao db = new ServiceDao();
            int service_id = ((Long) obj.get("id")).intValue();
            int status = ((Long) obj.get("status")).intValue();
            rowOp = db.updateServiceStatus(service_id, status == 1 ? 0 : 1);
        } catch (Exception exp) {
            System.out.println(exp);
        }
        JSONObject response = new JSONObject();
        response.put("update", rowOp);

        return sendResponse(response.toJSONString());
    }

    private JSONObject convertSerivceToJson(Service p) {
        JSONObject jObj = new JSONObject();
        jObj.put("id", p.getId());
        jObj.put("title", p.getTitle());
        jObj.put("ownerId", p.getOwnerId());
        jObj.put("details", p.getDetails());
        jObj.put("cost", p.getCost());
        jObj.put("status", p.getStatus());
        jObj.put("salonname", p.getSalonName());
        jObj.put("srate",p.getAvgRate());
        jObj.put("pcount",p.getPicCount());
        if(p.getPicCount()>0){
            JSONArray imgArray = new JSONArray();
            for (String t : p.getImageList()) {
                JSONObject jObj2 = new JSONObject();
                jObj2.put("picturepath",t);
                imgArray.add(jObj2);
            }
            jObj.put("imagelist", imgArray );
        }
        return jObj;
    }

    private Service convertJsonStringToService(String jsonString, boolean isAdd, boolean isDel, boolean isEdit) {
        Service t = null;
        try {
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(jsonString);
            t = new Service();
            if (isDel || isEdit) {
                t.setId(((Long) obj.get("id")).intValue());
            }
            if (isAdd) {
                t.setOwnerId(((Long) obj.get("ownerId")).intValue());
            }
            if (isAdd || isEdit) {
                t.setTitle((String) obj.get("title"));
                t.setDetails((String) obj.get("details"));
                double z = ((Long) obj.get("cost")).doubleValue();
                t.setCost(z);
            }
        } catch (Exception exp) {
            System.out.println(exp);
            t = null;
        }
        return t;
    }

}
