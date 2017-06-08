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
    
    public StringFastContainer(String rawString) {
        this(rawString, ' ');
    }
    
    public StringFastContainer(String rawString, String... prefixList) {
        this(rawString, ' ', new PrefixSuffixCombo(prefixList));
    }
    public StringFastContainer(String rawString, char separator) {
        this(rawString, separator, new PrefixSuffixCombo(""));
    }
    public StringFastContainer(String rawString, char separator, String... prefixList) {
        this(rawString, separator, new PrefixSuffixCombo(prefixList));
    }
    public StringFastContainer(String rawString, char separator, PrefixSuffixCombo prefixSuffix) {
        super(rawString, Arrays.asList(new PrefixSuffixCombo[] {prefixSuffix}));
        
        String trimmedString = getTrimmedString();
        if (trimmedString.isEmpty()) {
            return;
        }
        setContent(Arrays.asList(trimmedString.split("" + separator)));
    }
    /*
    StringAdvancedContainer getFull() {
        return this.getFull(new char[] {' '});
    }
    StringAdvancedContainer getFull(char[] separatorList) {
        return new StringAdvancedContainer(getRawString(), separatorList, getPrefix(), getSuffix());
    }*/
}
