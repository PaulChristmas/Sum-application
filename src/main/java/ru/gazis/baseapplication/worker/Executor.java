package ru.gazis.baseapplication.worker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONObject;
import org.json.JSONArray;

public class Executor implements Runnable {
	private static final AtomicBoolean closed = new AtomicBoolean(false);
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private Queue<String> in = null;
	private Queue<String> out = null;
	private Computation comp = null;
	private Parser parser = null;

	public Executor(Queue in, Queue out) {
		this.in = in;
		this.out = out;
		parser = new Parser();
		comp = new Computation();
	}

	@Override
	public void run() {
		try {
			String str = "";
			while (!closed.get()) {
				if ((str = in.poll()) != null) {

					try {
						JSONObject obj = parser.parse(str);
						JSONArray arr = obj.getJSONArray("inputArr");

						String operation = obj.getString("operation");

						Double result = 0.0;

						if (operation.equals("sum")) {
							result = comp.getSum(arr.getDouble(0), arr.getDouble(1));
						} else if (operation.equals("subtr")) {

						} else if (operation.equals("mul")) {

						} else if (operation.equals("div")) {

						}
						JSONObject resultJson = new JSONObject();
						resultJson.put("input", obj);
						resultJson.put("output", result);

						out.add(resultJson.toString());

					} catch (Exception e) {
						System.err.println(e.getMessage());
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
