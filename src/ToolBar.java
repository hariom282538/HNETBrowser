/*
 * Copyright (c) 2012-2014 HNET Corp. All rights reserved.
 * HNET PROPRIETARY and CONFIDENTIAL.
 * Use is subject to license terms.
 */




import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.demo.resources.Resources;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;
import com.teamdev.jxbrowser.chromium.events.StartLoadingEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * @author TeamDev Ltd.
 */
public class ToolBar extends JPanel {
    private static final String RUN_JAVASCRIPT = "Run JavaScript...";
    private static final String CLOSE_JAVASCRIPT = "Close JavaScript Console";

    private JButton backwardButton;
    private JButton forwardButton;
    private JButton refreshButton;
    private JButton stopButton;
    private JMenuItem consoleMenuItem;

    public ToolBar(Browser browser) {
        setLayout(new GridBagLayout());
        add(createActionsPane(browser), new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        add(createAddressBar(browser), new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(4, 0, 4, 5), 0, 0));
        add(createMenuButton(), new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.LINE_END, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
    }

    public void didJSConsoleClose() {
        consoleMenuItem.setText(RUN_JAVASCRIPT);
    }

    private JPanel createActionsPane(Browser browser) {
        backwardButton = createBackwardButton(browser);
        forwardButton = createForwardButton(browser);
        refreshButton = createRefreshButton(browser);
        stopButton = createStopButton(browser);

        JPanel actionsPanel = new JPanel();
        actionsPanel.add(backwardButton);
        actionsPanel.add(forwardButton);
        actionsPanel.add(refreshButton);
        actionsPanel.add(stopButton);
        return actionsPanel;
    }

    private JTextField createAddressBar(final Browser browser) {
        final JTextField result = new JTextField("about:blank");
        result.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                browser.loadURL(result.getText());
            }
        });
        browser.addLoadListener(new LoadAdapter() {
            @Override
            public void onStartLoadingFrame(StartLoadingEvent event) {
                if (event.isMainFrame()) {
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            refreshButton.setEnabled(false);
                            stopButton.setEnabled(true);
                        }
                    });
                }
            }

            @Override
            public void onFinishLoadingFrame(final FinishLoadingEvent event) {
                if (event.isMainFrame()) {
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            result.setText(event.getValidatedURL());

                            refreshButton.setEnabled(true);
                            stopButton.setEnabled(false);

                            Browser browser = event.getBrowser();
                            final boolean canGoForward = browser.canGoForward();
                            final boolean canGoBack = browser.canGoBack();
                            SwingUtilities.invokeLater(new Runnable() {
                                public void run() {
                                    forwardButton.setEnabled(canGoForward);
                                    backwardButton.setEnabled(canGoBack);
                                }
                            });
                        }
                    });
                }
            }
        });
        return result;
    }

    private static JButton createBackwardButton(final Browser browser) {
        return createButton("Back", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                browser.goBack();
            }
        });
    }

    private static JButton createForwardButton(final Browser browser) {
        return createButton("Forward", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                browser.goForward();
            }
        });
    }

    private static JButton createRefreshButton(final Browser browser) {
        return createButton("Refresh", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                browser.reload();
            }
        });
    }

    private static JButton createStopButton(final Browser browser) {
        return createButton("Stop", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                browser.stop();
            }
        });
    }

    private static JButton createButton(String caption, Action action) {
        ActionButton button = new ActionButton(caption, action);
        String imageName = caption.toLowerCase();
        button.setIcon(Resources.getIcon(imageName + ".png"));
        button.setRolloverIcon(Resources.getIcon(imageName + "-selected.png"));
        return button;
    }

    private JComponent createMenuButton() {
        final JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.add(Text2SpeechMenuItem());
       popupMenu.addSeparator();
       popupMenu.add(SearchCrawlerMenuItem());
       popupMenu.addSeparator();
        popupMenu.add(DownloadManagerMenuItem());
        popupMenu.addSeparator();
        popupMenu.add(ZipperMenuItem());
       popupMenu.addSeparator();
        popupMenu.add(createAboutMenuItem());
        popupMenu.addSeparator();
        popupMenu.add(createConsoleMenuItem());
        popupMenu.addSeparator();
        popupMenu.add(createCloseTabMenuItem());

        final ActionButton button = new ActionButton("Preferences", null);
        button.setIcon(Resources.getIcon("gear.png"));
        button.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if ((e.getModifiers() & InputEvent.BUTTON1_MASK) != 0) {
                    popupMenu.show(e.getComponent(), 0, button.getHeight());
                } else {
                    popupMenu.setVisible(false);
                }
            }
        });
        return button;
    }

    private JMenuItem SearchCrawlerMenuItem() {
        JMenuItem menuItem = new JMenuItem("HNET Crawler");
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               SearchCrawler crawler = new SearchCrawler();
                  crawler.show();
            }
        });
        return menuItem;
    }
    
    
      private JMenuItem DownloadManagerMenuItem() {
        JMenuItem menuItem = new JMenuItem("HNET Downloader");
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DownloadManager manager = new DownloadManager();
                manager.show();
            }
        });
        return menuItem;
    }

      private JMenuItem ZipperMenuItem() {
        JMenuItem menuItem = new JMenuItem("HNET Zipper");
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               String col[] = {"Name", "Size", "Location"};
                Zipper z = new Zipper();
                 z.main(col);
            }
        });
        return menuItem;
    }
      private JMenuItem Text2SpeechMenuItem() {
        JMenuItem menuItem = new JMenuItem("Text2Speech");
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               String col[] = {"Name", "Size", "Location"};
                Text2Speech t = new Text2Speech();
                 t.main(col);
            }
        });
        return menuItem;
    }



    

    private JMenuItem createConsoleMenuItem() {
        consoleMenuItem = new JMenuItem(RUN_JAVASCRIPT);
        consoleMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (RUN_JAVASCRIPT.equals(consoleMenuItem.getText())) {
                    consoleMenuItem.setText(CLOSE_JAVASCRIPT);
                    firePropertyChange("JSConsoleDisplayed", false, true);
                } else {
                    consoleMenuItem.setText(RUN_JAVASCRIPT);
                    firePropertyChange("JSConsoleClosed", false, true);
                }
            }
        });
        return consoleMenuItem;
    }

    

    private JMenuItem createAboutMenuItem() {
        JMenuItem menuItem = new JMenuItem("About HNET Browser");
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(ToolBar.this);
                AboutDialog aboutDialog = new AboutDialog(parentFrame);
                aboutDialog.setVisible(true);
            }
        });
        return menuItem;
    }
    private JMenuItem createCloseTabMenuItem() {
        JMenuItem menuItem = new JMenuItem("Close Tab");
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                firePropertyChange("TabClosed", false, true);
            }
        });
        return menuItem;
    }
    
     
     

    private static class ActionButton extends JButton {
        private ActionButton(String hint, Action action) {
            super(action);
            setContentAreaFilled(false);
            setBorder(BorderFactory.createEmptyBorder());
            setBorderPainted(false);
            setRolloverEnabled(true);
            setToolTipText(hint);
            setText(null);
            setFocusable(false);
            setDefaultCapable(false);
        }
    }

}