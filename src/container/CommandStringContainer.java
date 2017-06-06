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
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author bowen
 */
public class CommandStringContainer extends StringContainer {
    
    private final Set<String> flags; //flags start with -, eg -f, -l, -help, but the - is not saved in the string
    private final List<ParameterStringContainer> parameters; //parameters start with --, and ends until another parameter or flag is detected or end of string, eg --get 123
    
    public CommandStringContainer(String command) {
        this(command, new char[] {' '}, new String[] {"!"}, new String[0]);
    }
    
    public CommandStringContainer(String command, char[] separatorList, String[] prefixList, String[] suffixList) {
        super(command, separatorList, prefixList, suffixList);
        
        flags = new HashSet();
        parameters = new LinkedList();
        
        LinkedList<String> contentsToRemove = new LinkedList();
        
        boolean isInParameter = false;
        LinkedList<String> lastParameter = new LinkedList();
        
        for (String stringToken : getContent()) {
            if (isInParameter) {
                if (stringToken.startsWith("--")) {
                    parameters.add(new ParameterStringContainer(lastParameter, separatorList));
                    lastParameter = new LinkedList();
                    lastParameter.add(stringToken.substring(2));
                    contentsToRemove.add(stringToken);
                } else if (stringToken.startsWith("-")) {
                    isInParameter = false;
                    parameters.add(new ParameterStringContainer(lastParameter, separatorList));
                    lastParameter = new LinkedList();
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
        
        LinkedList<String> tempContent = new LinkedList(getContent());
        
        for (String toRemove : contentsToRemove) {
            tempContent.remove(toRemove);
        }
        setContent(tempContent);
        
    }
    
    public boolean checkFlag(String flag) {
        return flags.contains(flag);
    }
    public boolean checkParameter(String parameter) {
        return parameters.stream().anyMatch((p) -> (p.get(0).equals(parameter)));
    }
    public ParameterStringContainer getParameter(String parameter) {
        for (ParameterStringContainer p : parameters) {
            if (p.get(0).equals(parameter)) {
                ParameterStringContainer newP = p.clone();
                newP.next();
                return newP;
            }
        }
        List<String> emptyParameter = new LinkedList();
        emptyParameter.add(parameter);
        return new ParameterStringContainer(emptyParameter);
    }
}
