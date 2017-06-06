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
    
    public ParameterStringContainer(List<String> content) {
        this(content, new char[] {' '});
    }
    public ParameterStringContainer(List<String> content, char[] separatorList) {
        this(untokenizeAsString(content, separatorList), content);
    }
    private ParameterStringContainer(String rawString, List<String> content) {
        super(rawString, rawString.substring(2), content, "--", "");
    }
    private ParameterStringContainer(int index, int reverseIndex, String rawString, List<String> content) {
        super(index, reverseIndex, rawString, rawString.substring(2), content, "--", "");
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
    public ParameterStringContainer clone() {
        return new ParameterStringContainer(index, reverseIndex, getRawString(), getContent());
    }
    
}
