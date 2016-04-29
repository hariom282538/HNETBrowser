import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserFactory;

import javax.swing.*;
import java.awt.*;

/**
 * This sample demonstrates how to create JxBrowser instance,
 * add JxBrowser component to JFrame container and
 * navigate to the "www.google.com" web site.
 */
public class BrowserSample {
    public static void main(String[] args) {
        Browser browser = BrowserFactory.create();

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(browser.getView().getComponent(), BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        browser.loadURL("http://www.hnetbrowser.com");
    }
}
