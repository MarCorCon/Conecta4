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
import com.vaadin.ui.VerticalLayout;
import es.upo.connect4.Database.MongoClientHelper;
import es.upo.connect4.Database.User;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Marco
 */
public class MainMenu implements Serializable{
    
        private VaadinSession vSession = VaadinSession.getCurrent();

    public void getMainMenu(HorizontalLayout horizontalLayout){
                        horizontalLayout.removeAllComponents();

        User u = (User) vSession.getAttribute("user");
        UsersChatLayout ucl = new UsersChatLayout();
        
        VerticalLayout usersChatLayout = ucl.getUsuariosAndChat();
        usersChatLayout.setImmediate(true);
        VerticalLayout openMatchesLayout = new VerticalLayout();
        
        List<String> openMatchesList = MongoClientHelper.findMyOpenMatches(u.getUsername());
        for(String thisUser : openMatchesList){
                    BrowserWindowOpener gameOpener = new BrowserWindowOpener(PopUpGame.class);
                    gameOpener.setFeatures("height=800,width=800");
                    gameOpener.setUriFragment("connect4match");

            Button b = new Button(thisUser);
                    gameOpener.extend(b);
                    b.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
             gameOpener.setParameter(thisUser, u.getUsername());
             openMatchesLayout.addComponent(b);
            }
        });
                    

            
        }
       usersChatLayout.addComponent(openMatchesLayout);
        horizontalLayout.addComponent(usersChatLayout);
        
    }
    
   
    

 
}
