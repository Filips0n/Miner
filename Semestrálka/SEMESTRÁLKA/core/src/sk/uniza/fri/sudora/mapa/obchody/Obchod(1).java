
package sk.uniza.fri.sudora.mapa.obchody;


import sk.uniza.fri.sudora.mapa.Map;

/**
 * Abstraktna trieda pre vytvorenie obchodu pre kupu a predaj.
 */
public abstract class Obchod {
    
    private final int posX;
    private final int posY;
    private Map hernaMapa;

    public Obchod(int posX, int posY, Map hernaMapa) {
        this.posX = posX;
        this.posY = posY;
        this.hernaMapa = hernaMapa;
    }
    
    public int getPosX() {
        return this.posX;
    }

    public int getPosY() {
        return this.posY;
    }
    
    public abstract void vykresli();
}
