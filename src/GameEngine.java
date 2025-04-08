import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class GameEngine {
    private final List<GameObject> gameObjects = new ArrayList<>();
    public HashMap<Integer, ArrayList<GameObject>> layers = new HashMap<>();


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
        ArrayList<GameObject> goLayer = layers.get(go.transform().layer());
        goLayer.remove(go);
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
     * Detecta colisões entre os GameObjects dentro da mesma camada.
     * 
     * @return Lista de colisões no formato "GameObject1 GameObject2".
     */
    public List<String> detectCollisions() {
        List<String> collisions = new ArrayList<>();

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
}
