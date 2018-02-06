package karstenroethig.parkinglot.springbootservicestopper;

import java.rmi.UnmarshalException;

import javax.management.InstanceNotFoundException;
import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;

/**
 * Stop a Spring Boot application as a service.
 *
 * @author  Stephane Nicoll
 */
public class StopSpringBootService
{
	/**
	 * This main method is executed by the service.exe when the service is stopped.
	 */
	public static void main(String[] args) throws Exception
	{
		System.out.println("Stopping Spring Boot application...");

		if (args == null || args.length < 1 )
		{
			throw new IllegalArgumentException("no args");
		}

		int jmxPort = Integer.parseInt(args[0]);
		String jmxName = SpringApplicationAdminClient.DEFAULT_OBJECT_NAME;
		JMXConnector connector = SpringApplicationAdminClient.connect(jmxPort);

		try
		{
			MBeanServerConnection connection = connector.getMBeanServerConnection();

			try
			{
				new SpringApplicationAdminClient(connection, jmxName).stop();
			}
			catch (InstanceNotFoundException ex)
			{
				throw new IllegalStateException("Spring application lifecycle JMX bean not found, could not stop application gracefully", ex);
			}
		}
		finally
		{
			try
			{
				connector.close();
			}
			catch (UnmarshalException ex)
			{
				if (!"Connection reset".equals(ex.getCause().getMessage()))
				{
					System.err.println("Error on closing jmx connection");
					ex.printStackTrace(System.err);
				}
			}
		}
	}
}
