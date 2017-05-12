/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upo.connect4;

import com.vaadin.event.UIEvents;
import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import es.upo.connect4.Database.Match;
import es.upo.connect4.Database.MongoClientHelper;
import es.upo.connect4.Database.User;
import java.io.File;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Marco
 */
public class UsersChatLayout implements Serializable{

    private String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();

    private  FileResource chatIconResource = new FileResource(new File(basepath
            + "/WEB-INF/icons/chat-icon.png"));
    private  FileResource gameIconResource = new FileResource(new File(basepath
            + "/WEB-INF/icons/c4icon.png"));
    private VaadinSession vSession = VaadinSession.getCurrent();

    public VerticalLayout getUsuariosAndChat() {
        VaadinSession vSession= VaadinSession.getCurrent();
        List<User> listaUsuarios = MongoClientHelper.findAllUsers();
        VerticalLayout vl = new VerticalLayout();
        vl.setWidth("20%");
        User user = (User) vSession.getAttribute("user");
        HorizontalLayout usersHl = new HorizontalLayout();
        ComboBox comboUsuarios = new ComboBox("Usuarios");
        usersHl.addComponent(comboUsuarios);
        for (User u : listaUsuarios) {
            if(!u.getUsername().equals(user.getUsername()))
            comboUsuarios.addItem(u.getUsername());
        }
        BrowserWindowOpener chatOpener = new BrowserWindowOpener(PopUpChat.class);
        BrowserWindowOpener gameOpener = new BrowserWindowOpener(PopUpGame.class);

        comboUsuarios.addValueChangeListener(event
                -> chatOpener.setParameter("u2", comboUsuarios.getValue().toString()));
        comboUsuarios.addValueChangeListener(event
                -> gameOpener.setParameter("u2", comboUsuarios.getValue().toString()));

        Button newChatButton = new Button(chatIconResource);
        chatOpener.setFeatures("height=300,width=300");
        chatOpener.extend(newChatButton);
        // Add a parameter
        chatOpener.setParameter("u1", user.getUsername());
        // Set a fragment
        chatOpener.setUriFragment("connect4chat");
        usersHl.addComponent(newChatButton);

        Button newGameButton = new Button(gameIconResource);
        gameOpener.setFeatures("height=800,width=800");

        gameOpener.extend(newGameButton);
        gameOpener.setParameter("u1", user.getUsername());
        gameOpener.setUriFragment("connect4match");

        usersHl.addComponent(newGameButton);
        
        vl.addComponent(usersHl);
        
        VerticalLayout openMatchesLayout = new VerticalLayout();
        
     
        
                Label partidosAbiertos = new Label("PARTIDOS ABIERTOS: ");
openMatchesLayout.addComponent(partidosAbiertos);
        List<String> openMatchesList = MongoClientHelper.findMyOpenMatches(user.getUsername());
        for(String thisUser : openMatchesList){

            Button b = new Button(thisUser);
                    b.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
             gameOpener.setParameter("u2", thisUser);
             
            }
        });
                    
                    openMatchesLayout.addComponent(b);
            
        }
       vl.addComponent(openMatchesLayout);
        return vl;
    }

   
}
