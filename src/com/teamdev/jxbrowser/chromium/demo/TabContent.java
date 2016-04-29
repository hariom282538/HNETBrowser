/*
 * Copyright (c) 2000-2013 TeamDev Ltd. All rights reserved.
 * TeamDev PROPRIETARY and CONFIDENTIAL.
 * Use is subject to license terms.
 */

package com.teamdev.jxbrowser.chromium.demo;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * @author TeamDev Ltd.
 */
public class TabContent extends JPanel {

    private final Browser browser;
    private final ToolBar toolBar;
    private final JComponent jsConsole;
    private final JComponent container;
    private final JComponent browserContainer;

    public TabContent(final Browser browser) {
        this.browser = browser;
        this.browser.addLoadListener(new LoadAdapter() {
            @Override
            public void onFinishLoadingFrame(FinishLoadingEvent event) {
                if (event.isMainFrame()) {
                    firePropertyChange("PageTitleChanged", null, browser.getTitle());
                }
            }
        });

        browserContainer = createBrowserContainer();
        jsConsole = createConsole();
        toolBar = createToolBar(browser);

        container = new JPanel(new BorderLayout());
        container.add(browserContainer, BorderLayout.CENTER);

        setLayout(new BorderLayout());
        add(toolBar, BorderLayout.NORTH);
        add(container, BorderLayout.CENTER);
    }

    private ToolBar createToolBar(Browser browser) {
        ToolBar toolBar = new ToolBar(browser);
        toolBar.addPropertyChangeListener("TabClosed", new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                firePropertyChange("TabClosed", false, true);
            }
        });
        toolBar.addPropertyChangeListener("JSConsoleDisplayed", new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                showConsole();
            }
        });
        toolBar.addPropertyChangeListener("JSConsoleClosed", new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                hideConsole();
            }
        });
        return toolBar;
    }

    private void hideConsole() {
        showComponent(browserContainer);
    }

    private void showComponent(JComponent component) {
        container.removeAll();
        container.add(component, BorderLayout.CENTER);
        validate();
    }

    private void showConsole() {
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.add(browserContainer, JSplitPane.TOP);
        splitPane.add(jsConsole, JSplitPane.BOTTOM);
        splitPane.setResizeWeight(0.8);
        splitPane.setBorder(BorderFactory.createEmptyBorder());
        showComponent(splitPane);
    }

    private JComponent createConsole() {
        JSConsole result = new JSConsole(browser);
        result.addPropertyChangeListener("JSConsoleClosed", new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                hideConsole();
                toolBar.didJSConsoleClose();
            }
        });
        return result;
    }

    private JComponent createBrowserContainer() {
        JPanel container = new JPanel(new BorderLayout());
        container.add(browser.getView().getComponent(), BorderLayout.CENTER);
        return container;
    }

    public void dispose() {
        browser.dispose();
    }
}
