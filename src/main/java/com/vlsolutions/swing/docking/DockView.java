/*
    VLDocking Framework 3.0
    Copyright Lilian Chamontin, 2004-2013

    www.vldocking.com
    vldocking@googlegroups.com
------------------------------------------------------------------------
This software is distributed under the LGPL license

The fact that you are presently reading this and using this class means that you have had
knowledge of the LGPL license and that you accept its terms.

You can read the complete license here :

    http://www.gnu.org/licenses/lgpl.html

 */

package com.vlsolutions.swing.docking;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vlsolutions.swing.docking.event.DockDragEvent;
import com.vlsolutions.swing.docking.event.DockDropEvent;
import com.vlsolutions.swing.docking.event.DockEvent;
import com.vlsolutions.swing.docking.event.DockingActionCreateTabEvent;

/**
 * DefaultImplementation of the SingleDockableContainer.
 * <p>
 * A Dockview is the component responsible for displaying a single user component inside a DockingPanel.
 * <p>
 * The DockView contains a DockViewTitleBar, used as a header to display useful information and optional controls
 * (close/maximize/hide/float)
 * <p>
 * UI : The user component is displayed with decorations provided by a
 * {@link com.vlsolutions.swing.docking.ui.DockViewUI} delegate.
 * <p>
 * Here is an example (VLDocking 1.1 "shadow" style) :
 * <ul>
 * <li>A gadient for the title bar and a set of icons for the buttons.
 * <li>A shadowed border for enhanced visibility. <br>
 * </ul>
 * <p align="center">
 * <img src="doc-files/dockview.gif" alt="Sample DockView" >
 * </p>
 *
 * <p>
 * Note : multiple user-components are not displayable by the dockview : the default implementation of
 * TabbedDockableContainer is the {@link DockTabbedPane}.
 *
 * @see DockViewTitleBar
 * 
 * @author Lilian Chamontin, vlsolutions.
 * @version 1.0
 * @update 2005/12/09 Lilian Chamontin : added NPE safety check around uninstallDocking as sometimes uninstalling is
 *         done twice.
 * @update 2007/01/08 Lilian Chamontin : delegated the creation of the titlebar to allow easy override.
 */
public class DockView extends JPanel implements DockDropReceiver, SingleDockableContainer {

    private static final Logger LOGGER = LoggerFactory.getLogger(DockView.class);

    private static final long serialVersionUID = 1L;

    private static final String uiClassID = "DockViewUI";

    protected DockViewTitleBar title = getTitleBar();

    /** the desktop managing this view */
    protected DockingDesktop desktop;

    /** the dockable managed by this view */
    protected Dockable dockable; // user component

    /** remember the last drop position to cache the shape used for showing the drop position */
    protected Shape lastDropShape, lastDropGeneralPath;

    /** listen to the titlebar actions */
    private PropertyChangeListener listener = new PropertyChangeListener() {

        @Override
        public void propertyChange(PropertyChangeEvent e) {
            if (e.getPropertyName().equals(DockViewTitleBar.PROPERTY_AUTOHIDE)) {
                if (e.getOldValue().equals(Boolean.TRUE)) {
                    // from autohide to dock : not possible because DockView isn't used
                }
                else {
                    // dock to autohide
                    desktop.setAutoHide(dockable, true);
                }
            }
            else if (e.getPropertyName().equals(DockViewTitleBar.PROPERTY_CLOSED)) {
                desktop.close(dockable);
            }
            else if (e.getPropertyName().equals(DockViewTitleBar.PROPERTY_MAXIMIZED)) {
                if (e.getNewValue().equals(Boolean.TRUE)) {
                    desktop.maximize(dockable);
                }
                else {
                    desktop.restore(dockable);
                }
            }
            else if (e.getPropertyName().equals(DockViewTitleBar.PROPERTY_FLOAT)) {
                desktop.setFloating(dockable, ((Boolean) e.getNewValue()).booleanValue());
            }
        }
    };

    /**
     * Constructs an empty DockView.
     * <p>
     * A {@link #setDockable(Dockable)} call is requiered for this constructor to be usefull.
     */
    public DockView() {
        this(null);
    }

    /**
     * Constructs a new DockView for the given dockable.
     *
     * @param dockable
     *            the dockable to show
     */
    public DockView(Dockable dockable) {
        this(dockable, true);
    }

    /**
     * Constructs a new DockView for the given dockable.
     *
     * @param dockable
     *            the dockable to show
     */
    public DockView(Dockable dockable, boolean showTitle) {
        super(new BorderLayout());
        add(title, BorderLayout.NORTH);
        setName("DockView");
        if (dockable != null) {
            setDockable(dockable);
        }
        if (!showTitle) {
            title.setVisible(false);
        }
    }

