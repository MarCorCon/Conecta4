/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upo.connect4;

import com.vaadin.annotations.Push;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import java.io.Serializable;
import es.upo.connect4.Database.MongoClientHelper;
import es.upo.connect4.Database.User;
import java.util.List;

@Push
public class Chat {

    static VerticalLayout newChatWith(String username) {
        VerticalLayout chatLayout = new VerticalLayout();
        User user = (User) VaadinSession.getCurrent().getAttribute("user");
        Label chatTitle = new Label("Chat con " + username);
        chatLayout.addComponent(chatTitle);
        List<es.upo.connect4.Database.Chat> chats = MongoClientHelper.findChats(user.getUsername(), username);
        for(es.upo.connect4.Database.Chat ch : chats){
            chatLayout.addComponent(new Label(ch.getText()));
        }
        
        return chatLayout;
    }
    
}
