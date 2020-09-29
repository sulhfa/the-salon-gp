package rest;

import javax.ws.rs.core.Response;

public class RestHelper {
    public static Response sendResponse(String ret){
        
        return Response
            .status(200)
            .header("Access-Control-Allow-Origin", "*")
            .entity(ret)
            .build();
    }
}
