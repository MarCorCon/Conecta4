/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upo.connect4;

import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import es.upo.connect4.Database.MongoClientHelper;
import es.upo.connect4.Database.User;
import es.upo.connect4.Database.Chat;
import es.upo.connect4.Listeners.ChatsListener;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author Marco
 */
public class PopUpChat extends UI{
     protected void init(VaadinRequest request) { 
        User user = (User) VaadinSession.getCurrent().getAttribute("user");
        String me = user.getUsername(); 
        String other = request.getParameter("u2");
        VerticalLayout chatLayout = new VerticalLayout();
        VerticalLayout sendLayout = new VerticalLayout();
        TextField textToSend = new TextField("");          
        Button send = new Button("Send");
        
      //PONER BIEN EL CLICK LISTENER CON LO DE DENTRO y agregar boton al layout
      /*  send.addClickListener....{
             String text = textToSend.getValue();
             String time = new Timestamp(System.currentTimeMillis()).toString();
             MongoClientHelper.createEntity(new Chat(me,other,text,time));
        }*/
        send.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
               String text = textToSend.getValue();
             String time = new Timestamp(System.currentTimeMillis()).toString();
             MongoClientHelper.createEntity(new Chat(me,other,text,time));
            }
        });
        chatLayout.addComponent(sendLayout);
        
        Label chatTitle = new Label("Chat con " + other);
        chatLayout.addComponent(chatTitle);
        chatLayout.addComponent(textToSend);
        chatLayout.addComponent(send);
        List<es.upo.connect4.Database.Chat> chats = MongoClientHelper.findChats(me, other);
        for(es.upo.connect4.Database.Chat ch : chats){
            chatLayout.addComponent(new Label(ch.getText()));
        }
        setContent(chatLayout);
        new ChatsListener(this,chatLayout,me,other).run();
               
    }   

  
}
