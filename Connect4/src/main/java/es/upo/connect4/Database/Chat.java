/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upo.connect4.Database;

import java.util.Objects;

/**
 *
 * @author S
 */
public class Chat implements EntityObject, Comparable<Chat> {
    private String _id;
    private String _rev;
    private String type;
    private String from;
    private String to;
    private String text;
    private Long sentAt;
    private String type;

    public Chat(String from,String to, String text,Long sentAt) {

        this.from = from;
        this.text = text;
        this.to = to;
        this.sentAt=sentAt;
        type = "chat";

    }
    
    public String getTo() {
        return to;
    }

    public void setSentAt(Long sentAt) {
        this.sentAt = sentAt;
    }
    
    public Long getSentAt() {
        return sentAt;
    }

       public String getId() {
        return _id;

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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.sentAt);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Chat other = (Chat) obj;
        if (!Objects.equals(this.sentAt, other.sentAt)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Chat other) {
     
        return this.getSentAt().compareTo(other.getSentAt()) * -1;
    }

    
}
