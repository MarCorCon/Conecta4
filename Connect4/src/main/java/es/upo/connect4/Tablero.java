/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upo.connect4;

import com.vaadin.annotations.Theme;
import com.vaadin.server.FileResource;
import com.vaadin.server.Sizeable;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import es.upo.connect4.Database.EntityObject;
import es.upo.connect4.Database.Match;
import es.upo.connect4.Database.MongoClientHelper;
import es.upo.connect4.Database.User;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Marco
 */
@Theme("mytheme")
public class Tablero {
    VaadinSession vSession= VaadinSession.getCurrent();
        String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
    private String user =((User)vSession.getAttribute("user")).getUsername();
    HorizontalLayout arrows = new HorizontalLayout();
    private Match match;
    private GridLayout grid = new GridLayout(7, 6);
    FileResource emptyIconResource = new FileResource(new File(basepath
            + "/WEB-INF/icons/empty.png"));
    FileResource redIconResource = new FileResource(new File(basepath
            + "/WEB-INF/icons/red.png"));
    FileResource yellowIconResource = new FileResource(new File(basepath
            + "/WEB-INF/icons/yellow.png"));
    String colors = "rye";
    String[] arrowStyles = {basepath + "/WEB-INF/icons/red_arrow_down.png", basepath + "/WEB-INF/icons/yellow_arrow_down.png"};
    // gestión temporánea de turnos
    char myColor;
    Label winnerLabel = new Label();
    private boolean insertPiece(int column) {
        boolean inserted = false;

        StringBuilder tableBuilder = new StringBuilder(match.getStatus());
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

            match.setStatus(tableBuilder.toString()); 
            updateCell(pos*7+column, myColor);
            
            inserted = true;
            //updateTable();

            List<Integer> onLine;
            onLine = checkWin(pos * 7 + column);
            if (onLine.size() == 4) {
                if(match.getTurn()%2== 0){
                    winnerLabel.setCaption("GANA " + match.getP2());
                    Notification.show("GANA " + match.getP2(), Notification.Type.ERROR_MESSAGE);
                    match.setWinner(match.getP2());
                    match.setTurn(42);
                     
                }else{
                    winnerLabel.setCaption("GANA" +match.getP1());
                    Notification.show("GANA " + match.getP1(), Notification.Type.ERROR_MESSAGE);
                    match.setTurn(42);

                    
                }
            }
            match.setTurn(match.getTurn()+1);
            MongoClientHelper.updateEntity(match);
            updateArrows();

        }

        return inserted;
    }
    private Tablero(String player2){
        List <Match> matches = MongoClientHelper.findOpenMatch(user, player2);
        if(matches.isEmpty()){
            Match thisMatch = new Match(user, player2);
            EntityObject matchEntity = thisMatch;
            MongoClientHelper.createEntity(matchEntity);
            do{
               matches = MongoClientHelper.findOpenMatch(user, player2);

            }while(matches.isEmpty());
           
        }else{
            match = matches.get(0);
            if(match.getP1().equals(user)){
                myColor = colors.charAt(1);
            }else{
                myColor = colors.charAt(0);
            }
        }
         
         
        
        
    }
    public static void newTablero(HorizontalLayout horizontalLayout, String otherPlayer){
                
                Tablero t = new Tablero(otherPlayer);
                horizontalLayout.removeAllComponents();
                VerticalLayout menuIzquierda = new VerticalLayout();
                VerticalLayout menuDerecha = UsersChatLayout.getUsuariosAndChat();
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
        FileResource arrowIconResource = new FileResource(new File(arrowStyles[match.getTurn() % 2]));
        for (int i = 0; i < 7; i++) {
            final int bi = i;
            Button b = new Button(arrowIconResource);
            b.setHeight("100px");
            b.setWidth("100px");
            b.addClickListener(new Button.ClickListener() {

                @Override
                public void buttonClick(Button.ClickEvent event) {
                    if (match.getTurn() < 42) {
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
            updateCell(i,match.getStatus().charAt(i));
        }
    }

    private void updateCell(int pos, char colorCode){
        
        Image im = new Image();
            
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
            
                        im.setWidth("100px");

                       //im.setStyleName("boardCell");
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
        while (i >= 0 && match.getStatus().charAt(i) == myColor) {
            n.add(i);
            i -= 7;
        }
        //busco hacia el sur;
        List<Integer> s = new ArrayList<>();
        i = x + 7;
        while (i < 42 && match.getStatus().charAt(i) == myColor) {
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
        while (i / 7 == row && match.getStatus().charAt(i) == myColor) {
            w.add(i);
            i--;
        }
        //busco hacia el este
        List<Integer> e = new ArrayList<>();
        i = x + 1;
        while (i / 7 == row && match.getStatus().charAt(i) == myColor) {
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
        while (i >= 0 && i % 7 != 6 && match.getStatus().charAt(i) == myColor) {
            nw.add(i);
            i -= 8;
        }
        //busco hacia el sureste
        List<Integer> se = new ArrayList<>();
        i = x + 8;
        while (i < 42 && i % 7 != 0 && match.getStatus().charAt(i) == myColor) {
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
        while (i < 42 && i % 7 != 6 && match.getStatus().charAt(i) == myColor) {
            sw.add(i);
            i += 6;
        }
        // busco hacia el noreste
        List<Integer> ne = new ArrayList<>();
        i = x - 6;
        while (i >= 0 && i % 7 != 0 && match.getStatus().charAt(i) == myColor) {
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
