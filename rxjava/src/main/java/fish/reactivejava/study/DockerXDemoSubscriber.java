package fish.reactivejava.study;

import java.util.concurrent.Flow;

/**
 * 自己的Flow类
 *
 * @author : Fish Paradise
 * @version 1.0
 * @date : 2019/10/21 22:17
 */
public class DockerXDemoSubscriber<T> implements Flow.Subscriber {

    private String name;
    private Flow.Subscription subscription;
    final long buffsize;
    long count;

    public String getName() {
        return name;
    }

    public Flow.Subscription getSubscriber() {
        return subscription;
    }

    public DockerXDemoSubscriber(String name, long buffsize) {
        this.name = name;
        this.buffsize = buffsize;
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        (this.subscription = subscription).request(buffsize);
        System.out.println("开始onSubsciribe 订阅；");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onNext(Object item) {
        System.out.println("####" + Thread.currentThread().getName()+ " name: "+name + " item: " +item + "####");
        System.out.println(name + " recived: " +item);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onComplete() {
        System.out.println("onComplete !");
    }
}
