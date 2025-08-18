package com.fm.knight.knight.level;

public enum LogLevel {
    OFF(2147483647),

    FATAL(50000),

    ERROR(40000),

    WARN(30000),

    INFO(20000),

    DEBUG(10000),

    ALL(-2147483648),

    ACCESS(1000),

    SERVICE(1001);

    int type;

    public int getType() {
        return type;
    }

    private LogLevel(int type) {
        this.type = type;
    }
}
