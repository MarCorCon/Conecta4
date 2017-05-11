/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upo.connect4;

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
public class Login {

    private static VaadinSession vSession = VaadinSession.getCurrent();
    private static TextField nameField = new TextField("Introduzca su nombre de usuario");
    private static PasswordField passwordField = new PasswordField("Introduzca su contraseña");
    private static Button loginButton = new Button("login");

    public static void muestraLogin(HorizontalLayout hl) {
        hl.removeAllComponents();
        loginButton.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                realizarLogin(nameField.getValue(), passwordField.getValue());
                if (vSession.getAttribute("user") != null) {
                    MainMenu.getMainMenu(hl);
                }
            }
        });
        VerticalLayout vl = new VerticalLayout();
        vl.addComponent(nameField);
        vl.addComponent(passwordField);
        vl.addComponent(loginButton);
        hl.addComponent(vl);

    }

    private static void realizarLogin(String username, String password) {
        User u = MongoClientHelper.findUser(username, password);
        if (u != null) {
            vSession.setAttribute("user", u);

        } else {
            Notification sample = new Notification("Login incorrecto");
            sample.show("Login incorrecto", "Intentelo de nuevo", Notification.Type.ERROR_MESSAGE);
        }

    }

}
