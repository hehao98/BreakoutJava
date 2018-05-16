

public class Physics {

    static final int UP    = 0;
    static final int DOWN  = 1;
    static final int LEFT  = 2;
    static final int RIGHT = 3;

    public interface BoxCollider {

        double getCenterX();

        double getCenterY();

        double getHalfWidth();

        double getHalfHeight();

    }

    public interface CircleCollider {

        double getCenterX();

        double getCenterY();

        double getRadius();

    }

    public static class CollisionInfo {
        boolean hasCollision;
        double penetrationX;
        double penetrationY;
        int direction;
    }

    static CollisionInfo checkCollision(BoxCollider staticObject, CircleCollider movingObject) {
        // static box info
        double c1x = staticObject.getCenterX();
        double c1y = staticObject.getCenterY();
        double hw  = staticObject.getHalfWidth();
        double hh  = staticObject.getHalfHeight();
        // moving sphere info
        double c2x = movingObject.getCenterX();
        double c2y = movingObject.getCenterY();
        double r   = movingObject.getRadius();

        CollisionInfo info = new CollisionInfo();

        // Early pruning
        if (Math.abs(c1x - c2x) > hw + r || Math.abs(c1y - c2y) > hh + r) {
            info.hasCollision = false;
            return info;
        }

        // Check collision
        // The diff vector
        double diffX = c2x - c1x;
        double diffY = c2y - c1y;
        // Clamp the diff vector
        double clampedX = clamp(diffX, -hw, hw);
        double clampedY = clamp(diffY, -hh, hh);
        // Acquire closest point coordinate
        double closestX = c1x + clampedX;
        double closestY = c1y + clampedY;

        double length = Math.sqrt((closestX - c2x) * (closestX - c2x) + (closestY - c2y) * (closestY - c2y));
        if (length > r) { // No hit
            info.hasCollision = false;
            return info;
        }

        // Collision happens here
        info.hasCollision = true;
        if (clampedY == hh) {
            info.direction = DOWN;
            info.penetrationY = length - r;
        } else if (clampedY == -hh) {
            info.direction = UP;
            info.penetrationY = r - length;
        } else if (clampedX == hw) {
            info.direction = RIGHT;
            info.penetrationX = length - r;
        } else {
            info.direction = LEFT;
            info.penetrationX = r - length;
        }
        return info;
    }

    static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

}
