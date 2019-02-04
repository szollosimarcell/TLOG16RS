package com.mszollosi.tlog16rs.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WorkDayRB {

    @JsonProperty
    private int year;

    @JsonProperty
    private int month;

    @JsonProperty
    private int day;

    @JsonProperty
    private int requiredHours = 450;

}
