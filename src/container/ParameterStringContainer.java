/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package container;

import helpers.ParserUtils;
import java.util.List;

/**
 *
 * @author bowen
 */
public class ParameterStringContainer extends StringContainer {
    
    private ParameterStringContainer(String rawString, List<String> content) {
        super(rawString, rawString.substring(2), content, "--", "");
    }
    private ParameterStringContainer(int index, int reverseIndex, String rawString, List<String> content) {
        super(index, reverseIndex, rawString, rawString.substring(2), content, "--", "");
    }
    public ParameterStringContainer(List<String> content) {
        this(content, new char[] {' '});
    }
    public ParameterStringContainer(List<String> content, char[] separatorList) {
        this(ParserUtils.untokenizeString(content, separatorList), content);
    }
    public ParameterStringContainer clone() {
        return new ParameterStringContainer(index, reverseIndex, getRawString(), getContent());
    }
    
}
