package ruzaik.mh.todolistapp.util;

import java.util.Date;

import androidx.room.TypeConverter;
import ruzaik.mh.todolistapp.model.Priority;

public class Converter {

    @TypeConverter
    public static Date fromTimesstamp(Long value){
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimesstamp(Date date){
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static String fromPriority(Priority priority){
        return priority == null ? null : priority.name();
    }

    @TypeConverter
    public static Priority toPriority(String priority){
        return priority == null ? null : Priority.valueOf(priority);
    }
}
