/*
 * Copyright (c) 2000-2013 TeamDev Ltd. All rights reserved.
 * TeamDev PROPRIETARY and CONFIDENTIAL.
 * Use is subject to license terms.
 */



import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserFactory;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 * @author TeamDev Ltd.
 */
public final class TabFactory {

    public static Tab createFirstTab() {
        return createTab("http://www.google.com");
    }

    public static Tab createTab() {
        return createTab("about:blank");
    }

    public static Tab createTab(String url) {
        Browser browser = BrowserFactory.create();
        browser.loadURL(url);
       class PopUpDemo extends JPopupMenu {
    JMenuItem anItem;
    public PopUpDemo(){
        anItem = new JMenuItem("Click Me!");
        add(anItem);
    }
}
  /*  browser is the instance of Browser class which is associated with rendering engine which doesn't 
   * support right click context menu...REASON:it works on chromium RE but only support windowless Addons.
   * in simple langauge it is not a part of RE and we can't add new functionalities in it.


   class PopClickListener extends MouseAdapter {
    public void mousePressed(MouseEvent e){
        if (e.isPopupTrigger())
            doPop(e);
    }

    public void mouseReleased(MouseEvent e){
        if (e.isPopupTrigger())
            doPop(e);
    }

    private void doPop(MouseEvent e){
        PopUpDemo menu = new PopUpDemo();
        menu.show(e.getComponent(), e.getX(), e.getY());
    }
}
        browser.addMouseListener(new PopClickListener());
   
   */
        final TabCaption tabCaption = new TabCaption();
        tabCaption.setTitle("about:blank");

        TabContent tabContent = new TabContent(browser);
        tabContent.addPropertyChangeListener("PageTitleChanged", new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                tabCaption.setTitle((String) evt.getNewValue());
            }
        });
        return new Tab(tabCaption, tabContent);

    }
}
