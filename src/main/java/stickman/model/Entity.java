package stickman.model;


public interface Entity {
    String getImagePath();
    void setXPos(double xPos);
    void setYPos(double yPos);
    double getXPos();
    double getYPos();
    double getHeight();
    double getWidth();
    Layer getLayer();
    void setXVel(double xVel);
    void setYVel(double yVel);
    double getXVel();
    boolean isOnGround();
    void setWidth(double width);
    void setHeight(double height);
    void tick();
    void animate();
    boolean checkCollision(Entity otherEntity);
    void handleCollision(Entity otherEntity);
    boolean isDeleted();
    void delete();

        enum Layer {
        BACKGROUND, FOREGROUND, EFFECT
    }
}
