package stickman.model;

public class MovingPlatform extends Platform {
    private final boolean vertical;
    private final double RANGE;
    private double initialX;
    private double initialY;

    public MovingPlatform(String imgPath, double xPos, double yPos, boolean vertical) {
        super(imgPath, xPos, yPos);
        this.vertical = vertical;
        if(vertical) {
            this.yVel = -0.2;
        }
        else {
            this.xVel = 0.2;
        }
        this.RANGE = 60;
        this.initialX = xPos;
        this.initialY = yPos;

    }

    public void moveX() {
        //move along the x axis
        this.setXPos(this.xPos + this.xVel);
        if(this.xPos > this.initialX + RANGE) {
            //move the other direction
            this.xPos = this.initialX + RANGE;
            this.setXVel(this.xVel * -1);
        }
        else if(this.xPos < this.initialX) {
            this.xPos = this.initialX;
            this.setXVel(this.xVel * -1);
        }

    }

    public void moveY() {
        this.setYPos(this.yPos + this.yVel);
        if(this.yPos > this.initialY + RANGE) {
            //move the other direction
            this.yPos = this.initialY + RANGE;
            this.setYVel(this.yVel * -1);
        }
        else if(this.yPos < this.initialY) {
            this.yPos = this.initialY;
            this.setYVel(this.yVel * -1);
        }
    }

    public void tick() {
        if(vertical) {
            moveY();
        }
        else {
            moveX();
        }
    }
}
