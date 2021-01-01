package stickman.CollisionStrategy;


import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import stickman.model.*;

import java.net.URL;

public class StickmanCollisionStrategy implements EntityCollisionStrategy {
    @Override
    public boolean checkCollision(Entity self, Entity otherEntity) {
        if(self.equals(otherEntity)) {
            return false;
        }

        if(self.getLayer().equals(Entity.Layer.BACKGROUND) ||
                self.getLayer().equals(Entity.Layer.EFFECT) ||
                otherEntity.getLayer().equals(Entity.Layer.EFFECT) ||
                otherEntity.getLayer().equals(Entity.Layer.BACKGROUND)) {
            return false;
        }

        return (self.getXPos() < (otherEntity.getXPos() + (otherEntity.getWidth())) &&
                (self.getXPos() + (self.getWidth() / 2)) > otherEntity.getXPos() &&
                (self.getYPos() < (otherEntity.getYPos() + otherEntity.getHeight())) &&
                (self.getYPos() + self.getHeight()) > otherEntity.getYPos());
    }

    @Override
    public void handleCollision(Entity hero, Entity otherEntity) {
        Stickman self = (Stickman) hero;
        //if entity was platform
        if(otherEntity instanceof Platform) {
            //if collision was on the x axis
            if(Math.abs(otherEntity.getXPos() - self.getXPos()) > Math.abs(otherEntity.getYPos() - self.getYPos())) {
                //check if collision is from the right (of stickman)
                if(self.getXPos() < otherEntity.getXPos()) {
                    self.setXPos(otherEntity.getXPos() - (self.getWidth() /2));
                }
                //check if the collision is from the left
                else if(self.getXPos() + (self.getWidth() / 2) > otherEntity.getXPos() ) {
                    self.setXPos(otherEntity.getXPos() + otherEntity.getWidth());
                }
            }

            //y axis collision
            else {
                //check if collision was on top of the platform
                //only if stickman's legs touch the platform not any where else
                if(self.getYPos() + self.getHeight() < (otherEntity.getYPos() + otherEntity.getHeight())) {
                    self.setJumping(false);
                    self.setYPos(otherEntity.getYPos() - self.getHeight());
                }

                //check if collision was from bottom of the platform
                else if((self.getYPos() + (self.getHeight() / 2)) > otherEntity.getYPos()) {
                    //stickman should fall down (can't intersect platform)
                    self.setYVel(self.getGravity());
                }
            }
        }


        //if entity was enemy, player loses a life and restarts position
        else if(otherEntity instanceof Enemy) {
            self.setLives(self.getLives() - 1);
            self.restartPosition();
            //if player's lives are 0 or less; player lost
            //play losing sound and mark died state as true
            if(self.getLives() <= 0) {
                URL mediaUrlLost = getClass().getResource("/lost.wav");
                String lostURL = mediaUrlLost.toExternalForm();
                Media lostSound = new Media(lostURL);
                MediaPlayer LostMediaPlayer = new MediaPlayer(lostSound);
                LostMediaPlayer.stop();
                LostMediaPlayer.play();
                self.setDead();
            }
        }

        //if entity was mushroom, player can now shoot, mushroom should disappear
        else if(otherEntity instanceof Mushroom) {
            self.setCanShoot(true);
            otherEntity.delete();
        }

        //if entity was flag, player won: play winning sound
        else if(otherEntity instanceof Flag) {
            URL mediaUrlWon = getClass().getResource("/finish.wav");
            String wonURL = mediaUrlWon.toExternalForm();
            Media wonSound = new Media(wonURL);
            MediaPlayer wonMediaPlayer = new MediaPlayer(wonSound);
            wonMediaPlayer.stop();
            wonMediaPlayer.play();
            self.setWon();
            otherEntity.delete();
        }
    }
}
