/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package token;

import java.text.DateFormat;
import java.util.Date;

/**
 *
 * @author bowen
 */
public class DateToken extends Token<Date> {
    private final Date content;
    
    public DateToken(Date date) {
        super(date.toString());
        content = new Date(date.getTime());
    }
    public DateToken(Long l) {
        super(l.toString());
        content = new Date(l);
    }
    public DateToken(String string) {
        super(string);
        content = new Date(Long.parseLong(string));
    }
    

    @Override
    public String getContentAsString() {
        return "" + content.getTime();
    }

    @Override
    public String getContentAsReadableString() {
        return content.toString();
    }

    @Override
    public Date getContent() {
        return content;
    }
    
}
