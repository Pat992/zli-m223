package ch.zli.m223.api19a.crm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Error finding Memo")
public class MemoNotFoundException extends RuntimeException {}
