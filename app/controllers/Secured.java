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
    public String getUsername(Http.Context ctx) {
        //GOFO ctx.response().setHeader("Access-Control-Allow-Origin", "*");
                if(AuthorizarionValidator.validate(ctx)){

      //  if(0==0){
            AuthorizarionValidator.activityAuthorizationExtender(ctx);
            return "valid";

        }
        // ctx.response().setHeader("Access-Control-Allow-Origin", "https://akshitsbatman.herokuapp.com");
        return null;

    }

    // @Override
    // public Result onUnauthorized(Context ctx) {
    //     return unauthorized(routes.Authentication.IsUnauthorized());
    // }

    @Override
    public Result onUnauthorized(Context context) {
        //response().setHeader("Access-Control-Allow-Origin", "http://batman.com");
        
        //Context ctx = Context.current();
        Response response = context.response();
        //GOFO response.setHeader("Access-Control-Allow-Origin", "batman.com");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        // response.setHeader("Access-Control-Allow-Origin", "http://batman.com");
        // response.setHeader("Allow", "*");        
        // response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS");
        // response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Referer, User-Agent");
        return super.onUnauthorized(context);
    }

    
    

}