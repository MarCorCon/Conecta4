/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upo.connect4.Database;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author S
 */
public class Match implements EntityObject{
    private String _id;
    private String _rev;
    private String winner;
    private String type;
    private String status;
    private int turn;
    //private List<Chat> messages;
    private String p1;
    private String p2;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public Match(String p1, String p2) {
        this.winner = "none";
        this.type = "match";
        this.p1 = p1;
        this.p2 = p2;
        this.turn = 0;
        this.status = "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee";
    }

    public Match(String winner, String type, String status, int turn, String p1, String p2) {
        this.winner = winner;
        this.type = type;
        this.status = status;
        this.turn = turn;
        this.p1 = p1;
        this.p2 = p2;
    }

    public Match(String _id, String _rev, String winner, String type, String status, int turn, String p1, String p2) {
        this._id = _id;
        this._rev = _rev;
        this.winner = winner;
        this.type = type;
        this.status = status;
        this.turn = turn;
        this.p1 = p1;
        this.p2 = p2;
    }
    
    
    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public String getRev() {
        return _rev;
    }

    public void setRev(String _rev) {
        this._rev = _rev;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

//    public List<Chat> getMessages() {
//        return messages;
//    }
//
//    public void setMessages(List<Chat> messages) {
//        if(this.messages!=null && this.messages.size()>0){
//            this.messages.addAll(messages);
//        }
//        else{
//            this.messages = messages;
//        }
//        
//    }
//    
//    public void addMessages(Chat message){
//        if(this.messages==null){
//            this.messages = new ArrayList<Chat>();
//        }
//        messages.add(message);
//    }

    public String getP1() {
        return p1;
    }

    public void setP1(String p1) {
        this.p1 = p1;
    }

    public String getP2() {
        return p2;
    }

    public void setP2(String p2) {
        this.p2 = p2;
    }
    
    
    
    @Override
    public String toString(){
        return p1+" - "+p2+" |||| "+_id;
    }
}
