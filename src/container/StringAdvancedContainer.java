/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package container;

import helpers.ParserUtils;
import java.util.ArrayList;
import java.util.Arrays;
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
public class StringAdvancedContainer extends StringContainer {
    
    private final Set<String> flags; //flags start with -, eg -f, -l, -help, but the - is not saved in the string
    private final List<ParameterStringContainer> parameters; //parameters start with --, and ends until another parameter or flag is detected or end of string, eg --get 123
    
    public StringAdvancedContainer(String rawString, ContainerSettings settings) {
        super(rawString, settings);
        
        flags = new HashSet();
        parameters = new LinkedList();
        List<String> content = getContent();
        
        setContent(ParserUtils.retrieveParameters(content, settings.getSeparatorList(), flags, parameters));
        
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
        return new ParameterStringContainer(emptyParameter, new char[0]);
    }
}
