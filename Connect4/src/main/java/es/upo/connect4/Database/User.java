/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upo.connect4.Database;

import java.util.List;

/**
 *
 * @author S
 */
public class User implements EntityObject, Comparable<User>{

    private String _id;
    private String _rev;
    private String type;
    private String username;
    private String password;
    private int won;
    private int lost;
    private int draws;

    public User() {

    }

    public User(String username, String password) {
        this.type = "user";
        this.username = username;
        this.password = password;
        this.won = 0;
        this.lost = 0;
        this.lost = 0;
    }

    public User(String _id, String username, String password, int won, int lost, int draws) {
        this._id = _id;
        this.type = "user";
        this.username = username;
        this.password = password;
        this.won = won;
        this.lost = lost;
        this.draws = draws;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getWon() {
        return won;
    }

    public void setWon(int won) {
        this.won = won;
    }

    public int getLost() {
        return lost;
    }

    public void setLost(int lost) {
        this.lost = lost;
    }

    public int getDraws() {
        return draws;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }

    public String toString() {
        return username;
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final User other = (User) obj;
        
          Integer p = 3* getWon() + getDraws();
        Integer pOtro = 3 * other.getWon() + other.getDraws();
        if(p==pOtro){
        return true;
        }else{
            return false;
        }
        
    }

    @Override
    public int compareTo(User o) {
        Integer p = 3* getWon() + getDraws();
        Integer pOtro = 3 * o.getWon() + o.getDraws();
        return pOtro.compareTo(p);
    }

}
