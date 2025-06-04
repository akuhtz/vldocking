package com.vlsolutions.swing.sample;

import com.vlsolutions.swing.docking.DockingConstants;

import javax.swing.SwingUtilities;

public class MyTabbedDockApp extends DockedApplication {

    public MyTabbedDockApp() {
        desk.addDockable(editorPanel);
        desk.split(editorPanel, treePanel, DockingConstants.SPLIT_LEFT);
        desk.split(editorPanel, buttonGrid, DockingConstants.SPLIT_RIGHT);
        desk.createTab(buttonGrid, tablePanel, 0, true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().add(desk);
    }

    public static void main(String[] args) {
        MyTabbedDockApp frame = new MyTabbedDockApp();
        frame.setName("parent");
        frame.setSize(800, 600);
        frame.validate();
        // in the event dispatch thread
        SwingUtilities.invokeLater(() -> frame.setVisible(true));
    }
}
