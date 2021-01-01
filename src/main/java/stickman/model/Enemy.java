package stickman.model;

import stickman.CollisionStrategy.EnemyCollisionStrategy;

public class Enemy extends EntityImpl {
    private int imgNum = 0;
    private int type;
    public Enemy(String imgPath, double xPos, double yPos, double height, double width, double xVel, int enemyType) {
        super(imgPath, xPos, yPos, height, width, Layer.FOREGROUND);
        this.xVel = xVel;
        this.type = enemyType;
        this.collisionStrategy = new EnemyCollisionStrategy();
    }

    public void moveX() {
        this.setXPos(this.xPos + this.xVel);
        if(this.xPos == 0) {
            this.xVel *= -1;
        }
    }

    public void animate() {
        this.imgNum = (this.imgNum % 2) + 1;
        this.imgPath = String.format("slime%d%c.png", this.type, this.imgNum == 1? 'a' : 'b');
    }

    public void tick() {
        moveX();
    }
}
