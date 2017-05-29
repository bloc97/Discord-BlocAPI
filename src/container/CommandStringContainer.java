/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package container;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author bowen
 */
public class CommandStringContainer extends StringContainer {
    
    private final Set<String> flags; //flags start with -, eg -f, -l, -help
    private final Map<String, ParameterStringContainer> parameters; //parameters start with --, and ends until another parameter or flag is detected or end of string, eg --get 123
    
    public CommandStringContainer(String command, char[] separatorList, String[] prefixList, String[] suffixList) {
        super(command, separatorList, prefixList, suffixList);
        
        flags = new HashSet<>();
        parameters = new HashMap<>();
        
        LinkedList<String> contentsToRemove = new LinkedList<>();
        
        boolean isInParameter = false;
        LinkedList<String> lastParameter = new LinkedList<>();
        
        for (String stringToken : content) {
            if (isInParameter) {
                if (stringToken.startsWith("--")) {
                    parameters.put(lastParameter.getFirst(), new ParameterStringContainer(lastParameter, separatorList));
                    lastParameter = new LinkedList<>();
                    lastParameter.add(stringToken.substring(2));
                    contentsToRemove.add(stringToken);
                } else if (stringToken.startsWith("-")) {
                    isInParameter = false;
                    parameters.put(lastParameter.getFirst(), new ParameterStringContainer(lastParameter, separatorList));
                    lastParameter = new LinkedList<>();
                    flags.add(stringToken.substring(1));
                    contentsToRemove.add(stringToken);
                } else {
                    lastParameter.add(stringToken);
                    contentsToRemove.add(stringToken);
                }
                
            } else {
                if (stringToken.startsWith("--")) {
                    isInParameter = true;
                    lastParameter.add(stringToken.substring(2));
                    contentsToRemove.add(stringToken);
                } else if (stringToken.startsWith("-")) {
                    flags.add(stringToken.substring(1));
                    contentsToRemove.add(stringToken);
                }
            }
        }
        
        LinkedList<String> tempContent = new LinkedList<>(content);
        
        for (String remove : contentsToRemove) {
            tempContent.remove(remove);
        }
        
        content = new ArrayList<>(tempContent);
        reverseIndex = content.size()-1;
        
    }
    
}
