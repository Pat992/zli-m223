package ch.zli.m223.api19a.crm.model;

public interface Memo {
	Long getId();
	long getCustomerId();
	long getTimeInMs();
	String getNote();
}
