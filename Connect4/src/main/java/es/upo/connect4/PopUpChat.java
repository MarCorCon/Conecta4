/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upo.connect4;

import com.vaadin.event.UIEvents;
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
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import java.util.List;

/**
 *
 * @author Marco
 */
public class PopUpChat extends UI{
     protected void init(VaadinRequest request) { 
         SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmsS");
        User user = (User) VaadinSession.getCurrent().getAttribute("user");
        final String me = user.getUsername(); 
        final String other = request.getParameter("u2");

        VerticalLayout chatLayout = new VerticalLayout();
        //chatLayout.setImmediate(true);
        VerticalLayout sendLayout = new VerticalLayout();
        TextField textToSend = new TextField("");          
        Button send = new Button("Send");

        send.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                String text = textToSend.getValue();
                Date now =  new Date();
                Long time = Long.parseLong(sdf.format(now));
             //String time = new Timestamp(System.currentTimeMillis()).toString();
             
             MongoClientHelper.createEntity(new Chat(me,other,text,time));
            }
        });
             
       
        VerticalLayout msgsLayout = new VerticalLayout();
        Label chatTitle = new Label("Chat con " + other);
        chatLayout.addComponent(chatTitle);
        List<Chat > chats = MongoClientHelper.findChats(me, other);
         Collections.sort(chats);
        for(Chat ch : chats){
             
            msgsLayout.addComponent(new Label(ch.getFrom() +":                                  "+ch.getText()));
            msgsLayout.addComponent(new Label("*   *   *   *   *   *   *   *   *   *   *"));

        }
         sendLayout.addComponent(textToSend);
        sendLayout.addComponent(send);
        chatLayout.addComponent(sendLayout);
        chatLayout.addComponent(msgsLayout);
        setContent(chatLayout);

               this.setPollInterval(1000);
         
              this.addPollListener(new UIEvents.PollListener() {
            @Override
            public void poll(UIEvents.PollEvent event) {
        msgsLayout.removeAllComponents();
               List<Chat> newChats = MongoClientHelper.findChats(me, other);
               Collections.sort(newChats);
               
       for(Chat ch : newChats){
            msgsLayout.addComponent(new Label(ch.getFrom() +":                                  "+ch.getText()));
            msgsLayout.addComponent(new Label("*   *   *   *   *   *   *   *   *   *   *"));

       }            }
        });
      

       
        

               
    }   

  
}
