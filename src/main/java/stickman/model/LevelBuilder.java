package stickman.model;

public interface LevelBuilder {
    LevelBuilder setLevelConfig();
    LevelBuilder addStickman();
    LevelBuilder addGrassBlock();
    LevelBuilder addPlatform();
    LevelBuilder addCloud();
    LevelBuilder addEnemy();
    LevelBuilder addMushroom();
    LevelBuilder addFlag();
    LevelImpl build();
}
