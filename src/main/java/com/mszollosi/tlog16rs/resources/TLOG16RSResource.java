package com.mszollosi.tlog16rs.resources;



import com.mszollosi.tlog16rs.api.WorkDayRB;
import com.mszollosi.tlog16rs.api.WorkMonthRB;
import com.mszollosi.tlog16rs.core.entities.TimeLogger;
import com.mszollosi.tlog16rs.core.entities.WorkDay;
import com.mszollosi.tlog16rs.core.entities.WorkMonth;
import com.mszollosi.tlog16rs.core.exceptions.RedundantMonthAdditionException;
import com.mszollosi.tlog16rs.core.exceptions.ViolationOfRequiredWorkingMinutesException;
import com.mszollosi.tlog16rs.resources.util.TLOG16RSService;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/timelogger")
@Slf4j
public class TLOG16RSResource {

   private final TLOG16RSService service = new TLOG16RSService();

   /**
    * Gets a list of all workmonths of the user
    *
    * @return - the list of workmonths
    */
   @GET
   @Path("/workmonths")
   @Produces(MediaType.APPLICATION_JSON)
   public Response getWorkMonths() {
      TimeLogger timeLogger = service.getTimeLogger();
      return Response.ok(timeLogger.getMonths()).build();
   }

   /**
    * Creates a new workmonth and adds it to the users timelogger
    *
    * @param month - the month that should be created
    * @return - the created month
    */
   @POST
   @Path("/workmonths")
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.APPLICATION_JSON)
   public Response addNewMonth(WorkMonthRB month) {
      WorkMonth workMonth = new WorkMonth(month.getYear(), month.getMonth());
      TimeLogger timeLogger = service.getTimeLogger();
      try {
         timeLogger.addMonth(workMonth);
         return Response.ok(workMonth).build();

      } catch (RedundantMonthAdditionException e) {
         log.error(e.getMessage(), e);
         return Response.status(Response.Status.UNAUTHORIZED).build();
      }
   }

   /**
    * Gets the list of all workdays of a specified workmonth
    *
    * @param year - the year of the month
    * @param month - the chosen month
    * @return - the list of workdays
    */
   @GET
   @Path("/workmonths/{year}/{month}")
   @Produces(MediaType.APPLICATION_JSON)
   public Response getWorkDays(@PathParam(value = "year") String year, @PathParam(value = "month") String month) {
      WorkMonth workMonth = service.getWorkMonth(year, month);
      return Response.ok(workMonth.getDays()).build();
   }

   /**
    *
    *
    * @param day
    * @return
    */
   @POST
   @Path("/workmonths/workdays")
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.APPLICATION_JSON)
   public Response addNewDay(WorkDayRB day) {
      try {
         WorkDay workDay = new WorkDay(day.getRequiredHours(), day.getYear(), day.getMonth(), day.getDay());
         service.addDay(workDay, false);
         return Response.ok(workDay).build();
      } catch (ViolationOfRequiredWorkingMinutesException e) {
         log.error(e.getMessage(), e);
         return Response.status(Response.Status.UNAUTHORIZED).build();
      }
   }

   /*
   @GET
   @Path("/workmonths/{year}/{month}/{day}")
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.APPLICATION_JSON)
   public Response getTasks(@PathParam(value = "year") String year, @PathParam(value = "month") String month, @PathParam(value = "day") String day) {
      WorkDay workDay = service.getWorkDay(year, month, day);
      return Response.ok(workDay.getTasks()).build();
   }
   */

   @POST
   @Path("/workmonths/workdays/tasks/start")
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.APPLICATION_JSON)
   public Response startTask() {
      return Response.status(400).build();
   }

   @PUT
   @Path("/workmonths/workdays/tasks/finish")
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.APPLICATION_JSON)
   public Response finishTask() {
      return Response.status(400).build();
   }

   @PUT
   @Path("/workmonths/workdays/tasks/modify")
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.APPLICATION_JSON)
   public Response modifyTask() {
      return Response.status(400).build();
   }

   @PUT
   @Path("/workmonths/workdays/tasks/delete")
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.APPLICATION_JSON)
   public Response deleteTask() {
      return Response.status(400).build();
   }

}
