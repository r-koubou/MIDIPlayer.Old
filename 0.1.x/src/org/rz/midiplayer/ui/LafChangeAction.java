
package org.rz.midiplayer.ui;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author rz
 */
class LafChangeAction extends AbstractAction
{
    private final UIManager.LookAndFeelInfo info;
    private final JFrame root;

    ////////////////////////////////////////////////////////////////////////////
    /**
     * LafChangeAction インスタンスを生成する。
     */
    public LafChangeAction( UIManager.LookAndFeelInfo info_, JFrame root_ )
    {
        info = info_;
        root = root_;
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    @Override
    public void actionPerformed( ActionEvent e )
    {
        try
        {
            UIManager.setLookAndFeel( info.getClassName() );
            SwingUtilities.updateComponentTreeUI( root );
            root.pack();
        }
        catch( Throwable ex )
        {
        }
    }
}
