package game;

import engine.*;
import gameManager.ObjectCreator;
import interfaces.*;

/**
 * Classe Abstrata ABehaviour
 * 
 * @author Alexandre Menino a83974
 * @author Grégory Endrio Leite a90952
 * @author Valentim Khakhitva a81785
 * @version 11/05/2025
 */
public class BlockBehaviour extends AAABehaviour {
    
        static final GameEngine engine = GameEngine.getInstance();
    
        IGameObject topHitbox;
        IGameObject leftHitbox;
        IGameObject rightHitbox;
        IGameObject bottomHitbox;
        private final int width;
        private final int height;

        /**
         * Para cada bloco é criado 4 Colliders a representar os 4 lados para facilitar na hora das colisões
         * @param x
         * @param y
         * @param width
         * @param height
         */
        public BlockBehaviour(int width, int height){
            this.width = width;
            this.height = height;
        }

        @Override
        public void oninit(){
            double x = myGo.transform().position().x();
            double y = myGo.transform().position().y();
            topHitbox = ObjectCreator.blockWall(x, y - height/2 + 1, 0,0,1, width-5, 2, "floor");
            bottomHitbox = ObjectCreator.blockWall(x, y + height/2 - 1, 0,0,1, width-20, 2, "celing");
            leftHitbox = ObjectCreator.blockWall(x - width/2 + 1, y, 0,0,1, 2, height-10, "rightWall");
            rightHitbox = ObjectCreator.blockWall(x + width/2 - 1, y, 0,0,1, 2, height-10, "leftWall");
            //se o bloco tiver scale ou rotation isto nao funciona
            engine.addEnabled(topHitbox);
            engine.addEnabled(leftHitbox);
            engine.addEnabled(rightHitbox);
            engine.addEnabled(bottomHitbox);
    
        }
        @Override
        public void onDestroy() {
            engine.destroy(topHitbox);
            engine.destroy(leftHitbox);
            engine.destroy(rightHitbox);
            engine.destroy(bottomHitbox);
        }
    
    
        @Override
        public void onEnable() {
            engine.enable(topHitbox);
            engine.enable(leftHitbox);
            engine.enable(rightHitbox);
            engine.enable(bottomHitbox);
        }
        @Override
        public void onDisable() {
            engine.disable(topHitbox);
            engine.disable(leftHitbox);
            engine.disable(rightHitbox);
            engine.disable(bottomHitbox);
        }
    
    
        @Override
        public void onUpdate(double dT){
        }
        
}
