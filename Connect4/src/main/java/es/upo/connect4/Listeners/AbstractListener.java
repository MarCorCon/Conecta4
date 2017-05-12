/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upo.connect4.Listeners;

import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.UI;
import es.upo.connect4.MyUI;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author S
 */
public abstract class AbstractListener extends Thread{
    UI ui;
    String time;
    public abstract void doWork();
    
    public void setUI(UI ui){
        this.ui=ui;
    }    
    
    @Override
    public void run() {
        try {
                
                while(true){
                    Thread.sleep( 1000 );
                    time = new Timestamp(System.currentTimeMillis()).toString();
                    /*ui.access( new Runnable()
                    {
                        @Override
                        public void run ()
                        {*/
                            doWork();
                        /*}
                    } );*/
                }
                
            } catch (InterruptedException ex) {
                Logger.getLogger(MyUI.class.getName()).log(Level.SEVERE, null, ex);
            }    
    }
    
    
    
}
