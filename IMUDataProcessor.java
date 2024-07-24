import java.util.List;

public class IMUDataProcessor implements Processor<IMUData> {
    private static final double NOISE_THRESHOLD = 0.1;// threshold for noise filtering
    private final Database database;

    public IMUDataProcessor(Database database) {
        this.database = database;
    }
    @Override
    public void process(List<IMUData> batch) {
        for (IMUData data : batch) {
            IMUData filteredData = filterNoise(data);
           this.database.insert(filteredData);
        }
    }
    private IMUData filterNoise(IMUData data) {
        if( Math.abs(data.getAccX()) < NOISE_THRESHOLD &&
            Math.abs(data.getAccY()) < NOISE_THRESHOLD &&
            Math.abs(data.getAccZ()) < NOISE_THRESHOLD) {
            return null;
        }
        return data;
    }
}
