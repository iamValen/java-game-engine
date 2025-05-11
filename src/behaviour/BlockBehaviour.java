package behaviour;

import engine.*;
import gui.ObjectCreator;
import interfaces.*;

/**
 * Classe Abstrata ABehaviour
 * 
 * @author Alexandre Menino a83974
 * @author Grégory Endrio Leite a90952
 * @author Valentim Khakhitva a81785
 * @version 11/05/2025
 */
public class BlockBehaviour extends ABehaviour {
    
        static final GameEngine engine = GameEngine.getInstance();
    
        IGameObject topHitbox;
        IGameObject leftHitbox;
        IGameObject rightHitbox;
        IGameObject bottomHitbox;

        /**
         * Para cada bloco (retângulo) é criado 4 Colliders a representar os 4 lados para facilitar na hora das colisões
         * @param x
         * @param y
         * @param width
         * @param height
         */
        public BlockBehaviour(double x, double y, double width, double height) {
            topHitbox = ObjectCreator.blockWall(x, y - height/2 + 1, width-5, 2, "floor");
            bottomHitbox = ObjectCreator.blockWall(x, y + height/2 - 1, width-5, 2, "celing");
            leftHitbox = ObjectCreator.blockWall(x - width/2 + 1, y, 2, height-10, "rightWall");
            rightHitbox = ObjectCreator.blockWall(x + width/2 - 1, y, 2, height-10, "leftWall");

        }
    
        @Override
        public void oninit() {
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
