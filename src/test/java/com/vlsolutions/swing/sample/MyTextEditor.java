package com.vlsolutions.swing.sample;

import com.vlsolutions.swing.docking.DockKey;
import com.vlsolutions.swing.docking.Dockable;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

public class MyTextEditor extends JPanel implements Dockable {
    DockKey key = new DockKey("textEditor");
    JTextArea textArea = new JTextArea("A Text Area");

    public MyTextEditor() {
        setName("myTextEditorPanel");
        setLayout(new BorderLayout());
        textArea.setName("myTextEditorTextArea");
        JScrollPane jsp = new JScrollPane(textArea);
        jsp.setName("myTextEditorScrollPane");
        jsp.setPreferredSize(new Dimension(300, 400));
        add(jsp, BorderLayout.CENTER);
        key.setName("The Text Area");
        key.setCloseEnabled(false);
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
