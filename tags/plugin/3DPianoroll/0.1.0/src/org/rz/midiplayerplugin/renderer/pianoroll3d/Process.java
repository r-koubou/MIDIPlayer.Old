
package org.rz.midiplayerplugin.renderer.pianoroll3d;

import java.util.LinkedList;
import org.rz.midiplayer.logging.Loggable;

/**
 *
 * @author rz
 */
class Process implements Runnable, Loggable
{
    private final LinkedList<NoteObject> masterObjList;
    private final LinkedList<NoteObject> activeObjList;
    private final LinkedList<NoteObject> workObjList = new LinkedList<NoteObject>();

    private volatile boolean threadAlive;
    private Thread thread;

    ////////////////////////////////////////////////////////////////////////////
    /**
     * Process インスタンスを生成する。
     */
    Process( LinkedList<NoteObject> mas, LinkedList<NoteObject> act )
    {
        masterObjList = mas;
        activeObjList = act;
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     * 
     */
    public void start()
    {
        stop();
        thread = new Thread( this );
        thread.start();
        logger.info( "started processing thread..." );
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     * 
     */
    public void stop()
    {
        if( thread != null && thread.isAlive() )
        {
            try
            {
                threadAlive = false;
                thread.join();
            }
            catch( Throwable e ) {}

            thread = null;
            logger.info( "stopped processing thread..." );
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     * 
     */
    @Override
    public void run()
    {
        long period = 16666666L;   // 60fps
        long before;
        long after;
        long diffTime;
        long sleepTime;
        long sleepDiffTime = 0;
        boolean except = false;

        before = System.nanoTime();

        threadAlive = true;
        while( threadAlive )
        {
            try
            {
                update();
            }
            catch( Throwable e )
            {
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
    private void update()
    {
        synchronized( masterObjList )
        {
            synchronized( activeObjList )
            {
                int i;
                int s;

/*
                workObjList.clear();

                for( NoteObject n : activeObjList )
                {
                    if( ! n.visible )
                    {
                        workObjList.add( n );
                    }
                }

                for( NoteObject n : workObjList )
                {
                    masterObjList.addLast( n );
                }

                activeObjList.removeAll( workObjList );

                for( NoteObject n : activeObjList )
                {
                    n.update();
                }
/*/
                for( i = 0; i < activeObjList.size(); )
                {
                    NoteObject n = activeObjList.get( i );
                    if( ! n.visible )
                    {
                        activeObjList.remove( i );
                        masterObjList.addLast( n );
                        continue;
                    }
                    n.update();
                    i++;
                }
/**/
            }
        }
    }
}
