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
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Marco
 */
public class Tablero {
        String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();

    private String table = "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee";
    HorizontalLayout arrows = new HorizontalLayout();

    private GridLayout grid = new GridLayout(7, 6);
    FileResource emptyIconResource = new FileResource(new File(basepath
            + "/WEB-INF/icons/empty.png"));
    FileResource redIconResource = new FileResource(new File(basepath
            + "/WEB-INF/icons/red.png"));
    FileResource yellowIconResource = new FileResource(new File(basepath
            + "/WEB-INF/icons/yellow.png"));
    String colors = "rye";
    String[] arrowStyles = {basepath + "/WEB-INF/icons/red_arrow_down.png", basepath + "/WEB-INF/icons/yellow_arrow_down.png"};
    int turn = 0; 
    // gestión temporánea de turnos
    char myColor;
    private int ganado = -1;
    Label winnerLabel = new Label();
    private boolean insertPiece(int column) {
        myColor = colors.charAt(turn % 2);
        boolean inserted = false;

        StringBuilder tableBuilder = new StringBuilder(table);
        if (tableBuilder.charAt(column) == 'e') {
            int pos = 0;
            for (int i = 1; i < 6; i++) {
                if (tableBuilder.charAt(7 * i + column) == 'e') {
                    pos = i;
                } else {
                    break;
                }

                //Page.getCurrent().reload();
            }
            tableBuilder.setCharAt(pos * 7 + column, myColor);

            table = tableBuilder.toString();
            updateCell(pos*7+column, myColor);

            inserted = true;
            //updateTable();

            List<Integer> onLine;
            onLine = checkWin(pos * 7 + column);
            if (onLine.size() == 4) {
                if(turn%2== 0){
                    winnerLabel.setCaption("GANA EL ROJO");
                    Notification.show("GANA EL ROJO", Notification.Type.ERROR_MESSAGE);
                     ganado = 0;
                }else{
                    winnerLabel.setCaption("GANA EL AMARILLO");
                     Notification.show("GANA EL AMARILLO", Notification.Type.ERROR_MESSAGE);

                     ganado = 1;
                }
            }
            turn++;
            updateArrows();

        }

        return inserted;
    }
    
    public static void newTablero(HorizontalLayout horizontalLayout){
                Tablero t = new Tablero();
                horizontalLayout.removeAllComponents();
                VerticalLayout menuIzquierda = new VerticalLayout();
        VerticalLayout menuDerecha = new VerticalLayout();
                Button homePageButton = new Button("Volver al menu principal");
                
                homePageButton.addClickListener(new Button.ClickListener() {

                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                       MainMenu.getMainMenu(horizontalLayout);
                    }

                });
                
                menuIzquierda.addComponent(homePageButton);
                                horizontalLayout.addComponent(menuIzquierda);

                horizontalLayout.addComponent(t.createTablero());
                horizontalLayout.addComponent(menuDerecha);
                
    }
    
    public VerticalLayout createTablero(){
        
        VerticalLayout tableroLayout = new VerticalLayout();
// These buttons take the minimum size.
        updateTable();
        updateArrows();
        tableroLayout.addComponent(arrows);
        tableroLayout.addComponent(grid);
        tableroLayout.addComponent(winnerLabel);
        
        return tableroLayout;
    }

    private void updateArrows() {
        arrows.removeAllComponents();
        FileResource arrowIconResource = new FileResource(new File(arrowStyles[turn % 2]));
        for (int i = 0; i < 7; i++) {
            final int bi = i;
            Button b = new Button(arrowIconResource);
            b.setHeight("200px");
            b.setWidth("200px");
            b.addClickListener(new Button.ClickListener() {

                @Override
                public void buttonClick(Button.ClickEvent event) {
                    if (ganado < 0) {
                        insertPiece(bi);
                    }
                }

            });
            arrows.addComponent(b);
        }

    }

    private void updateTable() {
        //grid.removeAllComponents();
        for (int i = 0; i < 42; i++) {
            updateCell(i, table.charAt(i));
        }
    }

    private void updateCell(int pos, char colorCode){
        
        Image im = new Image();
            im.setWidth("200px");
            switch (colorCode) {
                case 'e':
                    im.setSource(emptyIconResource);
                    break;
                case 'r':
                    im.setSource(redIconResource);
                    break;
                case 'y':
                    im.setSource(yellowIconResource);
            }
            if(grid.getComponent(pos % 7, pos / 7)!=null){
                grid.removeComponent(pos % 7, pos / 7);
            }
            grid.addComponent(im, pos % 7, pos / 7);
    }
    private List<Integer> checkWin(Integer x) {
        int row = x / 7;
        List<Integer> connected = new ArrayList<>();

        //, ne, e, se, s, sw, w, nw, connected 
        // busco hacia el norte:
        List<Integer> n = new ArrayList<>();
        int i = x - 7;
        while (i >= 0 && table.charAt(i) == myColor) {
            n.add(i);
            i -= 7;
        }
        //busco hacia el sur;
        List<Integer> s = new ArrayList<>();
        i = x + 7;
        while (i < 42 && table.charAt(i) == myColor) {
            s.add(i);
            i += 7;
        }
        //controlo vertical
        if (n.size() + s.size() == 3) {
            connected.addAll(n);
            connected.add(x);
            connected.addAll(s);
            return connected;
        }

        // busco hacia el oeste
        List<Integer> w = new ArrayList<>();
        i = x - 1;
        while (i / 7 == row && table.charAt(i) == myColor) {
            w.add(i);
            i--;
        }
        //busco hacia el este
        List<Integer> e = new ArrayList<>();
        i = x + 1;
        while (i / 7 == row && table.charAt(i) == myColor) {
            e.add(i);
            i++;
        }

        // controlo horizontal
        if (w.size() + e.size() == 3) {
            connected.addAll(w);
            connected.add(x);
            connected.addAll(e);
            return connected;
        }

        // busco hacia el noroeste
        List<Integer> nw = new ArrayList<>();
        i = x - 8;
        while (i >= 0 && i % 7 != 6 && table.charAt(i) == myColor) {
            nw.add(i);
            i -= 8;
        }
        //busco hacia el sureste
        List<Integer> se = new ArrayList<>();
        i = x + 8;
        while (i < 42 && i % 7 != 0 && table.charAt(i) == myColor) {
            se.add(i);
            i += 8;
        }

        // controlo diagonal nw se
        if (nw.size() + se.size() == 3) {
            connected.addAll(nw);
            connected.add(x);
            connected.addAll(se);
            return connected;
        }
        //busco hacia el suroeste
        List<Integer> sw = new ArrayList<>();
        i = x + 6;
        while (i < 42 && i % 7 != 6 && table.charAt(i) == myColor) {
            sw.add(i);
            i += 6;
        }
        // busco hacia el noreste
        List<Integer> ne = new ArrayList<>();
        i = x - 6;
        while (i >= 0 && i % 7 != 0 && table.charAt(i) == myColor) {
            ne.add(i);
            i -= 6;
        }
        // controlo diagonal nw se
        if (sw.size() + ne.size() == 3) {
            connected.addAll(sw);
            connected.add(x);
            connected.addAll(ne);

        }

        return connected;

    }
}
