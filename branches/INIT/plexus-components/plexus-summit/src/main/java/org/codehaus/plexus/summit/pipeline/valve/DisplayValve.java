package org.codehaus.plexus.summit.pipeline.valve;

import org.codehaus.plexus.summit.display.Display;
import org.codehaus.plexus.summit.exception.SummitException;
import org.codehaus.plexus.summit.rundata.RunData;

import java.io.IOException;

/**
 * @todo the encoding needs to be configurable.
 * @todo Valves need to have an initialization phase
 */
public class DisplayValve
    extends AbstractValve
{
    public void invoke( RunData data )
        throws IOException, SummitException
    {
        try
        {
            Display display = (Display) data.lookup( Display.ROLE, "new" );

            display.render( data );
        }
        catch ( Exception e )
        {
            throw new SummitException( "Can't display!", e );
        }
    }
}
