package ru.javawebinar.topjava.util;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;

public class LocalDateTimeFormatAnnotationFormatterFactory implements AnnotationFormatterFactory<LocalDateTimeFormat> {

    @Override
    public Set<Class<?>> getFieldTypes() {
        return Collections.singleton(LocalDateTime.class);
    }

    @Override
    public Printer<?> getPrinter(LocalDateTimeFormat annotation, Class<?> fieldType) {
        return new DateTimeUtil.LocalDateTimeFormatter();
    }

    @Override
    public Parser<?> getParser(LocalDateTimeFormat annotation, Class<?> fieldType) {
        return new DateTimeUtil.LocalDateTimeFormatter();
    }
}