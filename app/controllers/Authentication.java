package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import java.util.List;
import models.User;
import play.libs.Json;
import java.util.HashMap;
import com.fasterxml.jackson.databind.JsonNode;
import play.mvc.Http.*;
import utils.AuthorizarionValidator;
import play.mvc.Http;
import java.util.Optional;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import play.mvc.With;
import play.Application.*;
import controllers.*;
import play.*;
import play.mvc.*;
import play.mvc.Result;
import play.mvc.With;





public class Authentication extends Controller{

    public static Result Login() {
    
        //Get the Context and JSON input
        Context ctx = Context.current();
        JsonNode json = request().body().asJson();
    
        //Set the Respose Headers to Allow Cross Origin Access
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
        if(AuthorizarionValidator.validate(ctx))    
        {

            responseJson.put("status", "Already Logged In");
            return badRequest(Json.toJson(responseJson));
            
           
        }
       
        //Authenticate the Login attempt
        String status = User.authenticateUser(username,password);

        //For Invalid Credentials
        if(status=="false"){
            responseJson.put("status", "Incorrect Username or Password");
            return badRequest(Json.toJson(responseJson));
        }
        //For Valid Credentials
        else{

            //Fetch the Timeout Constants From Configuration
            long LOGIN_DEFAULT_TIMEOUT = Long.parseLong(play.Play.application().configuration().getString("timeoutconfig.LOGIN_DEFAULT_TIMEOUT"));        
            long INACTIVITY_TIMEOUT_IN_MINUTES = Long.parseLong(play.Play.application().configuration().getString("timeoutconfig.INACTIVITY_TIMEOUT_IN_MINUTES"));            

            //Get Current Epoch timestamp
            Instant currentInstant = Instant.now();
            long currentTimeStampMillis = currentInstant.toEpochMilli();

            // Get login Validity by adding no of minutes to current time
            Instant loginValidityInstant = currentInstant.plus(LOGIN_DEFAULT_TIMEOUT, ChronoUnit.MINUTES);
            long loginValidityTimeStampMillis = loginValidityInstant.toEpochMilli();

            //  Get Active Validity by adding no of minutes to current time
            Instant lastActivityValidityInstant = currentInstant.plus(INACTIVITY_TIMEOUT_IN_MINUTES, ChronoUnit.MINUTES);
            long lastActivityValidityTimeStampMillis = lastActivityValidityInstant.toEpochMilli();


            //Put The Session Cookies
            ctx.session().put("loggedinstatus", "true");
            ctx.session().put("user", username);
            ctx.session().put("loggedinvalidity", Long.toString(loginValidityTimeStampMillis));
            ctx.session().put("lastactivevalidity", Long.toString(lastActivityValidityTimeStampMillis));
            
            System.out.println(ctx.session());
            responseJson.put("status", "Login Is A Success");
            responseJson.put("user", username);

             //TO Implement the keep me signed in and the rest of functionality explore this  https://developer.mozilla.org/en-US/docs/Web/HTTP/Cookies
            return ok(Json.toJson(responseJson));
        }
    }

    public static Result Logout() {
        
        //Get the Context and JSON input
        Context ctx = Context.current();
        JsonNode json = request().body().asJson();


        //Creating the response Object               
        HashMap<String, Object> responseJson = new HashMap<String, Object>(){
            {
                put("status", "" );
            }
        };

        response().setHeader("Access-Control-Allow-Origin", request().getHeader("Origin"));
        response().setHeader("Allow", request().getHeader("Origin"));   
        response().setHeader("Origin", request().getHeader("Origin"));
        response().setHeader("Access-Control-Max-Age", "36000");
        response().setHeader("Access-Control-Allow-Credentials","true");
        response().setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS");
        response().setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Referer, User-Agent");


        //If already logged out Respond Accordingly
        if(!AuthorizarionValidator.validate(ctx)){
            ctx.session().clear();
            responseJson.put("status", "Already Logged Out");
            return badRequest(Json.toJson(responseJson));

        } 
        //Else
        //Clear the Session cookies
        ctx.session().clear();
        responseJson.put("status", "Logout is a Sucess");
        return ok(Json.toJson(responseJson));   
    }

    
    public static Result isauth(){

        Context ctx = Context.current();
        
        HashMap<String, Object> responseJson = new HashMap<String, Object>(){
            {
                put("status", "Is Not Logged In");
            }
        };
        
        response().setHeader("Access-Control-Allow-Origin", request().getHeader("Origin"));
        response().setHeader("Allow", request().getHeader("Origin"));   
        response().setHeader("Origin", request().getHeader("Origin"));
        response().setHeader("Access-Control-Max-Age", "36000");
        response().setHeader("Access-Control-Allow-Credentials","true");
        response().setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS");
        response().setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Referer, User-Agent");

        if(AuthorizarionValidator.validate(ctx))    
        {

            
            String user = ctx.session().get("user");

            responseJson.put("status", "Is Logged In");
            responseJson.put("user", user );
        
            return ok(Json.toJson(responseJson));
            
           
        }

        
        System.out.println(ctx.session());
        return unauthorized((Json.toJson(responseJson))); 
    }

} 