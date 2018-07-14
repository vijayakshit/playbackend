package controllers;

import play.*;
import play.mvc.*;
import play.mvc.Result;
import controllers.*;
import play.libs.Json;
import java.util.HashMap;
import views.html.*;
import play.mvc.With;
import java.time.Instant;
import models.User;
import play.mvc.Http.*;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;
import java.util.ArrayList;


public class HuntController extends Controller {


    
    public static Result createNewHunt() {

        Context ctx = Context.current();
        JsonNode payload = request().body().asJson();
    
        HashMap<String, Object> responseJson = new HashMap<String, Object>(){
            {
                put("loggedinvalidity", "cool");
                put("lastactivevalidity",0);
            
            }
        };

        if(payload == null) {
            responseJson.put("status", "Expecting Json data");
            return badRequest(Json.toJson(responseJson));
        }
        
        //Respond with Bad Request if username/password is not found
        String huntname = payload.findPath("huntname").textValue();
        
        JsonNode searchResultsNode = payload.get("questions");
        
        for (Object strTemp : searchResultsNode){

            System.out.println(strTemp);
            
            }

        System.out.println(huntname);
        
        // Object questions = json.findPath("questions")

        // if(username == null||password == null) {
        //     responseJson.put("status", "Missing username or password");
        //     return badRequest(Json.toJson(responseJson));
        // }




        return ok(Json.toJson(responseJson));
    }
}
