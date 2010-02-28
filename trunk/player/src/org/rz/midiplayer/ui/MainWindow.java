
package org.rz.midiplayer.ui;


import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.logging.Level;
import javax.sound.midi.MidiDevice;
import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileFilter;
import org.rz.midiplayer.context.Context;
import org.rz.midiplayer.logging.Log;
import org.rz.midiplayer.logging.Loggable;
import org.rz.midiplayer.midi.MIDIDeviceManager;
import org.rz.midiplayer.plugin.PluginManager;
import org.rz.midiplayer.plugin.info.PluginInfo;
import org.rz.midiplayer.plugin.renderer.RendererPlugin;
import org.rz.midiplayer.xmlmodule.appconfig.ApplicationConfig;


/**
 *
 * @author rz
 */
public class MainWindow extends javax.swing.JFrame implements Loggable
{
    private final ResourceBundle resourceBundle = ResourceBundle.getBundle( "org.rz.midiplayer.ui.resources.MainWindow" );
    private final Context context;
    private RendererPlugin renderer;
    private File selectedMidiFile;

    //==========================================================================
    // Netbeans によって自動生成されるフィールドここから
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private ButtonGroup deviceInfoButtonGroup;
    private JMenu deviceInfoMenu;
    private JMenuItem exitMenuItem;
    private JMenu fileMenu;
    private ButtonGroup lafButtonGroup;
    private JMenu lafMenu;
    private JMenuBar menuBar;
    private ButtonGroup midiInButtonGroup;
    private JMenu midiInMenu;
    private ButtonGroup midiOutButtonGroup;
    private JMenu midiOutMenu;
    private JMenuItem openLogWindpwMenuItem;
    private JMenuItem openMenuItem;
    private JMenu optionMenu;
    private JMenuItem playMenuItem;
    private JMenuItem realTimeMenuItem;
    private ButtonGroup rendererButtonGroup;
    private JMenu rendererMenu;
    private JMenuItem stopMenuItem;
    private JMenu toolMenu;
    // End of variables declaration//GEN-END:variables
    // Netbeans によって自動生成されるフィールドここまで
    //==========================================================================

    ////////////////////////////////////////////////////////////////////////////
    /**
     * MainWindow インスタンスを生成する。
     */
    public MainWindow( Context context_ )
    {
        context = context_;
        initComponents();

        try
        {
            ToolTipManager.sharedInstance().setInitialDelay( 250 );
        }
        catch( Throwable e ) {}
    }

