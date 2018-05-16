/**
 * This is the entry and structure of the whole program
 * 
 * @author hehao
 */

import java.awt.EventQueue;
import java.util.Timer;

public class Main {

    static BreakoutWindow window;

    public static void main(String[] args) {
        EventQueue.invokeLater(()->{
            // Init window
            window = new BreakoutWindow();

            // Show window
            window.setVisible(true);

            // Set up game loop
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(window.new ScheduleTask(), 100, 10);
        });
    }

}