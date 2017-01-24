package com.qaprosoft.zafira.dbaccess.dao;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.qaprosoft.zafira.dbaccess.dao.mysql.WorkItemMapper;
import com.qaprosoft.zafira.models.db.User;
import com.qaprosoft.zafira.models.db.WorkItem;
import com.qaprosoft.zafira.models.db.WorkItem.Type;

@Test
@ContextConfiguration("classpath:com/qaprosoft/zafira/dbaccess/dbaccess-test.xml")
public class WorkItemMapperTest extends AbstractTestNGSpringContextTests
{
	/**
	 * Turn this on to enable this test
	 */
	private static final boolean ENABLED = false;
	
	private static final WorkItem WORK_ITEM = new WorkItem()
	{
		private static final long serialVersionUID = 1L;
		{
			User user = new User();
			user.setId(1L);
			
			setJiraId("JIRA-123");
			setType(Type.BUG);
			setDescription("d1");
			setHashCode(1);
			setUser(user);
			setTestCaseId(1L);
		}
	};

	@Autowired
	private WorkItemMapper workItemMapper;
	
	
	@Test(enabled = ENABLED)
	public void createWorkItem()
	{
		workItemMapper.createWorkItem(WORK_ITEM);

		assertNotEquals(WORK_ITEM.getId(), 0, "WorkItem ID must be set up by autogenerated keys");
	}
	
	@Test(enabled = ENABLED, dependsOnMethods =
	{ "createWorkItem" }, expectedExceptions =
	{ DuplicateKeyException.class })
	public void createWorkItemFail()
	{
		workItemMapper.createWorkItem(WORK_ITEM);
	}

	@Test(enabled = ENABLED, dependsOnMethods =
	{ "createWorkItem" })
	public void getWorkItemById()
	{
		checkWorkItem(workItemMapper.getWorkItemById(WORK_ITEM.getId()));
	}

	@Test(enabled = ENABLED, dependsOnMethods =
	{ "createWorkItem" })
	public void getWorkItemByJiraId()
	{
		checkWorkItem(workItemMapper.getWorkItemByJiraIdAndType(WORK_ITEM.getJiraId(), Type.BUG));
	}

	@Test(enabled = ENABLED, dependsOnMethods =
	{ "createWorkItem" })
	public void updateWorkItem()
	{
		WORK_ITEM.setJiraId("JIRA-3344");
		WORK_ITEM.setType(Type.TASK);
		WORK_ITEM.setDescription("d2");
		WORK_ITEM.setHashCode(2);
		WORK_ITEM.getUser().setId(2L);
		WORK_ITEM.setTestCaseId(2L);
		
		workItemMapper.updateWorkItem(WORK_ITEM);

		checkWorkItem(workItemMapper.getWorkItemById(WORK_ITEM.getId()));
	}

	/**
	 * Turn this in to delete workItem after all tests
	 */
	private static final boolean DELETE_ENABLED = true;

	/**
	 * If true, then <code>deleteWorkItem</code> will be used to delete workItem after all tests, otherwise -
	 * <code>deleteWorkItemById</code>
	 */
	private static final boolean DELETE_BY_WORK_ITEM = false;

	@Test(enabled = ENABLED && DELETE_ENABLED && DELETE_BY_WORK_ITEM, dependsOnMethods =
	{ "createWorkItem", "createWorkItemFail", "getWorkItemById", "getWorkItemByJiraId", "updateWorkItem" })
	public void deleteWorkItem()
	{
		workItemMapper.deleteWorkItem(WORK_ITEM);

		assertNull(workItemMapper.getWorkItemById(WORK_ITEM.getId()));
	}

	@Test(enabled = ENABLED && DELETE_ENABLED && !DELETE_BY_WORK_ITEM, dependsOnMethods =
	{ "createWorkItem", "createWorkItemFail", "getWorkItemById", "getWorkItemByJiraId", "updateWorkItem" })
	public void deleteWorkItemById()
	{
		workItemMapper.deleteWorkItemById((WORK_ITEM.getId()));

		assertNull(workItemMapper.getWorkItemById(WORK_ITEM.getId()));
	}

	private void checkWorkItem(WorkItem workItem)
	{
		assertEquals(workItem.getJiraId(), WORK_ITEM.getJiraId(), "Jira ID must match");
		assertEquals(workItem.getHashCode(), WORK_ITEM.getHashCode(), "Hash code must match");
		assertEquals(workItem.getDescription(), WORK_ITEM.getDescription(), "Description must match");
		assertEquals(workItem.getType(), WORK_ITEM.getType(), "Type must match");
		assertEquals(workItem.getUser().getId(), WORK_ITEM.getUser().getId(), "User must match");
		assertEquals(workItem.getTestCaseId(), WORK_ITEM.getTestCaseId(), "Test case id must match");
	}
}
