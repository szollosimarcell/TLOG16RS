package com.mszollosi.tlog16rs.core.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mszollosi.tlog16rs.core.exceptions.DayAdditionException;
import lombok.Getter;
import lombok.Setter;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author MARCI
 */

@Getter
@Setter
public class WorkMonth {


    private final List<WorkDay> days = new ArrayList<>();

    private final YearMonth date;
    private final long requiredMinPerMonth = 9000;

    /**
     * Constructor of the WorkMonth class with the date parameters.
     *
     * @param year
     * @param month
     */
    public WorkMonth(int year, int month) {
        this.date = YearMonth.of(year, month);
    }

    /**
     * Adds a workday to the list of workdays.
     *
     * @param workDay - the workday that should be added
     * @param isWeekendEnabled - decides whether adding workday to weekend is enabled or not
     */
    public void addWorkDay(WorkDay workDay, boolean isWeekendEnabled) {
        if (isWeekendEnabled && isNewDate(workDay) || !isWeekendEnabled && Util.isWeekday(workDay) && isNewDate(workDay)) {
            days.add(workDay);
        } else {
            if (!Util.isWeekday(workDay)) {
                throw new DayAdditionException("You can't add workday to weekend!");
            }
        }
    }

    /**
     * Adds a task to a given workday.
     *
     * @param dayIndex - the index of the workday where the task should be added to
     * @param task - the task that should be added
     */
    protected void addTaskByWorkDay(int dayIndex, Task task) {
        days.get(dayIndex).addTask(task);
    }

    /**
     * Removes a task from a given workday.
     *
     * @param dayIndex - the index of the workday the task should be removed from
     * @param taskIndex - the index of the task that should be removed
     */
    protected void removeTaskByWorkDay(int dayIndex, int taskIndex) {
        days.get(dayIndex).removeTask(taskIndex);
    }

    /**
     * Replaces a task with a new one in a given workday.
     *
     * @param dayIndex - the index of the day where the task should be replaced
     * @param taskIndex - the index of the task that should be replaces
     * @param newTask - the new task that should take the places of the old one
     */
    protected void setTaskByWorkDay(int dayIndex, int taskIndex, Task newTask) {
        days.get(dayIndex).setTask(taskIndex, newTask);
    }

    /**
     * Gets the difference between the required working minutes and the completed working minutes in a month.
     *
     * @return - long - the difference in minutes
     */
    public long getExtraMinPerMonth() {
        return getSumPerMonth() - requiredMinPerMonth;
    }

    /**
     * Gets the sum of the working minutes in a month.
     *
     * @return - long - sum of the working minutes
     */
    public long getSumPerMonth() {
        long sum = 0;
        if (areThereAnyDays()) {
            sum = days.stream().map(day -> day.getSumPerDay()).reduce(sum, (accumulator, _item) -> accumulator + _item);
        }
        return sum;
    }

    /**
     * Checks whether the given workday already exists in the month.
     *
     * @param workDay - the workday that should be checked
     * @return
     */
    @JsonIgnore
    public boolean isNewDate(WorkDay workDay) {
        if (days.stream().noneMatch(day -> (day.getActualDay().equals(workDay.getActualDay())))) {
            return true;
        } else {
            throw new DayAdditionException("This day already exists in this month!");
        }
    }

    /**
     * Checks whether the list of workdays is empty.
     *
     * @return
     */
    @JsonIgnore
    public boolean areThereAnyDays() {
        return !days.isEmpty();
    }

    @Override
    public boolean equals(Object obj) {
        boolean same = false;
        if (obj instanceof WorkMonth) {
            same = this.date.equals(((WorkMonth) obj).getDate());
        }
        return same;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(date);
    }
}
