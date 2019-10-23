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

    public void submit(T item){
        System.out.println("*********************开始发布元素*********************");
        list.forEach(e->{
            e.future = executor.submit(()->{
                e.subscriber.onNext(item);
            });
        });
    }

    static class DockerXDemoSubscription<T> implements Flow.Subscription{
        private final Flow.Subscriber<? super T> subscriber;
        private final  ExecutorService executor;
        private Future<?> future;
        private T item;
        private boolean completed;

        DockerXDemoSubscription(Flow.Subscriber<? super T> subscriber, ExecutorService executor) {
            this.subscriber = subscriber;
            this.executor = executor;
        }


        @Override
        public void request(long n) {
            if(n!=0 && !completed){
                if(n<0){
                    IllegalArgumentException ex = new IllegalArgumentException();
                    executor.execute(()->subscriber.onError(ex));
                } else {
                    future = executor.submit(()->{
                       subscriber.onNext(item);
                    });
                }
            } else {
                subscriber.onComplete();
            }
        }

        @Override
        public void cancel() {
            completed = true;
            if(future !=null && !future.isCancelled()){
                this.future.cancel(true);
            }
        }
    }

    private final ExecutorService executor;
    private CopyOnWriteArrayList<DockerXDemoSubscription> list = new CopyOnWriteArrayList<>();


    @Override
    public void close() throws Exception {
        list.forEach(e->{
            e.future = executor.submit(()->{
                e.subscriber.onComplete();
            });
        });
    }

    @Override
    public void subscribe(Flow.Subscriber<? super T> subscriber) {
        subscriber.onSubscribe(new DockerXDemoSubscription<>(subscriber,executor));
        list.add(new DockerXDemoSubscription(subscriber,executor));
    }
}
