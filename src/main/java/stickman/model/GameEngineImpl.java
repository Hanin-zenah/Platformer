package stickman.model;

import stickman.JSONFileExtractor;

public class GameEngineImpl implements GameEngine {
    private Level currentLevel;
    private JSONFileExtractor configObj;

    public GameEngineImpl(JSONFileExtractor jsonFileObject) {
        if(jsonFileObject == null) {
            throw new NullPointerException("Config File null");
        }
        this.configObj = jsonFileObject;
        startLevel();
    }

    @Override
    public Level getCurrentLevel() {
        return this.currentLevel;
    }

    @Override
    public void startLevel() {
        this.currentLevel = new NormalLevelBuilder(this.configObj)
                .setLevelConfig()
                .addStickman()
                .addGrassBlock()
                .addPlatform()
                .addCloud()
                .addEnemy()
                .addMushroom()
                .addFlag()
                .build();
    }

    @Override
    public boolean jump() {
        return this.currentLevel.jump();
    }

    @Override
    public boolean moveLeft() {
        return this.currentLevel.moveLeft();
    }

    @Override
    public boolean moveRight() {
        return this.currentLevel.moveRight();
    }

    @Override
    public boolean stopMoving() {
        return this.currentLevel.stopMoving();
    }

    @Override
    public void tick() {
        currentLevel.tick();
    }

    @Override
    public void animate() {
        currentLevel.animate();
    }
}
