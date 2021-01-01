package stickman.model;

import java.util.List;

public interface Level {
    List<Entity> getEntities();
    double getHeight();
    double getWidth();

    void tick();
    void animate();

    double getFloorHeight();
    double getHeroX();

    boolean jump();
    boolean moveLeft();
    boolean moveRight();
    boolean stopMoving();

    Stickman getHero();

    boolean heroShoot();
}
