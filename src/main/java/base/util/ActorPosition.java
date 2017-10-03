package util;

public class ActorPosition {

    private String actor;

    private int x;

    private int y;

    public ActorPosition() {

    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "ActorPosition{" +
                "actor='" + actor + '\'' +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
