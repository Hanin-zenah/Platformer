package stickman.CollisionStrategy;

import stickman.model.Entity;

public interface EntityCollisionStrategy {
    boolean checkCollision(Entity self, Entity otherEntity);
    void handleCollision(Entity self, Entity otherEntity);
}
