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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;

/**
 * the object responsible for painting the shape label
 */
class ShapeLabelPainter {

    private final DragControlerGlassPane dragControlerGlassPane;
    private final Color textColor = Color.WHITE;

    private final Color textFillColor = new Color(32, 32, 32, 128);
    // private Color textFillColor = new Color(128, 128,128);

    private final Color textBorderColor = new Color(64, 64, 64);

    public ShapeLabelPainter(DragControlerGlassPane dragControlerGlassPane) {
        this.dragControlerGlassPane = dragControlerGlassPane;
    }

    public void paintLabel(Graphics2D g2, Shape s, String name) {
        Rectangle bounds = s.getBounds();
        FontMetrics fm = g2.getFontMetrics();
        int w = fm.stringWidth(name);

        g2.setColor(textFillColor);
        int bx, by;
        if (dragControlerGlassPane.getControler().isFloatingShape()) {
            bx = bounds.x + bounds.width / 2 - w / 2;
            by = bounds.y + bounds.height / 2 - fm.getHeight() / 2;
        } else {
            bx = 4 * ((bounds.x + bounds.width / 2 - w / 2) / 4);
            // 2005/11/01 small hack to overcome small drifts of the label
            // (for example when used on a tabbedpane and when the selected tab is
            // one or two pixels bigger than a non selected tab.
            // just snapping it with a 4*4 grid avoid those glitches (without changing
            // too much the shapes algorithm).
            by = 4 * ((bounds.y + bounds.height / 2 - fm.getHeight() / 2) / 4);
        }
        g2.fillRect(bx - 5, by, w + 10, fm.getHeight());
        g2.setStroke(new BasicStroke(1));
        g2.setColor(textBorderColor);
        g2.drawRect(bx - 5, by, w + 10, fm.getHeight());
        g2.setColor(textColor);
        g2.drawString(name, bx, by + fm.getAscent());
    }
}
