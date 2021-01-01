package stickman.CollisionStrategy;

import stickman.model.Entity;
import stickman.model.Platform;

public class BulletCollisionStrategy implements EntityCollisionStrategy {

    @Override
    public boolean checkCollision(Entity self, Entity otherEntity) {
        if(this.equals(otherEntity)) {
            return false;
        }

        if(self.getLayer().equals(Entity.Layer.BACKGROUND) ||
                self.getLayer().equals(Entity.Layer.EFFECT) ||
                otherEntity.getLayer().equals(Entity.Layer.EFFECT) ||
                otherEntity.getLayer().equals(Entity.Layer.BACKGROUND)) {
            return false;
        }

        return (self.getXPos() < (otherEntity.getXPos() + (otherEntity.getWidth())) &&
                (self.getXPos() + (self.getWidth())) > otherEntity.getXPos() &&
                (self.getYPos() < (otherEntity.getYPos() + otherEntity.getHeight())) &&
                (self.getYPos() + self.getHeight()) > otherEntity.getYPos());
    }

    @Override
    public void handleCollision(Entity self, Entity otherEntity) {
        if(otherEntity instanceof Platform) {
            self.delete();
        }
    }
}
