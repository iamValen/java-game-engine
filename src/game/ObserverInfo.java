package game;

/**
 * Estrutura de dados para passar valores atualizados aos Observers
 * Utilizado para dinamizar os valors que os Observers precisam de atualizar
 * 
 * @author Alexandre Menino a83974
 * @author Grégory Endrio Leite a90952
 * @author Valentim Khakhitva a81785
 * @version 11/05/2025
 */
public class ObserverInfo {
    public final int i;
    public final long l;
    /**
     * Construtor
     * 
     * @param i inteiro
     * @param l long
     */
    public ObserverInfo(int i, long l) {
        this.i = i;
        this.l = l;
    }
}
