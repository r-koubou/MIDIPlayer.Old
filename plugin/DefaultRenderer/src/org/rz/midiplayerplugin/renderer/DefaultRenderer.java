
package org.rz.midiplayerplugin.renderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import javax.imageio.ImageIO;
import org.rz.midiplayer.context.Context;
import org.rz.midiplayer.context.DefaultMidiEventHandler;
import org.rz.midiplayer.context.MidiChannelEventAdaptor;
import org.rz.midiplayer.plugin.renderer.SimpleRenderer;
import org.rz.midiplayer.util.PathUtil;
import org.rz.midiplayer.xmlmodule.JAXBUtil;
import org.rz.midiplayerplugin.renderer.config.Config;

/**
 *
 * @author rz
 */
public class DefaultRenderer extends SimpleRenderer
{
    private Context context;

    private final TrackRenderer[] trackRenderers = new TrackRenderer[ 16 ];
    private final Dimension screenSize           = new Dimension( 512, 384 );
    private DefaultMidiEventHandler midiEventHandler;

    private final NoteObjectPool noteObjects          = new NoteObjectPool( 2048 );
    private final ArrayList<NoteObject> noteList      = new ArrayList<NoteObject>( 2048 );

    private BufferedImage trackImageBase;
    private BufferedImage fontImage;
    private final StringBuilder textWork = new StringBuilder( 32 );

    static public final Color[] DEFAULT_COLORS =
    {
        new Color( 0x7fffd4 ),
        new Color( 0xb0e0e6 ),
        new Color( 0xffdab9 ),
        new Color( 0xffa07a ),
        new Color( 0x98fb98 ),
        new Color( 0xffff00 ),
        new Color( 0xffd700 ),
        new Color( 0xee82ee ),
        new Color( 0xef5272 ),
        new Color( 0xd2691e ),
        new Color( 0x00fa9a ),
        new Color( 0x7fff00 ),
        new Color( 0xf5deb3 ),
        new Color( 0xffb6c1 ),
        new Color( 0xff1493 ),
        new Color( 0x00bfff ),
        new Color( 0xb8860b ),
        new Color( 0xff6347 ),
    };

