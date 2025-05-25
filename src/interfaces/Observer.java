package interfaces;

import game.ObserverInfo;

/**
 * Classe Observer 
 * 
 * @author Alexandre Menino a83974
 * @author Grégory Endrio Leite a90952
 * @author Valentim Khakhitva a81785
 * @version 11/05/2025
 */
public interface Observer {

    /**
     * Atualiza com os campos necessários com os valores recebidos 
     * 
     * @param info Informação dentro da estrutura de dados personalizada
     */
    public void update(ObserverInfo info);
}