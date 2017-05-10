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
        
        Button newChatButton = new Button(chatIconResource);

        newChatButton.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                VerticalLayout chatLayout = Chat.newChatWith("userID");
                vl.addComponent(chatLayout);
            }

        });
        usersHl.addComponent(newChatButton);
        vl.addComponent(usersHl);
        return vl;
    }
}
