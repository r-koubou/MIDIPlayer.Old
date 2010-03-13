
package org.rz.midiplayer;

import java.util.logging.Level;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import org.rz.midiplayer.context.Context;
import org.rz.midiplayer.logging.Loggable;
import org.rz.midiplayer.plugin.PluginManager;
import org.rz.midiplayer.plugin.renderer.RendererPlugin;
import org.rz.midiplayer.ui.MainWindow;

/**
 * アプリケーション起動クラス
 * @author rz
 */
public class Main implements Loggable
{
    ////////////////////////////////////////////////////////////////////////////////
    /**
     * アプリケーション・エントリポイント
     */
    static public void main( String[] args )
    {
        SwingUtilities.invokeLater( new Runnable() {
            public void run()
            {
                try
                {
                    Context ctx = new Context();
                    PluginManager<RendererPlugin> pm = new PluginManager<RendererPlugin>( RendererPlugin.class, "data/plugin/renderer" );
                    UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
                    MainWindow win = new MainWindow( ctx );
                    win.setVisible( true );

                }
                catch( Throwable e )
                {
                    logger.log( Level.SEVERE, "ERROR", e );
                }
            }
        });
    }
}
