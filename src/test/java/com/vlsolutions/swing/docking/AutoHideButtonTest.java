package com.vlsolutions.swing.docking;

import com.vlsolutions.swing.TestBase;
import com.vlsolutions.swing.sample.MySplitDockApp;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.Test;

import javax.swing.SwingUtilities;

public class AutoHideButtonTest extends TestBase {

    @Override
    protected void onSetUp() {
        application = createDockedApplication(MySplitDockApp.class);
        window = new FrameFixture(robot(), application);
        window.show();
    }

    @Test
    public void testAutoHideButton() {
        SwingUtilities.invokeLater(() -> application.setVisible(true));
        window.panel("DockingPanel").panel(new PanelMatcher("DockView", 2)).panel("DockViewTitleBar")
                .button("DockButton").click();
        window.panel("AutoHideButtonPanel").label(new AutoHideButtonMatcher("AutoHideButton", 0)).click();
        window.panel("AutoHideExpandPanel").panel("DockViewTitleBar").label("TitleLabel")
                .requireText("The table");
        window.panel("AutoHideExpandPanel").panel("DockViewTitleBar").button("DockButton").click();
    }
}
