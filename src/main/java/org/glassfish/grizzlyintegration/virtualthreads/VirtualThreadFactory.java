package org.glassfish.grizzlyintegration.virtualthreads;

import java.util.concurrent.ThreadFactory;

public class VirtualThreadFactory implements ThreadFactory {

    int threadIndex = 0;

    String name = "virtual-thread";

    @Override
    public Thread newThread(Runnable runnable) {
        return Thread.ofVirtual().name(name + "(" + threadIndex++ + ")").unstarted(runnable);
    }
}
