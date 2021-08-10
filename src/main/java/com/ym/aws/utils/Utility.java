/**
 * 
 */
package com.ym.aws.utils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Pranit.Mhatre
 *
 */
public final class Utility {

	public static final String DEFAULT_TIME_ZONE = "Asia/Calcutta";
	public static final String DEFAULT_DATE_FORMAT = "dd-MM-yyyy HH:mm:ss.SSS";
	public static final String DD_MM_YYYY_DATE_PATTERN = "dd-MM-yyyy";
	public static final String YYYY_MM_DD_DATE_PATTERN = "yyyy-MM-dd";
	public static final String DD_MM_YYYY_HH_MM_DATE_PATTERN = "dd-MM-yyyy HH:mm";
	private static SimpleDateFormat dateFormatter;

	static {
		TimeZone.setDefault(TimeZone.getTimeZone(DEFAULT_TIME_ZONE));
	}

	private Utility() {
		throw new IllegalAccessError("Can not instantiate utility class.");
	}

	public static JsonNode textToNode(String json) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readTree(json);
	}

	public static <T> T toObject(String jsonContent, Class<T> clazz) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {
			return mapper.readValue(jsonContent, clazz);
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static String toJson(Object object) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {
			return mapper.writeValueAsString(object);
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static Date currentDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeZone(TimeZone.getTimeZone(DEFAULT_TIME_ZONE));
		return cal.getTime();
	}

	public static long dateToMillis() {
		return dateToMillis(currentDate());
	}

	public static long dateToMillis(String date) {
		return dateToMillis(textToDate(date, DD_MM_YYYY_DATE_PATTERN));
	}

	public static long dateToMillis(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeZone(TimeZone.getTimeZone(DEFAULT_TIME_ZONE));
		cal.setTime(date);
		return cal.getTime().getTime();
	}

	public static Date textToDate(String date) {
		dateFormatter = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
		try {
			return dateFormatter.parse(date);
		} catch (ParseException ex) {
			System.out.println(ex.getMessage());
			return null;
		}
	}

	public static Date textToDate(String date, String datePattern) {
		dateFormatter = new SimpleDateFormat(datePattern);
		try {
			return dateFormatter.parse(date);
		} catch (ParseException ex) {
			System.out.println(ex.getMessage());
			return null;
		}
	}

	public static String dateToText() {
		return dateToText(currentDate(), DEFAULT_DATE_FORMAT);
	}

	public static String dateToText(String pattern) {
		return dateToText(currentDate(), pattern);
	}

	public static String dateToText(Date date, String pattern) {
		dateFormatter = new SimpleDateFormat(pattern);
		return dateFormatter.format(date);
	}

	public static String formatDate(String date, String pattern) {
		DateTimeFormatter dateFormatter = new DateTimeFormatterBuilder().appendPattern(YYYY_MM_DD_DATE_PATTERN)
				.toFormatter();
		LocalDate parsedDate = LocalDate.parse(date, dateFormatter);
		DateTimeFormatter f2 = DateTimeFormatter.ofPattern(pattern);
		return parsedDate.format(f2);
	}

	public static int compareDates(Date date1, Date date2) {
		if (date2 == null) {
			return 1;
		}
		if (date1 == null) {
			return -1;
		}
		return date1.compareTo(date2);
	}

	public static int compareDates(String value1, String value2) {
		Date date1 = null;
		Date date2 = null;
		if (Objects.nonNull(value1)) {
			date1 = textToDate(value1,DD_MM_YYYY_HH_MM_DATE_PATTERN);
		}
		if (Objects.nonNull(value2)) {
			date2 = textToDate(value2,DD_MM_YYYY_HH_MM_DATE_PATTERN);
		}
		return compareDates(date1, date2);
	}
}
