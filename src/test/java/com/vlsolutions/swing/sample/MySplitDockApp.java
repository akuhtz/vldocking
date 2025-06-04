package com.vlsolutions.swing.sample;

import com.vlsolutions.swing.docking.DockingConstants;

import javax.swing.SwingUtilities;

public class MySplitDockApp extends DockedApplication {

    public MySplitDockApp() {
        desk.addDockable(editorPanel);
        desk.split(editorPanel, treePanel, DockingConstants.SPLIT_LEFT);
        desk.split(editorPanel, buttonGrid, DockingConstants.SPLIT_RIGHT);
        desk.split(buttonGrid, tablePanel, DockingConstants.SPLIT_BOTTOM);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().add(desk);
    }

    public static void main(String[] args) {
        MySplitDockApp frame = new MySplitDockApp();
        frame.setName("parent");
        frame.setSize(800, 600);
        frame.validate();
        // in the event dispatch thread
        SwingUtilities.invokeLater(() -> frame.setVisible(true));
    }
}