/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upo.connect4.Database;

import java.util.Date;
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
    private List<Chat> messages;
    private String p1;
    private String p2;

    public Match(String winner, String type, String p1, String p2) {
        this.winner = winner;
        this.type = "match";
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

    public List<Chat> getMessages() {
        return messages;
    }

    public void setMessages(List<Chat> messages) {
        if(this.messages!=null && this.messages.size()>0){
            this.messages.addAll(messages);
        }
        else{
            this.messages = messages;
        }
        
    }

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
