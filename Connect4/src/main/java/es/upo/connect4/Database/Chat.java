/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upo.connect4.Database;

/**
 *
 * @author S
 */
public class Chat implements EntityObject{
    private String _id;
    private String _rev;
    private String type;
    private String from;
    private String to;
    private String text;
    private String sentAt;

    public Chat(String from,String to, String text,String sentAt) {
        this.from = from;
        this.text = text;
        this.to = to;
        this.sentAt=sentAt;
        this.type="chat";
    }
    
    public String getTo() {
        return to;
    }

    public void setSentAt(String sentAt) {
        this.sentAt = sentAt;
    }
    
    public String getSentAt() {
        return sentAt;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public String getId() {
        return _id;
    }

    

    public String getRev() {
        return _rev;
    }

    public void setRev(String _rev) {
        this._rev = _rev;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
    
    
    @Override
    public String toString(){
        return "From "+from+": "+text;
    }
}
