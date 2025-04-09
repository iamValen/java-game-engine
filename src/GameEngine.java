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

    /** 
     * Detecta colisões entre os GameObjects, mas não considera a camada.
     * 
     * @return Lista de colisões no formato "GameObject1 GameObject2".
     */
    public ArrayList<String> detectCollisions(){
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
