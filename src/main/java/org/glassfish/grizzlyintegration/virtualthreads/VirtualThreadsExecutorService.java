/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.glassfish.grizzlyintegration.virtualthreads;

import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import org.glassfish.grizzly.config.ConfigAwareElement;
import org.glassfish.grizzly.config.dom.NetworkListener;
import org.glassfish.grizzly.config.dom.ThreadPool;
import org.glassfish.hk2.api.ServiceLocator;

/**
 *
 * @author Ondro Mihalyi
 */
public class VirtualThreadsExecutorService extends AbstractExecutorService // TODO        implements MonitoringAware<ThreadPoolProbe>
        implements ConfigAwareElement<ThreadPool> {

    VirtualThreadFactory threadFactory = new VirtualThreadFactory();
    ExecutorService pool = Executors.newThreadPerTaskExecutor(threadFactory);

    @Override
    public void shutdown() {
        pool.shutdown();
    }

    @Override
    public List<Runnable> shutdownNow() {
        return pool.shutdownNow();
    }

    @Override
    public boolean isShutdown() {
        return pool.isShutdown();
    }

    @Override
    public boolean isTerminated() {
        return pool.isTerminated();
    }

    @Override
    public void execute(Runnable r) {
        pool.execute(r);
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return pool.awaitTermination(timeout, unit);
    }

//    protected final DefaultMonitoringConfig<ThreadPoolProbe> monitoringConfig = new DefaultMonitoringConfig<ThreadPoolProbe>(ThreadPoolProbe.class) {
//
//        @Override
//        public Object createManagementObject() {
//            return MonitoringUtils.loadJmxObject("org.glassfish.grizzly.threadpool.jmx.ThreadPool", this, AbstractThreadPool.class);
//        }
//
//    };

//    @Override
//    public MonitoringConfig<ThreadPoolProbe> getMonitoringConfig() {
//        return monitoringConfig;
//    }
    @Override
    public void configure(ServiceLocator habitat, NetworkListener networkListener, ThreadPool configuration) {
        threadFactory.name = networkListener.getName();
    }

    private static class VirtualThreadFactory implements ThreadFactory {

        int threadIndex = 0;

        String name = "virtual-thread";

        @Override
        public Thread newThread(Runnable r) {
            return Thread.ofVirtual().name(name + "(" + threadIndex++ + ")").unstarted(r);
        }
    }

}
