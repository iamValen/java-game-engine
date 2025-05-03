package engine;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.HashMap;

import gui.Loader;
import interfaces.IGameEngine;
import interfaces.IGameObject;
import interfaces.IShape;

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

    private boolean isRunning;
    private Canvas renderSurface;

    public boolean isRunning(){ return this.isRunning; }

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
        this.enabledList.add(go);
        this.layers.computeIfAbsent(go.transform().layer(), k -> new ArrayList<>()).add(go);
        if(go.behaviour() != null) go.behaviour().oninit();
    }

    @Override
    public void addDisabled(IGameObject go){
        this.disabledList.add(go);
        if(go.behaviour() != null) go.behaviour().oninit();
    }

    @Override
    public void enable(IGameObject go){
        this.disabledList.remove(go);
        this.enabledList.add(go);
        this.layers.computeIfAbsent(go.transform().layer(), k -> new ArrayList<>()).add(go);
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
    public void destroy(IGameObject go){
        if(isEnabled(go)){
            if(go.behaviour() != null) go.behaviour().onDestroy();
            this.enabledList.remove(go);
            this.layers.get(go.transform().layer()).remove(go);
        }
        else{
            this.disabledList.remove(go);
        }
    }
    
    @Override
    public void destroyAll(){
        for(IGameObject go : this.enabledList){
            if(go.behaviour() != null) go.behaviour().onDestroy();
        }
        this.layers.clear();
        this.disabledList.clear();
        this.enabledList.clear();
    }


    @Override
    public void run() {
        // Must have called setRenderSurface(canvas) first!
        BufferStrategy bs = renderSurface.getBufferStrategy();

        // Load level once *before* entering the loop
        new Loader().loadLevel();

        isRunning = true;
        final long frameTimeNs = 1_000_000_000L / 60; // 60 FPS cap
        long       last        = System.nanoTime();

        while (isRunning) {
            long now   = System.nanoTime();
            double dt  = (now - last) / 1_000_000_000.0;
            last       = now;

            // 1) UPDATE
            for (IGameObject go : enabledList) {
                if (go.behaviour() != null) go.behaviour().onUpdate(dt);
                if (go.collider()  != null) go.collider().onUpdate();
            }
            checkCollisions();

            // 2) RENDER
            Graphics g = bs.getDrawGraphics();
            // clear the back‑buffer
            g.setColor(java.awt.Color.WHITE);
            g.fillRect(0, 0, renderSurface.getWidth(), renderSurface.getHeight());

            // draw all GameObjects via your GameObject.render(g)
            for (IGameObject go : enabledList) {
                ((GameObject) go).render(g);
            }

            g.dispose();    // release this Graphics
            bs.show();      // flip buffers

            // 3) SLEEP to cap frame rate
            long elapsed = System.nanoTime() - now;
            long toSleep = (frameTimeNs - elapsed) / 1_000_000;
            if (toSleep > 0) {
                try { Thread.sleep(toSleep); }
                catch (InterruptedException ignored) {}
            }
        }
        //System.out.println(elapsed/1000);
    }

    public void render(Graphics g) {
        for (IGameObject go : enabledList) {
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
     * used for testing
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
        for(ArrayList<IGameObject> gol : layers.values()){ //itera as layers
    
            for(int i = 0; i < gol.size(); i++){          //nested loop para iterar pares de GameOBjects na mesma layer
    
                IGameObject goA = gol.get(i);
                if(goA.collider() == null)
                    continue;
    
                for(int j = i+1; j < gol.size(); j++){
    
                    IGameObject goB = gol.get(j);
                    if(goB.collider() == null)
                        continue;
    
                    if(goA.collider().isColliding(goB.collider())){
                        collisionMap.computeIfAbsent(goA, k -> new ArrayList<>()).add(goB);
                        collisionMap.computeIfAbsent(goB, k -> new ArrayList<>()).add(goA);
                    }
    
                }
            }
    
            // quando acabar de iterar a layer chama o onCollision dos GameObjects dessa layer
            for(IGameObject go : gol){
                if(go.behaviour() != null && collisionMap.get(go) != null)
                    go.behaviour().onCollision(collisionMap.get(go));
            }
        }
    }
}
