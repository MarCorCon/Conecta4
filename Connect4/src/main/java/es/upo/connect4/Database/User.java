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
public class User {
  private String _id;
  private String _rev = null;
  private String name;
  private List<Match> match; 
  private List<Chat> chat;

  public User() {
   
  }

  public String toString() {
    return name+"\n"+match;
  }
  
}
