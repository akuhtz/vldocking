package com.vlsolutions.swing.sample;

import com.vlsolutions.swing.docking.DockKey;
import com.vlsolutions.swing.docking.Dockable;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

class MyJTable extends JPanel implements Dockable {
    JTable table = new JTable();
    DockKey key = new DockKey("table");

    public MyJTable() {
        setName("myJTablePanel");
        setLayout(new BorderLayout());
        table.setModel(new DefaultTableModel(5, 5));
        table.setName("myJTableTable");
        JScrollPane jsp = new JScrollPane(table);
        jsp.setName("myJTableScrollPane");
        jsp.setPreferredSize(new Dimension(200, 200));
        add(jsp, BorderLayout.CENTER);
        key.setName("The table");
        key.setAutoHideEnabled(true);
        key.setCloseEnabled(false);
        key.setMaximizeEnabled(false);
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
