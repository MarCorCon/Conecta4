/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upo.connect4;

import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

/**
 *
 * @author Marco
 */
public class MainMenu {
    public static void getMainMenu(HorizontalLayout horizontalLayout){
                        horizontalLayout.removeAllComponents();

               VerticalLayout menuIzquierda = new VerticalLayout();
        VerticalLayout menuDerecha = new VerticalLayout();
        
        Button newGameButton = new Button("Nuevo partido");
        ComboBox listaUsuarios = new ComboBox("Usuarios");

        newGameButton.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                Tablero.newTablero(horizontalLayout);
            }

        });
        menuIzquierda.addComponent(newGameButton);
        menuDerecha.addComponent(listaUsuarios);
        horizontalLayout.addComponent(menuIzquierda);
        horizontalLayout.addComponent(menuDerecha);
        
    }
    

 
}
