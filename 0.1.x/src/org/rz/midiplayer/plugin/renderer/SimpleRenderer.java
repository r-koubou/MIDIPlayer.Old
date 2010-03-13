
package org.rz.midiplayer.plugin.renderer;

import java.awt.Canvas;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.util.logging.Level;
import org.rz.midiplayer.context.Context;

/**
 * 60FPS で描画を行うレンダラー。
 * @author rz
 */
abstract public class SimpleRenderer implements RendererPlugin
{
    volatile private boolean alive;

    private final Toolkit toolkit = Toolkit.getDefaultToolkit();
    protected final Canvas canvas   = new Canvas();
    private BufferStrategy bufferStrategy;

    private Thread thread;

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    @Override
    synchronized public void startRendering()
    {
        stopRendering();

        if( bufferStrategy == null )
        {
            logger.info( "create a bufferStrategy..." );
            try
            {
                canvas.createBufferStrategy( 3 );
                bufferStrategy = canvas.getBufferStrategy();
                logger.info( "bufferStrategy was created..." );
            }
            catch( Throwable e )
            {
                logger.log( Level.SEVERE, "failed to create bufferStrategy instance.", e );
            }
        }

        thread = new Thread( new Runnable() {
            @Override
            public void run()
            {
                mainLoop();
            }
        });

        thread.start();
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    @Override
    public void stopRendering()
    {
        if( thread != null && thread.isAlive() )
        {
            try
            {
                alive = false;
                thread.join();
            }
            catch( Throwable e ) {}

            thread = null;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    private void mainLoop()
    {
        long period = 16666666L;   // 60fps
        long before;
        long after;
        long diffTime;
        long sleepTime;
        long sleepDiffTime = 0;
        boolean except = false;

        before = System.nanoTime();

        alive = true;
        while( alive )
        {
            try
            {
                update();
                render();
                flush();
            }
            catch( Throwable e )
            {
                logger.log( Level.SEVERE, "Renderer ERROR", e );
                except = true;
                break;
            }

            after    = System.nanoTime();
            diffTime = after - before;

            sleepTime = ( period - diffTime ) - sleepDiffTime;

            if( sleepTime > 0 )
            {
                try
                {
                    Thread.sleep( sleepTime / 1000000L );
                }
                catch( InterruptedException e ) {}
                sleepDiffTime = ( System.nanoTime() - after ) - sleepTime;
            }
            else
            {
                sleepDiffTime = 0;
            }

            before = System.nanoTime();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    private void render()
    {
        BufferStrategy b = bufferStrategy;
        if( b != null )
        {
            Graphics2D g = (Graphics2D)b.getDrawGraphics();
            try
            {
                if( ! b.contentsLost() )
                {
                    draw( g );
                    b.show();
                    toolkit.sync();
                }
            }
            finally
            {
                g.dispose();
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    @Override
    public void flush()
    {
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
    synchronized public void onDispose( Context ctx )
    {
        bufferStrategy.dispose();
        bufferStrategy = null;
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    abstract protected void update();

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    abstract protected void draw( Graphics2D g );
}
