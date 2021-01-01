package stickman.model;

import stickman.CollisionStrategy.DefaultCollisionStrategy;
import stickman.CollisionStrategy.EntityCollisionStrategy;

/* This class will implement the interface Entity and will be a super class to all the game objects (eg. enemy, hero, platform, etc) */
public class EntityImpl implements Entity {
    protected String imgPath;
    protected double xPos;
    protected double yPos;
    protected double height;
    protected double width;
    protected double xVel;
    protected double yVel;
    protected Layer layer;
    protected boolean left;
    protected boolean right;
    protected boolean deleted;
    protected boolean isOnGround;
    //set collision strategy to default one
    protected EntityCollisionStrategy collisionStrategy = new DefaultCollisionStrategy();

    public EntityImpl(String imgPath, double xPos, double yPos, double height, double width, Layer layer) {
        this.imgPath = imgPath;
        this.xPos = xPos;
        this.yPos = yPos;
        this.height = height;
        this.width = width;
        this.layer = layer;
    }

    public EntityImpl(String imgPath, double xPos, double yPos, Layer layer) {
        this.imgPath = imgPath;
        this.xPos = xPos;
        this.yPos = yPos;
        this.layer = layer;
    }

    @Override
    public String getImagePath() {
        return this.imgPath;
    }

    @Override
    public void setXPos(double xPos) {
        if(xPos < 0) {
            this.xPos = 0;
            return;
        }
        this.xPos = xPos;
    }

    @Override
    public void setYPos(double yPos) {
        this.yPos = yPos;
    }

    @Override
    public double getXPos() {
        return this.xPos;
    }

    @Override
    public double getYPos() {
        return this.yPos;
    }

    @Override
    public double getHeight() {
        return this.height;
    }

    @Override
    public double getWidth() {
        return this.width;
    }

    @Override
    public Layer getLayer() {
        return this.layer;
    }

    @Override
    public void setXVel(double xVel) {
        this.xVel = xVel;
    }

    @Override
    public double getXVel() {
        return this.xVel;
    }

    @Override
    public void setYVel(double yVel) {
        this.yVel = yVel;
    }

    @Override
    public boolean isOnGround() {
        return this.isOnGround;
    }

    @Override
    public void setWidth(double width) {
        this.width = width;
    }

    @Override
    public void setHeight(double height) {
        this.height = height;
    }

    @Override
    public void tick() {
        return;
    }

    @Override
    public void animate() {
        return;
    }

    @Override
    public boolean checkCollision(Entity otherEntity) {
        return collisionStrategy.checkCollision(this, otherEntity);
    }

    @Override
    public void handleCollision(Entity otherEntity) {
        collisionStrategy.handleCollision(this, otherEntity);
    }

    @Override
    public boolean isDeleted() {
        return this.deleted;
    }

    @Override
    public void delete() {
        this.deleted = true;
    }

}
