/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upo.connect4;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import es.upo.connect4.Database.MongoClientHelper;
import es.upo.connect4.Database.User;
import java.io.Serializable;

/**
 *
 * @author Daniel
 */
public class Login implements Serializable{

    private  VaadinSession vSession = VaadinSession.getCurrent();
    private  TextField nameField = new TextField("Introduzca su nombre de usuario");
    private  PasswordField passwordField = new PasswordField("Introduzca su contraseña");
    private  Button loginButton = new Button("login");
    private  Button signButton = new Button("Sign in");

    public void muestraLogin(HorizontalLayout hl) {
        
        hl.removeAllComponents();
        
        loginButton.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                realizarLogin(nameField.getValue(), passwordField.getValue());
                if (vSession.getAttribute("user") != null) {
                    MainMenu mm = new MainMenu();
                    mm.getMainMenu(hl);
                }
            }
        });
        
        signButton.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {                
                new SignIn().muestaSigIn(hl);
            }
        });        
        
        VerticalLayout vl = new VerticalLayout();
        vl.addComponent(new Label("LOGIN"));
        vl.addComponent(nameField);
        vl.addComponent(passwordField);
        vl.addComponents(loginButton,signButton);
        hl.addComponent(vl);

    }

    private void realizarLogin(String username, String password) {
        User u = MongoClientHelper.findUser(username, password);
        if (u != null) {
            vSession.setAttribute("user", u);

        } else {
            Notification sample = new Notification("Login incorrecto");
            sample.show("Login incorrecto", "Intentelo de nuevo", Notification.Type.ERROR_MESSAGE);
        }

    }

}
