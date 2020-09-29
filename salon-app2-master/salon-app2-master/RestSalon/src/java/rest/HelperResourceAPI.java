package rest;

import dao.ISecurityQuestion;
import dao.SecurityQuestionDao;
import dto.SecurityQuestion;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import static rest.RestHelper.sendResponse;


@Path("helper")
public class HelperResourceAPI {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of HelperResourceAPI
     */
    public HelperResourceAPI() {
    }

    @POST
    @Consumes("text/plain")
    @Produces("text/plain")
    public String postTest(String content) {
        return "Postback: " + content;
    }
    
    /**
     * Retrieves representation of an instance of rest.SecQuestionsAPI
     * @return an instance of java.lang.String
     */
    @Path("secQuestions")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getQuestionsJson() {
        ISecurityQuestion questions=   new SecurityQuestionDao();
        JSONParser parser = new JSONParser(); 
        JSONObject obj;
        JSONArray array = new JSONArray();
        for (SecurityQuestion t : questions.getAll()) {
            array.add(convertSQToJson(t));
        }
        return sendResponse(array.toString());
    }

    private JSONObject convertSQToJson(SecurityQuestion p) {
        JSONObject jObj = new JSONObject();
        jObj.put("id", p.getId());
        jObj.put("title", p.getTitle());
        return jObj;
    }
}
