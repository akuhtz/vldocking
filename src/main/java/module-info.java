module org.omegat.vldocking {
    requires java.base;
    requires java.desktop;
    requires java.prefs;
    requires org.slf4j;

    exports com.vlsolutions.swing.docking;
    exports com.vlsolutions.swing.docking.animation;
    exports com.vlsolutions.swing.docking.event;
    exports com.vlsolutions.swing.docking.ui;
    exports com.vlsolutions.swing.docking.ws;
    exports com.vlsolutions.swing.tabbedpane;
    exports com.vlsolutions.swing.toolbars;
}