package stickman.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;
import java.util.Random;

public class LevelImpl implements Level {
    private List<Entity> entities;
    private double height;
    private double width;
    private double floorHeight;
    private Stickman hero;
    private JSONObject cloudConfig;

    private int counter = 0;

    public LevelImpl(NormalLevelBuilder builder) {
        this.height = builder.getHeight();
        this.width = builder.getWidth();
        this.entities = builder.getEntities();
        this.floorHeight = builder.getFloorHeight();
        this.hero = builder.getHero();
        this.cloudConfig = builder.getCloudConfig();
    }

    @Override
    public List<Entity> getEntities() {
        return this.entities;
    }

    @Override
    public double getHeight() {
        return this.height;
    }

    @Override
    public double getWidth() {
        return this.width;
    }

    public boolean heroShoot() {
        //if hero can shoot
        if (this.hero.canShoot()) {
            Bullet bullet = new Bullet(hero.getXPos(), hero.getYPos() + this.hero.getHeight() / 1.5, hero.isFacingRight());
            this.entities.add(bullet);
            return true;
        }
        return false;
    }

    @Override
    public double getFloorHeight() {
        return this.floorHeight;
    }

    @Override
    public double getHeroX() {
        return this.hero.getXPos();
    }

    @Override
    public boolean jump() {
        return this.hero.jump();
    }

    @Override
    public boolean moveLeft() {
        return this.hero.moveLeft();
    }

    @Override
    public boolean moveRight() {
        return this.hero.moveRight();
    }

    @Override
    public boolean stopMoving() {
        return this.hero.stopMoving();
    }

    @Override
    public Stickman getHero() {
        return this.hero;
    }


    @Override
    public void tick() {
        counter++;
        for (Entity entity : this.entities) {
            //tick every entity
            entity.tick();
        }
        //check for collision here
        for (Entity entity : this.entities) {
            for (Entity other : this.entities) {
                if (entity.checkCollision(other)) {
                    entity.handleCollision(other);
                }
            }
        }

        this.entities.removeIf(Entity::isDeleted);

        //add clouds every 4000 ticks
        if (counter % 4000 == 0) {
            counter = 0;
            addClouds();
        }

    }

    @Override
    public void animate() {
        for (Entity entity : this.entities) {
            entity.animate();
        }
    }

    public void addClouds() {
        JSONArray cloudVel = (JSONArray) cloudConfig.get("velocity");
        JSONArray height = (JSONArray) cloudConfig.get("height");
        JSONArray width = (JSONArray) cloudConfig.get("width");
        Random random = new Random();
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
}