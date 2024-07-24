public class Main {
    public static void main(String[] args) {
        final int BATCHSIZE = 5;
        final int MAXWAITTIME = 1000;
        Database mockDatabase = new MockDB();
        IMUDataProcessor imuProcessor = new IMUDataProcessor(mockDatabase);
        BatchProcessor<IMUData> batchProcessor = new BatchProcessor<>(BATCHSIZE, MAXWAITTIME, imuProcessor);

        for (int i = 0; i < 5; i++) { // 5 concurrent user calls
            new Thread(() -> {
                for (int j = 0; j < 20; j++) { // 20 submissions
                    batchProcessor.load(new IMUData(Math.random(), Math.random(), Math.random()));
                    try {
                        Thread.sleep(50); // give a little time between submissions
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }).start();
        }

        try {
            Thread.sleep(5000); // Allow some time for processing before shutdown
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        batchProcessor.shutdown();
    }
}
