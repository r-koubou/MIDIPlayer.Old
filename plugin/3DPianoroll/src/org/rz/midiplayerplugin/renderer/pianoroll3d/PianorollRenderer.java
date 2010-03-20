
package org.rz.midiplayerplugin.renderer.pianoroll3d;

import com.sun.opengl.util.FPSAnimator;
import com.sun.opengl.util.GLUT;
import com.sun.opengl.util.j2d.TextRenderer;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.swing.SwingUtilities;
import javax.xml.bind.JAXBElement;
import org.rz.midiplayer.context.Context;
import org.rz.midiplayer.context.DefaultMidiEventHandler;
import org.rz.midiplayer.context.MidiChannelEventAdaptor;
import org.rz.midiplayer.plugin.renderer.RendererPlugin;
import org.rz.midiplayer.util.PathUtil;
import org.rz.midiplayer.xmlmodule.JAXBUtil;
import org.rz.midiplayerplugin.renderer.pianoroll3d.config.Camera;
import org.rz.midiplayerplugin.renderer.pianoroll3d.config.Config;
import org.rz.midiplayerplugin.renderer.pianoroll3d.config.Position;
import org.rz.midiplayerplugin.renderer.pianoroll3d.config.Renderer;

/**
 *
 * @author rz
 */
public class PianorollRenderer implements RendererPlugin, GLEventListener
{

    static final int NOTE_OBJ_NUM = 1024;

    private Context context;
    private String pluginDir;
    private final Dimension screenSize = new Dimension( 512, 384 );
    private DefaultMidiEventHandler midiEventHandler;

    private final LinkedList<NoteObject> masterObjList = new LinkedList<NoteObject>();
    private final LinkedList<NoteObject> activeObjList = new LinkedList<NoteObject>();

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
    private final float[] gridColor  = { 0.1f, 0.1f, 0.1f, 1.0f };
    private final float[] gridNormal = { 0.0f, 1.0f, 0.0f };

    private final GLCanvas canvas   = new GLCanvas();
    private final GLUT glut         = new GLUT();
    private final FPSAnimator animator = new FPSAnimator( canvas, 60, false );

    private final Color TEXT_COLOR = new Color( 0xff, 0xff, 0xff, 0x80 );
    private final TextRenderer text = new TextRenderer( new Font( Font.MONOSPACED, Font.PLAIN, 12 ) );

    private final ArrayList<LoadCameraAction>  execLoadCameraList  = new ArrayList<LoadCameraAction>();
    private final ArrayList<StoreCameraAction> execStoreCameraList = new ArrayList<StoreCameraAction>();

    private Point3D[] cameraPointList;
    private Point3D currentCamera;
    private int lastCameraIndex;

    private final LightInfo light = new LightInfo();

    private int lastX = -1;
    private int lastY = -1;

    private boolean visibleCameraPosition = false;
    private boolean visibleGrid           = true;

    private JAXBElement<Config> configRootElement;

