import java.util.Random;

public class ObstacleCreator {

    private static final Random random = new Random();
    private final int spawnX;
    private final int halfHeight;
    private final int maxHeight;
    private final int minHeight;
    private final int deltaHeight;
    private int lastY;

    public ObstacleCreator(int windowSize, int initialX, int halfHeight, int deltaHeight) {
        spawnX = initialX;
        this.halfHeight = halfHeight;
        maxHeight = halfHeight;
        minHeight = windowSize - halfHeight;
        this.deltaHeight = deltaHeight;
        lastY = windowSize / 2;
    }

    public Obstacle create() {
        final var max = Math.max(lastY - deltaHeight, maxHeight);
        final var min = Math.min(lastY + deltaHeight, minHeight);
        final var nextY = random.nextInt((min - max) + 1) + max;
        final var obstacle = new Obstacle(spawnX, nextY, halfHeight);
        lastY = nextY;
        return obstacle;
    }

}
