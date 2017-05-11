/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upo.connect4;

import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.ClientConnector;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
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
public class UsersChatLayout {

    private static String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();

    private static FileResource chatIconResource = new FileResource(new File(basepath
            + "/WEB-INF/icons/chat-icon.png"));
 private static FileResource gameIconResource = new FileResource(new File(basepath
            + "/WEB-INF/icons/c4icon.png"));
    public static VerticalLayout getUsuariosAndChat() {
        List<User> listaUsuarios = MongoClientHelper.findAllUsers();
        VerticalLayout vl = new VerticalLayout();
        vl.setWidth("20%");
        HorizontalLayout usersHl = new HorizontalLayout();
        ComboBox comboUsuarios = new ComboBox("Usuarios");
        usersHl.addComponent(comboUsuarios);
        for (User u : listaUsuarios) {
            comboUsuarios.addItem(u.getUsername());
        }
                BrowserWindowOpener chatOpener = new BrowserWindowOpener(PopUpChat.class);
                BrowserWindowOpener gameOpener = new BrowserWindowOpener(PopUpGame.class);

        
        comboUsuarios.addValueChangeListener(event ->
                chatOpener.setParameter("u2", comboUsuarios.getValue().toString()));
         comboUsuarios.addValueChangeListener(event ->
                gameOpener.setParameter("u2", comboUsuarios.getValue().toString()));
       
        Button newChatButton = new Button(chatIconResource);
        chatOpener.setFeatures("height=300,width=300");
        chatOpener.extend(newChatButton);
        // Add a parameter
        chatOpener.setParameter("u1", MyUI.user);
                // Set a fragment
        chatOpener.setUriFragment("myfragment");
        usersHl.addComponent(newChatButton);
        
                Button newGameButton = new Button(gameIconResource);
                gameOpener.setFeatures("height=300,width=300");

                gameOpener.extend(newGameButton);
                gameOpener.setParameter("u1", MyUI.user);
                 gameOpener.setUriFragment("myfragment");

        usersHl.addComponent(newGameButton);


        vl.addComponent(usersHl);
        return vl;
    }
}
