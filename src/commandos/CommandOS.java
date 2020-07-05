/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commandos;

/**
 * Clase del main, se inicializa el programa y se manda
 * a llamar a la ventana principal
 * @author Johann
 */
public class CommandOS {

    /**
     * Llama a la ventana del sistema operativo
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        ConsoleView console = new ConsoleView();
        console.setVisible(true);
    }
    
}
