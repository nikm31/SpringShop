package ru.geekbrains.springdata.dto;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ExecutionTimeLogger {

	private final Map<String, Map<String, Long>> packageLogs;

	public ExecutionTimeLogger(Map<String, Map<String, Long>> packageLogs) {
		this.packageLogs = packageLogs;
	}

	public void add(String className, String methodName, Long duration) {
		if (!packageLogs.containsKey(className)) {
			Map<String, Long> value = new HashMap<>();
			value.put(methodName, duration);
			packageLogs.put(className, value);
		} else {
			packageLogs.get(className).put(methodName, duration);
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("===== START EXECUTION TIME LOGGING ===== \n");
		packageLogs.forEach((key, value) -> {
			sb.append(key.toUpperCase(Locale.ROOT)).append("\n");
			value.forEach((internalKey, internalValue) -> {
				sb.append("FUNCTION: ").append(internalKey).append("\n");
				sb.append("EXECUTION TIME: ").append(internalValue).append("\n");
				sb.append("=========================== \n");
			});
		});
		sb.append("===== END EXECUTION TIME LOGGING ===== \n");
		return sb.toString();
	}
}
