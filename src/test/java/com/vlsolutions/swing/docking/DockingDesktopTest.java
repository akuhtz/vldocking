package com.vlsolutions.swing.docking;

import com.vlsolutions.swing.TestBase;
import com.vlsolutions.swing.sample.MySplitDockApp;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import javax.swing.SwingUtilities;

import java.awt.Point;
import java.lang.reflect.InvocationTargetException;

public class DockingDesktopTest extends TestBase {
    @Override
    protected void onSetUp() {
        application = createDockedApplication(MySplitDockApp.class);
        window = new FrameFixture(robot(), application);
        window.show();
    }

    @Test
    public void testFloating() throws InterruptedException, InvocationTargetException {
        SwingUtilities.invokeAndWait(() -> {
            application.setVisible(true);
            application.getDesktop().setFloating(application.getTreePanel(), true);
        });
        assertThat(application.getDesktop().getDockableState(application.getTreePanel()).isFloating()).isTrue();
    }

    @Test
    public void testFloatingPoint() throws InterruptedException, InvocationTargetException {
        SwingUtilities.invokeAndWait(() -> {
            application.setVisible(true);
            Point p = application.getDesktop().getBounds().getLocation();
            application.getDesktop().setFloating(application.getTreePanel(), true, p);
        });
        assertThat(application.getTreePanel().getDockKey().getLocation()).isEqualTo(DockableState.Location.FLOATING);
        assertThat(application.getDesktop().getDockableState(application.getTreePanel()).isFloating()).isTrue();
    }

    @Test
    public void testMaximize() throws InterruptedException, InvocationTargetException {
        SwingUtilities.invokeAndWait(() -> {
            application.setVisible(true);
            application.getDesktop().maximize(application.getTreePanel());
        });
        assertThat(application.getDesktop().getDockableState(application.getTreePanel()).isMaximized()).isTrue();
    }

}
