/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commandos;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.text.Element;

/**
 * Filtro de edicion del JTextArea 
 * que evita editar partes ya impresas
 * Libreria externa
 * Fuente: https://stackoverflow.com/questions/10030477/make-parts-of-a-jtextarea-non-editable-not-the-whole-jtextarea
 * @author Johann
 */
class CommandFilter extends DocumentFilter 
{
    private  String PROMPT = "CommandOS> ";
    
    public void setCommandPrompt(String message)
    {
        PROMPT = message+"> ";
    }
    

    @Override 
    public void insertString(DocumentFilter.FilterBypass fb, int offset, String string,AttributeSet attr) throws BadLocationException 
    {
        if(string == null) 
        {
            return;
        }
        else
        {
            replace(fb, offset, 0, string, attr);
        }   
    }

    @Override 
    public void remove(DocumentFilter.FilterBypass fb, int offset,int length) throws BadLocationException 
    {
        replace(fb, offset, length, "", null);
    }

    @Override 
    public void replace(DocumentFilter.FilterBypass fb, int offset, int length,String text, AttributeSet attrs) throws BadLocationException 
    {     
        Document doc = fb.getDocument();
        Element root = doc.getDefaultRootElement();
        int count = root.getElementCount();
        int index = root.getElementIndex(offset);
        Element cur = root.getElement(index);
        int promptPosition = cur.getStartOffset()+PROMPT.length();

        if(index==count-1 && offset-promptPosition>=0) 
        {
            if(text.equals("\n")) 
            {
                String cmd = doc.getText(promptPosition, offset-promptPosition);
                
                if(cmd.trim().isEmpty()) 
                {
                    text = "";
                }
                else
                {
                    text = "\n" + PROMPT;
                }
            }
            fb.replace(offset, length, text, attrs);
        }
    }
}