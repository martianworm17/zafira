package com.qaprosoft.zafira.dbaccess.dao.mysql.search;

import java.util.Date;
import java.util.List;

public class TestCaseSearchCriteria extends SearchCriteria
{
	private Long id;
	private List<Long> ids;
	private String testClass;
	private String testMethod;
	private String testSuiteName;
	private String testSuiteFile;
	private String userName;
	private Date date;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}
	
	public List<Long> getIds()
	{
		return ids;
	}

	public void setIds(List<Long> ids)
	{
		this.ids = ids;
	}

	public String getTestClass()
	{
		return testClass;
	}

	public void setTestClass(String testClass)
	{
		this.testClass = testClass;
	}

	public String getTestMethod()
	{
		return testMethod;
	}

	public void setTestMethod(String testMethod)
	{
		this.testMethod = testMethod;
	}

	public String getTestSuiteName()
	{
		return testSuiteName;
	}

	public void setTestSuiteName(String testSuiteName)
	{
		this.testSuiteName = testSuiteName;
	}

	public String getTestSuiteFile()
	{
		return testSuiteFile;
	}

	public void setTestSuiteFile(String testSuiteFile)
	{
		this.testSuiteFile = testSuiteFile;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}
}