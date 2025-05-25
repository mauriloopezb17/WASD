package com.wasd.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;

/**
 * A JPanel with individually-rounded corners and optional border,
 * including true clipping so all child components stay inside.
 *
 * Example:
 *     PanelRound p = new PanelRound();
 *     p.setRoundTopLeft(30);
 *     p.setRoundBottomRight(60);
 *     p.setBackground(new Color(84,137,242));
 *     p.setBorderColor(Color.WHITE);
 */
public class PanelRound extends JPanel {

    private int roundTopLeft     = 0;
    private int roundTopRight    = 0;
    private int roundBottomLeft  = 0;
    private int roundBottomRight = 0;

    private Color borderColor    = null;
    private int   borderStroke   = 1;

    public PanelRound() {
        setOpaque(false);
    }

    public int  getRoundTopLeft()          { return roundTopLeft; }
    public void setRoundTopLeft(int r)     { roundTopLeft = r;  repaint(); }

    public int  getRoundTopRight()         { return roundTopRight; }
    public void setRoundTopRight(int r)    { roundTopRight = r; repaint(); }

    public int  getRoundBottomLeft()       { return roundBottomLeft; }
    public void setRoundBottomLeft(int r)  { roundBottomLeft = r; repaint(); }

    public int  getRoundBottomRight()      { return roundBottomRight; }
    public void setRoundBottomRight(int r) { roundBottomRight = r; repaint(); }

    public Color getBorderColor()                 { return borderColor; }
    public void  setBorderColor(Color c)          { borderColor = c; repaint(); }

    public int   getBorderStroke()                { return borderStroke; }
    public void  setBorderStroke(int px)          { borderStroke = px; repaint(); }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        applyQualityHints(g2);

        // round outline
        Path2D outline = createRoundedPath(getWidth(), getHeight());

        //clipping
        g2.setClip(outline);

        g2.setColor(getBackground());
        g2.fill(outline);

        // inside
        super.paintComponent(g2);

        if (borderColor != null) {
            g2.setClip(null);
            g2.setStroke(new BasicStroke(borderStroke));
            g2.setColor(borderColor);
            g2.draw(outline);
        }
        g2.dispose();
    }

    @Override
    protected void paintChildren(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        applyQualityHints(g2); // ADD THIS LINE
        g2.setClip(createRoundedPath(getWidth(), getHeight()));
        super.paintChildren(g2);
        g2.dispose();
    }

    private Path2D createRoundedPath(int w, int h) {
        int tl = Math.min(roundTopLeft,     Math.min(w, h));
        int tr = Math.min(roundTopRight,    Math.min(w, h));
        int br = Math.min(roundBottomRight, Math.min(w, h));
        int bl = Math.min(roundBottomLeft,  Math.min(w, h));

        Path2D path = new Path2D.Float();
        path.moveTo(tl, 0);

        path.lineTo(w - tr, 0);
        if (tr > 0) path.quadTo(w, 0, w, tr);

        path.lineTo(w, h - br);
        if (br > 0) path.quadTo(w, h, w - br, h);

        path.lineTo(bl, h);
        if (bl > 0) path.quadTo(0, h, 0, h - bl);

        path.lineTo(0, tl);
        if (tl > 0) path.quadTo(0, 0, tl, 0);

        path.closePath();
        return path;
    }

    private void applyQualityHints(Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,   RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING,      RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
    }
}
