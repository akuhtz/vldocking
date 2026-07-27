package com.vlsolutions.swing.docking.ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.UIDefaults;
import javax.swing.UIManager;

import static org.assertj.core.api.Assertions.assertThat;

public class DockingUISettingsTest {

    private static int notificationBlinkCount = 2;

    @BeforeEach
    public void setUp() {
        DockingUISettings.setInstance(new CustomDockingUISettings());
    }

    @Test
    public void testCustomizedParameters() {
        DockingUISettings.getInstance().installUI();
        assertThat(UIManager.get("DockingDesktop.notificationBlinkCount")).isEqualTo(notificationBlinkCount);
        notificationBlinkCount = 3;
        DockingUISettings.getInstance().updateUI();
        assertThat(UIManager.get("DockingDesktop.notificationBlinkCount")).isEqualTo(notificationBlinkCount);
    }

    public static class CustomDockingUISettings extends DockingUISettings {

        @Override
        protected UIDefaults getDefaults(UIDefaults table) {
            table.put("DockingDesktop.notificationBlinkCount", notificationBlinkCount);
            return super.getDefaults(table);
        }
    }
}
