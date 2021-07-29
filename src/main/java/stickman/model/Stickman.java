package stickman.model;

import stickman.CollisionStrategy.StickmanCollisionStrategy;

public class Stickman extends EntityImpl {
    private String size;
    private final double JUMP_HEIGHT = 65;
    private final double JUMP_VEL = -1.1;
    private final double  GRAVITY = 2;
    private double positionBeforeJumping;
    private final double initialY;
    private final double initialX;
    private boolean facingRight;
    private int standingImgNum = 0;
    private int walkingImgNumber = 0;
    private boolean canShoot = false;
    private final int shootingRate = 1;
    private int LIVES = 3;
    private boolean won = false;
    private boolean died = false;
    private boolean jumping;
    private int counter = 0;

    public Stickman(String imgPath, double xPos, double floorHeight, String size) {
        super(imgPath, xPos, 0, 0, 0, Layer.FOREGROUND);
        this.collisionStrategy = new StickmanCollisionStrategy();
        this.size = size;
        this.setWidth(this.getWidth());
        this.setHeight(this.getHeight());
        this.yPos = floorHeight - this.height;
        this.initialY = this.yPos;
        if(this.xPos < 0) {
            this.xPos = 0;
        }
        this.initialX = xPos;
        this.isOnGround = true;
        this.jumping = false;
        this.left = false;
        this.right = false;
        this.yVel = -1;
        this.facingRight = true;
    }

    public void setXPos(double xPos) {
        //can't go further to the left than the left border of the game
        //(ie can't have a negative x coordinate)
        if(xPos < 0) {
            this.xPos = 0;
            return;
        }
        this.xPos = xPos;
    }

    public double getHeight() {
        if(this.size.equals("normal")) {
            return 30.0;
        }
        else {
            return 50.0;
        }
    }

    public double getWidth() {
        if(this.size.equals("normal")) {
            return 30.0;
        }
        else {
            return 50.0;
        }
    }

    public boolean moveRight() {
        this.right = true;
        this.setXPos(this.xPos + this.xVel);

        return this.right;
    }

    public boolean moveLeft() {
        this.left = true;
        this.setXPos(this.xPos + this.xVel);
        return this.left;
    }

    public boolean stopMoving() {
        this.right = false;
        this.left = false;
        return true;
    }

    public boolean moveX() {
        if(this.xVel > 0) { //positive x velocity mean moving forward to the right
            this.right = true;
            this.left = false;
        }
        else if(this.xVel < 0) { //negative x velocity = moving to the left
            this.right = false;
            this.left = true;
        }
        else {
            this.left = false;
            this.right = false;
        }

        this.setXPos(this.getXPos() + this.xVel);
        return this.right;
    }

    public boolean jump() {
        this.setYPos(this.yPos + this.yVel);

        /* when maximum jump height with respect to stickman's position before jumping
        /* is reached, stickman should be pulled down */
        if(this.yPos < positionBeforeJumping - JUMP_HEIGHT) {
            this.yVel = GRAVITY;
            this.yPos = positionBeforeJumping - JUMP_HEIGHT;
        }
        //can't go further down than the ground
        else if(this.yPos >= initialY) {
            this.yPos = this.initialY;
            this.yVel = 0;
            this.isOnGround = true;
            this.jumping = false;
        }
        return true;
    }

    public void setYPos(double yPos) {
        if(yPos >= this.initialY) {
            this.yPos = this.initialY;
            this.isOnGround = true;
        }
        else {
            this.yPos = yPos;
        }
    }

    public boolean isOnGround() {
        return this.isOnGround;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
        if(jumping) {
            this.isOnGround = false;
            this.positionBeforeJumping = this.yPos;
            this.setYVel(JUMP_VEL);
        }
        else {
            this.isOnGround = true;
            setYVel(0);
        }
    }

    public boolean isFacingRight() {
        return this.facingRight;
    }

    public void setCanShoot(boolean canShoot) {
        this.canShoot = canShoot;
    }

    public boolean canShoot() {
        if(!this.canShoot || this.counter % shootingRate != 0) {
            return false;
        }

        return this.canShoot;
    }

    public void updateImagePath(String path) {
        this.imgPath = path;
    }

    public void restartPosition() {
        this.setXPos(initialX);
        this.setYPos(initialY);
    }

    public int getLives() {
        return LIVES;
    }

    public void setLives(int lives) {
        this.LIVES = lives;
    }

    public void setWon() {
        this.won = true;
    }

    public boolean won() {
        return this.won;
    }

    public void setDead() {
        this.died = true;
    }

    public boolean lost() {
        return this.died;
    }

    public double getGravity() {
        return this.GRAVITY;
    }

    //sprite animate the stickman
    public void animate() {
        if(this.right) {
            this.walkingImgNumber = (walkingImgNumber % 4) + 1;
            this.updateImagePath(String.format("ch_walk_right%d.png", walkingImgNumber));
            this.facingRight = true;
        } else if(this.left) {
            this.walkingImgNumber = (walkingImgNumber % 4) + 1;
            this.updateImagePath(String.format("ch_walk_left%d.png", walkingImgNumber));
            this.facingRight = false;
        } else {
            this.standingImgNum = (standingImgNum % 3) + 1;
            if(this.facingRight) {
                this.imgPath = String.format("ch_stand_right%d.png", standingImgNum);
            } else {
                this.imgPath = String.format("ch_stand_left%d.png", standingImgNum);
            }
        }
    }

    public void tick() {
        this.counter++; //this counter will keep track of the ticks

        if(jumping) {
            jump();
        }
        moveX();

        // make sure stickman is pulled by gravity at all times
        if(!jumping) {
            setYVel(GRAVITY);
            setYPos(this.yPos + GRAVITY);
        }
    }
}
