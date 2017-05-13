package com.lifepro;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class HttpPerfTest {

	public static void main(String[] args) throws Exception {
		Runnable run = new Runnable() {

			public void run() {

				long successCount = 0, failureCount = 0, startTime = System
						.currentTimeMillis();
				try {
					final URL url = new URL(
							"http://localhost:8080/xfini/xfini/task/perftest.do");
					while (true) {
						URLConnection yc = url.openConnection();
						BufferedReader in = new BufferedReader(
								new InputStreamReader(yc.getInputStream()));
						String inputLine;
						while ((inputLine = in.readLine()) != null) {
							if ("true".equals(inputLine)) {
								successCount++;
							} else {
								successCount++;
							}
							long count = successCount + failureCount;
							if (count % 5000 == 0) {
								System.out
										.println(Thread.currentThread()
												.getName()
												+ "- Av Number of Http calls in a second="
												+ count
												/ (1 + (System
														.currentTimeMillis() - startTime) / 1000)
												+ " successCount="
												+ successCount
												+ " failureCount="
												+ failureCount);
							}
						}
						in.close();

					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		};

		for (int i = 0; i < Integer.parseInt(args[0]); i++) {
			new Thread(run, "Thread" + i).start();

		}

	}
}