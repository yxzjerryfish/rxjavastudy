package fish.reactivejava.study;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Flow;
import java.util.concurrent.Future;

/**
 * Publisher
 *
 * @author : Fish Paradise
 * @version 1.0
 * @date : 2019/10/22 21:48
 */
public class DockerXDemoPublisher<T> implements Flow.Publisher<T>,AutoCloseable {
    public DockerXDemoPublisher(ExecutorService executor) {
        this.executor = executor;
    }

    static class DockerXDemoSubscription<T> implements Flow.Subscription{
        private final Flow.Subscriber<? super T> subscriber;
        private final  ExecutorService executor;
        private Future<T> future;
        private T item;
        private boolean completed;

        DockerXDemoSubscription(Flow.Subscriber<? super T> subscriber, ExecutorService executor) {
            this.subscriber = subscriber;
            this.executor = executor;
        }


        @Override
        public void request(long n) {

        }

        @Override
        public void cancel() {

        }
    }

    private final ExecutorService executor;
    private CopyOnWriteArrayList<DockerXDemoSubscription> list = new CopyOnWriteArrayList<>();


    @Override
    public void close() throws Exception {

    }

    @Override
    public void subscribe(Flow.Subscriber<? super T> subscriber) {

    }
}
