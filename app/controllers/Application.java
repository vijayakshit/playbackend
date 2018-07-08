package controllers;

import play.*;
import play.mvc.*;
import play.mvc.Result;
import controllers.*;
import play.libs.Json;
import java.util.HashMap;

import views.html.*;
import play.mvc.With;

import models.User;
import play.mvc.Http.*;


public class Application extends Controller {


    //@Security.Authenticated(Secured.class)
    public static Result index() {

        Context ctx = Context.current();
        System.out.println(ctx.session());
        //GOFO response().setHeader("Access-Control-Allow-Origin", "*");
        //response().setHeader("Access-Control-Allow-Origin", "http://192.168.0.72:3000/");
        //response().setHeader("Access-Control-Allow-Origin", "http://batman.com:3000/','http://batman.com:3000/']");   
        response().setHeader("Access-Control-Allow-Origin", "https://akshitsbatman.herokuapp.com");
        response().setHeader("Allow", "https://akshitsbatman.herokuapp.com");    
        response().setHeader("Access-Control-Allow-Credentials","true");
        response().setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS");
        response().setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Referer, User-Agent");


        
        HashMap<String, Object> responseJson = new HashMap<String, Object>(){
            {
                put("status", "" );
            }
        };
        return ok(Json.toJson(responseJson));
        //return ok(index.render("You will Only see this if you are logged in."));
    
    }

    public static Result preflight() {
        response().setHeader("Access-Control-Allow-Origin", "https://akshitsbatman.herokuapp.com");
        response().setHeader("Allow", "https://akshitsbatman.herokuapp.com");    
        response().setHeader("Access-Control-Allow-Credentials","true");
        response().setHeader("Access-Control-Allow-Methods", "POST, PUT, DELETE, OPTIONS");
        response().setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Referer, User-Agent");
        //response().setHeader("Access-Control-Allow-Headers", "content-type, Accept, application/json, text/plain, */*");
        return ok();
    }
    public static Result preflight2() {
        response().setHeader("Access-Control-Allow-Origin", "batman.com");
        response().setHeader("Allow", "batman.com");    
        response().setHeader("Access-Control-Allow-Credentials","true");
        response().setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS");
        //response().setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Referer, User-Agent");
        response().setHeader("Access-Control-Allow-Headers", "content-type, Accept, application/json, text/plain, */*");
        return ok();
    }

}
