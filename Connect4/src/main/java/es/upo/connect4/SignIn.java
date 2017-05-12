/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upo.connect4;

import com.google.gson.JsonObject;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import es.upo.connect4.Database.MongoClientHelper;
import es.upo.connect4.Database.User;

/**
 *
 * @author Daniel
 */
public class SignIn {

    private  VaadinSession vSession = VaadinSession.getCurrent();
    private  TextField nameField = new TextField("Introduzca su nombre de usuario");
    private  PasswordField passwordField = new PasswordField("Introduzca su contraseña");
    private  Button signInButton = new Button("Sign In");

    public void muestaSigIn(HorizontalLayout hl) {

        signInButton.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                realizarLogin(nameField.getValue(), passwordField.getValue());
                if (vSession.getAttribute("user") != null) {
                    MainMenu mm = new MainMenu();
                    mm.getMainMenu(hl);
                    }

            }
        });
        VerticalLayout vl = new VerticalLayout();
        vl.addComponent(nameField);
        vl.addComponent(passwordField);
        vl.addComponent(signInButton);
        hl.addComponent(vl);

    }

    private void realizarLogin(String username, String password) {
        User u = new User(username, password);
       /* JsonObject json = new JsonObject();
        EntityObject jlol=new 
        json.addProperty("username", username);
        json.addProperty("password", password);*/
        MongoClientHelper.createEntity(u);
        vSession.setAttribute("user", u);

    }
}
