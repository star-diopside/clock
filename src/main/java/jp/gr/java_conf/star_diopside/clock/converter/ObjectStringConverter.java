package jp.gr.java_conf.star_diopside.clock.converter;

import java.util.function.Function;

import javafx.util.StringConverter;

public class ObjectStringConverter<T> extends StringConverter<T> {

    private Function<T, String> formatter;
    private Function<String, T> parser;

    public ObjectStringConverter(Function<T, String> formatter, Function<String, T> parser) {
        this.formatter = formatter;
        this.parser = parser;
    }

    @Override
    public String toString(T object) {
        if (formatter == null) {
            throw new UnsupportedOperationException();
        } else {
            return formatter.apply(object);
        }
    }

    @Override
    public T fromString(String string) {
        if (parser == null) {
            throw new UnsupportedOperationException();
        } else {
            return parser.apply(string);
        }
    }
}
