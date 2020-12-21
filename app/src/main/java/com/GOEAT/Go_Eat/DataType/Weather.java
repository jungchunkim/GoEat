package com.GOEAT.Go_Eat.DataType;

public enum Weather {
    TEMPERATURE("span.todaytemp"),
    DESCRIPTION("p.cast_txt");

    private final String element;
    private String value;

    Weather(String element) {
        this.element = element;
    }

    public String getElement() {
        return element;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}