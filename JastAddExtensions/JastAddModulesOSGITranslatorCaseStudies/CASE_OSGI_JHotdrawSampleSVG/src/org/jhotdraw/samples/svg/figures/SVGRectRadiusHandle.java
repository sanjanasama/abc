/*
 * @(#)SVGRectRadiusHandle.java  1.0  2006-12-10
 *
 * Copyright (c) 2006 by the original authors of JHotDraw
 * and all its contributors.
 * All rights reserved.
 *
 * The copyright of this software is owned by the authors and  
 * contributors of the JHotDraw project ("the copyright holders").  
 * You may not use, copy or modify this software, except in  
 * accordance with the license agreement you entered into with  
 * the copyright holders. For details see accompanying license terms. 
 */

package org.jhotdraw.samples.svg.figures;

import javax.swing.undo.*;
import org.jhotdraw.draw.*;
import org.jhotdraw.geom.*;
import org.jhotdraw.util.*;
import org.jhotdraw.undo.*;
import java.awt.*;
import java.awt.geom.*;

import static org.jhotdraw.svg.SVGAttributeKeys.*;

/**
 * A Handle to manipulate the radius of a round lead rectangle.
 *
 * @author  Werner Randelshofer
 * @version 1.0 2006-12-10 Adapted from RoundRectangleHandle.
 */
public class SVGRectRadiusHandle extends AbstractHandle {
    private final static boolean DEBUG = false;
    private static final int OFFSET = 6;
    private Dimension2DDouble originalArc2D;
    CompositeEdit edit;
    
    /** Creates a new instance. */
    public SVGRectRadiusHandle(Figure owner) {
        super(owner);
    }
    
    /**
     * Draws this handle.
     */
    public void draw(Graphics2D g) {
        drawDiamond(g, Color.yellow, Color.black);
    }
    
    protected Rectangle basicGetBounds() {
        Rectangle r = new Rectangle(locate());
        r.grow(getHandlesize() / 2 + 1, getHandlesize() / 2 + 1);
        return r;
    }
    
    private Point locate() {
        SVGRectFigure owner = (SVGRectFigure) getOwner();
        Rectangle2D.Double r = owner.getBounds();
        Point2D.Double p = new Point2D.Double(
                r.x + owner.getArcWidth(), 
                r.y + owner.getArcHeight()
                );
        if (TRANSFORM.get(owner) != null) {
            TRANSFORM.get(owner).transform(p, p);
        }
        return view.drawingToView(p);
    }
    
    public void trackStart(Point anchor, int modifiersEx) {
        SVGRectFigure svgRect = (SVGRectFigure) getOwner();
        originalArc2D = svgRect.getArc();
    }
    
    public void trackStep(Point anchor, Point lead, int modifiersEx) {
        int dx = lead.x - anchor.x;
        int dy = lead.y - anchor.y;
        SVGRectFigure svgRect = (SVGRectFigure) getOwner();
        svgRect.willChange();
        Point2D.Double p = view.viewToDrawing(lead);
        if (TRANSFORM.get(svgRect) != null) {
            try {
                TRANSFORM.get(svgRect).inverseTransform(p, p);
            } catch (NoninvertibleTransformException ex) {
                if (DEBUG) ex.printStackTrace();
            }
        }
        Rectangle2D.Double r = svgRect.getBounds();
        svgRect.setArc(p.x - r.x, p.y - r.y);
        svgRect.changed();
    }
    public void trackEnd(Point anchor, Point lead, int modifiersEx) {
        final SVGRectFigure svgRect = (SVGRectFigure) getOwner();
        final Dimension2DDouble oldValue = originalArc2D;
        final Dimension2DDouble newValue = svgRect.getArc();
        view.getDrawing().fireUndoableEditHappened(new AbstractUndoableEdit() {
            public String getPresentationName() {
                ResourceBundleUtil labels = ResourceBundleUtil.getLAFBundle("org.jhotdraw.samples.svg.Labels");
                return labels.getString("arc");
            }
            public void undo() throws CannotUndoException {
                super.undo();
                svgRect.willChange();
                svgRect.setArc(oldValue);
                svgRect.changed();
            }
            public void redo() throws CannotRedoException {
                super.redo();
                svgRect.willChange();
                svgRect.setArc(newValue);
                svgRect.changed();
            }
        });
   }
    public String getToolTipText(Point p) {
        return ResourceBundleUtil.getLAFBundle("org.jhotdraw.draw.Labels").getString("roundRectangleRadiusHandle.tip");
        }
}
