/*
 * Copyright (c) 2000-2013 TeamDev Ltd. All rights reserved.
 * TeamDev PROPRIETARY and CONFIDENTIAL.
 * Use is subject to license terms.
 */

package com.teamdev.jxbrowser.chromium.demo;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserFactory;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * @author TeamDev Ltd.
 */
public final class TabFactory {

    public static Tab createFirstTab() {
        return createTab("http://www.teamdev.com/jxbrowser");
    }

    public static Tab createTab() {
        return createTab("about:blank");
    }

    public static Tab createTab(String url) {
        Browser browser = BrowserFactory.create();
        browser.loadURL(url);

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
