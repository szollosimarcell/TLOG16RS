package com.mszollosi.tlog16rs.resources.util;

import com.mszollosi.tlog16rs.core.entities.TimeLogger;
import com.mszollosi.tlog16rs.core.entities.WorkDay;
import com.mszollosi.tlog16rs.core.entities.WorkMonth;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.YearMonth;

@Slf4j
@NoArgsConstructor
public class TLOG16RSService {

    @Getter
    private final TimeLogger timeLogger = new TimeLogger();

    public WorkMonth getWorkMonth(String yearP, String monthP) {
        int year = Integer.parseInt(yearP);
        int month = Integer.parseInt(monthP);
        for (WorkMonth workMonth : timeLogger.getMonths()) {
            if (workMonth.getDate().equals(YearMonth.of(year, month))) {
                return workMonth;
            }
        }
        return new WorkMonth(year, month);
    }


    public void addDay(WorkDay workDay, boolean isWeekendEnabled) {
        WorkMonth workMonth = new WorkMonth(workDay.getActualDay().getYear(), workDay.getActualDay().getMonthValue());
        if (timeLogger.getMonths().contains(workMonth)) {
            try {
                WorkMonth wd = timeLogger.getMonths().stream()
                                                     .filter(month -> month.equals(workMonth))
                                                     .findFirst()
                                                     .orElse(null);
                wd.addWorkDay(workDay, isWeekendEnabled);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        } else {
            try {
                workMonth.addWorkDay(workDay, isWeekendEnabled);
                timeLogger.addMonth(workMonth);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    /*
    public WorkDay getWorkDay(String yearP, String monthP, String dayP) {
        YearMonth monthDate = YearMonth.parse(yearP + "-" + monthP);
        LocalDate dayDate = LocalDate.parse(yearP + "-" + monthP + "-" + dayP);
        int day = Integer.parseInt(dayP);
        WorkMonth workMonth = timeLogger.getMonths().stream()
                                                    .filter(workM -> workM.getDate().equals(monthDate))
                                                    .findFirst()
                                                    .orElse(null);
        workMonth.getDays().stream().filter(workD -> workD.getActualDay().equals(dayDate)).findFirst().orElse(null);
    }
    */
}
