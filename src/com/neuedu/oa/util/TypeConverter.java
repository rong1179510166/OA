package com.neuedu.oa.util;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public abstract class TypeConverter {
	private TypeConverter() {}
	
	public static Timestamp toTimestamp(java.util.Date d ) {
		if (d != null) {
			return  new Timestamp(d.getTime());
		} 
		return null;
	}
	
	public static Timestamp toTimestamp(LocalDateTime d ) {
		if (d != null) {
			return Timestamp.valueOf(d);
		} 
		return null;
	}
	
	public static Timestamp toTimestamp(Instant instant) {
		if (instant != null) {
			return  new Timestamp(instant.getEpochSecond()*1000);
		} 
		return null;
	}
	
	public static LocalDateTime toLocalDateTime(Timestamp  t) {
		if (t != null) {
			return  t.toLocalDateTime();
		} 
		return null;
	}
	public static Instant toInstant(Timestamp timestamp ) {
		if (timestamp != null) {
			return  timestamp.toInstant();
		} 
		return null;
	}
	public static LocalDate toLocalDate(java.sql.Date d) {
		if (d != null) {
			return  d.toLocalDate();
		} 
		return null;
	}
	public static java.sql.Date toSqlDate(LocalDate localDate) {
		if (localDate != null) {
			return  java.sql.Date.valueOf(localDate);
		} 
		return null;
	}
	public static LocalTime  toLocalTime(java.sql.Time time) {
		if(time!=null) {
			return time.toLocalTime();
		}
		return null;
	}
	public static java.sql.Time  toSqlTime(LocalTime time) {
		if(time!=null) {
			return Time.valueOf(time);
		}
		return null;
	}
	
}
