package org.codehaus.plexus.security.ui.web.jsp;

/*
 * Copyright 2006 The Codehaus.
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

import com.opensymphony.xwork.ActionContext;
import org.codehaus.plexus.PlexusContainer;
import org.codehaus.plexus.component.repository.exception.ComponentLookupException;
import org.codehaus.plexus.security.authorization.AuthorizationException;
import org.codehaus.plexus.security.authorization.AuthorizationDataSource;
import org.codehaus.plexus.security.authorization.memory.MemoryAuthorizationDataSource;
import org.codehaus.plexus.security.system.SecuritySession;
import org.codehaus.plexus.security.system.SecuritySystem;
import org.codehaus.plexus.security.user.User;
import org.codehaus.plexus.xwork.PlexusLifecycleListener;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.jstl.core.ConditionalTagSupport;

/**
 * IfAuthorizedTag:
 *
 * @author Jesse McConnell <jesse@codehaus.org>
 * @version $Id:$
 */
public class IfAuthorizedTag
    extends ConditionalTagSupport

{
    private String permission;

    public void setPermission( String permission )
    {
        this.permission = permission;
    }

    protected boolean condition()
        throws JspTagException
    {
        ActionContext context = ActionContext.getContext();

        PlexusContainer container = (PlexusContainer) context.getApplication().get( PlexusLifecycleListener.KEY );
        SecuritySession securitySession = (SecuritySession) context.getSession().get( SecuritySession.ROLE );

        try
        {
            SecuritySystem securitySystem = (SecuritySystem) container.lookup( SecuritySystem.ROLE );

            //todo make this not suck
            User user = securitySession.getUser();


            AuthorizationDataSource source = new MemoryAuthorizationDataSource( user.getPrincipal(), user, permission );

            return securitySystem.isAuthorized( source );
        }
        catch ( ComponentLookupException cle )
        {
            throw new JspTagException( "unable to locate security system", cle );
        }
        catch ( AuthorizationException ae )
        {
            throw new JspTagException( "error with authorization", ae );
        }
    }
}
