package com.ginny.alexademo.web.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServiceLookupNLprocessor {
	Map<String, List<String>> wordServiceIdListMap = new HashMap<>();

	class QuerySentence {
		String rawSentence;
		String[] words;

	}

	class ServiceMatchingConfidence {
		// confidence 0-1;
		double connfidence;
		String serviceId;
	}

	public static class RegexMatches {
		class ServiceMatchingInputs {
			String serviceId;
			List<InputTypeValueOptions> definitions;
		}

		class InputTypeValueOptions {
			String type;
			String value;
			TreeSet<String> options;
			TreeSet<String> allRequiredWords;
			TreeSet<String> allRequiredPhrases;

		}

		public void parse(String line) {
			String tPattern = "\\[.*\\]";
			Pattern tp = Pattern.compile(tPattern);

			String vPattern = "\\{(.*)\\}";
			Pattern vp = Pattern.compile(vPattern);

			String oPattern = "\\(.*\\)";
			Pattern op = Pattern.compile(vPattern);

			// String to be scanned to find the pattern.
			String[] serviceIdDefinition = line.split("=");
			String serviceId = serviceIdDefinition[0];
			String serviceDefinitionStr = serviceIdDefinition[1];
			String[] typeValueOptions = serviceDefinitionStr.split(",");

			for (int i = 0; i < typeValueOptions.length; i++) {

				Matcher m = tp.matcher(typeValueOptions[0]);
				if (m.find()) {
					System.out.println("Found value: " + m.group(0));
				} else {
					System.out.println("NO MATCH");
				}

				m = vp.matcher(typeValueOptions[0]);
				if (m.find()) {
					System.out.println("Found value: " + m.group(0));
				} else {
					System.out.println("NO MATCH");
				}

				m = op.matcher(typeValueOptions[0]);
				if (m.find()) {
					System.out.println("Found value: " + m.group(0));

					System.out.println("Found value: " + m.group(1));

					System.out.println("Found value: " + m.group(2));
				} else {
					System.out.println("NO MATCH");
				}
			}

		}
	}

	public static void main(String[] args) {
		new RegexMatches().parse(args[0]);

	}

}
