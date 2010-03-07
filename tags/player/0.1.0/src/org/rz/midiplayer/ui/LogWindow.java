
package org.rz.midiplayer.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ResourceBundle;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import org.rz.midiplayer.logging.LogFormatter;
import org.rz.midiplayer.logging.Loggable;

/**
 *
 * @author rz
 */
public class LogWindow extends javax.swing.JFrame implements Loggable
{
    //==========================================================================
    // Netbeans によって自動生成されるフィールドここから
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JMenuItem clearMenuItem;
    private JMenu editMenu;
    private JMenuBar jMenuBar;
    private JScrollPane jScrollPane;
    private JTextArea jTextArea;
    // End of variables declaration//GEN-END:variables
    // Netbeans によって自動生成されるフィールドここまで
    //==========================================================================

    private final ResourceBundle resourceBundle = ResourceBundle.getBundle( "org.rz.midiplayer.ui.resources.LogWindow" );
    private final LogHandler logHandler;

    ////////////////////////////////////////////////////////////////////////////
    /**
     * LogWindow インスタンスを生成する。
     */
    public LogWindow()
    {
        initComponents();
        logHandler = new LogHandler( jTextArea );
        logger.addHandler( logHandler );
    }

    ////////////////////////////////////////////////////////////////////////////
    /**
     * Netbeans によって自動生成されるフォーム生成処理。
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane = new JScrollPane();
        jTextArea = new JTextArea();
        jMenuBar = new JMenuBar();
        editMenu = new JMenu();
        clearMenuItem = new JMenuItem();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent evt) {
                onWindowClosed(evt);
            }
        });

        jTextArea.setBackground(Color.black);
        jTextArea.setColumns(80);
        jTextArea.setFont(new Font("Monospaced", 0, 12)); // NOI18N
        jTextArea.setForeground(Color.white);
        jTextArea.setRows(25);
        jTextArea.setTabSize(4);
        jScrollPane.setViewportView(jTextArea);

        getContentPane().add(jScrollPane, BorderLayout.CENTER);

        editMenu.setText(resourceBundle.getString( "editManu.label" )); // NOI18N

        clearMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_MASK));
        clearMenuItem.setMnemonic('C');
        clearMenuItem.setText(resourceBundle.getString( "editMenu.clearItem.label" )); // NOI18N
        clearMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                onClearItemAction(evt);
            }
        });
        editMenu.add(clearMenuItem);

        jMenuBar.add(editMenu);

        setJMenuBar(jMenuBar);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    ////////////////////////////////////////////////////////////////////////////////
    /**
     * 
     */
    private void onWindowClosed(WindowEvent evt)//GEN-FIRST:event_onWindowClosed
    {//GEN-HEADEREND:event_onWindowClosed
        logger.removeHandler( logHandler );
    }//GEN-LAST:event_onWindowClosed

    private void onClearItemAction(ActionEvent evt)//GEN-FIRST:event_onClearItemAction
    {//GEN-HEADEREND:event_onClearItemAction
        jTextArea.setText( null );
    }//GEN-LAST:event_onClearItemAction

    /**
     * テキストエリアに描画するためのログハンドラ
     */
    private class LogHandler extends Handler
    {
        private final JTextArea target;
        private final LogFormatter formatter = new LogFormatter();

        public LogHandler( JTextArea target_ )
        {
            target = target_;
            setFormatter( formatter );
        }

        @Override
        public void close() throws SecurityException
        {
        }

        @Override
        public void flush()
        {
        }

        @Override
        public void publish( LogRecord record )
        {
            String txt = getFormatter().format( record );
            SwingUtilities.invokeLater( new Logging( target, txt ) );
        }

        /**
         * Swing エベントディスパッチスレッドでのロギング処理用
         */
        private class Logging implements Runnable
        {
            final JTextArea target;
            final String message;

            public Logging( JTextArea target_, String msg )
            {
                target  = target_;
                message = msg;
            }

            @Override
            public void run()
            {
                target.append( message );
            }

        }

    }

}
