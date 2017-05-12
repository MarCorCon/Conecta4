/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upo.connect4;


import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import java.io.Serializable;

/**
 *
 * @author Marco
 */
public class MainMenu implements Serializable{
    
    
    public void getMainMenu(HorizontalLayout horizontalLayout){
                        horizontalLayout.removeAllComponents();

        
        UsersChatLayout ucl = new UsersChatLayout();
        
        VerticalLayout usersChatLayout = ucl.getUsuariosAndChat();

        horizontalLayout.addComponent(usersChatLayout);
        
    }
    
   
    

 
}
