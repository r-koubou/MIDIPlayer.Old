
package org.rz.midiplayerplugin.renderer.pianoroll3d;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.rz.midiplayerplugin.renderer.pianoroll3d.config.Position;

/**
 *
 * @author rz
 */
public class StoreCameraAction extends AbstractAction
{
    private final Position target;
    private final Point3D src;

    ////////////////////////////////////////////////////////////////////////////
    /**
     * StoreCameraAction インスタンスを生成する。
     */
    public StoreCameraAction( Position target_, Point3D src_ )
    {
        target = target_;
        src    = src_;
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    @Override
    public void actionPerformed( ActionEvent e )
    {
        exec();
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     * 
     */
    synchronized void exec()
    {
        synchronized( src )
        {
            target.setTx( src.tx );
            target.setTy( src.ty );
            target.setTz( src.tz );
            target.setRx( src.rx );
            target.setRy( src.ry );
            target.setRz( src.rz );
        }
    }
}
