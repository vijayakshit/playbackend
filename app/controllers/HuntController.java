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
import play.db.ebean.Model;
import utils.*;


public class HuntController extends Controller {
   
    public static Result createNewHunt() {

        Context ctx = Context.current();
        JsonNode payload = request().body().asJson();
    
        HashMap<String, Object> responseJson = new HashMap<String, Object>(){
            {
                put("huntid", "Not Created");
            }
        };

        if(payload == null) {
            responseJson.put("status", "Expecting Json data");
            return badRequest(Json.toJson(responseJson));
        }
        
        //Respond with Bad Request if username/password is not found
        String huntname = payload.findPath("huntname").textValue();
        JsonNode questionsNode = payload.get("questions");
        
        String huntId = Hunt.createHunt(huntname);
        
        List<String> questionIdList = new ArrayList<String>();
        for (JsonNode questionNode : questionsNode){
            //System.out.println(question);
            Integer answerIndex = Integer.parseInt(questionNode.get("answerindex").asText());
           
            String questionText = questionNode.findPath("questiontext").textValue();
            List<String> options = new ArrayList<String>();
            JsonNode optionsNode = questionNode.get("options");
            for (JsonNode option : optionsNode){
                options.add(option.textValue());
            }
            String questionid = Question.createQuestion(questionText, options, answerIndex,huntId);
            questionIdList.add(questionid);
        }

        responseJson.put("huntid",huntId);

        return ok(Json.toJson(responseJson));
    }

    public static Result getHuntLeaderboard() {

        Context ctx = Context.current();
    
        HashMap<String, Object> playerOne = new HashMap<String, Object>(){
            {
                put("position", "1");
                put("hunter","Akshit");
                put("score","4");
            }
        };

        HashMap<String, Object> playerTwo = new HashMap<String, Object>(){
            {
                put("position", "2");
                put("hunter","Ujjwal");
                put("score","3");
            }
        };

        List<HashMap> leaderboard = new ArrayList<HashMap>();
        leaderboard.add(playerOne);
        leaderboard.add(playerTwo);

        HashMap<String, List<HashMap>> responseJson = new HashMap<String, List<HashMap>>(){
            {
                put("leaderboard",leaderboard );
            }
        };


        

      
        
 

       // play clean compile &  git add . & git commit -m "Added Dummy Route" & git push heroku


        return ok(Json.toJson(responseJson));
    }




}
