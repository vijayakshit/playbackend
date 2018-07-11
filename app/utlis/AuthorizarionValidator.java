package utils;

import play.mvc.Http.*;
import play.mvc.Controller;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import play.Application.*;


public class AuthorizarionValidator {
  public static boolean validate(Context ctx) {
    //Check If login Cookie is present
    System.out.println(ctx.session());
    if(ctx.session().get("loggedinstatus")!=null){
        
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
        return true;
    }

    //If Login Cookie is Present
    invalidateSession(ctx);
    return false;  
  }

  private static void invalidateSession(Context ctx){
    ctx.session().clear();
  }


  public static void activityAuthorizationExtender(Context ctx) {
    
    long INACTIVITY_TIMEOUT_IN_MINUTES = Long.parseLong(play.Play.application().configuration().getString("timeoutconfig.INACTIVITY_TIMEOUT_IN_MINUTES"));            
    Instant lastActivityValidityInstant = Instant.now().plus(INACTIVITY_TIMEOUT_IN_MINUTES, ChronoUnit.MINUTES);
    long lastActivityValidityTimeStampMillis = lastActivityValidityInstant.toEpochMilli();
    ctx.session().put("lastactivevalidity",Long.toString(lastActivityValidityTimeStampMillis));
  }
}

