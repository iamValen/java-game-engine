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

    public static GameEngine GAMEENGINE = null;
    private boolean isRunning;
    public boolean isRunning(){
        return this.isRunning;
    }

    private final HashMap<Integer, ArrayList<IGameObject>> layers = new HashMap<>();
    private final ArrayList<IGameObject> enabledList = new ArrayList<>();
    private final ArrayList<IGameObject> disabledList = new ArrayList<>();

    /**
     * call this instead of new GameEngine before we make this class singleton
     */
    public static GameEngine GameEngineConstructor(){
        GameEngine out = new GameEngine();
        GameEngine.GAMEENGINE = out;
        return  out;
    }

    private GameEngine() {
        this.isRunning = false;
    }

    @Override
    public void addEnabled(IGameObject go){
        this.enabledList.add(go);
        this.layers.get(go.transform().layer()).add(go);
        go.behaviour().oninit();
    }

    @Override
    public void addDisabled(IGameObject go){
        this.disabledList.add(go);
        go.behaviour().oninit();
    }

    @Override
    public void enable(IGameObject go){
        this.disabledList.remove(go);
        this.enabledList.add(go);
        this.layers.get(go.transform().layer()).add(go);
        go.behaviour().onEnable();
    }
    
    @Override
    public void disable(IGameObject go){
        this.enabledList.remove(go);
        this.layers.get(go.transform().layer()).remove(go);
        this.disabledList.add(go);
        go.behaviour().onDisable();
    }
    
    @Override
    public boolean isEnabled(IGameObject go){
        return this.enabledList.contains(go);
    }
    
    @Override
    public boolean isDisabled(IGameObject go){
        return this.disabledList.contains(go);
    }
    
    @Override
    public ArrayList<IGameObject> enabled(){
        return this.enabledList;
    }
    
    @Override
    public ArrayList<IGameObject> disabled(){
        return this.disabledList;
    }
    
    @Override
    public void destroy(IGameObject go){
        if(isEnabled(go)){
            go.behaviour().onDestroy();
            this.enabledList.remove(go);
            this.layers.get(go.transform().layer()).remove(go);
        }
        else{
            this.disabledList.remove(go);
        }
    }
    
    @Override
    public void destroyAll(){
        for(IGameObject GO : this.enabledList){
            GO.behaviour().onDestroy();
        }
        this.layers.clear();
        this.disabledList.clear();
        this.enabledList.clear();
    }


    /**
     * Retorna o mapa de layers com os gameobjects.
     * 
     * @return Lista de GameObjects.
     */
    @Override
    public HashMap<Integer, ArrayList<IGameObject>> layers(){
        return this.layers;
    }



    @Override
    public void run(){
        this.isRunning = true;
        for(;;){
            //get input

            for(IGameObject GO : this.enabledList){
                if(GO.behaviour() != null)
                    GO.behaviour().onUpdate(1d/60d);
                if(GO.collider() != null)
                    GO.collider().onUpdate();
            }
            checkCollisions();
        }
    }


    /**
     * used for testing
    */
    @Override
    public void generateNextFrame(){
        this.isRunning = true;

        for(IGameObject GO : this.enabledList)
            if(GO.behaviour() != null)
                GO.behaviour().onUpdate(1d/60d);

        for(IGameObject GO : this.enabledList)
            if(GO.collider() != null)
                GO.collider().onUpdate();

        checkCollisions();
        this.isRunning = false;
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
     * Detecta colisoes entre os GameObjects na mesma layer.
     * 
     */
    @SuppressWarnings("unused") //for lambda parameter in computeIfAbsent
    @Override
    public void checkCollisions(){
        HashMap<IGameObject, ArrayList<IGameObject>> collisionMap = new HashMap<>();
        for(ArrayList<IGameObject> GOArr : layers.values()){ //itera as layers

            for(int i = 0; i < GOArr.size(); i++){          //nested loop para iterar pares
                for(int j = i+1; j < GOArr.size(); j++){   //de GameOBjects na mesma layer

                    IGameObject GOA = GOArr.get(i);
                    IGameObject GOB = GOArr.get(j);
                    if(GOA.collider().isColliding(GOB.collider())){
                        collisionMap.computeIfAbsent(GOA, k -> new ArrayList<>()).add(GOB);
                        collisionMap.computeIfAbsent(GOB, k -> new ArrayList<>()).add(GOA);
                    }

                }
            }

            // quando acabar de iterar a layer chama o onCollision dos GameObjects dessa layer
            for(IGameObject GO : GOArr){
                GO.behaviour().onCollision(collisionMap.get(GO));
            }
        }
    }
}
