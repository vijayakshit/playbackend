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


public class Application extends Controller {


    //@Security.Authenticated(Secured.class)
    public static Result index() {
        //GOFO response().setHeader("Access-Control-Allow-Origin", "*");
        //response().setHeader("Access-Control-Allow-Origin", "http://192.168.0.72:3000/");
        //response().setHeader("Access-Control-Allow-Origin", "http://localhost:3000/','http://localhost:3000/']");   
   
        HashMap<String, Object> responseJson = new HashMap<String, Object>(){
            {
                put("status", "" );
            }
        };
        return ok(Json.toJson(responseJson));
        //return ok(index.render("You will Only see this if you are logged in."));
    
    }

    public static Result preflight() {
        response().setHeader("Access-Control-Allow-Origin", "*");
        response().setHeader("Allow", "*");    
        response().setHeader("Access-Control-Allow-Credentials","true");
        response().setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS");
        response().setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Referer, User-Agent");
        return ok();
    }

}
