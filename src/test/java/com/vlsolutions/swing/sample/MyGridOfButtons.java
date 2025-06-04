package com.vlsolutions.swing.sample;

import com.vlsolutions.swing.docking.DockKey;
import com.vlsolutions.swing.docking.Dockable;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

public class MyGridOfButtons extends JPanel implements Dockable {

    DockKey key = new DockKey("gridOfButtons");

    public MyGridOfButtons() {
        setName("myGridOfButtonsPanel");
        setLayout(new FlowLayout(FlowLayout.TRAILING, 3, 3));
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int number = i * 3 + j;
                JButton btn = new JButton("btn" + number);
                btn.setName("myGridOfButtonsButton" + number);
                add(btn);
            }
        }
        setPreferredSize(new Dimension(200, 300));
        key.setName("The Grid of Buttons");
        key.setCloseEnabled(false);
        key.setFloatEnabled(true);
        key.setMaximizeEnabled(false);
        key.setAutoHideEnabled(false);
    }

    @Override
    public DockKey getDockKey() {
        return key;
    }

    @Override
    public Component getComponent() {
        return this;
    }
}