package stickman.model;

public class Platform extends EntityImpl {
    private final double DEFAULT_HEIGHT = 20;
    private final double DEFAULT_WIDTH = 40;

    public Platform(String imgPath, double xPos, double yPos, double height, double width) {
        super(imgPath, xPos, yPos, height, width, Layer.FOREGROUND);
    }

    public Platform(String imgPath, double xPos, double yPos) {
        super(imgPath, xPos, yPos, 0, 0, Layer.FOREGROUND);
        this.setHeight(DEFAULT_HEIGHT);
        this.setWidth(DEFAULT_WIDTH);
    }

}


