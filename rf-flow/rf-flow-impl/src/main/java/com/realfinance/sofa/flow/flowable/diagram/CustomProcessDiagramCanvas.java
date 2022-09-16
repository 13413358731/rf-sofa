package com.realfinance.sofa.flow.flowable.diagram;

import org.flowable.bpmn.model.AssociationDirection;
import org.flowable.image.impl.DefaultProcessDiagramCanvas;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.RoundRectangle2D;

public class CustomProcessDiagramCanvas extends DefaultProcessDiagramCanvas {

    protected static final Color HIGHLIGHT_GREEN = new Color(7,193,96);
    protected static final Color HIGHLIGHT_RED = new Color(250,81,81);


    public CustomProcessDiagramCanvas(int width, int height, int minX, int minY, String imageType, String activityFontName, String labelFontName, String annotationFontName, ClassLoader customClassLoader) {
        super(width, height, minX, minY, imageType, activityFontName, labelFontName, annotationFontName, customClassLoader);
    }

    public CustomProcessDiagramCanvas(int width, int height, int minX, int minY, String imageType) {
        super(width, height, minX, minY, imageType);

    }

    @Override
    public void drawSequenceflow(int srcX, int srcY, int targetX, int targetY, boolean conditional, boolean highLighted, double scaleFactor) {
        Paint originalPaint = g.getPaint();
        if (highLighted) {
            g.setPaint(getHighLightColor());
        }

        Line2D.Double line = new Line2D.Double(srcX, srcY, targetX, targetY);
        g.draw(line);
        drawArrowHead(line, scaleFactor);

        if (conditional) {
            drawConditionalSequenceFlowIndicator(line, scaleFactor);
        }

        if (highLighted) {
            g.setPaint(originalPaint);
        }
    }

    @Override
    public void drawConnection(int[] xPoints, int[] yPoints, boolean conditional, boolean isDefault, String connectionType,
                               AssociationDirection associationDirection, boolean highLighted, double scaleFactor) {

        Paint originalPaint = g.getPaint();
        Stroke originalStroke = g.getStroke();

        g.setPaint(CONNECTION_COLOR);
        if ("association".equals(connectionType)) {
            g.setStroke(ASSOCIATION_STROKE);
        } else if (highLighted) {
            g.setPaint(getHighLightColor());
            g.setStroke(HIGHLIGHT_FLOW_STROKE);
        }

        for (int i = 1; i < xPoints.length; i++) {
            int sourceX = xPoints[i - 1];
            int sourceY = yPoints[i - 1];
            int targetX = xPoints[i];
            int targetY = yPoints[i];
            Line2D.Double line = new Line2D.Double(sourceX, sourceY, targetX, targetY);
            g.draw(line);
        }

        if (isDefault) {
            Line2D.Double line = new Line2D.Double(xPoints[0], yPoints[0], xPoints[1], yPoints[1]);
            drawDefaultSequenceFlowIndicator(line, scaleFactor);
        }

        if (conditional) {
            Line2D.Double line = new Line2D.Double(xPoints[0], yPoints[0], xPoints[1], yPoints[1]);
            drawConditionalSequenceFlowIndicator(line, scaleFactor);
        }

        if (associationDirection == AssociationDirection.ONE || associationDirection == AssociationDirection.BOTH) {
            Line2D.Double line = new Line2D.Double(xPoints[xPoints.length - 2], yPoints[xPoints.length - 2], xPoints[xPoints.length - 1], yPoints[xPoints.length - 1]);
            drawArrowHead(line, scaleFactor);
        }
        if (associationDirection == AssociationDirection.BOTH) {
            Line2D.Double line = new Line2D.Double(xPoints[1], yPoints[1], xPoints[0], yPoints[0]);
            drawArrowHead(line, scaleFactor);
        }
        g.setPaint(originalPaint);
        g.setStroke(originalStroke);
    }

    @Override
    public void drawSequenceflowWithoutArrow(int srcX, int srcY, int targetX, int targetY, boolean conditional, boolean highLighted, double scaleFactor) {
        Paint originalPaint = g.getPaint();
        if (highLighted) {
            g.setPaint(getHighLightColor());
        }

        Line2D.Double line = new Line2D.Double(srcX, srcY, targetX, targetY);
        g.draw(line);

        if (conditional) {
            drawConditionalSequenceFlowIndicator(line, scaleFactor);
        }

        if (highLighted) {
            g.setPaint(originalPaint);
        }
    }

    @Override
    public void drawHighLight(int x, int y, int width, int height) {
        Paint originalPaint = g.getPaint();
        Stroke originalStroke = g.getStroke();

        g.setPaint(getHighLightColor());
        g.setStroke(THICK_TASK_BORDER_STROKE);

        RoundRectangle2D rect = new RoundRectangle2D.Double(x, y, width, height, 20, 20);
        g.draw(rect);

        g.setPaint(originalPaint);
        g.setStroke(originalStroke);
    }

    protected Color getHighLightColor() {
        return HIGHLIGHT_GREEN;
    }

    public void drawRedHighLight(int x, int y, int width, int height) {
        Paint originalPaint = g.getPaint();
        Stroke originalStroke = g.getStroke();

        g.setPaint(HIGHLIGHT_RED);
        g.setStroke(THICK_TASK_BORDER_STROKE);

        RoundRectangle2D rect = new RoundRectangle2D.Double(x, y, width, height, 20, 20);
        g.draw(rect);

        g.setPaint(originalPaint);
        g.setStroke(originalStroke);
    }
}
