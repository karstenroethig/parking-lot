package karstenroethig.parkinglot.springbootservicestopper;

import java.io.IOException;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

/**
 * A JMX client for the {@code SpringApplicationAdmin} MBean.
 * Permits to obtain information about a given Spring application.
 *
 * @author  Stephane Nicoll
 */
class SpringApplicationAdminClient
{
	/** Note: see SpringApplicationAdminJmxAutoConfiguration */
	static final String DEFAULT_OBJECT_NAME = "org.springframework.boot:type=Admin,name=SpringApplication";

	private final MBeanServerConnection connection;
	private final ObjectName objectName;

	/**
	 * Creates a new SpringApplicationAdminClient object.
	 */
	SpringApplicationAdminClient(MBeanServerConnection connection, String jmxName)
	{
		this.connection = connection;
		this.objectName = toObjectName(jmxName);
	}

	/**
	 * Check if the spring application managed by this instance is ready. Returns {@code false} if the mbean is not yet
	 * deployed so this method should be repeatedly called until a timeout is reached.
	 *
	 * @return  {@code true} if the application is ready to service requests
	 */
	public boolean isReady()
	{
		try
		{
			return (Boolean)this.connection.getAttribute(this.objectName, "Ready");
		}
		catch (InstanceNotFoundException ex)
		{
			return false; // Instance not available yet
		}
		catch (AttributeNotFoundException ex)
		{
			throw new IllegalStateException("Unexpected: attribute 'Ready' not available", ex);
		}
		catch (ReflectionException ex)
		{
			throw new IllegalStateException("Failed to retrieve Ready attribute", ex);
		}
		catch (MBeanException ex)
		{
			throw new IllegalStateException(ex.getMessage(), ex);
		}
		catch (IOException ex)
		{
			throw new IllegalStateException(ex.getMessage(), ex);
		}
	}

	/**
	 * Stop the application managed by this instance.
	 *
	 * @throws  IOException                if an I/O error occurs
	 * @throws  InstanceNotFoundException  if the lifecycle mbean cannot be found
	 */
	public void stop() throws IOException, InstanceNotFoundException
	{
		try
		{
			this.connection.invoke(this.objectName, "shutdown", null, null);
		}
		catch (ReflectionException ex)
		{
			throw new IllegalStateException("Shutdown failed", ex);
		}
		catch (MBeanException ex)
		{
			throw new IllegalStateException("Could not invoke shutdown operation", ex);
		}
	}

	private ObjectName toObjectName(String name)
	{
		try
		{
			return new ObjectName(name);
		}
		catch (MalformedObjectNameException ex)
		{
			throw new IllegalArgumentException("Invalid jmx name '" + name + "'", ex);
		}
	}

	/**
	 * Create a connector for an {@link javax.management.MBeanServer} exposed on the current machine and the current
	 * port. Security should be disabled.
	 *
	 * @param   port  the port on which the mbean server is exposed
	 *
	 * @return  a connection
	 *
	 * @throws  IOException  if the connection to that server failed
	 */
	public static JMXConnector connect(int port) throws IOException
	{
		String url = "service:jmx:rmi:///jndi/rmi://127.0.0.1:" + port + "/jmxrmi";
		JMXServiceURL serviceUrl = new JMXServiceURL(url);

		return JMXConnectorFactory.connect(serviceUrl, null);
	}
}
