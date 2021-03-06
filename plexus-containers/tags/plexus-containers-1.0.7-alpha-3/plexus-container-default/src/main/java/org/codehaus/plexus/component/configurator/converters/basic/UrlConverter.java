package org.codehaus.plexus.component.configurator.converters.basic;

/*
 * Copyright 2001-2006 Codehaus Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.codehaus.plexus.component.configurator.ComponentConfigurationException;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author <a href="mailto:brett@codehaus.org">Brett Porter</a>
 */
public class UrlConverter
    extends AbstractBasicConverter
{
    public boolean canConvert( Class type )
    {
        return type.equals( URL.class );
    }

    public Object fromString( String str )
        throws ComponentConfigurationException
    {
        try
        {
            return new URL( str );
        }
        catch ( MalformedURLException e )
        {
            throw new ComponentConfigurationException( "Unable to convert '" + str + "' to an URL", e );
        }
    }
}
