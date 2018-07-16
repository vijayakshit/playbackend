
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
    public Integer noOfQuestions;
    public List<Question> huntquestions;
    
    public Hunt(String huntname,List<Question> questions ){
        
        this.huntid = UUID.randomUUID().toString();
        this.huntname = huntname;
        //this.hunters = new ArrayList<Hunter>();
        //this.noOfQuestions = questions.length;
        this.huntquestions = questions;
        
    }


    public static void createHunt(String huntname,List<Question> questions){
         Hunt newHunt = new Hunt( huntname, questions);
         System.out.println(newHunt);
         newHunt.save();
     }



}