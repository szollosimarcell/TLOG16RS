package com.mszollosi.tlog16rs.core.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mszollosi.tlog16rs.core.exceptions.InvalidInputException;

import java.time.Duration;
import java.time.LocalTime;

import lombok.Getter;
import lombok.Setter;

/**
 * @author MARCI
 */
@Getter
@Setter
public final class Task {

    private String taskId;
    private transient LocalTime startTime;
    private transient LocalTime endTime;
    private String comment;

    @JsonIgnore
    private String taskIdOld;
    @JsonIgnore
    private transient LocalTime startTimeOld;
    @JsonIgnore
    private transient LocalTime endTimeOld;
    @JsonIgnore
    private String commentOld;

    /**
     * Constructor of the Task class with separated time parts.
     *
     * @param taskId
     * @param startHour
     * @param startMin
     * @param endHour
     * @param endMin
     * @param comment
     */
    public Task(String taskId, int startHour, int startMin, int endHour, int endMin, String comment) {
        this.taskId = taskId;
        this.startTime = LocalTime.of(startHour, startMin);
        this.endTime = LocalTime.of(endHour, endMin);
        this.comment = comment;
    }

    /**
     * Constructor of the Task class with LocalTime.
     *
     * @param taskId
     * @param startTime
     * @param endTime
     * @param comment
     */
    public Task(String taskId, String startTime, String endTime, String comment) {
        this.taskId = taskId;
        this.startTime = LocalTime.parse(startTime);
        this.endTime = LocalTime.parse(endTime);
        this.comment = comment;
    }

    /**
     * Constructor of the Task class without ending time.
     *
     * @param taskId
     * @param startTime
     * @param comment
     */
    public Task(String taskId, String startTime, String comment) {
        this.taskId = taskId;
        this.startTime = LocalTime.parse(startTime);
        this.comment = comment;
    }

    /**
     * Constructor of the Task class with only the id of the task.
     *
     * @param taskId
     */
    public Task(String taskId) {
        this.taskId = taskId;
        if (!isValidTaskId()) {
            throw new InvalidInputException("Invalid taskID!");
        }
    }

    /**
     * @return - the duration of the task in minutes
     */
    public long getMinPerTask() {
        return Duration.between(startTime, endTime).toMinutes();
    }

    /**
     * Checks whether the id of the task is valid or not.
     *
     * @return
     */
    @JsonIgnore
    public boolean isValidTaskId() {
        return isValidRedmineTaskId() || isValidLTTaskId();
    }

    @JsonIgnore
    public boolean isValidRedmineTaskId() {
        return taskId.matches("^\\d{4}$");
    }

    @JsonIgnore
    public boolean isValidLTTaskId() {
        return taskId.matches("^LT-\\d{4}$");
    }

    /**
     * Checks whether the ending time is after the starting time.
     *
     * @return
     */
    @JsonIgnore
    public boolean isValidTime() {
        return startTime.compareTo(endTime) <= 0;
    }

    public String showOld() {
        return "taskId: " + taskIdOld + ", startTime: " + startTimeOld + ", endTime: " + endTimeOld + ", comment: " + commentOld;
    }

    @Override
    public String toString() {
        return "taskId: " + taskId + ", startTime: " + startTime + ", endTime: " + endTime + ", comment: " + comment;
    }
}