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
import models.*;
import play.mvc.Http.*;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;
import java.util.ArrayList;
import javax.persistence.*;
import play.db.ebean.*;
import play.db.ebean.Model;
import java.util.Collection.*;
import utils.*;

import java.util.Set;
import java.util.Arrays;
import java.util.HashSet;



public class HunterController extends Controller {

    public static Result createHunter() {

        Context ctx = Context.current();
        JsonNode payload = request().body().asJson();
    
        HashMap<String, Object> responseJson = new HashMap<String, Object>(){
            {
                put("status", "Not Completed");            
            }
        };

        if(payload == null) {
            responseJson.put("status", "Expecting Json data");
            return badRequest(Json.toJson(responseJson));
        }
        
        //Respond with Bad Request if username/password is not found
        
        String hunterId = payload.findPath("hunterid").textValue();
        String hunterName = payload.findPath("huntername").textValue();    
        
        
        if(hunterId == null||hunterName == null) {
            responseJson.put("status", " Failure to Create HunteMissing Hunter Name or ID");
            return badRequest(Json.toJson(responseJson));
        } 
        
        String createdHunterId = Hunter.createHunter(hunterId, hunterName);
        responseJson.put("status", "Created Hunter");
        responseJson.put("createdHunterId", createdHunterId);

        return ok(Json.toJson(responseJson));
    }
    //TO DO Get this Right Sunscrobed un scubscribed buckets are wrong

    public static Result getHuntsForHunter(String hunterId) {

        Context ctx = Context.current();
    
        HashMap<String, Object> responseJson = new HashMap<String, Object>(){
            {
                put("subscribedHunts", ""); 
            }
        };


        Hunter hunter = Hunter.find.byId(hunterId);
 
        List<String> allHunts = Hunt.getAllHuntIds();
        List<String> subscribedHunts = Hunter.getSubscribedHunts(hunterId);
        
        Set<String> allHuntsSet = new HashSet<String>(allHunts);
        Set<String> registeredSet = new HashSet<String>(subscribedHunts);


        allHuntsSet.remove(registeredSet);

    
        

       // List<String> unSubscribedHunts = new ArrayList<>(CollectionUtils.subtract(allHunts, subscribedHunts));

        responseJson.put("registeredHunts", subscribedHunts); 
        responseJson.put("unregisteredHunts", allHuntsSet);    

        return ok(Json.toJson(responseJson));
    }
    
    public static Result registerForHunt(String hunterId,String huntId) {
        Context ctx = Context.current();

        HashMap<String, Object> responseJson = new HashMap<String, Object>(){
            {
                put("Hunt", "Not Registered");            
            }
        };

        List<String> updatedSubscribedHunts = Hunter.subscribeToHunt(hunterId,huntId);

        List<Question> questionsForHunt = Question.getHuntQuestions(huntId);

        Hunt thisHunt = Hunt.getHunt(huntId);

        String progressId = Progress.createProgress(hunterId, huntId, questionsForHunt.size());

        HashMap<String, Object> huntObject = new HashMap<String, Object>(){
            {
                put("hunterid", hunterId);  
                put("hunt", thisHunt);  
                put("questionsForHunt", questionsForHunt);
                put("progressid", progressId);  
              ;          
            }
        };


        responseJson.put("Hunt", huntObject); 

        return ok(Json.toJson(responseJson));
    }
        
    //TO DO Get this Right
    public static Result getHuntForHunter(String hunterId,String huntId, String progressId) {
        Context ctx = Context.current();

        HashMap<String, Object> responseJson = new HashMap<String, Object>(){
            {
                put("Hunt", "Not Found");            
            }
        };

       
        List<Question> questionsForHunt = Question.getHuntQuestions(huntId);




        Hunt thisHunt = Hunt.getHunt(huntId);

        Progress progress = Progress.getProgressById(progressId);

        HashMap<String, Object> huntObject = new HashMap<String, Object>(){
            {
                put("hunterid", hunterId);  
                put("hunt", thisHunt);  
                put("questionsForHunt", questionsForHunt);
                put("progress", progress);  
              ;          
            }
        };


        responseJson.put("Hunt", huntObject); 

        return ok(Json.toJson(responseJson));
    }
      
    public static Result updateScore(String progressId, Integer questionIndex, String scoreString){

        Context ctx = Context.current();

        HashMap<String, Object> responseJson = new HashMap<String, Object>(){
            {
                put("Updated Score", "Not Updated");            
            }
        };



        Progress updatedProgress = Progress.updateProgress(progressId, questionIndex, scoreString.charAt(0));

        responseJson.put("Updated Score", updatedProgress); 

        return ok(Json.toJson(responseJson));

    }
        



}
