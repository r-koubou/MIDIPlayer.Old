
package org.rz.midiplayerplugin.renderer.pianoroll3d;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.rz.midiplayerplugin.renderer.pianoroll3d.config.Position;

/**
 *
 * @author rz
 */
public class LoadCameraAction extends AbstractAction
{

    private final Point3D target;
    private final Position src;

    ////////////////////////////////////////////////////////////////////////////
    /**
     * LoadCameraAction インスタンスを生成する。
     */
    public LoadCameraAction( Point3D target_, Position src_ )
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
    synchronized public void exec()
    {
        synchronized( target )
        {
            target.tx = src.getTx();
            target.ty = src.getTy();
            target.tz = src.getTz();
            target.rx = src.getRx();
            target.ry = src.getRy();
            target.rz = src.getRz();
        }
    }
}
