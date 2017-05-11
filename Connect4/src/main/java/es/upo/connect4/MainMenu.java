/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upo.connect4;

import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import es.upo.connect4.Database.MongoClientHelper;
import es.upo.connect4.Database.User;
import java.io.File;
import java.util.List;

/**
 *
 * @author Marco
 */
public class MainMenu {
    
    
    public static void getMainMenu(HorizontalLayout horizontalLayout){
                        horizontalLayout.removeAllComponents();

        
        
        VerticalLayout usersChatLayout = UsersChatLayout.getUsuariosAndChat();

        horizontalLayout.addComponent(usersChatLayout);
        
    }
    
   
    

 
}
