
package org.rz.midiplayerplugin.renderer.pianoroll;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import org.rz.midiplayer.context.Context;
import org.rz.midiplayer.context.DefaultMidiEventHandler;
import org.rz.midiplayer.context.MidiChannelEventAdaptor;
import org.rz.midiplayer.plugin.renderer.SimpleRenderer;
import org.rz.midiplayer.util.PathUtil;
import org.rz.midiplayer.xmlmodule.JAXBUtil;
import org.rz.midiplayerplugin.renderer.pianoroll.config.Config;

/**
 *
 * @author rz
 */
public class PianorollRenderer extends SimpleRenderer
{
    private Context context;
    private final Dimension screenSize = new Dimension( 512, 384 );
    private DefaultMidiEventHandler midiEventHandler;

    private final NoteObjectPool noteObjects = new NoteObjectPool( 2048 );
    private final ArrayList<NoteObject> noteList = new ArrayList<NoteObject>( 2048 );

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
        midiEventHandler.addMidiChannelEventListener( new MidiChannelEventAdaptor(){

            @Override
            public void noteOff( int ch, int note )
            {
                PianorollRenderer.this.noteOff( ch, note );
            }

            @Override
            public void noteOn( int ch, int note, int vel )
            {
                PianorollRenderer.this.noteOn( ch, note, vel );
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

            for( org.rz.midiplayerplugin.renderer.pianoroll.config.Color c :  config.getPianoroll().getColor() )
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
        synchronized( noteList )
        {
            NoteObject n = noteObjects.create();
            n.noteOn( 512-40, 382 - ( 3 * noteNo ) );
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
    synchronized private void noteOff( int ch, int noteNo )
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

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    @Override
    public void onDispose( Context ctx )
    {
        ctx.removeMidiEventHandler( midiEventHandler );
        super.onDispose( ctx );
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    @Override
    public void update()
    {
        synchronized( noteList )
        {
            int i;
            int s;

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
        g.setColor( Color.black );
        g.fillRect( 0, 0, screenSize.width, screenSize.height );

        synchronized( noteList )
        {
            for( NoteObject n : noteList )
            {
                n.render( (Graphics2D)g );
            }
        }

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

}
