package utils;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;


public class ModelStringUtils {

    public static ArrayList<String> stringToArrayStrings(String textToBreak) {
        ArrayList<String> listOfStrings = new ArrayList<String>();
        if(textToBreak.indexOf(',')==-1){
            return listOfStrings;
        }
        listOfStrings = new ArrayList<String>(Arrays.asList(textToBreak.split(",")));
        return listOfStrings;
    }

    public static String arrayStringsToStrings(ArrayList<String> stringsToJoin) {
        String joinedString = "";
        
        for (String oneString : stringsToJoin){
            joinedString = joinedString + "," +oneString;
        }
        joinedString = joinedString.substring(1);

        return joinedString;
    }

}