package com.maks.durov.productslist.room;

import androidx.room.TypeConverter;
import java.time.LocalDateTime;

public class LocalDateTimeConverter {

    @TypeConverter
    public static LocalDateTime toDate(String dateString) {
        if (dateString != null) {
            return LocalDateTime.parse(dateString);
        }
        return null;
    }

    @TypeConverter
    public static String toDateString(LocalDateTime date) {
        if (date != null) {
            return date.toString();
        }
        return null;
    }
}
