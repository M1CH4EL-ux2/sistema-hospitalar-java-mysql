package visao.terminal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public final class DateUtil {

	private static final String DATE_FORMAT = "dd/MM/yyyy";
	private static final String DATETIME_FORMAT = DATE_FORMAT + " HH:mm:ss";

	private DateUtil() {
	}

	public static <T> T stringToDate(String date_text, Class<T> cls) {
		Object date;

		try {
			if (cls == LocalDate.class)
				date = LocalDate.parse(date_text, DateTimeFormatter.ofPattern(DATE_FORMAT));
			else if (cls == LocalDateTime.class)
				date = LocalDateTime.parse(date_text, DateTimeFormatter.ofPattern(DATETIME_FORMAT));
			else
				throw new RuntimeException(cls + " is not a valid type for this method!");
		} catch (DateTimeParseException e) {
			date = null;
		}

		return cls.cast(date);
	}

	public static <T> String dateToString(T date) {
		if (date instanceof LocalDate)
			return ((LocalDate) date).format(DateTimeFormatter.ofPattern(DATE_FORMAT));
		else if (date instanceof LocalDateTime)
			return ((LocalDateTime) date).format(DateTimeFormatter.ofPattern(DATETIME_FORMAT));
		else
			return null;
	}

	public static void main(String[] args) {
		System.out.println(stringToDate("16/09/1999", LocalDate.class));
		System.out.println(stringToDate("16/09/1999 22:45:10", LocalDateTime.class));

		System.out.println(stringToDate("16/13/1999", LocalDate.class));
		System.out.println(stringToDate("16/13/1999 22:45:10", LocalDateTime.class));

		System.out.println(dateToString(LocalDate.now()));
		System.out.println(dateToString(LocalDateTime.now()));

		System.out.println(stringToDate("16/13/1999 22:45:10", String.class));
	}

}
