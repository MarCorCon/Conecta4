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
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
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
import static es.upo.connect4.Login.muestraLogin;
import static es.upo.connect4.SignIn.muestaSigIn;
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

    private VaadinSession vSession = VaadinSession.getCurrent();

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        if (vSession.getAttribute("user") == null) {
            muestraLogin(horizontalLayout);
            muestaSigIn(horizontalLayout);
        } else {

            MainMenu.getMainMenu(horizontalLayout);

        }

        setContent(horizontalLayout);
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
