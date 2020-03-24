/**
 * 
 */
package com.strandls.migration.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.inject.Inject;
import com.strandls.migration.ApiConstants;
import com.strandls.migration.dao.ActivityDao;
import com.strandls.migration.pojo.Activity;
import com.strandls.migration.service.impl.MigrateThread;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author Abhishek Rudra
 *
 */

@Api("Migration Serivce")
@Path(ApiConstants.V1 + ApiConstants.SERVICE)
public class ActivityController {

	@Inject
	private MigrateThread migrationThread;

	@Inject
	private ActivityDao activityDao;

	@GET
	@Path(ApiConstants.PING)
	@Produces(MediaType.TEXT_PLAIN)
	public Response ping() {
		return Response.status(Status.OK).entity("PONG").build();
	}

	@GET
	@Path(ApiConstants.IBP + "/{activityId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)

	@ApiOperation(value = "Find activity by id", notes = "Returns the activity by id", response = Activity.class)
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "unable to retireve the data", response = String.class) })

	public Response getActivityIbp(@PathParam("activityId") String activityId) {
		try {
			Activity result = activityDao.findById(Long.parseLong(activityId));
			return Response.status(Status.OK).entity(result).build();
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}

	@POST
	@Path(ApiConstants.MIGRATE)
	@Produces(MediaType.TEXT_PLAIN)

	@ApiOperation(value = "Migrate the Old activity data", notes = "Starts a Thread that Migrate the data", response = String.class)
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "Unable to start the process", response = String.class) })

	public Response migrateData() {
		try {
			Thread thread = new Thread(migrationThread);
			thread.start();
			return Response.status(Status.OK).entity("Migration Started").build();
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}

}