import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * The class that defines a block in the game
 *
 * @author hehao
 */

public class Block implements Physics.BoxCollider {
    // Block type enums
    static final int NO_BLOCK    = 0;
    static final int RED_BLOCK   = 1;
    static final int GREEN_BLOCK = 2;
    static final int BLUE_BLOCK  = 3;
    static final int SOLID_BLOCK = 4;
    static final int CYAN_BLOCK  = 5;
    static final int YELLOW_BLOCK = 6;
    static final int PURPLE_BLOCK = 7;
    static final String[] IMAGE_PATH = new String[] {
            "",
            "resource/block-red.png",
            "resource/block-green.png",
            "resource/block-blue.png",
            "resource/block-solid.png",
            "resource/block-cyan.png",
            "resource/block-yellow.png",
            "resource/block-purple.png",
    };
    static Image[] images = new Image[10];

    int type;
    int row, column;
    double width, height;
    double posX, posY;

    static {
        try {
            images[RED_BLOCK]    = ImageIO.read(new File(IMAGE_PATH[RED_BLOCK]));
            images[GREEN_BLOCK]  = ImageIO.read(new File(IMAGE_PATH[GREEN_BLOCK]));
            images[BLUE_BLOCK]   = ImageIO.read(new File(IMAGE_PATH[BLUE_BLOCK]));
            images[SOLID_BLOCK]  = ImageIO.read(new File(IMAGE_PATH[SOLID_BLOCK]));
            images[CYAN_BLOCK]   = ImageIO.read(new File(IMAGE_PATH[CYAN_BLOCK]));
            images[YELLOW_BLOCK] = ImageIO.read(new File(IMAGE_PATH[YELLOW_BLOCK]));
            images[PURPLE_BLOCK] = ImageIO.read(new File(IMAGE_PATH[PURPLE_BLOCK]));
        } catch (IOException exec) {
            exec.printStackTrace();
        }
    }

    public Block(int type, int row, int column,
                 int panelRow, int panelColumn, int panelWidth, int panelHeight) {
        this.type = type;
        this.row = row;
        this.column = column;
        this.width = panelWidth / panelColumn;
        this.height = panelHeight / panelRow;
        this.posX = this.width * this.column;
        this.posY = this.height * this.row;
    }

    public Image getImage() {
        return images[this.type];
    }

    public int getScore() {
        return 1;
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
