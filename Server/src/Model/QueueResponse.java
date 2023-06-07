package Model;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

public class QueueResponse {
    private final Queue<String> action = new ConcurrentLinkedDeque<>();
    private final Queue<Object> object = new ConcurrentLinkedDeque<>();

    public synchronized void enqueueData(String action, Object object) {
        this.action.offer(action);
        this.object.offer(object);
        notify();
    }

    public synchronized String dequeueAction() throws InterruptedException {
        while (action.isEmpty()) {
            wait();
        }
        return action.poll();
    }

    public synchronized Object dequeueObject() throws InterruptedException {
        while (object.isEmpty()) {
            wait();
        }
        return object.poll();
    }

}
