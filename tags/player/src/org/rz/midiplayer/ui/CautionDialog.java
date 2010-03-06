
package org.rz.midiplayer.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

/**
 *
 * @author rz
 */
public class CautionDialog extends javax.swing.JDialog
{
    private final ResourceBundle resourceBundle = ResourceBundle.getBundle(  "org.rz.midiplayer.ui.resources.CautionDialog" );

    //==========================================================================
    // Netbeans によって自動生成されるフィールドここから
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JPanel buttonPanel;
    private JPanel contentPanel;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JButton okButton;
    // End of variables declaration//GEN-END:variables
    // Netbeans によって自動生成されるフィールドここまで
    //==========================================================================

    ////////////////////////////////////////////////////////////////////////////
    /**
     * CautionDialog インスタンスを生成する。
     */
    public CautionDialog( java.awt.Frame parent, boolean modal )
    {
        super( parent, modal );
        initComponents();
    }

    ////////////////////////////////////////////////////////////////////////////
    /**
     * Netbeans によって自動生成されるフォーム生成処理。
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        contentPanel = new JPanel();
        jLabel1 = new JLabel();
        jLabel2 = new JLabel();
        buttonPanel = new JPanel();
        okButton = new JButton();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(resourceBundle.getString( "window.title.label" )); // NOI18N
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        contentPanel.setBorder(BorderFactory.createEmptyBorder(1, 12, 1, 12));
        contentPanel.setLayout(new BorderLayout());

        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel1.setIcon(new ImageIcon(getClass().getResource("/org/rz/midiplayer/ui/resources/CautionDialog-img1.png"))); // NOI18N
        jLabel1.setBorder(null);
        contentPanel.add(jLabel1, BorderLayout.CENTER);

        jLabel2.setText(resourceBundle.getString( "message.text" )); // NOI18N
        jLabel2.setBorder(BorderFactory.createEmptyBorder(12, 1, 12, 1));
        contentPanel.add(jLabel2, BorderLayout.PAGE_START);

        getContentPane().add(contentPanel);

        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        okButton.setText(resourceBundle.getString( "okButton.label" )); // NOI18N
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                onOkButtonAction(evt);
            }
        });
        buttonPanel.add(okButton);

        getContentPane().add(buttonPanel);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    private void onOkButtonAction(ActionEvent evt)//GEN-FIRST:event_onOkButtonAction
    {//GEN-HEADEREND:event_onOkButtonAction
        dispose();
    }//GEN-LAST:event_onOkButtonAction


    ////////////////////////////////////////////////////////////////////////////////
    /**
     * Test code.
     */
    static public void main( String[] args )
    {
        new CautionDialog( null, false ).setVisible( true );
    }

}
