
package models;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;
import javax.persistence.*;
import utils.*;
import play.db.ebean.*;

@Entity
public class Hunt extends Model{

    @Id
    public String huntid;
    public String huntname;
    public static Finder<String,Hunt> find = new Finder<String,Hunt>(
        String.class, Hunt.class
    );
    
    
    public Hunt(String huntname ){       
        this.huntid = "HUNT_"+UUID.randomUUID().toString();
        this.huntname = huntname;        
    }

    public static String createHunt(String huntname){
         Hunt newHunt = new Hunt( huntname);
         System.out.println(newHunt);
         newHunt.save();
         System.out.println(newHunt.huntid);
         return newHunt.huntid;
     }

    public static List<String> getAllHuntIds(){ 
        List<Hunt> allHunts = Hunt.find.all();
        List<String> allHuntIds = new ArrayList<String>();

        for(Hunt hunt:allHunts){
            allHuntIds.add(hunt.huntid);
        }
        return allHuntIds;
    }

    public static Hunt getHunt(String huntId){ 
        Hunt thisHunt = Hunt.find.byId(huntId);
        return thisHunt;
    }

    
}