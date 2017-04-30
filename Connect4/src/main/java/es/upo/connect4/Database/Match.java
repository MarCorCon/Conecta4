/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upo.connect4.Database;

import java.util.Date;

/**
 *
 * @author S
 */
public class Match {
    private String _id;
    private String _rev = null;
    private String cosa; 
    private Date date;
    
    @Override
    public String toString(){
        return cosa+" - "+date.toString();
    }
}
