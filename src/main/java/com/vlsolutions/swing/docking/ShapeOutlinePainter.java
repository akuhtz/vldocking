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

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Shape;

/**
 * the object responsible for painting the shape outline
 */
class ShapeOutlinePainter {

    private final Color innerColor = new Color(64, 64, 64);

    public void paintShape(Graphics2D g2, Shape s) {
        Composite old = g2.getComposite();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.25f));
        g2.setStroke(new BasicStroke(3));
        g2.setColor(innerColor);
        g2.fill(s);
        g2.setComposite(old);
        g2.setColor(Color.DARK_GRAY);
        g2.draw(s);
    }
}
