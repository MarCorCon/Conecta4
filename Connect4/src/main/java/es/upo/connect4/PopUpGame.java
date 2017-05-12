/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upo.connect4;

import static com.sun.corba.se.spi.presentation.rmi.StubAdapter.request;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.HorizontalLayout;

import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import es.upo.connect4.Database.Match;
import es.upo.connect4.Listeners.MatchsListener;
import es.upo.connect4.Database.MongoClientHelper;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marco
 */

public class PopUpGame extends UI /*implements Runnable*/{
       private  Tablero t;
       Match m;
       VerticalLayout v = new VerticalLayout();
       
    @Override
    protected void init(VaadinRequest request) {
            this.setImmediate(true);

            t = new Tablero(request.getParameter("u1"),request.getParameter("u2") );
            m = t.getMatch();
            this.setImmediate(true);
            v.addComponent(t.newTablero()); 
            v.setImmediate(true);
            this.setContent(  v);
            //(new Thread(this)).start();
            new MatchsListener(this,t,m,v).run();



               
    }

         
   /*  @Override
    public void run() {
        while(m.getTurn()<42){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(PopUpGame.class.getName()).log(Level.SEVERE, null, ex);
            }
            int turn = m.getTurn();
            Match match = MongoClientHelper.getMatch(m.getId());
            if(match.getTurn()>turn){
            v.removeAllComponents();

            v.addComponent(t.newTablero()); 
            v.setImmediate(true);
            
            }
        }
    }*/

  }
