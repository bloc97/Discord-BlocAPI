/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package container;

import helpers.TextFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author bowen
 */
public abstract class Container<T> {
    protected int index = 0;
    protected int reverseIndex = 0;
    private ArrayList<T> content = new ArrayList();
    
    private final String rawString;
    private final String trimmedString;
    private final String prefix;
    private final String suffix;
    
    
    protected Container(String rawString, String trimmedString, String prefix, String suffix) {
        this.rawString = rawString;
        this.prefix = prefix;
        this.suffix = suffix;
        this.trimmedString = trimmedString;
        
    }
    protected Container(String rawString, String[] prefixList, String[] suffixList) {
        this.index = 0;
        this.reverseIndex = 0;
        this.rawString = rawString;
        this.prefix = findPrefixCaseless(rawString, prefixList);
        this.suffix = findSuffixCaseless(rawString, suffixList);
        
        String trimmedString = rawString;
        if (!prefix.isEmpty()) {
            trimmedString = trimmedString.substring(prefix.length());
        }
        if (!suffix.isEmpty()) {
            trimmedString = trimmedString.substring(0, trimmedString.length() - suffix.length());
        }
        this.trimmedString = trimmedString;
        
    }
    public static String findPrefix(String string, String[] prefixList) {
        for (String s : prefixList) {
            if (string.startsWith(s)) {
                return s;
            }
        }
        return "";
    }
    public static String findPrefixCaseless(String string, String[] prefixList) {
        for (String s : prefixList) {
            s = s.toLowerCase(); //Do not care about case
            if (string.toLowerCase().startsWith(s)) { //Do not care about case
                return s;
            }
        }
        return "";
    }
    public static String findSuffix(String string, String[] suffixList) {
        for (String s : suffixList) {
            if (string.endsWith(s)) {
                return s;
            }
        }
        return "";
    }
    public static String findSuffixCaseless(String string, String[] suffixList) {
        for (String s : suffixList) {
            s = s.toLowerCase(); //Do not care about case
            if (string.toLowerCase().endsWith(s)) { //Do not care about case
                return s;
            }
        }
        return "";
    }
    
    public String getRawString() {
        return rawString;
    }
    
    public String getTrimmedString() {
        return trimmedString;
    }
    @Override
    public String toString() {
        return rawString;
    }
    
    protected void setContent(List<T> content) {
        this.content = new ArrayList(content);
        reset();
    }
    
    public int getCurrentIndex() {
        return index;
    }
    public int getCurrentReverseIndex() {
        return reverseIndex;
    }
    public int size() {
        return content.size();
    }
    public int remainingSize() {
        /*if (index > reverseIndex) {
            return 0;
        }*/
        return reverseIndex - index + 1;
    }
    public void reset() {
        index = 0;
        reverseIndex = size() - 1;
    }
    public T get(int i) {
        if (isOob(i)) {
            return null;
        }
        return content.get(i);
    }
    public T get() {
        return get(index);
    }
    public T reverseGet() {
        return get(reverseIndex);
    }
    public T getNext() {
        return get(index+1);
    }
    public T reverseGetNext() {
        return get(reverseIndex-1);
    }
    public List<T> getContent() {
        return new ArrayList(content);
    }
    public T[] getContentAsArray() {
        return (T[]) getContent().toArray();
    }
    public List<T> getRemainingContent() {
        if (index > reverseIndex) {
            return new ArrayList();
        }
        
        int i = TextFormatter.boundExcludeMax(this.index, 0, size());
        int ri = TextFormatter.boundExcludeMax(this.reverseIndex, 0, size());
        return content.subList(i, ri+1);
    }
    public T[] getRemainingContentAsArray() {
        return (T[]) getRemainingContent().toArray();
    }
    public boolean hasNext() {
        return !isOob(index+1);
    }
    public boolean reverseHasNext() {
        return !isOob(reverseIndex-1);
    }
    public boolean isOob(int i) {
        return i >= size() || i < 0;
    }
    public void next() {
        index++;
    }
    public void previous() {
        index--;
    }
    public void reverseNext() {
        reverseIndex--;
    }
    public void reversePrevious() {
        reverseIndex++;
    }
    public String getPrefix() {
        return prefix;
    }
    public String getSuffix() {
        return suffix;
    }
    
}

