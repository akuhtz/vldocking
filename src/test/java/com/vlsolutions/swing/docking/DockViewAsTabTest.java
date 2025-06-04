package com.vlsolutions.swing.docking;

import com.vlsolutions.swing.TestBase;
import com.vlsolutions.swing.sample.MyTabbedDockApp;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.Test;

import javax.swing.SwingUtilities;

public class DockViewAsTabTest extends TestBase {

    @Override
    protected void onSetUp() {
        application = createDockedApplication(MyTabbedDockApp.class);
        window = new FrameFixture(robot(), application);
        window.show();
    }

    @Test
    public void testDockViewAsTab() {
        SwingUtilities.invokeLater(() -> application.setVisible(true));
        window.panel("myTreePanel").requireVisible();
        window.panel("myTextEditorPanel").requireVisible();
        window.tabbedPane("DockTabbedPane").requireVisible();
        window.panel("myJTablePanel").requireVisible();
        // select tab of hidden pane
        window.tabbedPane("DockTabbedPane").selectTab(0);
        window.panel("myGridOfButtonsPanel").requireVisible();
    }
}
