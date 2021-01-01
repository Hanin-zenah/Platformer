package stickman.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import stickman.JSONFileExtractor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NormalLevelBuilder implements LevelBuilder {
        private double height;
        private double width;
        private double floorHeight;
        private Stickman hero;
        private JSONFileExtractor configFile;
        private JSONObject cloudConfig;
        private List<Entity> entities = new ArrayList<>();

        NormalLevelBuilder(JSONFileExtractor configFile) {
            if (configFile == null) {
                throw new NullPointerException("configuration file object cannot be null");
            }
            this.configFile = configFile;
        }

        double getHeight() {
            return this.height;
        }

        double getWidth() {
            return this.width;
        }

        List<Entity> getEntities() {
            return this.entities;
        }

        double getFloorHeight() {
            return this.floorHeight;
        }

        Stickman getHero() {
            return this.hero;
        }

        JSONObject getCloudConfig() {
            return this.cloudConfig;
        }

        @Override
        public LevelBuilder setLevelConfig() {
            JSONObject levelConfig = this.configFile.getLevelConfig();
            if (levelConfig == null) {
                throw new NullPointerException("level configuration is null. please make sure the configuration file is structured correctly");
            }
            this.height = (double) levelConfig.get("height");
            this.width = (double) levelConfig.get("width");
            this.floorHeight = (double) levelConfig.get("floorHeight");
            return this;
        }

        @Override
        public LevelBuilder addStickman() {
            JSONObject stickmanConfig = this.configFile.getStickmanConfig();
            if (stickmanConfig == null) {
                throw new NullPointerException("Stickman configuration is null");
            }
            double xPos = (double) stickmanConfig.get("x");
            String size = (String) stickmanConfig.get("size");
            this.hero = new Stickman("ch_stand_right1.png", xPos, this.floorHeight, size);
            this.entities.add(hero);
            return this;
        }

        @Override
        public LevelBuilder addPlatform() {
            JSONObject platformConfig = this.configFile.getPlatformConfig();
            if (platformConfig != null) {
                JSONArray xCoords = (JSONArray) platformConfig.get("x");
                JSONArray yCoords = (JSONArray) platformConfig.get("y");
                JSONArray movement = (JSONArray) platformConfig.get("movement");
                if (xCoords == null || yCoords == null || movement == null) {
                    throw new NullPointerException("please check that the .json file is in the right format");
                }
                if (xCoords.size() != yCoords.size() || xCoords.size() != movement.size()) {
                    throw new IllegalArgumentException("platform configuration arrays should all be the same size");
                }
                Platform platform;

                for (int i = 0; i < xCoords.size(); i++) {
                    if (movement.get(i).toString().toLowerCase().equals("none")) {
                        //create normal platform
                        platform = new Platform("dotted_grass_platform.png", (double) xCoords.get(i), (double) yCoords.get(i));
                    } else if (movement.get(i).toString().toLowerCase().equals("vertical")) {
                        //create vertically moving platform
                        platform = new MovingPlatform("grass_platform.png", (double) xCoords.get(i), (double) yCoords.get(i), true);
                    } else if (movement.get(i).toString().toLowerCase().equals("horizontal")) {
                        //create horizontally moving platform
                        platform = new MovingPlatform("grass_platform.png", (double) xCoords.get(i), (double) yCoords.get(i), false);

                    } else {
                        throw new IllegalArgumentException("Movement specified should be either none, vertical or horizontal");
                    }

                    this.entities.add(platform);
                }
            }
            return this;
        }

        @Override
        public LevelBuilder addGrassBlock() {
            JSONObject grassBlockConfig = this.configFile.getGrassBlockConfig();
            if (grassBlockConfig != null) {
                JSONArray xCoords = (JSONArray) grassBlockConfig.get("x");
                Platform grassBlock;
                double grassBlockHeight = 20;
                double grassBlockWidth = 20;
                for (int i = 0; i < xCoords.size(); i++) {
                    double xPos = (double) xCoords.get(i);
                    grassBlock = new Platform("foot_tile.png", xPos, this.floorHeight - grassBlockHeight, grassBlockHeight, grassBlockWidth);
                    this.entities.add(grassBlock);
                }
            }
            return this;
        }

        @Override
        public LevelBuilder addCloud() {
            JSONObject cloudConfig = this.configFile.getCloudConfig();
            if (cloudConfig != null) {
                this.cloudConfig = cloudConfig;
                JSONArray cloudVel = (JSONArray) cloudConfig.get("velocity");
                JSONArray height = (JSONArray) cloudConfig.get("height");
                JSONArray width = (JSONArray) cloudConfig.get("width");
                Random random = new Random();

                if (cloudVel.size() != height.size() || height.size() != width.size()) {
                    throw new IllegalArgumentException("Cloud configuration arrays should all be the same size");
                }

                Cloud cloud;
                for (int i = 0; i < cloudVel.size(); i++) {
                    //choose the starting positions randomly
                    double xPos = this.width + (random.nextDouble() * (0 - this.width)); //always positive
                    //make sure that it is in the correct range: cloud can only be in the sky
                    double skyRange = 150;
                    double yPos = skyRange + (random.nextDouble() * (0 - skyRange));
                    //choose cloud image randomly
                    int cloudType = random.nextInt(2) + 1;
                    String cloudPath = String.format("cloud_%d.png", cloudType);
                    cloud = new Cloud(cloudPath, xPos, yPos, (double) height.get(i), (double) width.get(i), (double) cloudVel.get(i));
                    this.entities.add(cloud);
                }
            }
            return this;
        }

        @Override
        public LevelBuilder addEnemy() {
            JSONObject enemyConfig = this.configFile.getEnemyConfig();
            if (enemyConfig != null) {
                JSONArray xPos = (JSONArray) enemyConfig.get("x");
                JSONArray velocity = (JSONArray) enemyConfig.get("velocity");
                JSONArray height = (JSONArray) enemyConfig.get("height");
                JSONArray width = (JSONArray) enemyConfig.get("width");
                Random random = new Random();

                if (xPos.size() != velocity.size() || velocity.size() != height.size() || height.size() != width.size()) {
                    throw new IllegalArgumentException("Enemy configuration arrays should all be the same size");
                }

                Enemy enemy;
                for (int i = 0; i < xPos.size(); i++) {
                    //pick enemy image randomly
                    int enemyType = random.nextInt(5) + 1;
                    String enemyImgPath = String.format("slime%da.png", enemyType);
                    //ypos by default is the floor height
                    double eHeight = (double) height.get(i);
                    enemy = new Enemy(enemyImgPath, (double) xPos.get(i), this.floorHeight - eHeight, eHeight, (double) width.get(i), (double) velocity.get(i), enemyType);
                    this.entities.add(enemy);
                }
            }

            return this;
        }

        @Override
        public LevelBuilder addMushroom() {
            JSONObject mushroomConfig = this.configFile.getMushroomConfig();
            if (mushroomConfig != null) {
                double x;
                double y;
                double size = 15.0;
                //if x and y were not specified, by default mushroom will be placed on the bottom left corner of the screen
                try {
                    x = (double) mushroomConfig.get("x");
                    y = (double) mushroomConfig.get("y");
                } catch (NullPointerException e) {
                    x = 0.0;
                    y = this.floorHeight - size;
                }

                Mushroom mushroom = new Mushroom("mushroom.png", x, y, size);
                this.entities.add(mushroom);
            }
            return this;
        }

        @Override
        public LevelBuilder addFlag() {
            JSONObject flagConfig = this.configFile.getFlagConfig();

            if (flagConfig == null) {
                throw new NullPointerException("Flag configuration should be specified");
            }
            if (flagConfig.get("x") == null || flagConfig.get("y") == null) {
                throw new IllegalArgumentException("Flag position should be specified");
            }
            double x = (double) flagConfig.get("x");
            double y = (double) flagConfig.get("y");

            Flag flag = new Flag("flag.png", x, y);
            this.entities.add(flag);

            return this;
        }

        @Override
        public LevelImpl build() {
            LevelImpl level = new LevelImpl(this);
            return level;
        }
    }