    ////////////////////////////////////////////////////////////////////////////
    /**
     * Netbeans によって自動生成されるフォーム生成処理。
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lafButtonGroup = new ButtonGroup();
        rendererButtonGroup = new ButtonGroup();
        midiOutButtonGroup = new ButtonGroup();
        midiInButtonGroup = new ButtonGroup();
        deviceInfoButtonGroup = new ButtonGroup();
        menuBar = new JMenuBar();
        fileMenu = new JMenu();
        openMenuItem = new JMenuItem();
        exitMenuItem = new JMenuItem();
        openLogWindpwMenuItem = new JMenuItem();
        toolMenu = new JMenu();
        playMenuItem = new JMenuItem();
        stopMenuItem = new JMenuItem();
        realTimeMenuItem = new JMenuItem();
        optionMenu = new JMenu();
        midiOutMenu = new JMenu();
        midiInMenu = new JMenu();
        rendererMenu = new JMenu();
        deviceInfoMenu = new JMenu();
        lafMenu = new JMenu();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent evt) {
                onWindowClosed(evt);
            }
            public void windowClosing(WindowEvent evt) {
                onWindowClosing(evt);
            }
            public void windowOpened(WindowEvent evt) {
                onWindowOpened(evt);
            }
        });

        fileMenu.setMnemonic('F');
        fileMenu.setText(resourceBundle.getString( "fileMenu.label" )); // NOI18N

        openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
        openMenuItem.setMnemonic('O');
        openMenuItem.setText(resourceBundle.getString( "openMenuItem.label" )); // NOI18N
        openMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                onOpenMenuAction(evt);
            }
        });
        fileMenu.add(openMenuItem);

        exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));
        exitMenuItem.setMnemonic('X');
        exitMenuItem.setText(resourceBundle.getString( "exitMenuItem" )); // NOI18N
        exitMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                onExitMenuItemAction(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        openLogWindpwMenuItem.setMnemonic('L');
        openLogWindpwMenuItem.setText(resourceBundle.getString( "openLogWindowItem" )); // NOI18N
        openLogWindpwMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                onOpenLogWindpwMenuItemAction(evt);
            }
        });
        fileMenu.add(openLogWindpwMenuItem);
        openLogWindpwMenuItem.setEnabled( Log.loggingEnabled );

        menuBar.add(fileMenu);

        toolMenu.setMnemonic('T');
        toolMenu.setText(resourceBundle.getString( "toolMenu.label" )); // NOI18N

        playMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0));
        playMenuItem.setMnemonic('P');
        playMenuItem.setText(resourceBundle.getString( "playMenuItem.label" )); // NOI18N
        playMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                onPlayMenuItemAction(evt);
            }
        });
        toolMenu.add(playMenuItem);

        stopMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));
        stopMenuItem.setMnemonic('S');
        stopMenuItem.setText(resourceBundle.getString( "stopMenuItem.label" )); // NOI18N
        stopMenuItem.setEnabled(false);
        stopMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                onStopMenuItemAction(evt);
            }
        });
        toolMenu.add(stopMenuItem);

        realTimeMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, InputEvent.CTRL_MASK));
        realTimeMenuItem.setMnemonic('R');
        realTimeMenuItem.setText(resourceBundle.getString( "realTimeMenu.label" )); // NOI18N
        realTimeMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                onRealTimeMenuItemAction(evt);
            }
        });
        toolMenu.add(realTimeMenuItem);

        menuBar.add(toolMenu);

        optionMenu.setMnemonic('O');
        optionMenu.setText(resourceBundle.getString( "optionMenu.label" )); // NOI18N

        midiOutMenu.setMnemonic('O');
        midiOutMenu.setText(resourceBundle.getString( "midioutMenu.label" )); // NOI18N
        setupMidiOutMenu();
        optionMenu.add(midiOutMenu);

        midiInMenu.setMnemonic('I');
        midiInMenu.setText(resourceBundle.getString( "midiInMenu.label" )); // NOI18N
        setupMidiInMenu();
        optionMenu.add(midiInMenu);

        rendererMenu.setMnemonic('R');
        rendererMenu.setText(resourceBundle.getString( "rendererMenuItem.label" )); // NOI18N
        // setup the renderer selector
        setupRendererMenu();
        optionMenu.add(rendererMenu);

        deviceInfoMenu.setMnemonic('D');
        deviceInfoMenu.setText(resourceBundle.getString( "deviceInfoMenu.label" )); // NOI18N
        deviceInfoMenu.setToolTipText(resourceBundle.getString( "deviceInfoMenu.desc" )); // NOI18N
        setupDeviceInfoMenu();
        optionMenu.add(deviceInfoMenu);

        lafMenu.setMnemonic('L');
        lafMenu.setText(resourceBundle.getString( "optionMenu.laf.label" )); // NOI18N
        // L&F Selector
        {
            String current = UIManager.getLookAndFeel().getName();

            for( UIManager.LookAndFeelInfo i : UIManager.getInstalledLookAndFeels() )
            {
                JRadioButtonMenuItem item = new JRadioButtonMenuItem();
                item.setSelected( i.getName().equals( current ) );
                item.setAction( new LafChangeAction( i, this ) );
                item.setText( i.getName() );
                lafMenu.add( item );
                lafButtonGroup.add( item );
            }
        }
        optionMenu.add(lafMenu);

        menuBar.add(optionMenu);

        setJMenuBar(menuBar);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    ////////////////////////////////////////////////////////////////////////////////
    /**
     * 
     */
    private void setupRendererMenu()
    {
        String current = context.getConfig().getPlugin().getRenderer().getName();

        for( PluginInfo i : context.getRendererPluginManager().getPluginInfoList() )
        {
            JRadioButtonMenuItem item = new JRadioButtonMenuItem();

            item.setSelected( i.getName().equals( current ) );
            item.setAction( new AbstractAction(){
                @Override
                public void actionPerformed( ActionEvent e )
                {
                    JMenuItem it = (JMenuItem)e.getSource();
                    context.getConfig().getPlugin().getRenderer().setName( it.getText() );
                    startRendering();
                }
            });
            item.setText( i.getName() );
            item.setToolTipText( i.getDescription() + " - Author: " + i.getAuthor() + " (ver " + i.getVersion() + ")" );
            rendererMenu.add( item );
            rendererButtonGroup.add( item );
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    private void setupMidiOutMenu()
    {
        Vector<MidiDevice> devs = MIDIDeviceManager.getMidiOutDeviceList();
        String current = context.getConfig().getMidiout().getName();

        for( MidiDevice i : devs )
        {
            String name = i.getDeviceInfo().getName();
            JRadioButtonMenuItem item = new JRadioButtonMenuItem();

            item.setSelected( name.equals( current ) );
            item.setAction( new AbstractAction(){
                @Override
                public void actionPerformed( ActionEvent e )
                {
                    JMenuItem it = (JMenuItem)e.getSource();
                    context.getConfig().getMidiout().setName( it.getText() );
                }
            });
            item.setText( name );
            midiOutMenu.add( item );
            midiOutButtonGroup.add( item );
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    private void setupMidiInMenu()
    {
        Vector<MidiDevice> devs = MIDIDeviceManager.getMidiInDeviceList();
        String current = context.getConfig().getMidiin().getName();

        for( MidiDevice i : devs )
        {
            String name = i.getDeviceInfo().getName();
            JRadioButtonMenuItem item = new JRadioButtonMenuItem();

            item.setSelected( name.equals( current ) );
            item.setAction( new AbstractAction(){
                @Override
                public void actionPerformed( ActionEvent e )
                {
                    JMenuItem it = (JMenuItem)e.getSource();
                    context.getConfig().getMidiin().setName( it.getText() );
                }
            });
            item.setText( name );
            midiInMenu.add( item );
            midiInButtonGroup.add( item );
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    private void setupDeviceInfoMenu()
    {
        String current          = context.getConfig().getDevicefile().getFile();
        Vector<File> deviceDefs = context.getDeviceInfoList();

        for( File i : deviceDefs )
        {
            String name = i.getName();
            JRadioButtonMenuItem item = new JRadioButtonMenuItem();

            item.setSelected( name.equals( current ) );
            item.setAction( new AbstractAction(){
                @Override
                public void actionPerformed( ActionEvent e )
                {
                    JMenuItem it = (JMenuItem)e.getSource();

                    try
                    {
                        context.loadDeviceFile( it.getText() );
                    }
                    catch( Throwable ex )
                    {
                        logger.log( Level.SEVERE, "cannot reload device info file : " + it.getText(), ex );
                    }
                }
            });
            item.setText( name );
            deviceInfoMenu.add( item );
            deviceInfoButtonGroup.add( item );
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     * 
     */
    private void onWindowClosed(WindowEvent evt)//GEN-FIRST:event_onWindowClosed
    {//GEN-HEADEREND:event_onWindowClosed
        context.saveAppConfig();
        context.dispose();
        System.exit( 0 );
    }//GEN-LAST:event_onWindowClosed

    ////////////////////////////////////////////////////////////////////////////////
    /**
     * 
     */
    private void onPlayMenuItemAction(ActionEvent evt)//GEN-FIRST:event_onPlayMenuItemAction
    {//GEN-HEADEREND:event_onPlayMenuItemAction

        if( selectedMidiFile != null )
        {
            renderer.onMidiPlayingBefore( selectedMidiFile );
            if( context.play( selectedMidiFile ) )
            {
                updateMenuItemEnabled( true );
            }
        }
    }//GEN-LAST:event_onPlayMenuItemAction

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    private void onWindowOpened(WindowEvent evt)//GEN-FIRST:event_onWindowOpened
    {//GEN-HEADEREND:event_onWindowOpened
        startRendering();
    }//GEN-LAST:event_onWindowOpened

    private void onWindowClosing(WindowEvent evt)//GEN-FIRST:event_onWindowClosing
    {//GEN-HEADEREND:event_onWindowClosing
        stopRendering();
    }//GEN-LAST:event_onWindowClosing

    ////////////////////////////////////////////////////////////////////////////////
    /**
     * 
     */
    private void onOpenMenuAction(ActionEvent evt)//GEN-FIRST:event_onOpenMenuAction
    {//GEN-HEADEREND:event_onOpenMenuAction
        File current         = new File( context.getConfig().getLastdirectory().getDir() );
        JFileChooser chooser = new JFileChooser();

        if( current != null && current.exists() && current.isDirectory() )
        {
            chooser.setCurrentDirectory( current );
        }

        chooser.setFileFilter( new FileFilter() {

            @Override
            public boolean accept( File f )
            {
                if( f.isDirectory() )
                {
                    return true;
                }
                if( f.getName().toLowerCase().endsWith( ".mid" ) )
                {
                    return true;
                }
                return false;
            }

            @Override
            public String getDescription()
            {
                return "MIDI file(*.mid)";
            }
        } );

        if( chooser.showOpenDialog( this ) == JFileChooser.APPROVE_OPTION )
        {
            selectedMidiFile = chooser.getSelectedFile();
        }

    }//GEN-LAST:event_onOpenMenuAction

    private void onStopMenuItemAction(ActionEvent evt)//GEN-FIRST:event_onStopMenuItemAction
    {//GEN-HEADEREND:event_onStopMenuItemAction
        context.stop();
        if( renderer != null )
        {
            renderer.onMidiStoped();
        }
        updateMenuItemEnabled( false );
    }//GEN-LAST:event_onStopMenuItemAction

    private void onOpenLogWindpwMenuItemAction(ActionEvent evt)//GEN-FIRST:event_onOpenLogWindpwMenuItemAction
    {//GEN-HEADEREND:event_onOpenLogWindpwMenuItemAction
        LogWindow win = new LogWindow();
        win.setVisible( true );
    }//GEN-LAST:event_onOpenLogWindpwMenuItemAction

    private void onExitMenuItemAction(ActionEvent evt)//GEN-FIRST:event_onExitMenuItemAction
    {//GEN-HEADEREND:event_onExitMenuItemAction
        synchronized( this )
        {
            stopRendering();
            context.dispose();
        }
        dispose();
        System.exit( 0 );
    }//GEN-LAST:event_onExitMenuItemAction

    private void onRealTimeMenuItemAction(ActionEvent evt)//GEN-FIRST:event_onRealTimeMenuItemAction
    {//GEN-HEADEREND:event_onRealTimeMenuItemAction
        if( context.startRealTimeInput() )
        {
            updateMenuItemEnabled( true );
        }
    }//GEN-LAST:event_onRealTimeMenuItemAction

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    synchronized private void updateMenuItemEnabled( boolean play )
    {
        playMenuItem.setEnabled( !play );
        stopMenuItem.setEnabled( play );
        realTimeMenuItem.setEnabled( !play );

        midiOutMenu.setEnabled( !play );
        midiInMenu.setEnabled( !play );
        rendererMenu.setEnabled( !play );
        lafMenu.setEnabled( !play );
        deviceInfoMenu.setEnabled( !play );
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     * 
     */
    synchronized private void startRendering()
    {

        try
        {
            stopRenderingImpl();

            ApplicationConfig conf              = context.getConfig();
            String rendererName                 = conf.getPlugin().getRenderer().getName();
            PluginManager<RendererPlugin> mgr   = context.getRendererPluginManager();
            renderer = mgr.newInstance( context, mgr.getPluginInfo( rendererName ) );

            Component screen = renderer.getComponent();
            setResizable( renderer.isEnabledResizeWindow() );
            getContentPane().add( screen, BorderLayout.CENTER );

            pack();

            renderer.startRendering();

        }
        catch( Throwable e )
        {
            logger.log( Level.SEVERE, "cannot start rendering...", e  );
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    synchronized private void stopRendering()
    {
        logger.info( "Main window closing..." );
        stopRenderingImpl();
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    synchronized private void stopRenderingImpl()
    {
        if( renderer != null )
        {
            logger.info( "Stop current renderer..." );

            renderer.stopRendering();
            remove( renderer.getComponent());
            renderer.onDispose( context );
            renderer = null;

            logger.info( "Stopped current renderer..." );

        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     * Test code.
     */
    static public void main( String[] args ) throws Throwable
    {
        SwingUtilities.invokeLater( new Runnable() {
            @Override
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
