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

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * A glasspane use to paint drag / drop shape on top of the desktop
 *
 * @author Lilian Chamontin, VLSolutions
 */
public class DragControlerGlassPane extends JComponent {

    private static final long serialVersionUID = 1L;

    Cursor stopDragCursor, dragCursor, floatCursor, swapDragCursor;

    boolean paintShapes = true;

    private final ShapeLabelPainter labelPainter = new ShapeLabelPainter(this);

    private final ShapeOutlinePainter outlinePainer = new ShapeOutlinePainter();

    private final DragControler controler;

    DragControlerGlassPane(DragControler controler) {
        this.controler = controler;
        addMouseListener(new MouseAdapter() {
        }); // grab events
        addMouseMotionListener(new MouseMotionAdapter() {
        });
        showDragCursor();
    }

    /** Enables or disables shape painting */
    public void setPaintShapes(boolean paintShapes) {
        this.paintShapes = paintShapes;
    }

    DragControler getControler() {
        return controler;
    }

    public void paintComponent(Graphics g) {
        if (paintShapes) {
            Graphics2D g2 = (Graphics2D) g;
            if (controler.getDropShape() != null) {
                Shape s = controler.getDropShape();
                Point p = SwingUtilities.convertPoint((Component) controler.getDropReceiver(), 0, 0, this);
                Shape s2 = AffineTransform.getTranslateInstance(p.x, p.y).createTransformedShape(s);
                outlinePainer.paintShape(g2, s2);
                labelPainter.paintLabel(g2, s2, controler.getDockable().getDockKey().getName());
            }
        }
    }

    public void showStopDragCursor() {
        if (stopDragCursor == null) {
            Image stopDragImage = (Image) UIManager.get("DragControler.stopDragCursor"); // 2005/11/01
            stopDragCursor = Toolkit.getDefaultToolkit().createCustomCursor(stopDragImage, new Point(16, 16),
                    "stopdragcursor");
        }
        setCursor(stopDragCursor);
    }

    public void showSwapDragCursor() {
        if (swapDragCursor == null) {
            Image swapDragImage = (Image) UIManager.get("DragControler.swapDragCursor"); // 2005/11/01
            swapDragCursor = Toolkit.getDefaultToolkit().createCustomCursor(swapDragImage, new Point(16, 16),
                    "swapdragcursor");
        }
        setCursor(swapDragCursor);
    }

    public void showFloatCursor() {
        if (floatCursor == null) {
            Image floatImage = (Image) UIManager.get("DragControler.detachCursor"); // 2005/11/01
            floatCursor = Toolkit.getDefaultToolkit().createCustomCursor(floatImage, new Point(16, 16),
                    "floatcursor");
        }
        setCursor(floatCursor);
    }

    public void showDragCursor() {
        if (dragCursor == null) {
            Image dragImage = (Image) UIManager.get("DragControler.dragCursor"); // 2005/11/01
            dragCursor = Toolkit.getDefaultToolkit().createCustomCursor(dragImage, new Point(16, 16),
                    "dragcursor");
        }
        setCursor(dragCursor);
    }
}
