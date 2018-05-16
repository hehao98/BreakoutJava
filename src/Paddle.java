import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.Image;

/**
 * Paddle is the platform that player controls at the bottom
 *
 * @author hehao
 */

public class Paddle implements Physics.BoxCollider {

    double posX, posY;
    double width, height;
    Image image;

    Paddle(double posX, double posY, double width, double height) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        try {
            image = ImageIO.read(new File("resource/paddle.png"));
        } catch (IOException exec) {
            exec.printStackTrace();
        }
    }

    @Override
    public double getCenterX() {
        return posX + width / 2;
    }

    @Override
    public double getCenterY() {
        return posY + height / 2;
    }

    @Override
    public double getHalfWidth() {
        return width / 2;
    }

    @Override
    public double getHalfHeight() {
        return height / 2;
    }
}
