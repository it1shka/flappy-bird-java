public class Bird {

    private final int size;
    private final int positionX;
    private static final double gravity = 800;
    private final double initialY;
    private double positionY;
    private double velocity;

    public Bird(int size, int x, double y) {
        this.size = size;
        positionX = x;
        initialY = y;
        positionY = y;
        velocity = 0.0;
    }

    public void reset() {
        positionY = initialY;
        velocity = 0.0;
    }

    public int getSize() {
        return size;
    }

    public int getX() {
        return positionX;
    }

    public int getY() {
        return (int) Math.round(positionY);
    }

    public void update(double delta) {
        velocity += gravity * delta;
        positionY += velocity * delta;
    }

    public void jump(double impulse) {
        velocity = -impulse;
    }

}
