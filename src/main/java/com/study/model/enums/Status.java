package com.study.model.enums;

public enum Status {
	/**
	 * <code>PENDING = 1;</code>
	 *
	 * <pre>
	 * 操作尚未开始
	 * </pre>
	 */
	PENDING(1),
	/**
	 * <code>RUNNING = 2;</code>
	 *
	 * <pre>
	 * 操作开始
	 * </pre>
	 */
	RUNNING(2),
	/**
	 * <code>OK = 3;</code>
	 *
	 * <pre>
	 * 操作正常结束
	 * </pre>
	 */
	OK(3),
	/**
	 * <code>WARN = 4;</code>
	 *
	 * <pre>
	 * 有警告，但是正常结束
	 * </pre>
	 */
	WARN(4),
	/**
	 * <code>ERROR = 5;</code>
	 *
	 * <pre>
	 * 有错误，但是完整结束
	 * </pre>
	 */
	ERROR(5),
	/**
	 * <code>FAIL = 6;</code>
	 *
	 * <pre>
	 * 操作失败
	 * </pre>
	 */
	FAIL(6), ;

	/**
	 * <code>PENDING = 1;</code>
	 *
	 * <pre>
	 * 操作尚未开始
	 * </pre>
	 */
	public static final int PENDING_VALUE = 1;
	/**
	 * <code>RUNNING = 2;</code>
	 *
	 * <pre>
	 * 操作开始
	 * </pre>
	 */
	public static final int RUNNING_VALUE = 2;
	/**
	 * <code>OK = 3;</code>
	 *
	 * <pre>
	 * 操作正常结束
	 * </pre>
	 */
	public static final int OK_VALUE = 3;
	/**
	 * <code>WARN = 4;</code>
	 *
	 * <pre>
	 * 有警告，但是正常结束
	 * </pre>
	 */
	public static final int WARN_VALUE = 4;
	/**
	 * <code>ERROR = 5;</code>
	 *
	 * <pre>
	 * 有错误，但是完整结束
	 * </pre>
	 */
	public static final int ERROR_VALUE = 5;
	/**
	 * <code>FAIL = 6;</code>
	 *
	 * <pre>
	 * 操作失败
	 * </pre>
	 */
	public static final int FAIL_VALUE = 6;

	public final int getNumber() {
		return value;
	}

	public static Status valueOf(int value) {
		switch (value) {
		case 1:
			return PENDING;
		case 2:
			return RUNNING;
		case 3:
			return OK;
		case 4:
			return WARN;
		case 5:
			return ERROR;
		case 6:
			return FAIL;
		default:
			return null;
		}
	}

	private final int value;

	private Status(int value) {
		this.value = value;
	}

}
