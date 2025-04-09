import java.util.ArrayList;
import java.util.HashMap;

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

    /** Usado no gameEngine
     * Detecta colisões entre os GameObjects dentro da mesma camada.
     * 
     * @return Lista de colisões no formato "GameObject1 GameObject2".
     */
    public ArrayList<String> detectCollisions() {
        ArrayList<String> collisions = new ArrayList<>();

        for (int layer : layers.keySet()) {
            ArrayList<GameObject> objectsInLayer = layers.get(layer);
            if (objectsInLayer == null || objectsInLayer.size() < 2) continue;

            for (int i = 0; i < objectsInLayer.size(); i++) {
                GameObject go1 = objectsInLayer.get(i);
                StringBuilder collisionInfo = new StringBuilder(go1.name());
                boolean hasCollision = false;

                for (int j = i + 1; j < objectsInLayer.size(); j++) {
                    GameObject go2 = objectsInLayer.get(j);

                    if (go1.collider().collidesWith(go2.collider())) {
                        collisionInfo.append(" ").append(go2.name());
                        hasCollision = true;
                    }
                }

                if (hasCollision) {
                    collisions.add(collisionInfo.toString());
                }
            }
        }

        return collisions;
    }

    /** Apenas usado para o N
     * Detecta colisões entre os GameObjects, mas não considera a camada.
     * 
     * @return Lista de colisões no formato "GameObject1 GameObject2".
     */
    public ArrayList<String> detectCollisions2(){
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
