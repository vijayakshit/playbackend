package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import java.util.List;
import models.User;
import play.libs.Json;
import java.util.HashMap;
import com.fasterxml.jackson.databind.JsonNode;
import play.mvc.Http.*;
import play.mvc.Http;
import java.util.Optional;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import play.mvc.With;
import play.Application.*;

import controllers.*;

public class Game extends Controller{

    public static Result Dummy() {
    
        //Get the Context and JSON input
        Context ctx = Context.current();
        JsonNode json = request().body().asJson();
        
        try{
            System.out.println(json);
        }
        catch( Exception e){
            System.out.println(e);
           
        }
         
        //Creating the response Object               
        HashMap<String, Object> responseJson = new HashMap<String, Object>(){
            {
                put("status", "" );
            }
        };

        //Respond with Bad Request if Json is not found

        
        
            //TO Implement the keep me signed in and the rest of functionality explore this  https://developer.mozilla.org/en-US/docs/Web/HTTP/Cookies
        responseJson.put("status", "Ping is a Success");
        return ok(Json.toJson(responseJson));
        }
    }
