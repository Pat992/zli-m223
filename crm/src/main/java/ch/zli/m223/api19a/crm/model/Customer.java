package ch.zli.m223.api19a.crm.model;

import java.util.List;

public interface Customer {
	Long getId();
	String getName();
	String getStreet();
	String getCity();
	List<Memo> getMemos();
}
