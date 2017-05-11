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
import com.cloudant.client.api.model.Index;
import com.cloudant.client.api.model.Response;
import es.upo.connect4.MyUI;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author S Base de datos en:
 * https://esdroen.cloudant.com/dashboard.html#database/tablero/_all_docs
 */
public class MongoClientHelper {

    private static final String USERACC = "esdroen";
    private static final String PASSWORD = "1q2w3e4rasd";
    private static final String DB = "tablero";

    private static CloudantClient CLIENT;

    private static Database getDB() {
        if (CLIENT == null) {
            CLIENT = ClientBuilder.account(USERACC)
                    .username(USERACC)
                    .password(PASSWORD)
                    .build();
        }
        Database db = CLIENT.database(DB, false);
        return db;

    }

    public static User findUser(String username, String password) {
        Database db = getDB();
        List<User> lis = new ArrayList<>();
        lis = db.findByIndex("{\"username\":\"" + username + "\",\"password\":\"" + password + "\", \"type\":\"user\"}",
                User.class);
        User us = null;
        if (!lis.isEmpty()) {
            us = lis.get(0);
        }

        System.out.println(us);
        return us;
    }

    public static User findUser(String username) {
        Database db = getDB();
        User us = db.findByIndex("{\"username\":\"" + username + "\", \"type\":\"user\"}",
                User.class).get(0);
        System.out.println(us);
        return us;
    }

    public static List<Match> findUserMatchs(String username) {
        Database db = getDB();
        String query = "{ $or: [ { p1: " + username + "}, { p2: " + username + " } ] }";
        System.out.println(query);
        List<Match> matches = db.findByIndex(query,
                Match.class, new FindByIndexOptions()/*.sort(new IndexField("Movie_year", SortOrder.desc)).fields("Movie_name").fields("Movie_year")*/);
        System.out.println(matches);
        return matches;
    }

    public static List<Match> findOpenMatch(String player1, String player2) {
        Database db = getDB();
        String query = "{ $or: [ "
                + "{$and : [{ p1: " + player1 + "}, { p2: " + player2 + " }, {turn: { $lt : 42 }} ] },"
                + "{$and : [{ p1: " + player2 + "}, { p2: " + player1 + " }, {turn: { $lt : 42 }} ] }]}";
        //System.out.println(query);
        List<Match> matchs = db.findByIndex(query,
                Match.class);
        System.out.println(matchs);
        return matchs;
    }

    public static List<User> findAllUsers() {
        Database db = getDB();
        String query = "{\"type\": \"user\"}";
        System.out.println(query);
        List<User> users = db.findByIndex(query,
                User.class, new FindByIndexOptions()/*.sort(new IndexField("Movie_year", SortOrder.desc)).fields("Movie_name").fields("Movie_year")*/);
        System.out.println(users);
        return users;
    }

    public static void createEntity(EntityObject newDoc) {
        Database db = getDB();
        db.save(newDoc);
    }

    public static void updateEntity(EntityObject updatedDoc) {
        Database db = getDB();
        db.update(updatedDoc);
    }

    public static void deleteEntity(EntityObject removedDoc) {
        Database db = getDB();
        db.remove(removedDoc);
    }

}
/*

 */
