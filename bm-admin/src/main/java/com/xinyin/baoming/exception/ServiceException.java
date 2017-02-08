package com.xinyin.baoming.exception;
/**
 * 应用Service异常
 * 
 * @author HeMingwei
 *
 */
public class ServiceException extends RuntimeException {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -4287330644728346269L;
	public ServiceException() {
		super();
	}
	public ServiceException(String arg0){
		super(arg0);
	}
	public ServiceException(Throwable throwable){
		super(throwable);
	}
	public ServiceException(String s,Throwable throwable){
		super(s, throwable);
	}
}
