package org.example.util;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CalendarUtil {
    public static final List<LocalDate> HOLIDAYS_WITHOUT_PAY_2025 = Stream.of(
            "2025-01-01", "2025-01-02", "2025-01-03", "2025-01-04",
            "2025-01-05", "2025-01-06", "2025-01-07", "2025-01-08",
            "2025-02-23", "2025-03-08", "2025-05-01", "2025-05-09",
            "2025-06-12", "2025-11-04"
    ).map(LocalDate::parse).collect(Collectors.toList());

}
