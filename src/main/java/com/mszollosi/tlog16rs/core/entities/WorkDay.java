package com.mszollosi.tlog16rs.core.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mszollosi.tlog16rs.core.exceptions.EmptyListException;
import com.mszollosi.tlog16rs.core.exceptions.ViolationOfRequiredWorkingMinutesException;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author MARCI
 */
@Getter
@Setter
public class WorkDay {


    private final List<Task> tasks = new ArrayList<>();

    private static final long MINIMUM_MIN_PER_DAY = 250;

    private transient LocalDate actualDay;
    private long requiredMinPerDay = 450;

    /**
     * Constructor of the WorkDay class with separated LocalDate parameters.
     *
     * @param requiredMinPerDay
     * @param year
     * @param month
     * @param day
     */
    public WorkDay(long requiredMinPerDay, int year, int month, int day) {
        if (requiredMinPerDay <= MINIMUM_MIN_PER_DAY) {
            throw new ViolationOfRequiredWorkingMinutesException();
        }
        this.requiredMinPerDay = requiredMinPerDay;
        this.actualDay = LocalDate.of(year, month, day);
    }

    /**
     * Constructor of the WorkDay class with only LocalDate.
     *
     * @param year
     * @param month
     * @param day
     */
    public WorkDay(int year, int month, int day) {
        this.actualDay = LocalDate.of(year, month, day);
    }

    /**
     * Adds a task to the list of tasks
     *
     * @param t - the task that should be added
     */
    public void addTask(Task t) {
        tasks.add(t);
    }

    /**
     * Removes a task from the list a tasks
     *
     * @param taskIndex - the index of the task that should be removed
     */
    protected void removeTask(int taskIndex) {
        tasks.remove(taskIndex);
    }

    /**
     * Replaces a task to a new one in the list of tasks
     *
     * @param taskIndex - the index of the task that should be replaced
     * @param newTask - the new task that should take the place of the old one
     */
    protected void setTask(int taskIndex, Task newTask) {
        tasks.set(taskIndex, newTask);
    }

    /**
     * Gets the difference between the required minutes and the completed working minutes in a day
     *
     * @return - long - the difference in minutes
     */
    public long getExtraMinPerDay() {
        return getSumPerDay() - requiredMinPerDay;
    }

    /**
     * Gets the sum of the working minutes in a day
     *
     * @return - long - sum of the working minutes
     */
    public long getSumPerDay() {
        long sum = 0;
        if (areThereAnyTasks()) {
            for (Task task : tasks) {
                sum += task.getMinPerTask();
            }
        }
        return sum;
    }

    /**
     * Gets the latest end time of the list of the tasks of a day.
     *
     * @return - the latest end time
     */
    @JsonIgnore
    public LocalTime latestTaskEndTime() {
        LocalTime latestEndTime = LocalTime.of(0, 0);
        if (!tasks.isEmpty()) {
            for (Task task : tasks) {
                if (task.getEndTime().compareTo(latestEndTime) > 0) {
                    latestEndTime = task.getEndTime();
                }
            }
        }
        return latestEndTime;
    }

    /**
     * Checks whether the list of tasks is empty.
     *
     * @return - true, if the list of tasks is not empty
     */
    @JsonIgnore
    public boolean areThereAnyTasks() {
        return !tasks.isEmpty();
    }

    @Override
    public String toString() {
        return actualDay.toString();
    }
}
