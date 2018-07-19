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


public class Application extends Controller {


    @Security.Authenticated(Secured.class)
    public static Result index() {

        Context ctx = Context.current();

        response().setHeader("Access-Control-Allow-Origin", request().getHeader("Origin"));
        response().setHeader("Allow", request().getHeader("Origin"));   
        response().setHeader("Origin", request().getHeader("Origin"));
        response().setHeader("Access-Control-Max-Age", "36000");
        response().setHeader("Access-Control-Allow-Credentials","true");
        response().setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS");
        response().setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Referer, User-Agent");

        Instant currentInstant = Instant.now();
        long currentTimeStampMillis = currentInstant.toEpochMilli();

        long loggedinvalidity =  Long.parseLong(ctx.session().get("loggedinvalidity"));
        long lastactivevalidity =  Long.parseLong(ctx.session().get("lastactivevalidity"));

        HashMap<String, Object> responseJson = new HashMap<String, Object>(){
            {
                put("loggedinvalidity", loggedinvalidity-currentTimeStampMillis);
                put("lastactivevalidity",lastactivevalidity-currentTimeStampMillis);
            
            }
        };
        return ok(Json.toJson(responseJson));
    }

    public static Result preflight() {
        response().setHeader("Access-Control-Allow-Origin", request().getHeader("Origin"));
        response().setHeader("Allow", request().getHeader("Origin"));    
        response().setHeader("Access-Control-Allow-Credentials","true");
        response().setHeader("Access-Control-Allow-Methods", "POST, PUT, DELETE, OPTIONS");
        response().setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me");
        return ok();
    }

    public static Result preflight(String path) {
        response().setHeader("Access-Control-Allow-Origin", request().getHeader("Origin"));
        response().setHeader("Allow", request().getHeader("Origin"));    
        response().setHeader("Access-Control-Allow-Credentials","true");
        response().setHeader("Access-Control-Allow-Methods", "POST, PUT, DELETE, OPTIONS");
        response().setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me");
        return ok();
    }

}
