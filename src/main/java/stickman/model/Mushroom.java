package stickman.model;

public class Mushroom extends EntityImpl {
    private final double SIZE;

    public Mushroom(String imgPath, double xPos, double yPos, double size) {
        super(imgPath, xPos, yPos, 0, 0, Layer.FOREGROUND);
        SIZE = size;
        this.setHeight(SIZE);
        this.setWidth(SIZE);
    }
}
