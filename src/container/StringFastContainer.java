/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package container;

import helpers.ParserUtils;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author bowen
 */
public class StringFastContainer extends Container<String> {
    
    public StringFastContainer(String rawString, ContainerSettings settings) {
        super(rawString, settings.getPrefixSuffixComboList());
        
        String trimmedString = getTrimmedString();
        if (trimmedString.isEmpty()) {
            return;
        }
        if (settings.getSeparatorList().length < 1) {
            
        } else {
            setContent(Arrays.asList(trimmedString.split("" + settings.getSeparatorList()[0])));
        }
    }
    
    @Override
    public String getEmptyContent() {
        return "";
    }
    
    /*
    StringAdvancedContainer getFull() {
        return this.getFull(new char[] {' '});
    }
    StringAdvancedContainer getFull(char[] separatorList) {
        return new StringAdvancedContainer(getRawString(), separatorList, getPrefix(), getSuffix());
    }*/

}
