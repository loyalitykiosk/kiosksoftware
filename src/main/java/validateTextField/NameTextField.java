/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validateTextField;

import javafx.scene.control.TextField;

/**
 *
 * @author partha
 */
public class NameTextField extends TextField{

    @Override
    public void replaceText(int start, int end, String text) {
        if (text.matches("[a-zA-Z ]") || text.isEmpty()) {
            super.replaceText(start, end, text);
        }
    }
    
    @Override
    public void replaceSelection(String replacement) {
        super.replaceSelection(replacement); //To change body of generated methods, choose Tools | Templates.
    }
    
}
