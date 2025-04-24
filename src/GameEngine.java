import java.util.ArrayList;
import java.util.HashMap;

/**
 * Classe que representa o motor de jogo, responsável por gerenciar os GameObjects e suas interações.
 * 
 * @author Alexandre Menino a83974
 * @author Grégory Endrio Leite a90952
 * @author Valentim Khakhitva a81785
 * @version 28/03/2025
 */
public class GameEngine implements IGameEngine{

    public static GameEngine GAMEENGINE;

    public void addEnabled(IGameObject go){}
    public void addDisabled(IGameObject go){}

    public void enable(IGameObject go){}
    public void disable(IGameObject go){}

    public boolean isEnabled(IGameObject go){return false;}
    public boolean isDisabled(IGameObject go){return false;}

    public ArrayList<IGameObject> enabled(){return null;}
    public ArrayList<IGameObject> disabled(){ return null;}

    public void destroy(IGameObject go){}
    public void destroyAll(){}

    public void run(){
        for(;;){
            //get input
            //get colisions
            //for(all go's)
            //    go.update(colisions, input)
        }

    }

    private final ArrayList<GameObject> gameObjects = new ArrayList<>();
    public HashMap<Integer, ArrayList<GameObject>> layers = new HashMap<>();
    
    /**
     * Retorna a lista de GameObjects.
     * 
     * @return Lista de GameObjects.
     */
    public ArrayList<GameObject> gameObjects(){
        return gameObjects;
    }

    /**
     * Retorna o mapa de layers com os gameobjects.
     * 
     * @return Lista de GameObjects.
     */
    public HashMap<Integer, ArrayList<GameObject>> layers(){
        return layers;
    }


    /**
     * Adiciona um GameObject à lista.
     * 
     * @param go GameObject a ser adicionado.
     */
    public void add(GameObject go) {
        gameObjects.add(go);
        ArrayList<GameObject> goLayer = layers.get(go.transform().layer());
        if(goLayer == null){
            goLayer = new ArrayList<GameObject>();
            layers.put(go.transform().layer(), goLayer);
        }
        goLayer.add(go);
    }

    /**
     * Remove um GameObject da lista.
     * 
     * @param go GameObject a ser removido.
     */
    public void destroy(GameObject go) {
        layers.getOrDefault(go.transform().layer(), new ArrayList<>()).remove(go);
        gameObjects.remove(go);
    }

    public void generateNextFrame(){
        for(GameObject go : gameObjects){
            if(go.layerSpeed != 0){
                layers.get(go.transform().layer()).remove(go);
                go.generateNextFrame();
                ArrayList<GameObject> goLayer = layers.get(go.transform().layer());
                if(goLayer == null){
                    goLayer = new ArrayList<GameObject>();
                    layers.put(go.transform().layer(), goLayer);
                }
                goLayer.add(go);
            }
            else{
                go.generateNextFrame();
            }
        }
    }

    /**
     * Atualiza os GameObjects em cada frame.
     * 
     * @param frames Número de frames a simular.
     */
    public void simulateFrames(int frames) {
        for (int frame = 0; frame < frames; frame++) {
            generateNextFrame();
        }
    }

    /** 
     * Detecta colisões entre os GameObjects, mas não considera a camada.
     * (Usado para o N)
     * 
     * @return Lista de colisões.
     */
    public ArrayList<String> checkCollisions(){
        ArrayList<String> out = new ArrayList<>();
        for(GameObject go : gameObjects){
            boolean flag = false;
            StringBuilder sb = new StringBuilder(go.name());
            ArrayList<GameObject> arr = layers.get(go.transform().layer());
            for(GameObject go2 : arr){
                if(go == go2)
                    continue;
                if(go.colision(go2)){
                    sb.append(" " + go2.name());
                    flag = true;
                }
            }
            if(flag)
                out.add(sb.toString());
        }
        return out;
    }
}
