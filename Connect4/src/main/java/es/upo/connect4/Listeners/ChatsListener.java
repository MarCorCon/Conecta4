/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upo.connect4.Listeners;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import es.upo.connect4.Database.Chat;
import es.upo.connect4.Database.MongoClientHelper;
import es.upo.connect4.MyUI;

/**
 *
 * @author S
 */
public class ChatsListener extends AbstractListener{
    VerticalLayout hl;    
    String userTo;
    String userFrom;
    public ChatsListener(UI ui,VerticalLayout hl,String userTo,String userFrom){
        this.ui=ui;
        this.hl=hl;
        this.userFrom=userFrom;
        this.userTo=userTo;
    }    

    @Override
    public void doWork() {
        for(Chat ch : MongoClientHelper.findLastChat("Superman", userTo, time)){
            hl.addComponent(new Label(ch.getText()));
        }
        
    }
    
}
