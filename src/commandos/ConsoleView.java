/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commandos;

import java.awt.Color;
import java.awt.event.KeyEvent;
import javafx.scene.input.KeyCode;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.text.AbstractDocument;

/**
 * Clase encargada de la vista e interaccion
 * con el usuario
 * @author Johann
 */
public class ConsoleView extends javax.swing.JFrame {
    
    private ConsoleController console;

    /**
     * Creates new form ConsoleView
     */
    public ConsoleView() {
        initComponents();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        console = new ConsoleController((JTextArea)consoleText);
        this.setTitle("CommandOS version "+console.VERSION);
        ((AbstractDocument)consoleText.getDocument()).setDocumentFilter
        (
            new CommandFilter()
        );
        this.getContentPane().setBackground( Color.BLACK );

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        consoleScroll = new javax.swing.JScrollPane();
        consoleText = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        consoleText.setBackground(new java.awt.Color(0, 0, 0));
        consoleText.setColumns(20);
        consoleText.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        consoleText.setForeground(new java.awt.Color(0, 255, 0));
        consoleText.setLineWrap(true);
        consoleText.setRows(5);
        consoleText.setBorder(javax.swing.BorderFactory.createMatteBorder(10, 10, 10, 10, new java.awt.Color(0, 0, 0)));
        consoleText.setCaretColor(new java.awt.Color(0, 255, 0));
        consoleText.setDragEnabled(true);
        consoleText.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                consoleTextCaretUpdate(evt);
            }
        });
        consoleText.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                consoleTextKeyPressed(evt);
            }
        });
        consoleScroll.setViewportView(consoleText);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(consoleScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 558, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(consoleScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void consoleTextKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_consoleTextKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
        {
            console.handleCall();
        }
    }//GEN-LAST:event_consoleTextKeyPressed

    private void consoleTextCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_consoleTextCaretUpdate

    }//GEN-LAST:event_consoleTextCaretUpdate

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane consoleScroll;
    private javax.swing.JTextArea consoleText;
    // End of variables declaration//GEN-END:variables
}
