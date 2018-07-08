package utils;

import play.mvc.Http.*;
import play.mvc.Controller;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class AuthorizarionValidator {
  public static boolean validate(Context ctx) {
    //Check If login Cookie is present
    System.out.println(ctx.session());
    if(ctx.session().get("loggedinstatus")  != null){
        System.out.println("Logged In is True");
        
        //Check if expiration time has been met
         long loginValidityTimeStampMillis =  Long.parseLong(ctx.session().get("loggedinvalidity") );
         long lastActivityValidityTimeStampMillis =  Long.parseLong(ctx.session().get("lastactivevalidity") );
         
         Instant currentInstant = Instant.now();
         long currentTimestamp = currentInstant.toEpochMilli();

        //If Login validity has expired : invalidate
         if(loginValidityTimeStampMillis < currentTimestamp){
            
            invalidateSession(ctx);
            return false;  
         }
         //If Activity validity has expired : invalidate
         if(lastActivityValidityTimeStampMillis < currentTimestamp){
            invalidateSession(ctx);
            return false;  
         }

        
        //if(ctx.session().get("loggedinstatus"))
        
        return true;
    }
    invalidateSession(ctx);
    return false;  
  }

  private static void invalidateSession(Context ctx){
    ctx.session().remove("loggedinstatus");
    ctx.session().remove("loggedinemail");
    ctx.session().remove("loggedinat");
    ctx.session().remove("loggedinvalidity");
    ctx.session().remove("lastactivevalidity");
    ctx.session().clear();
  }


  public static void activityAuthorizationExtender(Context ctx) {
    
    Instant lastActivityValidityInstant = Instant.now().plus(1, ChronoUnit.MINUTES);
    long lastActivityValidityTimeStampMillis = lastActivityValidityInstant.toEpochMilli();
    ctx.session().put("lastactivevalidity",Long.toString(lastActivityValidityTimeStampMillis));
  }
}

