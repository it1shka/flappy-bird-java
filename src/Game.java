import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

public class Game extends Canvas {

    private static final int displaySize = 400;

    public static void main(String[] args) {
        final var window = new JFrame("Flappy bird");
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        final var game = new Game();
        game.setSize(displaySize, displaySize);
        window.add(game);
        window.pack();
        window.setVisible(true);

        game.startGame();
    }

    private static final int deltaTime = 20;
    private static final double delta = (double)deltaTime / 1000.0;
    private final Bird bird;
    private final ObstacleCreator obstacleCreator;
    private final LinkedList<Obstacle> obstacles;
    private static final double obstacleDeltaTime = 1000.0;
    private double currentObstacleTime;

    public Game() {
        bird = new Bird(10, 20, (double)displaySize / 2.0);
        obstacleCreator = new ObstacleCreator(displaySize, displaySize, 30, 150);
        obstacles = new LinkedList<>();
        currentObstacleTime = obstacleDeltaTime;
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() != 32) return;
                bird.jump(250);
            }
        });
        requestFocusInWindow();
    }

    public void startGame() {
        obstacles.add(obstacleCreator.create());
        while (true) {
            try {
                bird.update(delta);
                updateObstacles();
                checkDeath();
                repaint();
                Thread.sleep(deltaTime);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void updateObstacles() {
        obstacles.forEach(e -> e.update(delta, 180));
        obstacles.removeIf(e -> e.getX() < 0);
        currentObstacleTime -= deltaTime;
        if (currentObstacleTime < 0) {
            obstacles.add(obstacleCreator.create());
            currentObstacleTime = obstacleDeltaTime;
        }
    }

    private void checkDeath() {
        final var x = bird.getX();
        final var y = bird.getY();

        if (y < 0 || y > displaySize) {
            resetGame();
            return;
        }

        final var size = bird.getSize();

        for (final var obstacle: obstacles) {
            final var obstacleX = obstacle.getX();
            if (Math.abs(x - obstacleX) > obstacleWidth) continue;
            if (y - size <= obstacle.getUpperY() || y + size >= obstacle.getLowerY()) {
                resetGame();
                return;
            }
        }
    }

    private void resetGame() {
        bird.reset();
        currentObstacleTime = obstacleDeltaTime;
        obstacles.clear();
    }

    private static final int obstacleWidth = 5;
    @Override public void paint(Graphics graphics) {
        graphics.setColor(Color.blue);
        final var diameter = bird.getSize() * 2;
        graphics.fillOval(bird.getX(), bird.getY(), diameter, diameter);
        graphics.setColor(Color.black);
        for (final var obstacle: obstacles) {
            final var x = obstacle.getX();
            final var upperY = obstacle.getUpperY();
            final var lowerY = obstacle.getLowerY();
            graphics.fillRect(x - obstacleWidth, 0, obstacleWidth * 2, upperY);
            graphics.fillRect(x - obstacleWidth, lowerY, obstacleWidth * 2, displaySize - lowerY);
        }
    }

}
