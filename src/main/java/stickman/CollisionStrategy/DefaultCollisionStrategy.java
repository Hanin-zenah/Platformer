package stickman.CollisionStrategy;

import stickman.model.Entity;

public class DefaultCollisionStrategy implements EntityCollisionStrategy{
    @Override
    public boolean checkCollision(Entity self, Entity otherEntity) {
        return false;
    }

    @Override
    public void handleCollision(Entity self, Entity otherEntity) {

    }
}
