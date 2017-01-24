package com.qaprosoft.zafira.ws.controller;

import com.qaprosoft.zafira.models.db.Project;
import com.qaprosoft.zafira.services.exceptions.ServiceException;
import com.qaprosoft.zafira.services.services.ProjectService;
import com.qaprosoft.zafira.services.services.VersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@ApiIgnore
@RequestMapping("config")
public class ConfigurationController extends AbstractController
{
	@Autowired
	private VersionService versionService;
	
	@Autowired
	private ProjectService projectService;

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "version", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Map<String, String> getVersion() throws ServiceException
	{
		Map<String, String> config = new HashMap<String, String>();
		config.put("service", versionService.getServiceVersion());
		config.put("client", versionService.getClientVersion());
		return config;
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "projects", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Project> getAllProjects() throws ServiceException
	{
		return projectService.getAllProjects();
	}
}