    private int[] vboIds = new int[ 2 ];
    static final int VBO_VERTEX_NUM = NoteObject.VBO_VERTEX_NUM * NOTE_OBJ_NUM;
    private FloatBuffer vboVertex = FloatBuffer.allocate( VBO_VERTEX_NUM );

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    @Override
    public void onInit( Context ctx, String pluginDir )
    {
        int i;
        context = ctx;
        this.pluginDir = pluginDir;

        canvas.setPreferredSize( new Dimension( 512, 384 ) );
        canvas.addGLEventListener( this );
        canvas.addMouseWheelListener( new MouseWheelListener() {

            @Override
            public void mouseWheelMoved( MouseWheelEvent e )
            {
                onMouseWheelMoved( e );
            }
        });

        canvas.addKeyListener( new KeyAdapter(){
            @Override
            public void keyPressed( KeyEvent e )
            {
                onKeyPressed( e );
            }
        }) ;

        canvas.addMouseMotionListener( new MouseMotionAdapter() {

            @Override
            public void mouseDragged( MouseEvent e )
            {
                onMouseDragged( e );
            }

        });

        midiEventHandler = new DefaultMidiEventHandler( ctx );

        midiEventHandler.addMidiChannelEventListener( new MidiChannelEventAdaptor() {

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
            configRootElement =  jaxbConfig.loadFromFile( PathUtil.package2ClasspathString( getClass() ) + "/config.xsd", PathUtil.buildPath( pluginDir, "config.xml" ) );
            Config config = configRootElement.getValue();

            for( org.rz.midiplayerplugin.renderer.pianoroll3d.config.Color c :  config.getPianoroll().getColor() )
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

            visibleCameraPosition = config.getRenderer().isViewPosition();
            visibleGrid           = config.getRenderer().isViewGrid();

            List<Position> posList = config.getRenderer().getCamera().getPosition();
            cameraPointList = new Point3D[ posList.size() ];
            for( i = 0; i < posList.size(); i++ )
            {
                cameraPointList[ i ] = new Point3D();
                cameraPointList[ i ].tx = posList.get( i ).getTx();
                cameraPointList[ i ].ty = posList.get( i ).getTy();
                cameraPointList[ i ].tz = posList.get( i ).getTz();
                cameraPointList[ i ].rx = posList.get( i ).getRx();
                cameraPointList[ i ].ry = posList.get( i ).getRy();
                cameraPointList[ i ].rz = posList.get( i ).getRz();
            }

            currentCamera = new Point3D();

            lastCameraIndex = Math.min( config.getRenderer().getCamera().getLastUsed(), posList.size() - 1 );
            cameraPointList[ lastCameraIndex ].copyTo( currentCamera );

            i = 0;
            for( Position p : posList )
            {
                LoadCameraAction loadAct = new LoadCameraAction( currentCamera, p );
                execLoadCameraList.add( loadAct );

                StoreCameraAction storeAct = new StoreCameraAction( p, currentCamera );
                execStoreCameraList.add( storeAct );

                i++;
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
    public void startRendering()
    {
        animator.start();
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    @Override
    public void stopRendering()
    {
        animator.stop();
        canvas.removeGLEventListener( this );
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    @Override
    public Component getComponent()
    {
        return canvas;
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
        synchronized( activeObjList )
        {
            for( NoteObject o : activeObjList )
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
        synchronized( masterObjList )
        {
            synchronized( activeObjList )
            {
                NoteObject no;
                if( ! masterObjList.isEmpty() )
                {
                    no = masterObjList.removeFirst();
                }
                else
                {
                    no = activeObjList.removeFirst();
                }

                no.reset();
                no.noteOn( (float)( ( 100f / 128 ) * (64-noteNo) ),
                         //(float)( ( 100f / 128 ) * (noteNo-64) ),
                          (float)( ( 1f ) * (ch) ),
                          vel );
                no.noteNo = noteNo;
                no.channel = ch;
                no.setColor( pianoRollColors[ ch ] );

                activeObjList.addLast( no );
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    synchronized private void noteOff( int ch, int noteNo )
    {
        synchronized( activeObjList )
        {
            for( NoteObject no : activeObjList )
            {
                if( no.channel == ch && no.noteNo == noteNo )
                {
                    no.noteOff();
                }
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    @Override
    synchronized public void onDispose( Context ctx )
    {
        ctx.removeMidiEventHandler( midiEventHandler );
        try
        {
            if( configRootElement != null )
            {
                logger.info( "Save to config file" );

                Renderer r = configRootElement.getValue().getRenderer();
                Camera   c = r.getCamera();
                r.setViewGrid( visibleGrid );
                r.setViewPosition( visibleCameraPosition );
                c.setLastUsed( (short)lastCameraIndex );

                JAXBUtil<Config> jaxbConfig = new JAXBUtil<Config>( Config.class );
                jaxbConfig.writeToFile( configRootElement, PathUtil.package2ClasspathString( getClass() ) + "/config.xsd", new File( PathUtil.buildPath( pluginDir, "config.xml" ) ) );
                logger.info( "Save to config file successfully" );
            }
        }
        catch( Throwable e )
        {
            logger.log( Level.SEVERE, "Failed to save config..", e );
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    private void update( GL gl )
    {
        synchronized( masterObjList )
        {
            synchronized( activeObjList )
            {
                int i;
                int s;

                for( i = 0; i < activeObjList.size(); )
                {
                    NoteObject n = activeObjList.get( i );
                    if( ! n.visible )
                    {
                        activeObjList.remove( i );
                        masterObjList.addLast( n );
                        continue;
                    }
                    n.update( gl );
                    i++;
                }
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    private void render( GL gl )
    {
        Point3D camera = currentCamera;
        final float cx = camera.tx;
        final float cy = camera.ty;
        final float cz = camera.tz;
        final float rx = camera.rx;
        final float ry = camera.ry;
        final float rz = camera.rz;
        final LightInfo li = light;

        gl.glEnable( GL.GL_LIGHTING );
        gl.glEnable( GL.GL_LIGHT0 );

        gl.glClear( GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT );
        gl.glEnable( GL.GL_DEPTH_TEST );
        gl.glEnable( GL.GL_CULL_FACE );

        gl.glMatrixMode(GL.GL_MODELVIEW);

        gl.glLoadIdentity();

        gl.glLightfv( GL.GL_LIGHT0, GL.GL_POSITION, li.getLightPosition(), 0 );
        gl.glLightfv( GL.GL_LIGHT0, GL.GL_AMBIENT, li.getLightAmbient(), 0   );
        gl.glLightfv( GL.GL_LIGHT0, GL.GL_SPECULAR, li.getLightSpecular(), 0 );

        gl.glTranslatef( cx, cy, cz );
        gl.glRotatef( rx, 1.0f, 0f,   0f );
        gl.glRotatef( ry, 0.0f, 1.0f, 0f );
        gl.glRotatef( rz, 0.0f, 0f, 1.0f );

        if( visibleGrid )
        {
            gl.glPushMatrix();

            float x = 100f / 64f;

            gl.glLightfv( GL.GL_LIGHT0, GL.GL_DIFFUSE, gridColor, 0 );
            gl.glBegin( GL.GL_LINES );

            for( int i = 0; i < 64; i++ )
            {
                gl.glNormal3fv( gridNormal, 0 );
                gl.glVertex3f( x * (32-i), -1.0f, -250.0f );
                gl.glVertex3f( x * (32-i), -1.0f,  250.0f );
            }
            gl.glEnd();
            gl.glPopMatrix();
        }

        synchronized( activeObjList )
        {
            for( NoteObject n : activeObjList )
            {
                n.render( gl, glut );
            }
        }

        if( visibleCameraPosition )
        {
            text.beginRendering( 512, 384 );
            text.setColor( TEXT_COLOR );
            text.draw( "notes:" + activeObjList.size(), 0, 72 );
            text.draw( "tx:" + cx, 0, 60 );
            text.draw( "ty:" + cy, 0, 48 );
            text.draw( "tz:" + cz, 0, 36 );
            text.draw( "rx:" + rx, 0, 24 );
            text.draw( "ry:" + ry, 0, 12 );
            text.draw( "rz:" + rz, 0, 0 );
            text.endRendering();
        }

        gl.glFlush();

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
    @Override
    public void init( GLAutoDrawable drawable )
    {
        GL gl = drawable.getGL();
        synchronized( masterObjList )
        {
            masterObjList.clear();
            for( int i = 0; i < NOTE_OBJ_NUM; i++ )
            {
                masterObjList.addLast( new NoteObject( i, vboIds[ 0 ] ) );
            }
        }

        synchronized( activeObjList )
        {
            activeObjList.clear();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    @Override
    public void display( GLAutoDrawable drawable )
    {
        GL gl = drawable.getGL();

        update( gl );
        render( gl );

    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    @Override
    public void reshape( GLAutoDrawable drawable, int x, int y, int width, int height )
    {
        GL gl = drawable.getGL();
        float ratio = (float)height / (float)width;

        gl.glShadeModel( GL.GL_SMOOTH );

        gl.glViewport( 0, 0, width, height );

        gl.glMatrixMode( GL.GL_PROJECTION );
        gl.glLoadIdentity();
        gl.glFrustum( -1.0f, 1.0f, -ratio, ratio, 3.0f, 1000.0f );
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    @Override
    public void displayChanged( GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged )
    {
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     * 
     */
    synchronized private void onKeyPressed( KeyEvent e )
    {
        int i;
        int keyCode = e.getKeyCode();

        if( keyCode == KeyEvent.VK_D )
        {
            currentCamera.tx = 0; currentCamera.ty = 0; currentCamera.tz = 0;
            currentCamera.rx = 0; currentCamera.ry = 0; currentCamera.rz = 0;
        }

        if( keyCode == KeyEvent.VK_G )
        {
            visibleGrid = !visibleGrid;
        }

        if( keyCode == KeyEvent.VK_C )
        {
            visibleCameraPosition = !visibleCameraPosition;
        }

        if( keyCode == KeyEvent.VK_RIGHT )
        {
            currentCamera.tx += 0.5f;
        }
        if( keyCode == KeyEvent.VK_LEFT )
        {
            currentCamera.tx -= 0.5f;
        }
        if( keyCode == KeyEvent.VK_UP )
        {
            if( e.isShiftDown() )         currentCamera.rx += 1f;
            else if( e.isControlDown() )  currentCamera.ry += 1f;
            else if( e.isAltDown() )      currentCamera.rz += 1f;
            else                          currentCamera.ty -= 0.5f;
        }
        if( keyCode == KeyEvent.VK_DOWN )
        {
            if( e.isShiftDown() )         currentCamera.rx -= 1f;
            else if( e.isControlDown() )  currentCamera.ry -= 1f;
            else if( e.isAltDown() )      currentCamera.rz -= 1f;
            else                          currentCamera.ty += 0.5f;
        }

        i = -1;
        switch( keyCode )
        {
            case KeyEvent.VK_1: i = 0; break;
            case KeyEvent.VK_2: i = 1; break;
            case KeyEvent.VK_3: i = 2; break;
            case KeyEvent.VK_4: i = 3; break;
            case KeyEvent.VK_5: i = 4; break;
            case KeyEvent.VK_6: i = 5; break;
            case KeyEvent.VK_7: i = 6; break;
            case KeyEvent.VK_8: i = 7; break;
        }

        if( i >= 0 )
        {
            if( e.isShiftDown() )
            {
                StoreCameraAction a = execStoreCameraList.get( i );
                synchronized( a )
                {
                    a.exec();
                }
            }
            else
            {
                LoadCameraAction a = execLoadCameraList.get( i );
                synchronized( a )
                {
                    a.exec();
                }
                lastCameraIndex = i;
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    synchronized private void onMouseDragged( MouseEvent e )
    {
        int x = e.getX();
        int y = e.getY();

        int vx = lastX - x;
        int vy = lastY - y;

        float add = 0.5f;

        if( SwingUtilities.isLeftMouseButton( e ) && e.isAltDown() )
        {
            if( vx < 0 )
            {
                currentCamera.rz += add;
            }
            else if( vx > 0 )
            {
                currentCamera.rz -= add;
            }

            if( vy < 0 )
            {
                currentCamera.rz += add;
            }
            else if( vy > 0 )
            {
                currentCamera.rz -= add;
            }
        }
        else if( SwingUtilities.isLeftMouseButton( e ) && e.isControlDown() )
        {
            if( vx < 0 )
            {
                currentCamera.ry += add;
            }
            else if( vx > 0 )
            {
                currentCamera.ry -= add;
            }

        }
        else if( SwingUtilities.isLeftMouseButton( e ) && e.isShiftDown() )
        {
            if( vy < 0 )
            {
                currentCamera.rx += add;
            }
            else if( vy > 0 )
            {
                currentCamera.rx -= add;
            }
        }
        else if( SwingUtilities.isRightMouseButton( e ) )
        {
            if( vx < 0 )
            {
                currentCamera.tx += add;
            }
            else if( vx > 0 )
            {
                currentCamera.tx -= add;
            }
            if( vy < 0 )
            {
                currentCamera.ty -= add;
            }
            else if( vy > 0 )
            {
                currentCamera.ty += add;
            }
        }

        lastX = x;
        lastY = y;
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     * 
     */
    synchronized private void onMouseWheelMoved( MouseWheelEvent e )
    {
        final float rotation = 2.0f * e.getWheelRotation();

        if( e.isShiftDown() )
        {
            currentCamera.rx += rotation;
        }
        else if( e.isControlDown() )
        {
            currentCamera.ry += rotation;
        }
        else if( e.isAltDown() )
        {
            currentCamera.rz += rotation;
        }
        else
        {
            currentCamera.tz += e.getWheelRotation() * 10f;
        }
    }

}
