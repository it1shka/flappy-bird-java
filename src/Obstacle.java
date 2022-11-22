public class Obstacle {

    private double xPosition;
    private final int yPosition;
    private final int halfHeight;

    public Obstacle(double centerX, int centerY, int halfHeight) {
        xPosition = centerX;
        yPosition = centerY;
        this.halfHeight = halfHeight;
    }

    public int getX() {
        // return (int) Math.round(xPosition);
        return (int) xPosition;
    }

    public int getUpperY() {
        return yPosition - halfHeight;
    }

    public int getLowerY() {
        return yPosition + halfHeight;
    }

    public void update(double delta, double speed) {
        xPosition -= speed * delta;
    }

}
