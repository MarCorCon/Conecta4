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
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author S
 */
public class MongoClientHelper {
    private static final String USERACC = "conecta4";   
    private static final String PASSWORD = "1q2w3e4rasd";
    private static final String DB = "tablero";
    
    private static Database getDB(){
        CloudantClient client = ClientBuilder.account(USERACC)
                                     .username(USERACC)
                                     .password(PASSWORD)
                                     .build();    
        Database db = client.database(DB, false);
        return db;

    }
    
    public static User findUser(String username){
        //db.findByIndex('selector\": {\"Movie_year\": {\"$gt\": 1960}, \"Person_name\": \"Alec Guinness\"}'
        Database db = getDB();
        User us = db.findByIndex("\"selector\": {\"name\": \"falete\"}",
        User.class, new FindByIndexOptions()/*.sort(new IndexField("Movie_year", SortOrder.desc)).fields("Movie_name").fields("Movie_year")*/).get(0);
        System.out.println(us);
        return us;
    }
}
