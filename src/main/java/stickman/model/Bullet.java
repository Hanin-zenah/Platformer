package stickman.model;

import stickman.CollisionStrategy.BulletCollisionStrategy;

public class Bullet extends EntityImpl {
    private final double BULLET_SIZE = 10;
    private final double VELOCITY = 1.85;
    private final boolean right;

    public Bullet(double xPos, double yPos, boolean right) {
        //by default, fire ball will face right direction
        super("fire_ball_right.png", xPos, yPos, Layer.FOREGROUND);
        this.collisionStrategy = new BulletCollisionStrategy();
        this.height = BULLET_SIZE;
        this.width = BULLET_SIZE;
        this.right = right;
        //if hero was facing left: fire ball will face left direction too
        if(!right) {
            this.imgPath = "fire_ball_left.png";
        }
    }
    public void setXPos(double xPos) {
        this.xPos = xPos;
        if(this.xPos < 0) {
            this.delete();
        }
    }

    public void moveX() {
        if(this.right) {
            this.setXPos(this.xPos + VELOCITY);
        }
        else {
            this.setXPos(this.xPos - VELOCITY);
        }
    }

    public void tick() {
        moveX();
    }
}
