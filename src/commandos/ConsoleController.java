/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commandos;

import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import javax.swing.text.AbstractDocument;

/**
 * Clase encargada de controlar los eventos
 * en la consola y llevar el computo del sistema
 * operativo
 * @author Johann
 */
public class ConsoleController 
{
    public String VERSION = "0.1";
    private JTextArea view;
    private String prompt;
    
    public ConsoleController(JTextArea v)
    {
        view  = v;
        prompt = "CommandOS> ";
        view.setText("Buongiorno! Bienvenido a CommandOS --- [Version "+VERSION+"] by Johann"+"\n"+prompt);
        setAtEnd();
    }
    
    public void handleCall()
    {
        String command = getCall().trim();
        String[] commands = command.split(" ");
        switch(commands[0].trim().toLowerCase())
        {
            case "":
                log("");
                break;
            case "prompti":
                changePrompt(command);
                break;
            case "ciao":
                exit();
                break;
            default:
                log("No se reconoce el comando "+commands[0].trim()+".\nPruebe de nuevo o escriba helpti para obtener ayuda");
                break;
        }
    }
    
    private void exit()
    {
        ((AbstractDocument)view.getDocument()).setDocumentFilter(null);
        view.setText(view.getText()+"\nCiao! Gracias por usar CommandOS");
            new java.util.Timer().schedule( 
        
        new java.util.TimerTask() {
            @Override
            public void run() {
                System.exit(0);                 //Muchos errores con Thread.Sleep
            }
        }, 
        2000 
        );
    }
    
    private void changePrompt(String command)
    {
        String newPrompt = (command.contains(" "))? command.substring(command.indexOf(" ")).trim() : "";

        if(newPrompt.equals(""))
        {
            changeCommandPrompt("CommandOS");
            log("Prompt cambiado a default");
        }
        else
        {
            changeCommandPrompt(newPrompt);
            log("Prompt cambiado con exito a "+newPrompt);
        }
    }
    
    /***
     * Obtiene el ultimo comando en texto plano escrito 
     * @return Comando filtrado
     */
    private String getCall()
    {
        String[] lines = view.getText().split("\n");
        return lines[lines.length-1].substring(prompt.length()).trim();
    }
    /***
     * Permite mostrar un mensaje de log dentro de la consola
     * Inhabilita el filtro, imprime y vuelve a habilitarlo
     * @param message Log a mostrar
     */
    private void log(String message)
    {
        CommandFilter filter = (CommandFilter) ((AbstractDocument)view.getDocument()).getDocumentFilter();
        ((AbstractDocument)view.getDocument()).setDocumentFilter(null);
        if(message.equals(""))
        {
            view.setText(view.getText()+"\n"+prompt);
        }
        else 
        {
            view.setText(view.getText()+ "\n" +message+"\n\n"+prompt);
        }
        setAtEnd();
        ((AbstractDocument)view.getDocument()).setDocumentFilter(filter);
    }
    
    /***
     * Coloca el cursor en el ultimo caracter mostrado
     */
    public void setAtEnd()
    {
        view.setCaretPosition(view.getDocument().getLength());        
    }
    
    /***
     * Cambia el prompt tanto en el filtro como en la consola
     * @param message 
     */
    private void changeCommandPrompt(String message)
    {
        CommandFilter filter = (CommandFilter) ((AbstractDocument)view.getDocument()).getDocumentFilter();
        filter.setCommandPrompt(message);
        prompt = message + "> ";
    }
}