    /**
     * Returns the contained component
     *
     * {@inheritDoc}
     */
    @Override
    public Dockable getDockable() {
        return dockable;
    }

    /**
     * Adds the dockable's component to this DockView.
     *
     *
     * @param dockable
     *            the user dockable
     */
    public void setDockable(Dockable dockable) {
        this.dockable = dockable;

        add(dockable.getComponent(), BorderLayout.CENTER);
        if (title != null) {
            title.setDockable(dockable);
        }

        // allow resizing of split pane beyond minimum size
        // could be replaced by adding a JScrollPane instead of panels
        // setMinimumSize(new Dimension(30, 30));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "DockView of " + dockable.getDockKey();
    }

    /**
     * {@inheritDoc}
     * 
     * @since 2.0
     */
    @Override
    public String getUIClassID() {
        return uiClassID;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void processDockableDrag(DockDragEvent event) {
        scanDrop(event, false);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Please note that a drag into a DockView can also lead to create a DockTabbedPane.
     */
    @Override
    public void processDockableDrop(DockDropEvent event) {
        scanDrop(event, true);
    }

    /* common method for drag && drop gesture support */
    protected void scanDrop(DockEvent event, boolean drop) {
        // reject operation if the source is an ancestor of this view.
        if (event.getDragSource().getDockableContainer().isAncestorOf(this)) {
            // this is possible for compound containers (as they contain sub-dockables)
            // in that case, you cannnot drop a compound into one of its children // 2007/01/08
            if (drop) {
                ((DockDropEvent) event).rejectDrop();
            }
            else {
                ((DockDragEvent) event).delegateDrag();
            }
            return;
        }

        if (getParent() instanceof TabbedDockableContainer) {
            // cannot nest DockTabbedPanes
            if (drop) {
                ((DockDropEvent) event).rejectDrop();
            }
            else {
                ((DockDragEvent) event).delegateDrag();
            }
            return;
        }

        if (event.getDragSource().getDockable() == dockable) {
            // cannot drop onto itself
            if (drop) {
                ((DockDropEvent) event).rejectDrop();
            }
            else {
                ((DockDragEvent) event).rejectDrag();
            }
            return;
        }

        // The dockview isn't contained inside a tabbedpane, so there are now 5 drop zones :
        // - center : create a tabbedpane
        // - top/left/bottom/right : split the view

        Rectangle bounds = getBounds();

        BorderSplitter splitter = new BorderSplitter(this);
        boolean accepted;
        if (drop) {
            splitter.processDockableDrop((DockDropEvent) event);
            accepted = ((DockDropEvent) event).isDropAccepted();
        }
        else {
            splitter.processDockableDrag((DockDragEvent) event);
            accepted = ((DockDragEvent) event).isDragAccepted();
        }

        if (!accepted) {
            // not on the borders : we should create a new tab
            if (event.getDragSource().getDockableContainer() instanceof TabbedDockableContainer) {
                // cannot drop a whole tab container
                if (drop) {
                    ((DockDropEvent) event).rejectDrop();
                }
                else {
                    ((DockDragEvent) event).rejectDrag();
                }
                return;
            }

            // reject if key groups aren't compatible
            DockGroup thisGroup = dockable.getDockKey().getDockGroup();
            DockGroup dragGroup = event.getDragSource().getDockable().getDockKey().getDockGroup();
            if (!DockGroup.areGroupsCompatible(thisGroup, dragGroup)) {
                if (drop) {
                    ((DockDropEvent) event).rejectDrop();
                }
                else {
                    ((DockDragEvent) event).rejectDrag();
                }
                return;
            }

            Dockable d = event.getDragSource().getDockable();
            DockableState.Location dockableLocation = d.getDockKey().getLocation();
            DockableState.Location viewLocation = dockable.getDockKey().getLocation();
            if (drop) {
                event
                    .setDockingAction(new DockingActionCreateTabEvent(event.getDesktop(), d, dockableLocation,
                        viewLocation, dockable, 0));

                /*
                 * if (dockable.getDockKey().getLocation() == DockableState.STATE_FLOATING){ ((DockDropEvent)
                 * event).acceptDrop(false); // don't remove a floating dockable from its hierarchy yet } else {
                 */
                ((DockDropEvent) event).acceptDrop();
                /* } */
                if (((DockDropEvent) event).isDropAccepted()) {
                    desktop.createTab(dockable, event.getDragSource().getDockable(), 0, true);
                }
                else {
                    LOGGER.info("Drop was not accepted!");
                }
            }
            else {
                event
                    .setDockingAction(new DockingActionCreateTabEvent(event.getDesktop(), d, dockableLocation,
                        viewLocation, dockable, 0));

                Rectangle2D r2d = new Rectangle2D.Float(bounds.x, bounds.y, bounds.width, bounds.height);
                if (r2d.equals(lastDropShape)) {
                    // optimized shape caching
                    ((DockDragEvent) event).acceptDrag(lastDropGeneralPath);
                }
                else {
                    // draw a 'tabbed pane shape'
                    GeneralPath path = buildPathForTab(bounds);
                    ;

                    lastDropShape = r2d;
                    lastDropGeneralPath = path;
                    ((DockDragEvent) event).acceptDrag(lastDropGeneralPath);
                }
            }
        }
    }

    /** Create a path used to display the drag shape of a tab container */
    protected GeneralPath buildPathForTab(Rectangle bounds) {
        GeneralPath path = new GeneralPath();
        int tabPlacement = UIManager.getInt("TabbedDockableContainer.tabPlacement");
        if (tabPlacement == SwingConstants.BOTTOM) {
            Rectangle tabRect = new Rectangle(0, 0, bounds.width - 5, bounds.height - 5);
            path.moveTo(tabRect.x, tabRect.y);
            path.lineTo(tabRect.x + tabRect.width, tabRect.y);
            path.lineTo(tabRect.x + tabRect.width, tabRect.y + tabRect.height - 15);
            path.lineTo(tabRect.x + 30, tabRect.y + tabRect.height - 15);
            path.lineTo(tabRect.x + 25, tabRect.y + tabRect.height);
            path.lineTo(tabRect.x + 10, tabRect.y + tabRect.height);
            path.lineTo(tabRect.x + 10, tabRect.y + tabRect.height - 15);
            path.lineTo(tabRect.x, tabRect.y + tabRect.height - 15);
            path.closePath();
        }
        else { // TOP
            Rectangle tabRect = new Rectangle(0, 20, bounds.width - 5, bounds.height - 20 - 5);
            path.moveTo(tabRect.x, tabRect.y);
            path.lineTo(tabRect.x + 10, tabRect.y);
            path.lineTo(tabRect.x + 10, tabRect.y - 15);
            path.lineTo(tabRect.x + 25, tabRect.y - 15);
            path.lineTo(tabRect.x + 30, tabRect.y);
            path.lineTo(tabRect.x + tabRect.width, tabRect.y);
            path.lineTo(tabRect.x + tabRect.width, tabRect.y + tabRect.height);
            path.lineTo(tabRect.x, tabRect.y + tabRect.height);
            path.closePath();
        }
        return path;
    }

    /** {@inheritDoc} */
    @Override
    public void installDocking(DockingDesktop desktop) {
        this.desktop = desktop;
        desktop.installDockableDragSource(title);
        title.addPropertyChangeListener(DockViewTitleBar.PROPERTY_AUTOHIDE, listener);
        title.addPropertyChangeListener(DockViewTitleBar.PROPERTY_CLOSED, listener);
        title.addPropertyChangeListener(DockViewTitleBar.PROPERTY_MAXIMIZED, listener);
        title.addPropertyChangeListener(DockViewTitleBar.PROPERTY_FLOAT, listener);
        title.installDocking(desktop);
    }

    /** {@inheritDoc} */
    @Override
    public void uninstallDocking(DockingDesktop desktop) {
        if (title != null) { // safety check, as removing is sometimes cascaded and done once more than it
                             // should be
            // 2005/12/09
            // System.out.println("uninstallDocking VIEW on " + dockable.getDockKey());

            desktop.uninstallDockableDragSource(title);
            title.removePropertyChangeListener(DockViewTitleBar.PROPERTY_AUTOHIDE, listener);
            title.removePropertyChangeListener(DockViewTitleBar.PROPERTY_CLOSED, listener);
            title.removePropertyChangeListener(DockViewTitleBar.PROPERTY_MAXIMIZED, listener);
            title.removePropertyChangeListener(DockViewTitleBar.PROPERTY_FLOAT, listener);
            title.uninstallDocking(desktop);
        }
        // remove(title);
        title = null;
        this.desktop = null;
    }

    /** Returns (or creates) the title bar of this dockview */
    public DockViewTitleBar getTitleBar() {
        if (title == null) {
            title = DockableContainerFactory.getFactory().createTitleBar(); // 2007/01/08
        }
        return title;
    }
}
