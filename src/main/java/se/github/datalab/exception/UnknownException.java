package se.github.datalab.exception;

@SuppressWarnings("serial")
public class UnknownException extends RuntimeException
{
	public UnknownException(Throwable e)
	{
		super(e);
	}
}
