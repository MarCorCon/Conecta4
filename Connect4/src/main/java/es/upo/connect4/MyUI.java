package es.upo.connect4;

import com.vaadin.annotations.Push;
import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import es.upo.connect4.Database.Chat;
import es.upo.connect4.Database.Match;
import es.upo.connect4.Database.User;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This UI is the application entry point. A UI may either represent a browser
 * window (or tab) or some part of a html page where a Vaadin application is
 * embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is
 * intended to be overridden to add component to the user interface and
 * initialize non-component functionality.
 *
 * TODO: falta actualizar la vista con el nuevo tablero
 */
@Theme("mytheme")
@Push
public class MyUI extends UI {
    int lastPos;
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
    User loggedUser;
    Chat chat;
    Match match;
    

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        // Find the application directory

        // Show the image in the application
        final VerticalLayout layout = new VerticalLayout();
// These buttons take the minimum size.
        updateTable();
        updateArrows();
        layout.addComponent(arrows);
        layout.addComponent(grid);
        layout.addComponent(winnerLabel);
        
        setContent(layout);
    }

    /**
     * inicializa el tablero
     *
     * @return
     */
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

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
    
    
   /* 
    
    
    class ChatPush extends Thread
    {
        @Override
        public void run ()
        {
            
            try {
                Thread.sleep( 1000 );
                
                // Calling special 'access' method on UI object, for inter-thread communication.
                access( new Runnable()
                {
                    @Override
                    public void run ()
                    {
                     if(!chat.getFrom().equals(loggedUser.getId())){
                        //arrows.addComponent(new Label(chat.getText()));
                     }     
                    }
                } );
            } catch (InterruptedException ex) {
                Logger.getLogger(MyUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        
    }
    
    private void refreshNonOwnerChat(String text){
        chat = new Chat(loggedUser.getId(),text);
        match.addMessages(chat);
        new ChatPush().run();
    }
    
    
    
    
    class MatchPush extends Thread
    {
        @Override
        public void run ()
        {
            
            try {
                Thread.sleep( 1000 );
                
                access( new Runnable()
                {
                    @Override
                    public void run ()
                    {
                     if(true){
                         insertPiece(lastPos);
                     }     
                    }
                } );
            } catch (InterruptedException ex) {
                Logger.getLogger(MyUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        
    }*/ 
}
