package org.codehaus.plexus.appserver.service.configurator;

import org.codehaus.plexus.logging.AbstractLogEnabled;
import org.codehaus.plexus.component.configurator.ComponentConfigurator;
import org.codehaus.plexus.component.configurator.ComponentConfigurationException;
import org.codehaus.plexus.configuration.PlexusConfiguration;
import org.codehaus.plexus.appserver.service.PlexusService;
import org.codehaus.classworlds.ClassRealm;

/**
 * @author Jason van Zyl
 * @version $Id$
 */
public class DefaultServiceConfigurator
    extends AbstractLogEnabled
    implements ServiceConfigurator
{
    private ComponentConfigurator configurator;

    public Object configure( Object serviceConfiguration,
                             PlexusConfiguration configuration,
                             ClassRealm realm )
        throws ComponentConfigurationException
    {
        configurator.configureComponent( serviceConfiguration, configuration, realm );

        return serviceConfiguration;
    }
}