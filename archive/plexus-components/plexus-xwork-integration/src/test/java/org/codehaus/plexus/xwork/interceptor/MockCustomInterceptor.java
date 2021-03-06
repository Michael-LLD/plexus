package org.codehaus.plexus.xwork.interceptor;

/*
 * Copyright 2006-2007 The Codehaus Foundation.
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

import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.interceptor.Interceptor;

/**
 * @author <a href='mailto:rahul.thakur.xdev@gmail.com'>Rahul Thakur</a>
 * @version $Id$
 * @plexus.component role="com.opensymphony.xwork.interceptor.Interceptor role-hint="testCustomInterceptor"
 */
public class MockCustomInterceptor
    implements Interceptor
{
    /**
     * @plexus.requirement
     */
    private MockComponent testComponent;

    public MockCustomInterceptor()
    {
    }

    public MockCustomInterceptor( MockComponent testComponent )
    {
        this.testComponent = testComponent;
    }

    /* (non-Javadoc)
     * @see com.opensymphony.xwork.interceptor.Interceptor#destroy()
     */
    public void destroy()
    {
        // do nothing
    }

    /* (non-Javadoc)
     * @see com.opensymphony.xwork.interceptor.Interceptor#init()
     */
    public void init()
    {
        // do nothing
    }

    /**
     * @noinspection ProhibitedExceptionDeclared
     */
    public String intercept( ActionInvocation invocation )
        throws Exception
    {
        String result = "Hello Custom Interceptor";

        testComponent.displayResult( result );

        return result;
    }

    public MockComponent getTestComponent()
    {
        return testComponent;
    }

    // Introduce a Composition Exception , see PLX - 278 
    //    public void setTestComponent( MockComponent testComponent )
    //    {
    //        this.testComponent = testComponent;
    //    }

}
