
package models;
import java.util.List;
import java.util.ArrayList;
import javax.persistence.*;
import play.db.ebean.Model;
import java.util.UUID;
import utils.*;


@Entity
public class Question extends Model{

    @Id
	public String questionId;
    public String questionText;
    
    public String options;
    public Integer answerIndex;
    public String huntId;
    public static Finder<String,Question> find = new Finder<String,Question>(
        String.class, Question.class
    );
    
    public Question(String questionText, String options, Integer answerIndex,String huntId){
        
        this.questionId = "QUES_"+UUID.randomUUID().toString();
        this.questionText = questionText;
        this.options = options;
        this.answerIndex = answerIndex;
        this.huntId = huntId;
    }

    public static String createQuestion(String questionText, List<String> options, Integer answerIndex,String huntId){
        
        String allOptions = "";

        
        for (String option : options){
            allOptions = allOptions + "," +option;
        }
        allOptions = allOptions.substring(1);

        Question newQuestion = new Question( questionText, allOptions, answerIndex,huntId);
        newQuestion.save();

        
        return newQuestion.questionId;
    }

    public static List<Question>  getAllQuestions(){ 
        List<Question> questions = Question.find.all();
        return questions;
    }

    public static List<Question> getHuntQuestions(String huntId){

        
        List<Question> huntQuestions = Question.find.where().eq("huntId", huntId).findList();
                
        return huntQuestions;
    }

}