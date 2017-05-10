/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upo.connect4.Database;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.cloudant.client.api.Search;
import com.cloudant.client.api.model.FindByIndexOptions;
import es.upo.connect4.MyUI;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author S
 */
public class MongoClientHelper {
    private static final String USERACC = "esdroen";   
    private static final String PASSWORD = "1q2w3e4rasd";
    private static final String DB = "tablero";
    
    private static CloudantClient CLIENT;
    
    /*
    DATABASE MODEL:

    MATCH
    {
        "_id": "29590f4a818dd08395d7210fff206be3",
        "_rev": "3-93e77cd4b07d8e73d83af293c19e9b4f",
        "type": "match",
        "p1":"falete",
        "p2":"otroma",
        "messages": [
          {
            "from": "2",
            "text": "yo 2"
          },
          {
            "from": "1",
            "text": "yo bro 2"
          }
        ],
        "winner": "2"
    }
    
    USER
    {
        "_id": "29590f4a818dd08395d7210fff061ec1",
        "_rev": "1-54cd3d459c2ffeb86724332ac5d81424",
        "type": "user",
        "username": "a",
        "password": "b",
        "won": 1,
        "lost": 2,
        "draws": 3
    }
    
    */
    
    private static Database getDB(){
        if(CLIENT==null){
            CLIENT = ClientBuilder.account(USERACC)
                                     .username(USERACC)
                                     .password(PASSWORD)
                                     .build();   
        }
        Database db = CLIENT.database(DB, false);
        return db;

    }
    
//    public static List<User> getUsers(){
//        Database db = getDB(); 
//       
//    }
    public static User findUser(String username,String password){
        Database db = getDB();      
        User us = db.findByIndex("{\"username\":\""+username+"\",\"password\":\""+password+"\", \"type\":\"user\"}",                
        User.class).get(0);
        System.out.println(us);        
        return us;
    }
    
    public static User findUser(String _id){
        Database db = getDB();      
        User us = db.findByIndex("{\"_id\":\""+_id+"\", \"type\":\"user\"}",                
        User.class).get(0);
        System.out.println(us);        
        return us;
    }
    
    public static List<Match> findUserMatchs(String _id){
        Database db = getDB(); 
        String query = "{ $or: [ { p1: "+_id+"}, { p2: "+_id+" } ] }";
        System.out.println(query);
        List<Match> matchs = db.findByIndex(query,                
        Match.class, new FindByIndexOptions()/*.sort(new IndexField("Movie_year", SortOrder.desc)).fields("Movie_name").fields("Movie_year")*/);
        System.out.println(matchs); 
        return matchs;
    }
    
    public static void createEntity(EntityObject newDoc) {
        Database db = getDB();
        db.save(newDoc);    
    } 
    
    public static void updateEntity(EntityObject updatedDoc){  
        Database db = getDB();
        db.update(updatedDoc);        
    }
    
    public static void deleteEntity(EntityObject removedDoc){
        Database db = getDB();
        db.remove(removedDoc);
    }
    
    
}
 /*

*/