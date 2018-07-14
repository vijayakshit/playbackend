
package models;
import java.util.List;
import java.util.ArrayList;

public class Question{

	
	public String questionText;
    public List<String> options;
    public Integer answerIndex;
    
    public Question(String questionText, List<String> options, Integer answerIndex){
        
        this.questionText = questionText;
        this.options = options;
        this.answerIndex = answerIndex;
    }

    public void createQuestion(String questionText, List<String> options, Integer answerIndex){
        Question newQuestion = new Question( questionText, options, answerIndex);
        System.out.println("Adding a Question:");
        System.out.println(newQuestion);
    }

    

}