package rest;

import dto.Account;
import dao.AccountDao;
import dao.IAccountDao;
import dao.IOwnerDao;
import dao.OwnerDao;
import dto.Owner;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.*;
import org.json.simple.parser.*;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.GET;
import javax.ws.rs.core.MediaType;
import static rest.RestHelper.sendResponse;

@Path("accounts")
public class AccountAPI {

    @Context
    private UriInfo context;

    public AccountAPI() {

    }

    
    @Path("all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJson() {
        IAccountDao user = new AccountDao();
        JSONParser parser = new JSONParser();
        JSONObject obj;
        JSONArray array = new JSONArray();
        for (Account t : user.getAllusers()) {
            array.add(convertUserToJson(t, null));
        }
        return sendResponse(array.toString());
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePassword(String content) {
        int rowOp = -1;
        try {
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(content);
            IAccountDao db = new AccountDao();
            String username = (String) obj.get("username");
            String password = (String) obj.get("password");
            String newpass  = (String) obj.get("newpassword");;
            rowOp = db.updatePassword(username, password, newpass);

        } catch (Exception exp) {
            System.out.println(exp);
        }
        JSONObject response = new JSONObject();
        response.put("update", rowOp);

        return sendResponse(response.toJSONString());
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
            IAccountDao db = new AccountDao();
            int user_id = ((Long) obj.get("user_id")).intValue();
            int status = ((Long) obj.get("status")).intValue();
            rowOp = db.updateUserStatus(user_id, status == 1 ? 0 : 1);
        } catch (Exception exp) {
            System.out.println(exp);
        }
        JSONObject response = new JSONObject();
        response.put("update", rowOp);

        return sendResponse(response.toJSONString());
    }

    @Path("add")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addAccount(String content) {
        int returnId = -1;
        Account user = convertJsonStringToUser(content, true, false);
        if (user != null) {
            IAccountDao db = new AccountDao();
            returnId = db.createNewAccount(user);
            //System.out.println(user.toString());
        }
        JSONObject response = new JSONObject();
        response.put("update", returnId);

        return sendResponse(response.toJSONString());
    }

    @Path("update")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateAccount(String content) {
        int returnId = -1;
        Account user = convertJsonStringToUser(content, false, true);
        if (user != null) {
            IAccountDao db = new AccountDao();
            returnId = db.updateUser(user);
            //System.out.println(user.toString());
            if (user.getUserType() == 2) {

            }
        }
        JSONObject response = new JSONObject();
        response.put("update", returnId);

        return sendResponse(response.toJSONString());
    }

    @Path("valid")
    @POST
    @Consumes("text/plain")
    @Produces("text/plain")
    public Response getIfUnique(String content) {
        JSONObject jObj = new JSONObject();
        //String returnMessage;
        boolean isValid = false;
        try {

            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(content);
            String email = (String) obj.get("username");

            IAccountDao db = new AccountDao();
            isValid = db.isEmailUnique(email);

        } catch (ParseException ex) {
            Logger.getLogger(AccountAPI.class.getName()).log(Level.SEVERE, null, ex);
        }
        jObj.put("valid", isValid);
        return sendResponse(jObj.toJSONString());
    }

    @Path("login")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLogin(String content) {
        //String returnMessage;
        int returnId;
        Account user = convertJsonStringToUser(content, false, false);
        Owner ow = null;
        if (user != null) {
            IAccountDao db = new AccountDao();
            returnId = db.checkUserByEmailPassword(user);
            if (returnId == 0) {
                // wrong pass
                user = new Account(-2);
            } else {
                user = db.getUserById(returnId);
                if (user == null) {
                    // user data not found
                    user = new Account(-3);
                } else if (user.getUserType() == 2) { //owner
                    IOwnerDao ow_db = new OwnerDao();
                    ow = ow_db.getById(returnId);
                }
            }
        } else {
            // wrong input
            user = new Account(-1);
        }
        JSONObject response = convertUserToJson(user, ow);
        System.out.println(response.toJSONString());
        return sendResponse(response.toJSONString());
    }

    
    @Path("byservice")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserInfoByServiceId(String content) {
        Account u = new Account(-3);
        try {
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(content);

            int serviceId = (((Long) obj.get("serviceId")).intValue());

            if (serviceId != 0) {
                IAccountDao db = new AccountDao();
                u = db.getUserByServiceId(serviceId);
                if (u == null) {
                    u = new Account(-3);
                }
            }

        } catch (ParseException ex) {
            u = new Account(-3);
        }
        JSONObject response = convertUserToJson(u, null);
        return sendResponse(response.toJSONString());
    }
    
    @Path("info")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserInfo(String content) {
        Account u = new Account(-3);
        try {
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(content);

            int userId = (((Long) obj.get("user_id")).intValue());

            if (userId != 0) {
                IAccountDao db = new AccountDao();
                u = db.getUserById(userId);
                if (u == null) {
                    u = new Account(-3);
                }
            }

        } catch (ParseException ex) {
            u = new Account(-3);
        }
        JSONObject response = convertUserToJson(u, null);
        return sendResponse(response.toJSONString());
    }

    private JSONObject convertUserToJson(Account user, Owner ow) {
        JSONObject jObj = new JSONObject();
        jObj.put("user_id", user.getUser_id());
        jObj.put("username", user.getEmail());
        jObj.put("status", user.getStatus());
        jObj.put("fullname", user.getFullname());
        jObj.put("usertype", user.getUserType());
        jObj.put("location", user.getLocation());
        jObj.put("address", user.getAddress());
        jObj.put("phone", user.getPhone());
        if (ow != null) {
            jObj.put("salonname", ow.getSalonname());
        }
        return jObj;
    }

    private Account convertJsonStringToUser(String jsonString, boolean createAccount, boolean updateAccount) {
        Account u = null;
        try {
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(jsonString);
            u = new Account();
            u.setEmail((String) obj.get("username"));
            if (!updateAccount) {
                u.setPassword((String) obj.get("password"));
            }
            if (createAccount) {
                u.setEmail(u.getEmail()); // username is email address
                u.setSecurityQuestionId(Integer.valueOf((String) obj.get("security_question")));
                u.setSecurityAnswer((String) obj.get("security_answer"));
            }
            if (createAccount || updateAccount) {
                u.setUserType(((Long) obj.get("usertype")).intValue());
                u.setFullname((String) obj.get("fullname"));
                u.setPhone((String) obj.get("phone"));
                u.setSalonName((String) obj.get("salonname"));
            }

            if (updateAccount) {
                u.setLocation((String) obj.get("location"));
                u.setAddress((String) obj.get("address"));
            }
        } catch (Exception exp) {
            System.out.println(exp);
            u = null;
        }
        return u;
    }

}
