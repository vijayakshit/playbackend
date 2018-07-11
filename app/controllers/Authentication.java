package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import java.util.List;
import models.User;
import play.libs.Json;
import java.util.HashMap;
import com.fasterxml.jackson.databind.JsonNode;
//import jdk.nashorn.internal.runtime.Context;
import play.mvc.Http.*;
import play.mvc.Http;
import java.util.Optional;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import play.mvc.With;
import controllers.*;



public class Authentication extends Controller{

    public static Result Login() {
        //Get the Context and JSON input
        Context ctx = Context.current();
        System.out.println("login"+ctx.session());
        
        


        JsonNode json = request().body().asJson();
        
        //GOFO response().setHeader("Access-Control-Allow-Origin", "*");
        //GOFO response().setHeader("Access-Control-Allow-Credentials", "true");
  
        response().setHeader("Access-Control-Allow-Origin", request().getHeader("Origin"));
        response().setHeader("Allow", request().getHeader("Origin"));   
        response().setHeader("Origin", request().getHeader("Origin"));
        response().setHeader("Access-Control-Max-Age", "36000");

        response().setHeader("Access-Control-Allow-Credentials","true");
        response().setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS");
        response().setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Referer, User-Agent");

        //Creating the response Object               
        HashMap<String, Object> responseJson = new HashMap<String, Object>(){
            {
                put("status", "" );
            }
        };

        //Respond with Bad Request if Json is not found
        if(json == null) {
            responseJson.put("status", "Expecting Json data");
            return badRequest(Json.toJson(responseJson));
        }
        
        //Respond with Bad Request if username/password is not found
        String username = json.findPath("username").textValue();
        String password = json.findPath("password").textValue();    
        if(username == null||password == null) {
            responseJson.put("status", "Missing username or password");
            return badRequest(Json.toJson(responseJson));
        } 

        //Respond with Bad Request if username/password is not found
        if(ctx.session().get("loggedinstatus")  != null)    
        {
            responseJson.put("status", "Already Logged In");
            return badRequest(Json.toJson(responseJson));
        }
       
        //Authenticate the Login attempt
        String status = User.authenticateUser(username,password);

        //Invalid Credentials
        if(status=="false"){
            responseJson.put("status", "Incorrect Username or Password");
            return badRequest(Json.toJson(responseJson));
        }
        //Valid Credentials

        else{
            //Get Current Epoch timestamp
            Instant currentInstant = Instant.now();
            long currentTimeStampMillis = currentInstant.toEpochMilli();

            //Instant longValidityInstant = currentInstant.plus(Duration.ofHours(0).plusMinutes(2));
            // login Validity 
            Instant loginValidityInstant = currentInstant.plus(2, ChronoUnit.MINUTES);
            long loginValidityTimeStampMillis = loginValidityInstant.toEpochMilli();

            // active Validity         
            Instant lastActivityValidityInstant = currentInstant.plus(1, ChronoUnit.MINUTES);
            long lastActivityValidityTimeStampMillis = lastActivityValidityInstant.toEpochMilli();


            //Put The Session Cookies
           // ctx.session().clear();
            System.out.println("beforeset"+ctx.session());
            ctx.session().put("loggedinstatus", "true");
            ctx.session().put("loggedinemail", "true");
            ctx.session().put("loggedinat", Long.toString(currentTimeStampMillis));
            ctx.session().put("loggedinvalidity", Long.toString(loginValidityTimeStampMillis));
            ctx.session().put("lastactivevalidity", Long.toString(lastActivityValidityTimeStampMillis));
            
            System.out.println("Afterset"+ctx.session());

                 //TO Implement the rest of functionality reas this  https://developer.mozilla.org/en-US/docs/Web/HTTP/Cookies
            responseJson.put("status", "Login is ja Succesds");
            return ok(Json.toJson(responseJson));
        }
    }

    public static Result Logout() {
        //Get the Context and JSON input
        Context ctx = Context.current();
        JsonNode json = request().body().asJson();

        // response().setHeader("Access-Control-Allow-Origin", "https://akshitsbatman.herokuapp.com");
        // response().setHeader("Allow", "https://akshitsbatman.herokuapp.com");    
        // response().setHeader("Access-Control-Allow-Credentials","true");
        // response().setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS");
        // response().setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Referer, User-Agent");
        
        //Creating the response Object               
        HashMap<String, Object> responseJson = new HashMap<String, Object>(){
            {
                put("status", "" );
            }
        };

        if(ctx.session().get("loggedinstatus")  != null)    
        {
            ctx.session().clear();
            responseJson.put("status", "Logout is a Sucess");
            return ok(Json.toJson(responseJson));
        } 
        ctx.session().clear();
        responseJson.put("status", "Already Logged Out");
        return badRequest(Json.toJson(responseJson));
    }

    public static Result IsUnauthorized() {
        
        //Creating the response Object               
        HashMap<String, Object> responseJson = new HashMap<String, Object>(){
            {
                put("status", "unauthorized" );
            }
        };
        
        return unauthorized(Json.toJson(responseJson));
    }
    

}