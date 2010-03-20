
package org.rz.midiplayerplugin.renderer;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import org.rz.midiplayer.context.Context;
import org.rz.midiplayer.context.MidiChannel;
import org.rz.midiplayer.xmlmodule.deviceinfo.Instrument;

/**
 *
 * @author rz
 */
class TrackRenderer
{
    private final Context context;
    private final int channel;
    private Image offscreen;
    private Graphics2D ofsG;
    private Image track;
    private Image font;

    private MidiChannel midiChannel;

    private final Color pianoRollColor;

    int level;

    ////////////////////////////////////////////////////////////////////////////
    /**
     * TrackRenderer インスタンスを生成する。
     */
    TrackRenderer( Context ctx, int ch, MidiChannel midiCh, Canvas screen, Image trackImage, Image fontImage, Color pianoRollColor_  )
    {
        context     = ctx;
        channel     = ch;
        midiChannel = midiCh;
        offscreen   = screen.createImage( trackImage.getWidth(null), trackImage.getHeight(null) );
        ofsG        = (Graphics2D)offscreen.getGraphics();
        font        = fontImage;
        track       = trackImage;
        pianoRollColor = pianoRollColor_;
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     * 
     */
    void dispose()
    {
        if( ofsG != null )
        {
            try { ofsG.dispose(); } catch( Throwable e ){}
            ofsG = null;
        }
        if( offscreen != null )
        {
            try { offscreen.flush(); } catch( Throwable e ){}
            offscreen = null;
        }

        font = null;
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     * 
     */
     void render( Graphics2D g, int x, int y )
     {
         ofsG.drawImage( track, 0, 0, null );

        int lv  = level;

        MidiChannel ch   = midiChannel;
        Instrument patch = ch.instrument;

        Graphics2D tg = ofsG;

        tg.drawImage( track, 0, 0, null );

        renderChNo( tg, channel + 1 );
        renderChColor( tg, channel );
        renderPatch( tg, patch );

        renderLv( tg, 122,  level, 127 );
        renderLv( tg, 143,  ch.getCcValue( 7 ), 127 );
        renderLvH( tg, 164, ch.getCcValue( 10 ), 127 );
        renderLv( tg, 185, ch.getCcValue( 11 ), 127 );
        renderLvH( tg, 206, ch.getPitchBend(), 16383 );
        renderLv( tg, 227, ch.getCcValue( 91), 127 );
        renderLv( tg, 248, ch.getCcValue( 93 ), 127 );
        renderLv( tg, 269, ch.getCcValue( 94 ), 127 );

        g.drawImage( offscreen, x, y, null );

        //midiChannel.level = Math.max( midiChannel.level - 2, 0 );
    }

    static private final Color[] LV_COLOR =
    {
        new Color( 0x4d7afa ),
        new Color( 0x1b02da ),
    };

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    private void renderLv( Graphics2D g, int x, int value, int max )
    {
        int r = value * 16 / max;
        int i;
        Color[] c = LV_COLOR;

        for( i = 0; i < r; i++ )
        {
            g.setColor( c[ i & 0x1 ] );
            g.fillRect( x + i + 2, 14, 1, 7 );
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    private void renderLvH( Graphics2D g, int x, int value, int max )
    {
        int v  = value - ( ( max + 1 ) / 2 );
        int r  = v * 16 / max;

        int i;
        Color[] c = LV_COLOR;

        g.setColor( c[ 1 ] );
        g.fillRect( x + 8 + 2, 14, 1, 7 );

        if( v < 0 )
        {
            for( i = r; i < 0; i++ )
            {
                g.setColor( c[ i & 0x1 ] );
                g.fillRect( x + 8 + i + 2, 14, 1, 7 );
            }
        }
        else
        {
            for( i = 0; i < r; i++ )
            {
                g.setColor( c[ i & 0x1 ] );
                g.fillRect( x + 8 + i + 2, 14, 1, 7 );
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    private void renderPatch( Graphics2D g, Instrument p )
    {
        int dx = 20;
        int dy = 2;

        g.setClip( 20, 2, 96, 20 );

        DefaultRenderer.drawText( g, font, p.getMapName(), dx, dy );
        dy += 10;

        DefaultRenderer.drawText( g, font, p.getName(), dx, dy );
        dy += 10;

        g.setClip( null );

    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    private void renderChNo( Graphics2D g, int ch )
    {
        int dx = 2;
        int dy = 7;

        if( ch < 10 )
        {
            DefaultRenderer.drawText( g, font, String.valueOf( 0 ), dx,     dy );
            DefaultRenderer.drawText( g, font, String.valueOf( ch ), dx + 6, dy );
        }
        else
        {
            DefaultRenderer.drawText( g, font, String.valueOf( ch / 10 ), dx,     dy );
            DefaultRenderer.drawText( g, font, String.valueOf( ch % 10 ), dx + 6, dy );
        }

    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    private void renderChColor( Graphics2D g, int ch )
    {
        g.setColor( pianoRollColor );
        g.fillRect( 117, 4, 3, 16 );
    }
}
