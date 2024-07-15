package threadsafety.monitorvehicletracker;

//@NotThreadSafe
public class MutablePoint {
    public int x, y;
    public MutablePoint() { x = 0; y = 0; }
    public MutablePoint (MutablePoint p) {
        this.x = p.x;
        this.y = p.y;
    }

    public MutablePoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }
}
