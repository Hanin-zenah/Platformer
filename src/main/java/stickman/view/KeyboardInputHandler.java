package stickman.view;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import stickman.model.GameEngine;

import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class KeyboardInputHandler {
    private final GameEngine model;
    private boolean left = false;
    private boolean right = false;
    private Set<KeyCode> pressedKeys = new HashSet<>();
    private Map<String, MediaPlayer> sounds = new HashMap<>();

    KeyboardInputHandler(GameEngine model) {
        this.model = model;

        URL mediaUrl = getClass().getResource("/jump.wav");
        String jumpURL = mediaUrl.toExternalForm();
        Media sound = new Media(jumpURL);
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        sounds.put("jump", mediaPlayer);

        URL mediaUrlFiring = getClass().getResource("/smb_fireball.wav");
        String fireURL = mediaUrlFiring.toExternalForm();
        Media fireSound = new Media(fireURL);
        MediaPlayer mediaPlayerFire = new MediaPlayer(fireSound);
        sounds.put("shoot", mediaPlayerFire);
    }

    void handlePressed(KeyEvent keyEvent) {
        if (pressedKeys.contains(keyEvent.getCode())) {
            return;
        }
        pressedKeys.add(keyEvent.getCode());

        if (keyEvent.getCode().equals(KeyCode.UP)) {
            if (model.getCurrentLevel().getHero().isOnGround()) {
                MediaPlayer jumpPlayer = sounds.get("jump");
                jumpPlayer.stop();
                jumpPlayer.play();
                model.getCurrentLevel().getHero().setJumping(true);
                model.jump();

            }
        }

        if (keyEvent.getCode().equals(KeyCode.LEFT)) {
            left = true;
        }
        else if (keyEvent.getCode().equals(KeyCode.RIGHT)) {
            right = true;
        } else if(keyEvent.getCode().equals(KeyCode.SPACE)) {
            if(model.getCurrentLevel().heroShoot()) {
                MediaPlayer firePlayer = sounds.get("shoot");
                firePlayer.stop();
                firePlayer.play();
            }
        }
        else {
            return;
        }

        if (left) {
            if (right) {
                this.model.getCurrentLevel().getHero().setXVel(0);
                model.stopMoving();
            } else {
                this.model.getCurrentLevel().getHero().setXVel(-1.5);
                model.moveLeft();
            }
        } else if(right) {
            this.model.getCurrentLevel().getHero().setXVel(1.5);
            model.moveRight();
        }
    }

    void handleReleased(KeyEvent keyEvent) {
        pressedKeys.remove(keyEvent.getCode());

        if (keyEvent.getCode().equals(KeyCode.LEFT)) {
            left = false;
        }
        else if (keyEvent.getCode().equals(KeyCode.RIGHT)) {
            right = false;
        } else {
            return;
        }

        if (!(right || left)) {
            this.model.getCurrentLevel().getHero().setXVel(0);
            model.stopMoving();
        } else if (right) {
            this.model.getCurrentLevel().getHero().setXVel(1.5);
            model.moveRight();
        } else {
            this.model.getCurrentLevel().getHero().setXVel(-1.5);
            model.moveLeft();
        }
    }
}
