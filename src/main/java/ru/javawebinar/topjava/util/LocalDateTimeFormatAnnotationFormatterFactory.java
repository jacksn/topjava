package ru.javawebinar.topjava.util;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Formatter;
import org.springframework.format.Parser;
import org.springframework.format.Printer;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Locale;
import java.util.Set;

import static ru.javawebinar.topjava.util.DateTimeUtil.DATE_TIME_FORMATTER;

public class LocalDateTimeFormatAnnotationFormatterFactory implements AnnotationFormatterFactory<LocalDateTimeFormat> {

    @Override
    public Set<Class<?>> getFieldTypes() {
        return Collections.singleton(LocalDateTime.class);
    }

    @Override
    public Printer<?> getPrinter(LocalDateTimeFormat annotation, Class<?> fieldType) {
        return new LocalDateTimeFormatter();
    }

    @Override
    public Parser<?> getParser(LocalDateTimeFormat annotation, Class<?> fieldType) {
        return new LocalDateTimeFormatter();
    }

    public static class LocalDateTimeFormatter implements Formatter<LocalDateTime> {
        @Override
        public LocalDateTime parse(String text, Locale locale) throws ParseException {
            return StringUtils.isEmpty(text) ? LocalDateTime.now() : LocalDateTime.parse(text);
        }

        @Override
        public String print(LocalDateTime localDateTime, Locale locale) {
            localDateTime.format(DATE_TIME_FORMATTER);
            return localDateTime.toString();
        }
    }
}