    private final Color[] pianoRollColors = new Color[ 16 ];

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    @Override
    public void onInit( Context ctx, String pluginDir )
    {
        int i;
        context = ctx;

        midiEventHandler = new DefaultMidiEventHandler( ctx );
        midiEventHandler.addMidiChannelEventListener( new MidiChannelEventAdaptor() {

            @Override
            public void noteOff( int ch, int note )
            {
                DefaultRenderer.this.noteOff( ch, note );
            }

            @Override
            public void noteOn( int ch, int note, int vel )
            {
                DefaultRenderer.this.noteOn( ch, note, vel );
            }

        });
        ctx.addMidiEventHandler( midiEventHandler );

        try
        {
            for( i = 0; i < 16; i++ )
            {
                pianoRollColors[ i ] = DEFAULT_COLORS[ i ];
            }

            JAXBUtil<Config> jaxbConfig = new JAXBUtil<Config>( Config.class );
            Config config = jaxbConfig.loadFromFile( PathUtil.package2ClasspathString( getClass() ) + "/config.xsd",
                                                     PathUtil.buildPath( pluginDir, "config.xml" ) ).getValue();

            for( org.rz.midiplayerplugin.renderer.config.Color c :  config.getPianoroll().getColor() )
            {
                try
                {
                    int ch = c.getMidich();
                    Color col = Color.decode( c.getColor() );
                    pianoRollColors[ ch ] = col;
                }
                catch( Throwable e )
                {
                    logger.log( Level.WARNING, "Failed to loading a config file", e );
                }
            }
        }
        catch( Throwable e )
        {
            logger.log( Level.WARNING, "Failed to loading a config file", e );
        }

    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     * 
     */
    private void loadResources() throws IOException
    {
        URL track = getClass().getResource( "track.gif" );
        URL font  = getClass().getResource( "font.gif" );

        trackImageBase = ImageIO.read( track );
        fontImage      = ImageIO.read( font );

        for( int i = 0; i < 16; i++ )
        {
            trackRenderers[ i ] = new TrackRenderer( context, i, midiEventHandler.getMidiChannel( i ), canvas, trackImageBase, fontImage, pianoRollColors[ i ] );
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    private void disposeResources()
    {
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    @Override
    public synchronized void startRendering()
    {
        try
        {
            loadResources();
            super.startRendering();
        }
        catch( Throwable e )
        {
            logger.log( Level.SEVERE, "cannot start rendering...", e );
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    @Override
    public void onMidiPlayingBefore( File midiFile )
    {
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    @Override
    public void onMidiStoped()
    {
        synchronized( noteList )
        {
            for( NoteObject o : noteList )
            {
                o.noteOff();
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    private void noteOn( int ch, int noteNo, int vel )
    {
        trackRenderers[ ch ].level = Math.max( trackRenderers[ ch ].level, vel );

        synchronized( noteList )
        {
            NoteObject n = noteObjects.create();
            n.noteOn( 237-40, 382 - ( 3 * noteNo ) );
            n.noteNo = noteNo;
            n.channel = ch;
            n.setColor( pianoRollColors[ ch ] );
            noteList.add( n );
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    private void noteOff( int ch, int noteNo )
    {
        synchronized( noteList )
        {
            int s = noteList.size();
            int i;
            for( i = 0; i < s; i++ )
            {
                NoteObject n = noteList.get( i );
                if( n.channel == ch && n.noteNo == noteNo )
                {
                    n.noteOff();
                }
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    @Override
    public void onDispose( Context ctx )
    {
        midiEventHandler.removeAllMidiChannelEventListeners();
        ctx.removeMidiEventHandler( midiEventHandler );

        disposeResources();
        super.onDispose( ctx );
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    @Override
    public void update()
    {
        synchronized( this )
        {
            int i;
            int s;

            for( TrackRenderer tr :  trackRenderers )
            {
                tr.level = Math.max( tr.level - 2, 0 );
            }

            for( i = 0; i < noteList.size(); )
            {
                NoteObject n = noteList.get( i );
                if(n.rect.isEmpty() )
                {
                    noteList.remove( i );
                    continue;
                }
                n.update();
                i++;
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    @Override
    public void draw( Graphics2D g )
    {
        g.setRenderingHint( RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP );

        g.setColor( Color.black );
        g.fillRect( 0, 0, screenSize.width, screenSize.height );

        drawTracks( g );
        drawInfo( g );

        synchronized( noteList )
        {
            int i = 0;
            g.translate( 290, 0 );
            g.setClip( 0, 0, 222, 384 );

            for( NoteObject n : noteList )
            {
                n.render( (Graphics2D)g );
            }
            g.translate( -290, 0 );

        }

    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    private void drawTracks( Graphics2D g )
    {
        for( int i = 0; i < 16; i++ )
        {
            trackRenderers[ i ].render( g, 0, 23 * i );
        }
    }

    static private final Color TIME_GAUGE_FRAME_COLOR = new Color( 0x329dff );
    static private final Color TIME_GAUGE_COLOR = new Color( 0x004da8 );

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    private void drawInfo( Graphics2D g )
    {
        final int x = 1;
        final int y = 372;

        //--------------------------------------------------------------------------
        // play time
        //--------------------------------------------------------------------------

        int now          = context.getCurrentTimeInSecond();
        int end          = context.getPlayTimeInSecond();

        int min;
        int min100;
        int min10;
        int min1;

        int sec;

        StringBuilder sb = textWork;

        if( now > 60 * 999 + 59 ) now = 60 * 999 + 59;
        if( end > 60 * 999 + 59 ) end = 60 * 999 + 59;

        sb.delete( 0, sb.length() );
        sb.append( "Time:" );

        min = now / 60;
        sec = now % 60;

        min100 = ( min / 100 % 10 );
        min10  = ( min / 10 % 10 );
        min1   = ( min % 10 );

        if( min100 > 0 ) sb.append( min100 );
        if( min10  > 0 ) sb.append( min10 );
        sb.append( min1  );
        sb.append( ':'  );
        sb.append( ( sec / 10 ) % 10  );
        sb.append( sec % 10  );

        sb.append( "/" );

        min = end / 60;
        sec = end % 60;

        min100 = ( min / 100 % 10 );
        min10  = ( min / 10 % 10 );
        min1   = ( min % 10 );

        if( min100 > 0 ) sb.append( min100 );
        if( min10  > 0 ) sb.append( min10 );
        sb.append( min1  );
        sb.append( ':'  );
        sb.append( ( sec / 10 ) % 10  );
        sb.append( sec % 10  );

        DefaultRenderer.drawText( g, fontImage, sb.toString(), x, y );


        g.setColor( TIME_GAUGE_FRAME_COLOR );
        g.drawRect( x + 110, y, 100 + 1, 6 + 1 );

        if( end > 0 )
        {
            g.setColor( TIME_GAUGE_COLOR );
            g.fillRect( x + 110 + 1, y + 1, now * 100 / end, 6 );
        }

        //--------------------------------------------------------------------------
        // BPM
        //--------------------------------------------------------------------------
        int bpm  = context.getBPM();
        if( bpm > 999 )
        {
            bpm = 999;
        }

        sb.delete( 0, sb.length() );
        sb.append( "BPM:\u0000=" ).append( bpm );
        DefaultRenderer.drawText( g, fontImage, sb.toString(), x + 230, y );
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    @Override
    public Component getComponent()
    {
        Component c = super.getComponent();
        c.setPreferredSize( screenSize );
        return c;
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    @Override
    public boolean isEnabledResizeWindow()
    {
        return false;
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     * 
     */
    static void drawText( Graphics2D g, Image font, String txt, int x, int y )
    {
        drawText( g, font, txt.toCharArray(), x, y );
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    static void drawText( Graphics2D g, Image font, char[] txt, int x, int y )
    {
        int len = txt.length;
        int i;
        int sx;

        for( i = 0; i < len; i++ )
        {
            int ascii = (int)txt[ i ];

            if( ascii <= 0x7f )
            {
                sx = 6 * ascii;

                g.drawImage( font,
                             x, y, x + 6, y + 10,
                             sx, 0, sx + 6, 10,
                             null );

            }

            x += 6;

        }
    }
}
