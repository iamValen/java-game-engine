package engine;
import interfaces.IGameEngine;
import interfaces.IGameObject;
import interfaces.IShape;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
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
    /**
     * GameEngine tem apenas uma única instância
     * Desta maneira todos os objetos presentes no GE podem aceder ao proprio GE
     */
    private static GameEngine instance = null;

    private final HashMap<Integer, ArrayList<IGameObject>> layers = new HashMap<>();
    private final ArrayList<IGameObject> enabledList = new ArrayList<>();
    private final ArrayList<IGameObject> disabledList = new ArrayList<>();
    private final HashMap<IGameObject, ArrayList<IGameObject>> collisionMap = new HashMap<>();
    private final ArrayList<IGameObject> toEnable = new ArrayList<>();
    private final ArrayList<IGameObject> toDisable = new ArrayList<>();
    private final ArrayList<IGameObject> toAddEnabled = new ArrayList<>();
    private final ArrayList<IGameObject> toAddDisabled = new ArrayList<>();
    private final ArrayList<IGameObject> toDestroy = new ArrayList<>();
    private final int TARGET_FPS = 4000; 

    private boolean isRunning;
    private Canvas renderSurface;

    public boolean isRunning(){ return this.isRunning; }

    public int getScreenWidth() {
        return renderSurface.getWidth();
    }

    public int getScreenHeight() {
        return renderSurface.getHeight();
    }


    private GameEngine() {
        this.isRunning = false;
    }

    public static GameEngine getInstance() {
        if (instance == null) {
            instance = new GameEngine();
        }
        return instance;
    }

    @Override
    public ArrayList<IGameObject> enabled(){
        return this.enabledList;
    }
    
    @Override
    public ArrayList<IGameObject> disabled(){
        return this.disabledList;
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
    public void addEnabled(IGameObject go){
        toAddEnabled.add(go);
        if(go.behaviour() != null) go.behaviour().oninit();
    }

    @Override
    public void addDisabled(IGameObject go){
        toAddDisabled.add(go);
        if(go.behaviour() != null) go.behaviour().oninit();
    }


    @Override
    public void enable(IGameObject go){
        toEnable.add(go);
        if(go.behaviour() != null) go.behaviour().onEnable();
    }

    @Override
    public void disable(IGameObject go){
        toDisable.add(go);
        if(go.behaviour() != null) go.behaviour().onDisable();
    }


    @Override
    public boolean isEnabled(IGameObject go){
        return this.enabledList.contains(go);
    }
    
    @Override
    public boolean isDisabled(IGameObject go){
        return this.disabledList.contains(go);
    }


    //vamos sempre chamar o onDestroy e ele que decide se executa ou nao
    //caso o objeto esteja enabled ou disabled
    @Override
    public void destroy(IGameObject go){
        toDestroy.add(go);
    }
    
    @Override
    public void destroyAll(){
        for(IGameObject GO : enabledList){
            toDestroy.add(GO);
        }
        for(IGameObject GO : disabledList){
            toDestroy.add(GO);
        }
    }

    @SuppressWarnings("unused")
    private void manageGO(){

        for(IGameObject go : toEnable){
            this.disabledList.remove(go);
            this.enabledList.add(go);
            this.layers.computeIfAbsent(go.transform().layer(), k -> new ArrayList<>()).add(go);
        }
        toEnable.clear();


        for(IGameObject go : toDisable){
            this.enabledList.remove(go);
            this.layers.get(go.transform().layer()).remove(go);
            this.disabledList.add(go);
        }
        toDisable.clear();


        for(IGameObject go : toAddEnabled){
            this.enabledList.add(go);
            this.layers.computeIfAbsent(go.transform().layer(), k -> new ArrayList<>()).add(go);
        }
        toAddEnabled.clear();


        for(IGameObject go : toAddDisabled){
            this.disabledList.add(go);
        }
        toAddDisabled.clear();


        for(IGameObject go : toDestroy){
            if(this.enabledList.remove(go))
                this.layers.get(go.transform().layer()).remove(go);
            this.disabledList.remove(go);
        }

        toDestroy.clear();
    }

    @SuppressWarnings("BusyWait") //sleep in a loop
    @Override
    public void run(){

        // Must have called setRenderSurface(canvas) first!
        BufferStrategy bs = renderSurface.getBufferStrategy();

        isRunning = true;
        final long frameTimeNs = 1_000_000_000L / TARGET_FPS;
        long last = System.nanoTime();

        while(isRunning){
            long now = System.nanoTime();
            double dt = (now - last) / 1_000_000_000.0;
            last = now;

            // UPDATE
            for(IGameObject go : enabledList) {
                if (go.behaviour() != null) go.behaviour().onUpdate(dt);
                if (go.collider() != null) go.collider().onUpdate();
            }
            checkCollisions();

            // RENDER
            Graphics g = bs.getDrawGraphics();
            // clear the back‑buffer
            g.setColor(java.awt.Color.WHITE);
            g.fillRect(0, 0, renderSurface.getWidth(), renderSurface.getHeight());

            // draw all GameObjects via your GameObject.render(g)
            for(IGameObject go : enabledList) {
                if(go.shape() != null)((GameObject) go).render(g);
            }

            g.dispose();    // release this Graphics
            bs.show();      // flip buffers

            // SLEEP to cap frame rate
            long elapsed = System.nanoTime() - now;
            long toSleep = (frameTimeNs - elapsed) / 1_000_000;
            if (toSleep > 0) {
                try { Thread.sleep(toSleep); }
                catch (InterruptedException ignored) {}
            }
            // unreliable way to check frametimes for some reason
            // long actualFrameTime = elapsed + ((toSleep>0) ? toSleep : 0);
            // System.out.println((actualFrameTime)/1000);
            manageGO();
        }
    }

    public void render(Graphics g) {
        for(IGameObject go : enabledList) {
            int x = (int)go.transform().position().x();
            int y = (int)go.transform().position().y();
            IShape shape = go.shape();
            if (shape != null) {
                shape.render(g, x, y);
            }
        }
    }


    /**
     * Call this once from your GUI, *after* the Canvas is visible.
     * It prepares buffering for rendering.
     */
    public void setRenderSurface(Canvas canvas) {
        this.renderSurface = canvas;
        // create the double buffer on the Canvas
        this.renderSurface.createBufferStrategy(2);
    }

    /**
     * 
    */
    @Override
    public void generateNextFrame(){
        this.isRunning = true;

        for(IGameObject go : this.enabledList){
            if(go.behaviour() != null)
                go.behaviour().onUpdate(1d/60d);
            if(go.collider() != null)
                go.collider().onUpdate(); 
        }
        checkCollisions();
        this.isRunning = false;
    }

    /**
     * Função para teste
     * Atualiza os GameObjects em cada frame.
     * 
     * @param frames Número de frames a simular.
     */
    public void simulateFrames(int frames) {
        for(int frame = 0; frame < frames; frame++) {
            manageGO();
        }
    }

    /**  
     * Função para teste
     * Detecta colisoes entre os GameObjects na mesma layer.
     * 
     */
    @Override
    public void checkCollisions(){
        for(ArrayList<IGameObject> gol : layers.values()){ //itera as layers
    
            for(int i = 0; i < gol.size()-1; i++){      //nested loop para iterar pares de GameOBjects na mesma layer
                IGameObject goA = gol.get(i);
                if(goA.collider() == null)
                    continue;
    
                for(int j = i+1; j < gol.size(); j++){
                    IGameObject goB = gol.get(j);
                    if(goB.collider() == null)
                        continue;
    
                    if(goA.collider().isColliding(goB.collider())){
                        // getOrCreate(goA).add(goB);
                        // getOrCreate(goB).add(goA);
                        collisionMap.computeIfAbsent(goA, k -> new ArrayList<>()).add(goB);
                        collisionMap.computeIfAbsent(goB, k -> new ArrayList<>()).add(goA);
                    }
    
                }
            }
        }
            // quando acabar de iterar todas as layers chama o onCollision
            // dos GameObjects que estejam enabled
        for(IGameObject go : enabledList){
            ArrayList<IGameObject> layerCollisions = collisionMap.get(go);
            if(go.behaviour() != null && layerCollisions != null)
                go.behaviour().onCollision(layerCollisions);
        }

        collisionMap.clear();
    }

    @SuppressWarnings("unused")//mesmo q compute if absent
    private ArrayList<IGameObject> getOrCreate(IGameObject key){
        ArrayList<IGameObject> out = collisionMap.get(key);
        if(out == null){
            out = new ArrayList<>();
            collisionMap.put(key, out);
        }
        return out;
    }
}
