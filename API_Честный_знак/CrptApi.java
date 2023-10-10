import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class CrptApi {
    private final int requestLimit;
    private final long timeInterval;
    private final ReentrantLock lock;
    private int requestCount;
    private long lastRequestTime;

    public CrptApi(TimeUnit timeUnit, int requestLimit) {
        this.requestLimit = requestLimit;
        this.timeInterval = timeUnit.toMillis(1);
        this.lock = new ReentrantLock();
        this.requestCount = 0;
        this.lastRequestTime = System.currentTimeMillis();
    }

    public void createDocument(Object document, String signature) {
        lock.lock();
        try {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastRequestTime >= timeInterval) {
                requestCount = 0;
                lastRequestTime = currentTime;
            }
            if (requestCount >= requestLimit) {
                long sleepTime = timeInterval - (currentTime - lastRequestTime);
                if (sleepTime > 0) {
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                requestCount = 0;
                lastRequestTime = System.currentTimeMillis();
            }
            // Perform the API request here
            System.out.println("Document created: " + document);
            System.out.println("Signature: " + signature);
            requestCount++;
        } finally {
            lock.unlock();
        }
    }
}
