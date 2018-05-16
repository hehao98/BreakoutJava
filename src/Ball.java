/**
 * Player will control the bouncing ball in game
 *
 * @author hehao
 */

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Ball implements Physics.CircleCollider {
    double posX, posY;
    double radius;
    double velocityX, velocityY;

    Image image;

    public Ball(double posX, double posY, double radius) {
        this.posX = posX;
        this.posY = posY;
        this.radius = radius;

        try {
            image = ImageIO.read(new File("resource/awesomeface.png"));
        } catch (IOException exec) {
            exec.printStackTrace();
        }
    }

    public void update(double deltaTime) {
        posX += deltaTime * velocityX;
        posY += deltaTime * velocityY;
    }

    public void setRandomUpVelocity() {
        velocityY = -0.15 * Math.random() - 0.15;
        velocityX = Math.sqrt(0.3*0.3 - velocityY*velocityY);
    }

    public void resolveCollision(Physics.CollisionInfo info) {
        if (!info.hasCollision) return;

        this.posX -= info.penetrationX;
        this.posY -= info.penetrationY;

        if (info.direction == Physics.DOWN || info.direction == Physics.UP) {
            this.velocityY = -this.velocityY;
        }
        if (info.direction == Physics.LEFT || info.direction == Physics.RIGHT) {
            this.velocityX = -this.velocityX;
        }
    }

    @Override
    public double getCenterX() {
        return posX;
    }

    @Override
    public double getCenterY() {
        return posY;
    }

    @Override
    public double getRadius() {
        return radius;
    }
}
