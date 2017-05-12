/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upo.connect4;

import static com.sun.corba.se.spi.presentation.rmi.StubAdapter.request;
import com.vaadin.event.UIEvents;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;

import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import es.upo.connect4.Database.Match;
import es.upo.connect4.Database.MongoClientHelper;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marco
 */

public class PopUpGame extends UI {

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
            this.setContent(v);
            //(new Thread(this)).start();
           // new MatchsListener(this,t,m,v).doWork();
            this.setPollInterval(1000);
         
              this.addPollListener(new UIEvents.PollListener() {
            @Override
            public void poll(UIEvents.PollEvent event) {
                if(m.getTurn()%2 == 0){
                    Notification.show("Turno de " + m.getP2(), Notification.Type.TRAY_NOTIFICATION);
                }else{
           Notification.show("Turno de " + m.getP1(), Notification.Type.TRAY_NOTIFICATION);


                }
                if(m.getTurn()<42){
                    Match newMatch = MongoClientHelper.getMatch(m.getId());
                    if(newMatch.getTurn() > m.getTurn()){
                        t.updateArrows();
                        String oldStatus = m.getStatus();
                        String newStatus = newMatch.getStatus();
                        for(int i = 0; i<42 ; i++){
                            if(oldStatus.charAt(i) != newStatus.charAt(i)){
                                t.updateCell(i, newStatus.charAt(i));
                                break;
                            }
                        }
                        m = newMatch;
                    }

                }else{
                    if(m.getWinner().equals(m.getP1())){
                                            Notification.show("GANA " + m.getP1(), Notification.Type.ERROR_MESSAGE);

                    }else if(m.getWinner().equals(m.getP2())){
                                            Notification.show("GANA " + m.getP2(), Notification.Type.ERROR_MESSAGE);
                        
                    }else{
                        Notification.show("EMPATE " + m.getP1(), Notification.Type.ERROR_MESSAGE);
                    }
                }
       
        
          }
        });

    }

         
  
  }
