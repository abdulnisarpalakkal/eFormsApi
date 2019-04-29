package com.focowell.config.error;

import java.io.IOException;

public class StorageException extends Exception {
	public StorageException(String msg)
	{
		 super(msg);
		
	}

	public StorageException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
