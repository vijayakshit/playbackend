
package models;
import java.util.List;
import java.util.ArrayList;
import javax.persistence.*;
import play.db.ebean.Model;
import play.db.ebean.*;

import utils.*;


@Entity
public class Hunter extends Model{

    @Id
	public String hunterId;
	public String hunterName;
    public String subscibedHunts;
    public static Finder<String,Hunter> find = new Finder<String,Hunter>(
        String.class, Hunter.class
    );
    
    //Constructor
    public Hunter(String hunterId,String hunterName){
        this.hunterId = hunterId;
        this.hunterName = hunterName;
        this.subscibedHunts = null;
    }

    //Create and Save a new Hunter Returns Hunters Unique ID
    public static String createHunter(String hunterId,String hunterName){
        Hunter newHunter = new Hunter(hunterId,hunterName);
        newHunter.save(); 
        return newHunter.hunterId;
    }

    //Returns HuntIds For Subscribed Hunts
    public static List<String> getSubscribedHunts(String hunterId){
        Hunter hunter = Hunter.find.byId(hunterId);
        return ModelStringUtils.stringToArrayStrings(hunter.subscibedHunts);
    }

    //Adds a Hunt To Subscribed Hunts
    public static List<String> subscribeToHunt(String hunterId,String huntid){
        Hunter hunter = Hunter.find.byId(hunterId);
        if(hunter.subscibedHunts!=null){
            hunter.subscibedHunts = hunter.subscibedHunts + "," +huntid;
        }
        else{
            hunter.subscibedHunts = huntid;
        }
        hunter.save();
        return ModelStringUtils.stringToArrayStrings(hunter.subscibedHunts);
    }
}