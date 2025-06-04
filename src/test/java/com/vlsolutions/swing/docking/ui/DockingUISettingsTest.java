package com.vlsolutions.swing.docking.ui;

import org.junit.Before;
import org.junit.Test;

import javax.swing.UIDefaults;
import javax.swing.UIManager;

import static org.junit.Assert.assertEquals;

public class DockingUISettingsTest {

    private static int notificationBlinkCount = 2;

    @Before
    public void setUp() {
        DockingUISettings.setInstance(new CustomDockingUISettings());
    }

    @Test
    public void testCustomizedParameters() {
        DockingUISettings.getInstance().installUI();
        assertEquals(notificationBlinkCount, UIManager.get("DockingDesktop.notificationBlinkCount"));
        notificationBlinkCount = 3;
        DockingUISettings.getInstance().updateUI();
        assertEquals(notificationBlinkCount, UIManager.get("DockingDesktop.notificationBlinkCount"));
    }

    public static class CustomDockingUISettings extends DockingUISettings {

        @Override
        protected UIDefaults getDefaults(UIDefaults table) {
            table.put("DockingDesktop.notificationBlinkCount", notificationBlinkCount);
            return super.getDefaults(table);
        }
    }
}
