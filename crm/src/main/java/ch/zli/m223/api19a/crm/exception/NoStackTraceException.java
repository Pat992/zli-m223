package ch.zli.m223.api19a.crm.exception;

@SuppressWarnings("serial")
public class NoStackTraceException extends RuntimeException {
	public NoStackTraceException() { this(""); }
	public NoStackTraceException(String msg) { super(msg, null, false, false); }
}
