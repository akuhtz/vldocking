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

/**
 * Interface describing a container that can be used to display more than one Dockable in a Tabbed Pane
 * fashion.
 * <p>
 * This interface is meant for API Extenders in order to provide new kinds of TabbedDockableContainers (the
 * default implementation is <code>DockTabbedPane</code>).
 *
 *
 * @author Lilian Chamontin, vlsolutions.
 * @version 1.0
 */
public interface TabbedDockableContainer extends DockableContainer {

    /** Returns the currently selected (front) dockable */
    Dockable getSelectedDockable();

    /** Change the tab selection to display this dockable */
    void setSelectedDockable(Dockable dockable);

    /** Returs the current tab count (which is also the current dockable count) */
    int getTabCount();

    /** Returns the dockable at the index tab */
    Dockable getDockableAt(int index);

    /** Adds a dockable to the given index tab. */
    void addDockable(Dockable dockable, int index);

    /** Removes a dockable from the tabs */
    void removeDockable(Dockable dockable);

    /** Remove the dockable at specified index tab */
    void removeDockable(int index);

    /** Returns the index of the given dockable */
    int indexOfDockable(Dockable dockable);
}
