package fish.reactivejava.study;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * 响应式测试demo
 *
 * @author : Fish Paradise
 * @version 1.0
 * @date : 2019/10/23 21:52
 */
public class DemoSubcribe {
    public static void demoSubscribe(DockerXDemoPublisher<Integer> publisher,String subcriberName) {
        DockerXDemoSubscriber<Integer> subscriber = new DockerXDemoSubscriber<>(4L,subcriberName);
        publisher.subscribe(subscriber);
    }

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = ForkJoinPool.commonPool();
        try(DockerXDemoPublisher<Integer> publisher = new DockerXDemoPublisher<>(executorService)){
            demoSubscribe(publisher,"One");
            demoSubscribe(publisher,"Two");
            demoSubscribe(publisher,"Three");
            IntStream.range(1,5).forEach(publisher::submit);
        } finally {
            try{
                executorService.shutdown();
                int shutdownDelaySec =1;
                System.out.println("..................等待"+shutdownDelaySec+"秒后结束服务");
                executorService.awaitTermination(shutdownDelaySec, TimeUnit.SECONDS);
            } catch (Exception ex){
                System.out.println("捕获到 executorService.awaitTermination() 方法的异常"+ex.getClass().getName());
            } finally {
                System.out.println("调用 awaitTermination。shutdownNow()结束服务...");
                List<Runnable> l = executorService.shutdownNow();
                System.out.println("还剩 "+ l.size() + "个任务等待执行，服务已关闭");
            }
        }
    }
}
