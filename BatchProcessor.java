import java.util.LinkedList;
import java.util.List;

public class BatchProcessor<T> {
    private final LinkedList<T> queue;
    private final int batchSize;
    private final int maxWaitTime;
    private final Processor<T> processor;
    private final Object lock = new Object();
    private boolean running = true;

    public BatchProcessor(int batchSize, int maxWaitTime, Processor<T> processor) {
        this.queue = new LinkedList<>();
        this.batchSize = batchSize;
        this.maxWaitTime = maxWaitTime;
        this.processor = processor;

        Thread processorThread = new Thread(this::processBatches); 
        processorThread.start();// Start the processBatches thread
    }


    private void processBatches() {
        while (running) {
            List<T> batchQ = new LinkedList<>();
            synchronized (lock) { // build batchQ
                long startTime = System.currentTimeMillis();
                while (queue.size() < batchSize && maxWaitTime > (System.currentTimeMillis() - startTime)) {
                    try {
                        long waitTime = maxWaitTime - (System.currentTimeMillis() - startTime);
                        if (waitTime > 0) {
                            lock.wait(waitTime); // Wait until batchQ builds up to batchSize or maxWaitTime has reached(make sure you don't block indefinitly)
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                if (!queue.isEmpty()) {
                    int itemsToProcess = Math.min(batchSize, queue.size());
                    for (int i = 0; i < itemsToProcess; i++) {
                        batchQ.add(queue.removeFirst()); 
                    }
                }
            }
            if (!batchQ.isEmpty()) {
                processor.process(batchQ); // start process collected items
            }
        }
    }


    public void load(T item) {
        synchronized (lock) {
            queue.addLast(item);
            lock.notify();
        }
    }

    public void shutdown() {
        running = false;
        synchronized (lock) {
            lock.notify();
        }
    }
}
