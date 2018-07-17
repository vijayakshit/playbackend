
package models;
import java.util.List;
import java.util.ArrayList;
import javax.persistence.*;
import play.db.ebean.Model;
import play.db.ebean.*;
import java.util.UUID;
import utils.*;

@Entity
public class Progress extends Model{

    @Id
	public String progressId;
    public String hunterId;
    public String huntId;
    public String allScores;
    public static Finder<String,Progress> find = new Finder<String,Progress>(
        String.class, Progress.class
    );
    
    public Progress( String hunterId, String huntId,String allScores){
        this.progressId = "PROG_"+UUID.randomUUID().toString();
        this.hunterId = hunterId;
        this.huntId = huntId;
        this.allScores = allScores;    
    }

    public static String createProgress(String hunterId, String huntId,int noOfQuestions){
        
        String allScores = "";
        for (int i=0;i<noOfQuestions;i++){
            allScores = allScores + "," +"-1";
        }
        allScores = allScores.substring(1);
        Progress newProgress = new Progress(hunterId, huntId, allScores);
        newProgress.save();

        
        return newProgress.progressId;
    }

    public static Progress updateProgress(String progressId, int questionIndex , char scoreCharToUpdate){
        
        Progress progressToUpdate = Progress.find.byId(progressId);
        String oldScores = progressToUpdate.allScores;
        char[] newScoreArray = oldScores.toCharArray();
        newScoreArray[2*questionIndex] = scoreCharToUpdate;
        String newScore = String.valueOf(newScoreArray);
        progressToUpdate.allScores = newScore;
        progressToUpdate.save();
        return progressToUpdate;
    }
    
    public static List<Progress> getAllProgress(){

        List<Progress> progress = Progress.find.all();
        System.out.println(progress);
        return progress ; 
    }

    public static Progress getProgressById(String progressId){

        Progress progress = Progress.find.byId(progressId);
        System.out.println(progress);
        return progress ; 
    }

}