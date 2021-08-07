package com.thiendz.j6.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
	public static final Random RANDOM = new Random();

	public static String toStringJavascriptDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		int second = c.get(Calendar.SECOND);
		String strMonth = "" + (month < 10 ? "0" + month : month);
		String strDay = "" + (day < 10 ? "0" + day : day);
		String strHour = "" + (hour < 10 ? "0" + hour : hour);
		String strMinute = "" + (minute < 10 ? "0" + minute : minute);
		String strSecond = "" + (second < 10 ? "0" + second : second);
		final String patternJavascriptDate = "%s-%s-%sT%s:%s:%s"; // Y-m-d\TH:i:s
		String javascriptDate = String.format(patternJavascriptDate, year, strMonth, strDay, strHour, strMinute,
				strSecond);
		return javascriptDate;
	}

	public static Date toDate(String stringJavascriptDate) {
		final String REGEX = "^(\\d+)-(\\d+)-(\\d+)T(\\d+):(\\d+):(\\d+)$";
		final String REGEX_NUMBER = "(\\d+)";
		if (stringJavascriptDate != null && !regex(REGEX, stringJavascriptDate).isEmpty()) {
			List<String> listNumber = regex(REGEX_NUMBER, stringJavascriptDate);
			int year = toInt(listNumber.get(0));
			int month = toInt(listNumber.get(1));
			int day = toInt(listNumber.get(2));
			int hour = toInt(listNumber.get(3));
			int minute = toInt(listNumber.get(4));
			int second = toInt(listNumber.get(5));
			System.out.println(year + " " + month + " " + day + " " + hour + " " + minute + " " + second);
			Calendar c = Calendar.getInstance();
			c.set(year, month, day, hour, minute, second);
			return c.getTime();
		}
		return null;
	}

	public static List<String> regex(String regex, String input) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		List<String> alValue = new ArrayList<>();
		while (matcher.find()) {
			alValue.add(matcher.group());
		}
		return alValue;
	}

	public static int toInt(String in) {
		return toInt(in, 0);
	}

	public static int toInt(String in, int def) {
		try {
			return Integer.parseInt(in);
		} catch (Exception e) {
			return def;
		}
	}
}
