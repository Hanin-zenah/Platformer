package stickman.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import javafx.scene.text.Text;

import stickman.model.Entity;
import stickman.model.GameEngine;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class GameWindow {
    private final int width;
    private final int height;
    private Scene scene;
    private Pane pane;
    private GameEngine model;
    private List<EntityView> entityViews;
    private BackgroundDrawer backgroundDrawer;

    private double xViewportOffset = 0.0;
    private static final double VIEWPORT_MARGIN = 280.0;

    private double yViewportOffset = 0.0;
    private static double VIEWPORT_MARGIN_Y = 240.0;

    private Text time;
    private Text lives;
    private int tick = 0;
    private Timeline timeline;
    private KeyboardInputHandler keyboardInputHandler;


    public GameWindow(GameEngine model, int width, int height) {
        this.model = model;
        this.pane = new Pane();
        this.width = width;
        this.height = height;
        this.scene = new Scene(pane, width, height);
        time = new Text();
        time.setFont(Font.font(null, FontWeight.BOLD, 15));
        time.setFill(Color.BLACK);
        time.setTranslateX(0.0);
        time.setTranslateY(15.0);
        time.setViewOrder(0.0);
        pane.getChildren().add(time);

        lives = new Text();
        lives.setFill(Color.BLACK);
        lives.setTranslateX(width - 50.0);
        lives.setTranslateY(15.0);
        lives.setViewOrder(0.0);
        pane.getChildren().add(lives);

        this.entityViews = new ArrayList<>();

        keyboardInputHandler = new KeyboardInputHandler(model);

        scene.setOnKeyPressed(keyboardInputHandler::handlePressed);
        scene.setOnKeyReleased(keyboardInputHandler::handleReleased);

        this.backgroundDrawer = new BlockedBackground();

        backgroundDrawer.draw(model, pane);
    }

    public Scene getScene() {
        return this.scene;
    }

    public void run() {
        timeline = new Timeline(new KeyFrame(Duration.millis(17),
                t -> this.draw()));
        Timeline t2 = new Timeline(new KeyFrame(Duration.seconds(0.38), t -> this.animate()));

        timeline.setCycleCount(Timeline.INDEFINITE);
        t2.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        t2.play();
    }

    private void animate() {
        //this will take care of all the animation without having to keep a counter for every single class
        model.animate();
    }

    private String getTimeNow() {
        DecimalFormat timer = new DecimalFormat("#.00");
        String format = timer.format(tick * 0.017);
        return format;
    }

    private void setTime() {
        String format = getTimeNow();
        time.setText("Elapsed time: " + format);
    }

    private void setLives() {
        lives.setText("Lives: " + model.getCurrentLevel().getHero().getLives());
    }

    private void setGameOverText(String s) {
        //make all objects on screen invisible
        for (Node n : pane.getChildren()) {
            n.setOpacity(0);
        }

        for(Entity e: this.model.getCurrentLevel().getEntities()) {
            e.delete();
        }

        Text gameOverText = new Text(s);
        gameOverText.setFill(Color.BLACK);
        gameOverText.setFont(Font.font(30));
        gameOverText.setViewOrder(0.0);
        gameOverText.setX(scene.getWidth() / 3);
        gameOverText.setY(scene.getHeight() / 3);
        pane.getChildren().add(gameOverText);
        timeline.stop();
    }

    private void draw() {
        model.tick();

        if(model.getCurrentLevel().getHero().won()) {
            //end game and print winning screen
            String timeNow = getTimeNow();
            int livesNow = model.getCurrentLevel().getHero().getLives();
            String message = String.format("%s%s%s%d%s", "Congratulations!\n\nYou won in ", timeNow, "s\n\nand had ", livesNow, " lives.");
            setGameOverText(message);
            return;
        } else if(model.getCurrentLevel().getHero().lost()) {
            //play losing sound? and print losing screen
            String message = "Game Over!\n\n\t :(";
            setGameOverText(message);
            return;
        }

        tick++;

        setLives();
        setTime();

        List<Entity> entities = model.getCurrentLevel().getEntities();

        for (EntityView entityView: entityViews) {
            entityView.markForDelete();
        }

        double heroXPos = model.getCurrentLevel().getHeroX();
        heroXPos -= xViewportOffset;

        double heroYPos = model.getCurrentLevel().getHero().getYPos();
        heroYPos += yViewportOffset;

        if (heroXPos < VIEWPORT_MARGIN) {
            if (xViewportOffset >= 0) { // Don't go further left than the start of the level
                xViewportOffset -= VIEWPORT_MARGIN - heroXPos;
                if (xViewportOffset < 0) {
                    xViewportOffset = 0;
                }
            }
        } else if (heroXPos > width - VIEWPORT_MARGIN) {
            xViewportOffset += heroXPos - (width - VIEWPORT_MARGIN);
        }

        if (heroYPos > VIEWPORT_MARGIN_Y) {
            if (yViewportOffset >= 0) { // Don't go further down than bottom of the level screen
                yViewportOffset -= heroYPos - VIEWPORT_MARGIN_Y;
                if (yViewportOffset < 0) {
                    yViewportOffset = 0;
                }
            }
        } else if (heroYPos < height - VIEWPORT_MARGIN_Y) {
            yViewportOffset += (height - VIEWPORT_MARGIN_Y) - heroYPos;
        }

        backgroundDrawer.update(xViewportOffset, yViewportOffset);

        for (Entity entity: entities) {
            boolean notFound = true;
            for (EntityView view: entityViews) {
                if (view.matchesEntity(entity)) {
                    notFound = false;
                    view.update(xViewportOffset, yViewportOffset);
                    break;
                }
            }
            if (notFound) {
                EntityView entityView = new EntityViewImpl(entity);
                entityViews.add(entityView);
                pane.getChildren().add(entityView.getNode());
            }
        }

        for (EntityView entityView: entityViews) {
            if (entityView.isMarkedForDelete()) {
                pane.getChildren().remove(entityView.getNode());
            }
        }
        entityViews.removeIf(EntityView::isMarkedForDelete);
    }
}
