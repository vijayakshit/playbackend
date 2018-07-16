
package models;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;
import javax.persistence.*;
import play.db.ebean.Model;

@Entity
public class Hunt extends Model{

    @Id
    public String huntid;
    
	public String huntname;
    //public List<Hunter> hunters;
    
   
    //public List<String> questionIds;
    
    public Hunt(String huntname ){
        
        this.huntid = "HUNT_"+UUID.randomUUID().toString();
        this.huntname = huntname;
        //this.hunters = new ArrayList<Hunter>();
        //this.noOfQuestions = questions.length;
        //this.questionIds = questionIds;
        
    }


    public static String createHunt(String huntname){
         Hunt newHunt = new Hunt( huntname);
         System.out.println(newHunt);

         newHunt.save();
         return newHunt.huntid;
     }



}