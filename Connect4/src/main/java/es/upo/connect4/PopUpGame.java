/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upo.connect4;

import com.vaadin.server.VaadinRequest;

import com.vaadin.ui.UI;

/**
 *
 * @author Marco
 */

public class PopUpGame extends UI{
        @Override
    protected void init(VaadinRequest request) {
            Tablero t = new Tablero(request.getParameter("u1"),request.getParameter("u2") );
               this.setContent(  t.newTablero());


               
    }

  
}
