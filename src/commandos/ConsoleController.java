/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commandos;

import java.util.Hashtable;
import java.util.Set;
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
    public String VERSION = "0.2";
    private JTextArea view;
    private String prompt;
    private Hashtable<String, Double> vars;
    
    public ConsoleController(JTextArea v)
    {
        vars = new Hashtable<String,Double>();
        view  = v;
        prompt = "CommandOS> ";
        view.setText("Buongiorno! Bienvenido a CommandOS --- [Version "+VERSION+"] by Johann"+"\n"+prompt);
        setAtEnd();
    }
    
    /***
     * Lee el comando y basado en esto lo maneja
     */
    public void handleCall()
    {
        String command = getCall().trim();
        String[] commands = command.split(" ");
        switch(commands[0].trim().toLowerCase())
        {
            case "vari":
                createVariable(commands);
                break;
            case "helpti":
                showHelp(commands);
                break;
            case "value":
                listVars(commands);
                break;
            case "":
                log("");
                break;
            case "prompti":
                changePrompt(command);
                break;
            case "ciao":
                exit();
                break;
            case "clear":
                clearConsole();
                break;
            case "assign":
                changeValue(commands);
                break;
            default:
                log("No se reconoce el comando "+commands[0].trim()+".\nPruebe de nuevo o escriba helpti para obtener ayuda");
                break;
        }
    }
    
    /***
     * Cambia el valor de la variable correspondiente
     * @param commands arreglo de strings del comando
     * [1] variable a cambiar valor
     * [2] nuevo valor
     */
    private void changeValue(String[] commands)
    {
        if(commands.length == 3)
        {
            if(vars.containsKey(commands[1]))
            {
                Double newValue;
                if(isNumeric(commands[2]))
                {
                    newValue = getNumericValue(commands[2]);
                }
                else if(vars.containsKey(commands[2]))
                {
                    newValue = vars.get(commands[2]);
                }
                else
                {
                    log("Error, el valor destino es invalido");
                    return;
                }
                vars.replace(commands[1], newValue);
                log("La variable "+ commands[1]+" ahora tiene el valor de "+newValue);
            }
            else
            {
                log("Error, la variable a asignar el valor no existe");
            }
        }
    }
    
    private void listVars(String[] commands)
    {
        String text="";
        if(commands.length==1) //Listar todo
        {
            text = "Lista de variables:";
            Set<String> keys = vars.keySet();
            for(String key : keys)
            {
                text += "\n" + key + " ==> " + vars.get(key);
            }
            if(keys.size()==0)
            {
                text = "No hay variables declaradas";
            }
        }
        else
        {
            for (int i = 1; i < commands.length; i++) 
            {
                String name = commands[i].trim();
                text += name;
                text += "\n"+ ((vars.containsKey(name))? " ==> "+vars.get(name)+ "\n": " ===>No existe variable"); 
            }
        }
        log(text);
    }
    
    /***
     * Funcion encargada de manejar el comando para crear variables
     * Compara los argumentos del comando basado en su sintaxis y asigna
     * en caso de ser correcto
     * @param command comando completo
     */
    private void createVariable(String[] commands)
    {
        if(commands.length>1)
        {
            String name = commands[1].trim();
            if(Character.isAlphabetic(name.charAt(0)))
            {
                if(vars.containsKey(name))
                {
                    log("La variable "+name+" ya existe");
                    return;
                }
                Double value = 0.0;
                if(commands.length==3)
                {
                    if(isNumeric(commands[2]))
                    {
                        value = getNumericValue(commands[2]);
                    }
                    else
                    {
                        log("El valor asignado no es numerico (decimal o entero)");
                        return;
                    }
                }
                else if(commands.length>3)
                {
                    log("Demasiados argumentos para el comando vari");
                    return;
                }
                vars.put(name, value);
                log("Variable "+name+" creada con valor "+value);
            }
            else
            {
                log("Nombre invalido para la variable, no puede empezar con una letra");
            }
        }
        else
        {
            log("No se especifico el nombre de la variable. Escriba helpti vari para obtener ayuda");
        }
    }
    
    /***
     * Maneja el comando helpti
     */
    private void showHelp(String[] commands)
    {
        String text = "Para obtener mas detalles de cada comando, escriba helpti seguido "
                + "del nombre del comando:\n"
                + "ciao:      Sale de CommandOS\n"
                + "clear:     Limpia la consola\n"
                + "helpti:    Muestra ayuda con el manejo de CommandOS\n"
                + "value:     Muestra el valor de la(s) variable(s)\n"
                + "prompti:   Cambia el prompt de la consola\n"
                + "vari:      Crea variables numericas";
        log(text);
    }
    
    /***
     * Limpia la consola, elimina el filtro, coloca el prompt como nuevo texto
     * y vuelve a colocar el filtro
     */
    private void clearConsole()
    {
        CommandFilter filter = (CommandFilter) ((AbstractDocument)view.getDocument()).getDocumentFilter();
        ((AbstractDocument)view.getDocument()).setDocumentFilter(null);
        view.setText(prompt);
        setAtEnd();
        ((AbstractDocument)view.getDocument()).setDocumentFilter(filter);    
    }
    
    /***
     * Maneja el comando de salida
     * Imprime el mensaje de saida y despues de 2 segundos sale del programa
     * DISCLAIMER: El comando sleep tiene problemas despues de imprimir el comando, 
     * esto por como funcionan los JFrame, por lo que se optó por una alternativa
     */
    private void exit()
    {
        ((AbstractDocument)view.getDocument()).setDocumentFilter(null);
        view.append("\nCiao! Gracias por usar CommandOS");
        new java.util.Timer().schedule(
            new java.util.TimerTask() 
            {
                @Override
                public void run() 
                {
                    System.exit(0);                 //Muchos errores con Thread.Sleep
                }
            }, 
            2000 
        );
    }
    
    /***
     * Maneja el comando para cambiar el prompt dependiendo de los argumentos
     */
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
            view.append("\n"+prompt);
        }
        else 
        {
            view.append("\n" +message+"\n\n"+prompt);
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
     * @param message nuevo prompt
     */
    private void changeCommandPrompt(String message)
    {
        CommandFilter filter = (CommandFilter) ((AbstractDocument)view.getDocument()).getDocumentFilter();
        filter.setCommandPrompt(message);
        prompt = message + "> ";
    }
    
    private Double getNumericValue(String value)
    {
        return Double.parseDouble(value);
    }
    
    /***
     * Comprueba si un valor es una valor numerico
     * @param value string a comprobar
     * @return si es numerico
     */
    private boolean isNumeric(String value)
    {
        try 
        {                        
            Double val  = Double.parseDouble(value.trim());
            return true;
        } catch (NumberFormatException e) 
        {
            return false;
        }
    }
}
