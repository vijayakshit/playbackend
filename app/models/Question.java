
package models;
import java.util.List;
import java.util.ArrayList;
import javax.persistence.*;
import play.db.ebean.Model;
import java.util.UUID;

@Entity
public class Question extends Model{

    @Id
	public String questionId;
	public String questionText;
    public List<String> options;
    public Integer answerIndex;
    
    
    public Question(String questionText, List<String> options, Integer answerIndex){
        
        this.questionId = "QUES_"+UUID.randomUUID().toString();
        this.questionText = questionText;
        this.options = options;
        this.answerIndex = answerIndex;
    }

    public static String createQuestion(String questionText, List<String> options, Integer answerIndex){
        Question newQuestion = new Question( questionText, options, answerIndex);
        System.out.println("Adding a Question:");
        System.out.println(newQuestion);
        return newQuestion.questionId;
    }

    

}