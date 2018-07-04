package models;
import java.util.List;
import java.util.ArrayList;

public class User{

    //Member Decleration
    public String uid;
    public String email;
    public String password;


    //Constructor
    public User(String uid,String email,String password){
        this.uid = uid;
        this.email = email;
        this.password = password;
    }

    //Mocking Data
    private static List<User> registeredUsers ;
    static {
        registeredUsers = new ArrayList<User>();
        registeredUsers.add(new User("MT100", "akshitthevijay@gmail.com","Password"));
        registeredUsers.add(new User("MT101", "akshitthevijay1@gmail.com","Password1"));
        registeredUsers.add(new User("MT102", "akshitthevijay2@gmail.com","Password2"));
    }


    public static String checkForUser(String email,String password){
        for(User candidate:registeredUsers){
            if(candidate.email.equals(email)){
                if(candidate.password.equals(password)){
                    return "true";
                }
                return "false";
            }   
        }
        return "false";
    }

    public static String authenticateUser(String email, String password){
        String userStatus = checkForUser(email, password);
        return userStatus;
    }

    //TODO :Implement the method to user cookie values on the basis of his inputs, should return through the authenticate user function make this private; :)
    //public static 

    public String toString(){
        return String.format("%s - %s",uid,email);
    }

    

}