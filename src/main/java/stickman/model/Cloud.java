package stickman.model;

public class Cloud extends EntityImpl {
    private double velocity;

    public Cloud(String imgPath, double xPos, double yPos, double height, double width, double velocity) {
        super(imgPath, xPos, yPos, height, width, Layer.BACKGROUND);
        this.velocity = velocity;
    }

    public void setXPos(double xPos) {
        this.xPos = xPos;
        //remove when it is out of the left border of the game window
        if(this.xPos < 0 - width) {
            this.delete();
        }
    }
    public void moveCloud() {
        this.setXPos(this.xPos + this.velocity);
    }

    public void tick() {
        moveCloud();
    }
}
