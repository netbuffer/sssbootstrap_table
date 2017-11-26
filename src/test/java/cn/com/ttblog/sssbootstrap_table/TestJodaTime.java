package cn.com.ttblog.sssbootstrap_table;

import cn.com.ttblog.sssbootstrap_table.util.JodaTimeUtil;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

//@Ignore
public class TestJodaTime {
	Logger log = LoggerFactory.getLogger(this.getClass());
	private final static  String FORMAT="yyyy-MM-dd";
	private final static  String DATE_TIME_FORMAT="yyyy-MM-dd HH:mm:ss";

	@Test
	@Ignore
	public void testWeekStart(){
		log.debug("本周开始时间:{}",JodaTimeUtil.getWeekStart(new DateTime()).toString(FORMAT));
	}
	@Test
	@Ignore
	public void testWeekEnd(){
		log.debug("本周结束时间:{}",JodaTimeUtil.getWeekEnd(new DateTime()).toString(FORMAT));
	}
	@Test
	@Ignore
	public void testMonthStart(){
		log.debug("本月开始时间:{}",JodaTimeUtil.getMonthStart(new DateTime()).toString(FORMAT));
	}
	@Test
	@Ignore
	public void testMonthEnd(){
		log.debug("本月结束时间:{}",JodaTimeUtil.getMonthEnd(new DateTime()).toString(FORMAT));
	}
	@Test
	@Ignore
	public void testSeasonStart(){
		log.debug("本季度开始时间:{}",JodaTimeUtil.getSeasonStart(new DateTime()).toString(FORMAT));
	}
	@Test
	@Ignore
	public void testSeasonEnd(){
		log.debug("本季度结束时间:{}",JodaTimeUtil.getSeasonEnd(new DateTime()).toString(FORMAT));
	}
	@Test
	@Ignore
	public void testYearStart(){
		log.debug("本年度开始时间:{}",JodaTimeUtil.getYearStart(new DateTime()).toString(FORMAT));
	}
	@Test
	@Ignore
	public void testYearEnd(){
		log.debug("本年度结束时间:{}",JodaTimeUtil.getYearEnd(new DateTime()).toString(FORMAT));
	}

	@Test
	@Ignore
	public void testCompare(){
		Date yes=new DateTime().plusDays(-1).toDate();
		Date today=new Date();
		System.out.println("yes.compareTo(today):"+yes.compareTo(today));
		System.out.println("today.compareTo(yes):"+today.compareTo(yes));
		System.out.println("today.compareTo(today):"+today.compareTo(today));
	}
	@Test
	@Ignore
	public void testFormat(){
		System.out.println("-----------------------------------");
		SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(fmt.format(new Date((long)System.currentTimeMillis())));
		System.out.println(fmt.format(new Date(new Date().getTime())));
		String yDate="2016-07-15";
//		String date="2";
//		int flag=1;
		String date="12:52";
		int flag=0;
		Date current=new Date();
		if(flag==0){
			Date limit=new Date();
			String[] h=date.split(":");
			limit.setHours(Integer.parseInt(h[0]));
			limit.setMinutes(Integer.parseInt(h[1]));
			if(current.compareTo(limit)>0){
				System.out.println(">");
			}
			System.out.println("^^^^^^limit:"+new DateTime(limit).toString("yyyy-MM-dd HH:mm:ss")+",current:"+new DateTime(current).toString("yyyy-MM-dd HH:mm:ss"));
		}else if(flag==1){
			Date d=null;
			try {
				d=new SimpleDateFormat("yyyy-MM-dd").parse(yDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Date limit=new DateTime(d).plusDays(-Integer.parseInt(date)).toDate();
			if(limit.compareTo(current)>0){
				System.out.println(">");
			}
			System.out.println("******limit:"+new DateTime(limit).toString("yyyy-MM-dd HH:mm:ss")+",current:"+new DateTime(current).toString("yyyy-MM-dd HH:mm:ss"));
		}
		System.out.printf("new LocalDate().toString(FORMAT):%s\n",new LocalDate().toString(DATE_TIME_FORMAT));
		System.out.printf("new LocalTime(0,0,0,0).toDateTimeToday().toDate():%s\n",new LocalTime(0,0,0,0).toDateTimeToday().toDate());
	}
	
	@Test
	public void getTimeStamp(){
		System.out.println("timestamp:"+System.currentTimeMillis());
	}

	@Test
	public void parseDateTime(){
		String dateStr="2017-05-23 02:21:30";
		try {
			System.out.printf("new DateTime(\"%s\"):&s",dateStr,new DateTime(dateStr).toDate());
		}catch (Exception e){
			e.printStackTrace();
		}
		DateTimeFormatter formatter = DateTimeFormat.forPattern(DATE_TIME_FORMAT).withLocale(Locale.CHINA);
		DateTime dt = formatter.parseDateTime(dateStr);
		System.out.printf("date time:%s\n",dt.toString(DATE_TIME_FORMAT));
		try {
			DateTimeFormatter format = DateTimeFormat.forPattern(FORMAT).withLocale(Locale.CHINA);
			System.out.printf("format.parseDateTime(dateStr):%s",format.parseDateTime(dateStr));
		}catch (Exception e){
			e.printStackTrace();
		}
		Integer time=1469151612;
		System.out.println("日期:"+new DateTime(new Date(time*1000L)).toString("yyyy-MM-dd HH:mm:ss"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sdf.format(new Date(time*1000L));
		System.out.println(date);
		System.out.printf("new DateTime(new Date()).toString(DATE_TIME_FORMAT):%s",new DateTime(new Date()).toString(DATE_TIME_FORMAT));
	}

	@Test
	public void testDayOfWeek(){
		DateTime dateTime=new DateTime();
		switch (dateTime.getDayOfWeek()){
			case DateTimeConstants.MONDAY:
				System.out.printf("DateTimeConstants.MONDAY");
				break;
			case DateTimeConstants.TUESDAY:
				System.out.printf("Tuesday");
				break;
			case DateTimeConstants.WEDNESDAY:
				System.out.printf("wed");
				break;
			case DateTimeConstants.THURSDAY:
				System.out.printf("thus");
				break;
			case DateTimeConstants.FRIDAY:
				System.out.printf("FRIDAY");
				break;
		}
	}

	@Test
	public void testDateTimeProperties(){
		DateTime dateTime=DateTime.now();
		DateTime.Property month=dateTime.dayOfMonth();
		DateTime.Property week=dateTime.dayOfWeek();
		DateTime.Property year=dateTime.dayOfYear();
		System.out.printf("dateTime.dayOfMonth():%s,min:%s,max:%s\n",month.get(),month.getMinimumValue(),month.getMaximumValue());
		System.out.printf("dateTime.dayOfWeek():%s,min:%s,max:%s\n",week.get(),week.getMinimumValue(),week.getMaximumValue());
		System.out.printf("dateTime.dayOfYear():%s,min:%s,max:%s\n",year.get(),year.getMinimumValue(),year.getMaximumValue());
	}
}
