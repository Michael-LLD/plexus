package org.codehaus.plexus.component.repository.io;

import org.codehaus.plexus.component.repository.ComponentDescriptor;
import org.codehaus.plexus.component.repository.ComponentRequirement;
import org.codehaus.plexus.component.repository.ComponentSetDescriptor;
import org.codehaus.plexus.component.repository.ComponentDependency;
import org.codehaus.plexus.configuration.xml.XmlPlexusConfiguration;
import org.codehaus.plexus.configuration.xml.Xpp3DomBuilder;
import org.codehaus.plexus.configuration.PlexusConfiguration;

import java.io.Reader;
import java.io.StringReader;


/**
 * @author <a href="mailto:jason@maven.org">Jason van Zyl</a>
 * @version $Id$
 * @todo these are all really tools for dealing with xml configurations so they
 * should be packaged as such.
 */
public class PlexusTools
{
    public static PlexusConfiguration buildConfiguration( Reader configuration )
        throws Exception
    {
        return new XmlPlexusConfiguration( Xpp3DomBuilder.build( configuration ) );
    }

    public static PlexusConfiguration buildConfiguration( String configuration )
        throws Exception
    {
        return buildConfiguration( new StringReader( configuration ) );
    }

    public static ComponentDescriptor buildComponentDescriptor( String configuration )
        throws Exception
    {
        return buildComponentDescriptor( buildConfiguration( configuration ) );
    }

    public static ComponentDescriptor buildComponentDescriptor( PlexusConfiguration configuration )
        throws Exception
    {
        ComponentDescriptor cd = new ComponentDescriptor();

        cd.setRole( configuration.getChild( "role" ).getValue() );

        cd.setRoleHint( configuration.getChild( "role-hint" ).getValue() );

        cd.setImplementation( configuration.getChild( "implementation" ).getValue() );

        cd.setVersion( configuration.getChild( "version" ).getValue() );

        cd.setComponentType( configuration.getChild( "component-type" ).getValue() );

        cd.setInstantiationStrategy( configuration.getChild( "instantiation-strategy" ).getValue() );

        cd.setLifecycleHandler( configuration.getChild( "lifecycle-handler" ).getValue() );

        cd.setComponentProfile( configuration.getChild( "component-profile" ).getValue() );

        cd.setComponentComposer( configuration.getChild( "component-composer" ).getValue() );

        cd.setComponentFactory( configuration.getChild( "component-factory" ).getValue() );

        cd.setDescription( configuration.getChild( "description" ).getValue() );

        cd.setAlias( configuration.getChild( "alias" ).getValue() );

        String s = configuration.getChild( "isolated-realm" ).getValue();

        if ( s != null )
        {
            cd.setIsolatedRealm( s.equals( "true" ) ? true : false );
        }

        cd.setConfiguration( configuration.getChild( "configuration" ) );

        // ----------------------------------------------------------------------
        // Requirements
        // ----------------------------------------------------------------------

        PlexusConfiguration[] requirements = configuration.getChild( "requirements" ).getChildren( "requirement" );

        for ( int i = 0; i < requirements.length; i++ )
        {
            PlexusConfiguration requirement = requirements[i];

            ComponentRequirement cr = new ComponentRequirement();

            cr.setRole( requirement.getChild( "role" ).getValue() );

            cr.setRoleHint( requirement.getChild( "role-hint" ).getValue() );

            cr.setFieldName( requirement.getChild( "field-name" ).getValue() );

            cd.addRequirement( cr );
        }

        return cd;
    }

    public static ComponentSetDescriptor buildComponentSet( PlexusConfiguration c )
        throws Exception
    {
        ComponentSetDescriptor csd = new ComponentSetDescriptor();

        // ----------------------------------------------------------------------
        // Components
        // ----------------------------------------------------------------------

        PlexusConfiguration[] components = c.getChild( "components" ).getChildren( "component" );

        for ( int i = 0; i < components.length; i++ )
        {
            PlexusConfiguration component = components[i];

            csd.addComponentDescriptor( buildComponentDescriptor( component ) );
        }

        // ----------------------------------------------------------------------
        // Dependencies
        // ----------------------------------------------------------------------

        PlexusConfiguration[] dependencies = c.getChild( "dependencies" ).getChildren( "dependency" );

        for ( int i = 0; i < dependencies.length; i++ )
        {
            PlexusConfiguration d = dependencies[i];

            ComponentDependency cd = new ComponentDependency();

            cd.setArtifactId( d.getChild( "artifact-id" ).getValue() );

            cd.setGroupId( d.getChild( "group-id" ).getValue() );

            cd.setType( d.getChild( "type" ).getValue() );

            cd.setVersion( d.getChild( "version" ).getValue() );

            csd.addDependency( cd );
        }

        return csd;
    }
}
