package interfaces;
import java.util.ArrayList;


/**
 * Interface do IBehaviour
 * 
 * @author Alexandre Menino a83974
 * @author Grégory Endrio Leite a90952
 * @author Valentim Khakhitva a81785
 * @version 11/05/2025
 */
public interface IBehaviour {

    /**
     * Invocado quando o Behaviour é inicialmente associado a um GameObject,
     * antes de qualquer atualização ou ativação.
     */
    void oninit();

    /**
     * Invocado quando o Behaviour (ou o seu GameObject) é ativado no motor de jogo.
     * Ideal para reiniciar estados ou efeitos visuais.
     */
    void onEnable();

    /**
     * Invocado quando o Behaviour (ou o seu GameObject) é desativado no motor de jogo.
     * Útil para pausar animações ou lógica enquanto desativado.
     */
    void onDisable();

    /**
     * Invocado imediatamente antes de o Behaviour ser removido ou destruído.
     * Serve para libertar recursos ou notificar sistemas externos.
     */
    void onDestroy();

    /**
     * Chamado a cada frame de atualização do motor, recebendo o delta-time em segundos.
     * Deve conter toda a lógica que evolui o estado do Behaviour ao longo do tempo.
     * 
     * @param dT tempo em segundos desde o último frame
     */
    void onUpdate(double dT);

    /**
     * Chamado quando ocorrem colisões: fornece a lista de GameObjects com que houve contacto.
     * 
     * @param gol lista de GameObjects colididos neste frame com o próprio GO.
     */
    void onCollision(ArrayList<IGameObject> gol);

    /**
     * Associa este Behaviour ao seu GameObject proprietário.
     * É invocado logo após a criação do Behaviour e antes de oninit().
     * 
     * @param GO referência ao GameObject a que este Behaviour pertence
     */
    void setGO(IGameObject GO);
}
