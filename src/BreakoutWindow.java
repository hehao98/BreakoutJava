import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class BreakoutWindow extends JFrame {

    GamePanel panel;
    
    BreakoutGame game;

    Image background;

    /**
     * Create the frame.
     */
    public BreakoutWindow() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 620);

        try {
            background = ImageIO.read(new File("resource/background.jpg"));
        } catch (IOException exec) {
            exec.printStackTrace();
        }
        
        panel = new GamePanel();
        setContentPane(panel);
        panel.setDoubleBuffered(true);
        panel.setFocusable(true);
        panel.requestFocus();

        game = new BreakoutGame(this);

        panel.addKeyListener(new GameController());
        panel.addMouseMotionListener(new MouseController());
    }

    // The Main Game Loop
    public class ScheduleTask extends TimerTask {
        double lastUpdatedTime;

        @Override
        public void run() {
            double currentTime = new Date().getTime();

            // Update game states
            game.update(currentTime - lastUpdatedTime);

            // Update graphics
            panel.repaint();

            lastUpdatedTime = currentTime;
        }
    }

    class GamePanel extends JPanel {

        // Render according to data in the game
        @Override
        public void paint(Graphics g) {
            g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            // Paint blocks
            for (int x = 0; x < game.column; ++x) {
                for (int y = 0; y < game.row; ++y) {
                    Block b = game.blocks[y][x];
                    if (b.type == Block.NO_BLOCK) continue;
                    g.drawImage(b.getImage(), (int)b.posX, (int)b.posY, (int)b.width, (int)b.height, this);
                }
            }

            // Paint player
            Paddle p = game.player;
            g.drawImage(p.image, (int)p.posX, (int)p.posY, (int)p.width, (int)p.height, this);

            // Paint ball
            Ball ball = game.ball;
            g.drawImage(ball.image,
                    (int)(ball.posX - ball.radius),
                    (int)(ball.posY - ball.radius),
                    (int)ball.radius * 2,
                    (int)ball.radius * 2, this);

            g.setColor(Color.WHITE);
            if (game.state == BreakoutGame.STOPPED) {
                g.drawString("press enter to start", 50, 50);
            } else {
                g.drawString("Score: " + game.score, 50, 50);
            }
        }
    }

    class GameController extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_ENTER:
                    game.start();
                    break;
            }
        }
    }

    class MouseController extends MouseAdapter {
        @Override
        public void mouseMoved(MouseEvent e) {
            if (game.state == game.STARTED)
                game.player.posX = e.getX();
        }
    }

}
