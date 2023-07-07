/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.uniza.fri.sudora.bloky;

/**
 * Interface pre vsetky bloky.
 * @author Filip
 */
public interface IBlok {
    
    void vykresli(int x, int y);
    String getNazov();
    void setViditelnost(boolean viditelnost);
    boolean getZberatelnost();
    boolean getZnicitelnost();
    boolean getMaMinu();
    void setMaMinu(boolean b);
    
}
