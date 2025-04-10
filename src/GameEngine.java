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
public class GameEngine {
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
     * Detecta colisões entre os GameObjects no GameEngine.
     * 
     * @return Lista de colisões.
     */
    public ArrayList<String> detectCollisions() {
        ArrayList<String> collisions = new ArrayList<>();
        // Para cada layer
        for (Integer layer : layers.keySet()) {
            ArrayList<GameObject> objectsInLayer = layers.get(layer);
            if (objectsInLayer == null || objectsInLayer.size() < 2) continue;

            for (GameObject go1 : objectsInLayer) {
                StringBuilder sb = new StringBuilder();
                boolean hasCollision = false;
                for (GameObject go2 : objectsInLayer) {
                    if (go1 == go2)
                        continue;
                    if (go1.collider().collidesWith(go2.collider()))
                        sb.append(" " + go2.name());
                        hasCollision = true;
                }
                if (hasCollision) {
                    collisions.add(go1.name() + sb.toString());
                }
            }
        }
        return collisions;
    }

    /** 
     * Detecta colisões entre os GameObjects, mas não considera a camada.
     * (Usado para o N)
     * 
     * @return Lista de colisões.
     */
    public ArrayList<String> detectCollisionsN(){
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
