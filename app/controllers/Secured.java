package controllers;

import play.*;
import play.mvc.*;
import play.mvc.Http.*;
import controllers.routes;
import models.*;
import utils.*;
import play.mvc.Http.*;
import play.mvc.Controller;

import play.*;
import play.mvc.*;
import play.mvc.Result;
import controllers.*;

import java.util.HashMap;

import play.mvc.With;




public class Secured extends Security.Authenticator {

    @Override
    public String getUsername(Context ctx) {
        //GOFO ctx.response().setHeader("Access-Control-Allow-Origin", "*");
        if(AuthorizarionValidator.validate(ctx)){
            AuthorizarionValidator.activityAuthorizationExtender(ctx);
            return "valid";

        }
       //GOFO ctx.response().setHeader("Access-Control-Allow-Origin", "*");
        return null;

    }

    // @Override
    // public Result onUnauthorized(Context ctx) {
    //     return unauthorized(routes.Authentication.IsUnauthorized());
    // }

    @Override
    public Result onUnauthorized(Context context) {
        //response().setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        
        //Context ctx = Context.current();
        Response response = context.response();
        //GOFO response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        // response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        // response.setHeader("Allow", "*");        
        // response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS");
        // response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Referer, User-Agent");
        return super.onUnauthorized(context);
    }

    
    

}