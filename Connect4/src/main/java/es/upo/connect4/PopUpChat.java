/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upo.connect4;

import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import es.upo.connect4.Database.MongoClientHelper;
import es.upo.connect4.Database.User;
import es.upo.connect4.Listeners.ChatsListener;
import java.util.List;

/**
 *
 * @author Marco
 */
public class PopUpChat extends UI{
     protected void init(VaadinRequest request) {  
         
        VerticalLayout chatLayout = new VerticalLayout();
        VerticalLayout sendLayout = new VerticalLayout();
        
        chatLayout.addComponent(sendLayout);
        User user = (User) VaadinSession.getCurrent().getAttribute("user");
        Label chatTitle = new Label("Chat con " + request.getParameter("u2"));
        chatLayout.addComponent(chatTitle);
        List<es.upo.connect4.Database.Chat> chats = MongoClientHelper.findChats(user.getUsername(), request.getParameter("u2"));
        for(es.upo.connect4.Database.Chat ch : chats){
            chatLayout.addComponent(new Label(ch.getText()));
        }
        setContent(chatLayout);
        new ChatsListener(this,chatLayout,user.getUsername(),request.getParameter("u2")).doWork();
               
    }   

  
}
