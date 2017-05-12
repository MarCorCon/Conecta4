/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upo.connect4;

import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marco
 */
//@Theme("mytheme")
public class Tablero {
     private   String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
    private String user; 
    private HorizontalLayout arrows = new HorizontalLayout();
    private Match match;
    private int userInt;
    private GridLayout grid = new GridLayout(7, 6);
    private FileResource emptyIconResource = new FileResource(new File(basepath
            + "/WEB-INF/icons/empty.png"));
    private FileResource redIconResource = new FileResource(new File(basepath
            + "/WEB-INF/icons/red.png"));
    private FileResource yellowIconResource = new FileResource(new File(basepath
            + "/WEB-INF/icons/yellow.png"));
    private String colors = "rye";
    private String[] arrowStyles = {basepath + "/WEB-INF/icons/red_arrow_down.png", basepath + "/WEB-INF/icons/yellow_arrow_down.png"};
    // gestión temporánea de turnos
    private char myColor;
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
                if(match.getTurn()%2 == 0){
                    User ganador = MongoClientHelper.findUser(match.getP2());
                    ganador.setWon(ganador.getWon()+1);
                    MongoClientHelper.updateEntity(ganador);
                    winnerLabel.setCaption("GANA " + match.getP2());
                    Notification.show("GANA " + match.getP2(), Notification.Type.ERROR_MESSAGE);
                    match.setWinner(match.getP2());
                    match.setTurn(42);
                     
                }else{
                    winnerLabel.setCaption("GANA" +match.getP1());
                    Notification.show("GANA " + match.getP1(), Notification.Type.ERROR_MESSAGE);
                    match.setWinner(match.getP1());
                    User ganador = MongoClientHelper.findUser(match.getP1());
                    ganador.setWon(ganador.getWon()+1);
                    MongoClientHelper.updateEntity(ganador);
                    match.setTurn(42);

                    
                }
            }
            match.setTurn(match.getTurn()+1);
            if(match.getTurn()== 42&& match.getWinner().equals("none")){
                User emp1 = MongoClientHelper.findUser(match.getP1());
                    emp1.setDraws(emp1.getDraws()+1);
                    MongoClientHelper.updateEntity(emp1);
                    
                      User emp2 = MongoClientHelper.findUser(match.getP2());
                    emp2.setDraws(emp2.getDraws()+1);
                    MongoClientHelper.updateEntity(emp2);
            }
            
            MongoClientHelper.updateEntity(match);
            match = MongoClientHelper.findOpenMatch(match.getP1(), match.getP2()).get(0);
            updateTable();
            updateArrows();

        }

        return inserted;
    }
    public Tablero(String me, String other){
        user = me;
        List <Match> matches = MongoClientHelper.findOpenMatch(user, other);
       
        if(matches.size() == 0){


            Match thisMatch = new Match(user, other);
            EntityObject matchEntity = thisMatch;
            MongoClientHelper.createEntity(matchEntity);
            match = MongoClientHelper.findOpenMatch(user, other).get(0);
           
           
        }else{
            match = matches.get(0);
           System.out.println("Ya estaba");
            
        }
         if(match.getP1().equals(user)){
                userInt = 1;
            }else{
                userInt = 0;
            }
            myColor = colors.charAt(userInt);
    }
    
    public HorizontalLayout newTablero(){
                
                               HorizontalLayout hl = new HorizontalLayout();
// These buttons take the minimum size.
        updateTable();
        updateArrows();
        VerticalLayout vl = new VerticalLayout();
        vl.addComponent(arrows);
        vl.addComponent(grid);
        vl.addComponent(winnerLabel);
        hl.addComponent(vl);
        return hl;
    }

    public Match getMatch() {
        return match;
    }

    private void updateArrows() {
        arrows.removeAllComponents();
        FileResource arrowIconResource = new FileResource(new File(arrowStyles[match.getTurn() % 2]));
        for (int i = 0; i < 7; i++) {
            final int bi = i;
           //Button b = new Button(arrowIconResource);
           Image b = new Image();
           b.setSource(arrowIconResource);
            b.setHeight("100px");
            b.setWidth("100px");
            b.addClickListener((event) -> {
               if (match.getTurn() < 42 && match.getTurn()%2 == userInt) {
                        insertPiece(bi);
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
