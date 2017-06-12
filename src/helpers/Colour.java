/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

/**
 *
 * @author bowen
 */
public abstract class Colour {
    public static int getIntFromRGB(int r, int g, int b, int a) {
        int ia = (a << 24) & 0xFF000000;
        int ir = (r << 16) & 0x00FF0000;
        int ig = (g <<  8) & 0x0000FF00;
        int ib = (b      ) & 0x000000FF;
        
        return ia | ir | ig | ib;
    }
    public static int getIntFromRGB(int r, int g, int b) {
        return getIntFromRGB(r, g, b, 0);
    }
    public static int getIntFromRGB(float r, float g, float b, float a) {
        return getIntFromRGB(
                Math.round(r * 255),
                Math.round(g * 255),
                Math.round(b * 255),
                Math.round(a * 255));
    }
    public static int getIntFromRGB(float r, float g, float b) {
        return getIntFromRGB(r, g, b, 0);
    }
}
