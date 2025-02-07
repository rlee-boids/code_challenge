public class IMUData {
    private double accX;
    private double accY;
    private double accZ;

    public IMUData(double accX, double accY, double accZ) {
        this.accX = accX;
        this.accY = accY;
        this.accZ = accZ;
    }

    public double getAccX() {
        return accX;
    }

    public void setAccX(double accX) {
        this.accX = accX;
    }

    public double getAccY() {
        return accY;
    }

    public void setAccY(double accY) {
        this.accY = accY;
    }

    public double getAccZ() {
        return accZ;
    }

    public void setAccZ(double accZ) {
        this.accZ = accZ;
    }
@Override
    public String toString() {
        return "IMUData{" +
                "accX=" + accX +
                ", accY=" + accY +
                ", accZ=" + accZ +
                '}';
    }
}
