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
        if(AuthorizarionValidator.validate(ctx)){
            AuthorizarionValidator.activityAuthorizationExtender(ctx);
            return "valid";
        }
        return null;
    }

    @Override
    public Result onUnauthorized(Context context) {
        Response response = context.response();
        response.setHeader("Access-Control-Allow-Credentials", "true");
        return super.onUnauthorized(context);
    }
}