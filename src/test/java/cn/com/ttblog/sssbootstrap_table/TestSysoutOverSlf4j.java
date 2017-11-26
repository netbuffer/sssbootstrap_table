package cn.com.ttblog.sssbootstrap_table;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.org.lidalia.sysoutslf4j.context.SysOutOverSLF4J;

public class TestSysoutOverSlf4j {
	
	private static final Logger logger=LoggerFactory.getLogger(TestSysoutOverSlf4j.class);

	@Before
	public void before(){
		SysOutOverSLF4J.sendSystemOutAndErrToSLF4J();
	}

	@Test
	public void testSysout(){
		System.out.println("sysout");
	}

}
 