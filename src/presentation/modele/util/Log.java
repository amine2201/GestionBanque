package presentation.modele.util;

import java.time.LocalDate;
import java.time.LocalTime;

public class Log {
    private LocalDate date;
    private LocalTime time;
    private TypeLog type;
    private String message;

    public Log(LocalDate date, String msg){
        this.date = date;
        this.message = msg;
    }

    public Log(LocalDate date, LocalTime time, TypeLog type, String msg){
        this.date = date;
        this.message = msg;
        this.time = time;
        this.type = type;
    }
    public TypeLog getType() {
        return type;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        String logStr = "[" + date + "]["+time+"][{"+type+"}] : " +  message;

        return logStr;
    }
}
