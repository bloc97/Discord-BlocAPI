/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package container;

import helpers.TextFormatter;
import java.util.List;

/**
 *
 * @author bowen
 */
public class ParameterStringContainer extends StringContainer {
    
    public ParameterStringContainer(List<String> content, char[] separatorList) {
        super(1, content.size() - 1, untokenizeAsString(content, separatorList), content, "--", "");
    }
    public static String untokenizeAsString(List<String> content, char[] separatorList) {
        String string = "--";
        for (String s : content) {
            if (TextFormatter.containsCharacter(s, separatorList)) {
                string = string + " \"" + s + "\"";
            } else {
                string = string + " " + s;
            }
        }
        return string;
    }
    
}
