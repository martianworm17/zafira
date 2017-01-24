package com.qaprosoft.zafira.dbaccess.dao;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNull;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.qaprosoft.zafira.dbaccess.dao.mysql.EventMapper;
import com.qaprosoft.zafira.models.db.Event;
import com.qaprosoft.zafira.models.db.Event.Type;

@Test
@ContextConfiguration("classpath:com/qaprosoft/zafira/dbaccess/dbaccess-test.xml")
public class EventTest extends AbstractTestNGSpringContextTests
{
	/**
	 * Turn this on to enable this test
	 */
	private static final boolean ENABLED = false;
	
	private static final Event EVENT = new Event()
	{
		private static final long serialVersionUID = 1L;
		{
			setType(Type.REQUEST_DEVICE_CONNECT);
			setTestRunId(UUID.randomUUID().toString());
			setTestId(UUID.randomUUID().toString());
			setReceived(new Date());
			setData("One");
		}
	};

	@Autowired
	private EventMapper eventMapper;

	@Test(enabled = ENABLED)
	public void createEvent()
	{
		eventMapper.createEvent(EVENT);

		assertNotEquals(EVENT.getId(), 0, "Event ID must be set up by autogenerated keys");
	}

	@Test(enabled = ENABLED, dependsOnMethods =
	{ "createEvent" })
	public void getEventById()
	{
		checkEvent(eventMapper.getEventById(EVENT.getId()));
	}

	@Test(enabled = ENABLED, dependsOnMethods =
	{ "createEvent" })
	public void getEventByTypeAndTestRunIdAndTestId()
	{
		checkEvent(eventMapper.getEventByTypeAndTestRunIdAndTestId(EVENT.getType(), EVENT.getTestRunId(), EVENT.getTestId()));
	}

	@Test(enabled = ENABLED, dependsOnMethods =
	{ "createEvent" })
	public void updateEvent()
	{
		EVENT.setType(Type.REQUEST_DEVICE_DISCONNECT);
		EVENT.setTestRunId(UUID.randomUUID().toString());
		EVENT.setTestId(UUID.randomUUID().toString());
		EVENT.setReceived(new Date());
		EVENT.setData("Two");
		
		eventMapper.updateEvent(EVENT);

		checkEvent(eventMapper.getEventById(EVENT.getId()));
	}

	/**
	 * Turn this in to delete event after all tests
	 */
	private static final boolean DELETE_ENABLED = true;


	@Test(enabled = ENABLED && DELETE_ENABLED, dependsOnMethods =
	{ "createEvent", "getEventById", "getEventByTypeAndTestRunIdAndTestId", "updateEvent" })
	public void deleteEventById()
	{
		eventMapper.deleteEventById((EVENT.getId()));

		assertNull(eventMapper.getEventById(EVENT.getId()));
	}

	private void checkEvent(Event event)
	{
		assertEquals(event.getType(), EVENT.getType(), "Type must match");
		assertEquals(event.getTestRunId(), EVENT.getTestRunId(), "Test run ID must match");
		assertEquals(event.getReceived(), EVENT.getReceived(), "Received ID must match");
		assertEquals(event.getData(), EVENT.getData(), "Data must match");
	}
}
