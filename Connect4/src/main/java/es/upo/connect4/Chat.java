/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upo.connect4;

import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 *
 * @author Marco
 */
public class Chat {

    static VerticalLayout newChatWith(String username) {
        VerticalLayout chatLayout = new VerticalLayout();
        
        Label chatTitle = new Label("Chat con " + username);
        chatLayout.addComponent(chatTitle);
        return chatLayout;
    }
    
}
