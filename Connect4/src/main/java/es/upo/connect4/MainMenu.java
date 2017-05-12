/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upo.connect4;


import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import es.upo.connect4.Database.MongoClientHelper;
import es.upo.connect4.Database.User;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Marco
 */
public class MainMenu implements Serializable{
    
        private VaadinSession vSession = VaadinSession.getCurrent();

    public void getMainMenu(HorizontalLayout horizontalLayout){
                        horizontalLayout.removeAllComponents();
                        getLogoutButton(horizontalLayout);

        User u = (User) vSession.getAttribute("user");
        UsersChatLayout ucl = new UsersChatLayout();
        
        VerticalLayout usersChatLayout = ucl.getUsuariosAndChat();
        usersChatLayout.setImmediate(true);
        VerticalLayout clasificacion = new VerticalLayout();
        
        Label l = new Label("CLASIFICACIÓN:");
        
        clasificacion.addComponent(l);
        List<User> listaUsuarios = MongoClientHelper.findAllUsers();
        Collections.sort(listaUsuarios);
        for (int i = 0;i<10;i++) {
            User player = listaUsuarios.get(i);
            int points = 3 *player.getWon() + player.getDraws();
            Label playerLabel = new Label((i+1) +": " + player.getUsername() + "          W: " +player.getWon() + "       D: "+ player.getDraws()+  "       D: "+ player.getDraws()+  "       POINTS: "+ points);
            clasificacion.addComponent(new Label("________________________________________________________________________________________"));
           clasificacion.addComponent(playerLabel);
           clasificacion.addComponent(new Label("________________________________________________________________________________________"));
        }
       usersChatLayout.addComponent(openMatchesLayout);
       horizontalLayout.addComponent(usersChatLayout);       
    }
    
    public void getLogoutButton(HorizontalLayout horizontalLayout){
        Button logout = new Button("Log out");
        logout.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                if (vSession.getAttribute("user") != null) {
                   vSession.setAttribute("user", null);
                }
                new Login().muestraLogin(horizontalLayout);
            }
        });
        horizontalLayout.addComponent(logout);
        horizontalLayout.addComponent(clasificacion);
        horizontalLayout.addComponent(usersChatLayout);
        
    }
    
   
    

 
}
