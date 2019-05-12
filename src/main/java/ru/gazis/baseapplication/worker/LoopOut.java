package ru.gazis.baseapplication.worker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;

public class LoopOut implements Runnable {
    private static final AtomicBoolean closed = new AtomicBoolean(false);
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private Queue<String> queue = null;
    private Computation comp = null;

    public LoopOut(Queue queue, Computation comp) {
        this.queue = queue;
        this.comp = comp;
    }

    @Override
	public void run() {
		try {
			String str = "";
			while (!closed.get()) {
				if ((str = queue.poll()) != null) {
					try {
						String ab[] = str.split("\\+");
						Double result = comp.getSum(Double.parseDouble(ab[0]), Double.parseDouble(ab[1]));
						System.out.println(str + " = " + result);
					} catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
						System.err.println(str + " : Input doesn't contain '+' or number has wrong format");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    public void shutdown() {
        closed.set(true);
        comp.dispose();
    }
}
