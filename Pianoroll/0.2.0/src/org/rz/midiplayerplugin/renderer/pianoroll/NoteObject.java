
package org.rz.midiplayerplugin.renderer.pianoroll;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author rz
 */
public class NoteObject
{

    protected final Rectangle2D.Float rect = new Rectangle2D.Float();

    protected boolean visible;
    protected boolean noteOn;

    volatile public int channel;
    volatile public int noteNo;

    volatile private Color activeColor    = Color.RED;
    volatile private Color deactiveColor  = activeColor.darker();

    volatile public int circle;

    private long start = 0L;

    public NoteObject()
    {
        super();
        reset();
    }

    public void reset()
    {
        rect.setRect( 0, 0, 0, 0 );
        visible = false;
        noteOn  = false;
        channel = 0;
        noteNo  = 0;
    }

    public void noteOn( int sx, int sy )
    {
        rect.setRect( sx, sy, 1, 2 );
        circle  = 16;
        visible = true;
        noteOn  = true;
        start   = System.currentTimeMillis();
    }

    public void noteOff()
    {
        noteOn  = false;
    }

    public void update()
    {
        if( visible )
        {
            long now  = System.currentTimeMillis();
            float delta;

            //delta = ( System.currentTimeMillis() - start ) * 0.09f;
            delta = 1.40f;
            start = now;
            
            rect.x -= delta;

            if( circle > 0 )
            {
                circle--;
            }

            if( noteOn )
            {
                rect.width += delta;
            }
            else
            {
                if( rect.x + rect.width < 0 )
                {
                    reset();
                }
            }
        }
    }

    synchronized public void render( Graphics2D g )
    {
        if( visible )
        {
            if( noteOn )
            {
                g.setColor( activeColor );
            }
            else
            {
                g.setColor( deactiveColor );
            }
            g.fill( rect );

            if( circle > 0 )
            {
                g.setColor( activeColor );
                g.drawOval( 512-40, (int)rect.y-(circle>>1), circle, circle );
            }

        }
    }

    synchronized public void setColor( Color c )
    {
        activeColor    = c;
        deactiveColor  = c.darker().darker();
    }

}
