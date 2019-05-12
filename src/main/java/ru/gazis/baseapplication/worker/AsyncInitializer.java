package ru.gazis.baseapplication.worker;

import org.omg.PortableServer.ServantRetentionPolicyValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
public class AsyncInitializer {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private ExecutorService executorService;
    private Queue<String> queue = null;
    private LoopIn loopIn;
    private LoopOut loopOut;
    private Computation comp = null;

    @PostConstruct
    public void initPostConstruct() {
        logger.info("{} initPostConstruct", this.getClass());
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                loopIn.shutdown();
                loopOut.shutdown();
                executorService.shutdown();
                try {
                    logger.info("Try to awaitTermination of executorService (10000 ms)");
                    executorService.awaitTermination(10000, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        this.asyncInitializer();
    }

    @Async
    public void asyncInitializer() {
        logger.info("{} constructor", this.getClass());
        executorService = Executors.newFixedThreadPool(2);

        queue = new ConcurrentLinkedQueue<String>();
        
        loopIn = new LoopIn(queue);
        
        System.out.println("Initialization of computation module ... ");
        comp = new Computation();
        System.out.println("Done. Enter a + b: ");
        
        loopOut = new LoopOut(queue, comp);

        executorService.submit(loopIn);
        executorService.submit(loopOut);
    }
}
