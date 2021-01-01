package stickman.CollisionStrategy;

import stickman.model.Bullet;
import stickman.model.Enemy;
import stickman.model.Entity;
import stickman.model.Platform;

public class EnemyCollisionStrategy implements EntityCollisionStrategy {

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
    public void handleCollision(Entity enemy, Entity otherEntity) {
        Enemy self = (Enemy) enemy;
        if(otherEntity instanceof Bullet) {
            self.delete();
            otherEntity.delete();
        }
        else if(otherEntity instanceof Platform) {
            if(self.getXPos() < otherEntity.getXPos() - 1) {
                self.setXVel(self.getXVel() * -1);
                self.setXPos(otherEntity.getXPos() - otherEntity.getWidth());
            }
            //check if the collision is from the left
            else if(self.getXPos() > otherEntity.getXPos() - otherEntity.getWidth()) {
                self.setXVel(self.getXVel() * -1);
                self.setXPos(otherEntity.getXPos() + otherEntity.getWidth());
            }
        }
    }
}
