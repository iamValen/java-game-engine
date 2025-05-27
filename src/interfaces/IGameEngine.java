package interfaces;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.SortedMap;

/**
 * Interface que define os métodos essenciais do motor de jogo.
 * 
 * @author —
 * @version —
 */
public interface IGameEngine {

    /**
     * Devolve um mapa de camadas onde os objectos estão organizados por ordem de desenho.
     * @return Mapa de camadas (por ordem) com listas de objectos de jogo.
     */
    public SortedMap<Integer, ArrayList<IGameObject>> layers();

    /**
     * Devolve a lista de objectos actualmente activos no jogo.
     * @return Lista de objectos activos.
     */
    public ArrayList<IGameObject> enabled();

    /**
     * Devolve a lista de objectos actualmente desactivados no jogo.
     * @return Lista de objectos desactivados.
     */
    public ArrayList<IGameObject> disabled();

    /**
     * Gera e desenha o próximo frame.
     */
    public void generateNextFrame();

    /**
     * Adiciona um objecto à lista de activos.
     * @param go Objecto a activar.
     */
    public void addEnabled(IGameObject go);

    /**
     * Adiciona um objecto à lista de desactivados.
     * @param go Objecto a desactivar.
     */
    public void addDisabled(IGameObject go);

    /**
     * Activa um objecto do jogo.
     * @param go Objecto a activar.
     */
    public void enable(IGameObject go);

    /**
     * Desactiva um objecto do jogo.
     * @param go Objecto a desactivar.
     */
    public void disable(IGameObject go);

    /**
     * Verifica se um objecto está activo.
     * @param go Objecto a verificar.
     * @return true se estiver activo, false caso contrário.
     */
    public boolean isEnabled(IGameObject go);

    /**
     * Verifica se um objecto está desactivado.
     * @param go Objecto a verificar.
     * @return true se estiver desactivado, false caso contrário.
     */
    public boolean isDisabled(IGameObject go);

    /**
     * Remove permanentemente um objecto do jogo.
     * @param go Objecto a remover.
     */
    public void destroy(IGameObject go); 

    /**
     * Remove todos os objectos do jogo.
     */
    public void destroyAll();

    /**
     * Inicia o ciclo principal do motor de jogo.
     */
    public void run();

    /**
     * Renderiza todos os elementos
     * 
     * @param g Grahics
     */
    public void render(Graphics g);

    /**
     * Verifica colisões entre objectos activos.
     */
    public void checkCollisions();
}
