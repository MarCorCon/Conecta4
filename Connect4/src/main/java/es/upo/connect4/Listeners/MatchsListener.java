/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upo.connect4.Listeners;

import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.UI;

/**
 *
 * @author S
 */
public class MatchsListener extends AbstractListener{
    Tablero t;
    Match m;
    VerticalLayout v;
    
    public MatchsListener(UI ui,String otherUser,Tablero t, Match m, VerticalLayout v){
        this.ui=ui;
        this.t=t;
        this.m=m;
        this.v=v;
    }

    @Override
    public void doWork() {
        int turn = m.getTurn();
        Match match = MongoClientHelper.getMatch(m.getId());
        if(match.getTurn()>turn){
            v.removeAllComponents();
            v.addComponent(t.newTablero()); 
            v.setImmediate(true);   
        }
    }
    
}
