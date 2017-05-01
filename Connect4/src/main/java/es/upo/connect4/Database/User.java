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
public class User implements EntityObject{
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
  
  public User(String username,String password) {
      this.type="user";
      this.username=username;
      this.password=password;
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
  
  

  public String toString() {
    return username;
  }
  
}
