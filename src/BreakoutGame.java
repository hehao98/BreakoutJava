/**
 * A clone of the classic Atari breakout game
 * This is the main game class
 * 
 * @author hehao
 *
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class BreakoutGame {
    
    // Game state enums
    static final int STOPPED = 0;
    static final int STARTED = 1;

    // The application window
    BreakoutWindow frame;
    int panelWidth, panelHeight;
    int row,  column;
    
    // The Core Game
    Block[][] blocks;
    Paddle player;
    Ball ball;
    int state = STOPPED;
    int score = 0;
    
    public BreakoutGame(BreakoutWindow frame) {
        this.frame = frame;
        this.panelWidth = 800;
        this.panelHeight = 600;

        init();
    }

    public void init() {
        // Read level data from txt file
        File file = new File("level.txt");
        try {
            Scanner scanner = new Scanner(file);

            row = scanner.nextInt();
            column = scanner.nextInt();
            blocks = new Block[row][column];
            for (int i = 0; i < row; ++i) {
                for (int j = 0; j < column; ++j) {
                    int blockType = scanner.nextInt();
                    blocks[i][j] = new Block(
                            blockType, i, j, this.row, this.column, panelWidth, panelHeight);
                }
            }

            scanner.close();
        } catch (FileNotFoundException exec) {
            exec.printStackTrace();
        }

        // init player
        double playerWidth = 100;
        double playerHeight = 20;
        this.player = new Paddle(
                panelWidth / 2 - playerWidth / 2,
                panelHeight - playerHeight,
                playerWidth,
                playerHeight);

        // init ball
        double radius = 20;
        double ballPosX = panelWidth / 2;
        double ballPosY = player.posY - radius;
        this.ball = new Ball(ballPosX, ballPosY, radius);
    }

    public void start() {
        if (state == STARTED) return;

        state = STARTED;
        score = 0;
        ball.setRandomUpVelocity();
    }

    public void update(double deltaTime) {
        ball.update(deltaTime);

        // Update ball's velocity if it hits border
        checkBorder();

        Physics.CollisionInfo cinfo;

        cinfo = Physics.checkCollision(player, ball);
        if (cinfo.hasCollision) {
            ball.resolveCollision(cinfo);
        }

        for (int i = 0; i < row; ++i) {
            for (int j = 0; j < column; ++j) {
                if (blocks[i][j].type == Block.NO_BLOCK)
                    continue;
                cinfo = Physics.checkCollision(blocks[i][j], ball);
                if (cinfo.hasCollision) {
                    ball.resolveCollision(cinfo);
                    blocks[i][j].type = 0;
                    score += blocks[i][j].getScore();
                }
            }
        }

        if (checkGameOver()) {
            this.state = STOPPED;
            this.init();
        }
    }

    public void checkBorder() {
        // Check whether the ball is hitting edge
        if (ball.posX - ball.radius <= 0) {
            ball.velocityX = -ball.velocityX;
            ball.posX = ball.radius;
        }
        if (ball.posX + ball.radius >= panelWidth) {
            ball.velocityX = -ball.velocityX;
            ball.posX = panelWidth - ball.radius;
        }
        if (ball.posY - ball.radius <= 0) {
            ball.velocityY = -ball.velocityY;
            ball.posY = ball.radius;
        }
    }

    public boolean checkGameOver() {
        return ball.posY > this.panelHeight;
    }
}
