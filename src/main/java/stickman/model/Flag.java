package stickman.model;

public class Flag extends EntityImpl {
    private final double HEIGHT = 30.0;
    private final double WIDTH = 15.0;

    public Flag(String imgPath, double xPos, double yPos) {
        super(imgPath, xPos, yPos, 0, 0, Layer.FOREGROUND);
        setHeight(HEIGHT);
        setWidth(WIDTH);
    }
}
