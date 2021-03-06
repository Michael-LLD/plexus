package org.codehaus.plexus.formica.web.summary;

import com.thoughtworks.xstream.xml.XMLWriter;
import ognl.Ognl;
import ognl.OgnlException;
import org.codehaus.plexus.formica.Element;
import org.codehaus.plexus.formica.Form;
import org.codehaus.plexus.formica.Operation;
import org.codehaus.plexus.formica.SummaryElement;
import org.codehaus.plexus.formica.web.AbstractFormRenderer;
import org.codehaus.plexus.formica.web.FormRenderingException;
import org.codehaus.plexus.i18n.I18N;
import org.codehaus.plexus.util.StringUtils;

import java.util.Collection;
import java.util.Iterator;

/*
 * Copyright 2001-2004 The Apache Software Foundation.
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

/**
 * @author <a href="mailto:jason@maven.org">Jason van Zyl</a>
 * @version $Id$
 */
public class SummaryFormRenderer
    extends AbstractFormRenderer
{
    public String getHeaderTitle( Form form, I18N i18n )
    {
        System.out.println( "form.getSummary() = " + form.getSummary().getTitleKey() );

        return i18n.getString( form.getSummary().getTitleKey() );
    }

    public void body( Form form, XMLWriter w, I18N i18n, Object data, String baseUrl )
        throws FormRenderingException
    {
        w.startElement( "table" );

        w.addAttribute( "cellpadding", "3" );

        w.addAttribute( "cellspacing", "2" );

        w.addAttribute( "border", "1" );

        w.addAttribute( "width", "100%" );

        // ----------------------------------------------------------------------
        // Headings
        // ----------------------------------------------------------------------

        w.startElement( "tr" );

        for ( Iterator i = form.getSummary().getSummaryElements().iterator(); i.hasNext(); )
        {
            SummaryElement se = (SummaryElement) i.next();

            Element element = form.getElement( se.getId() );

            w.startElement( "th" );

            w.writeText( i18n.getString( element.getLabelKey() ) );

            w.endElement();
        }

        w.startElement( "th" );

        w.addAttribute( "colspan", "3" );

        w.writeText( "Operations" );

        w.endElement();

        w.endElement();

        // ----------------------------------------------------------------------
        // Summary
        // ----------------------------------------------------------------------
        // Here we have a collection of the form's target object and we want to
        // extract the fields from the object that correspond to what we want to
        // show in the summary.
        // ----------------------------------------------------------------------

        Collection collection = (Collection) data;

        // We need to know what elements are going to be used for
        // the summary. How do we define that? Really I need a list
        // of expressions

        String rowClass = "a";

        for ( Iterator i = collection.iterator(); i.hasNext(); )
        {
            w.startElement( "tr" );

            w.addAttribute( "class", rowClass );

            Object item = i.next();

            for ( Iterator j = form.getSummary().getSummaryElements().iterator(); j.hasNext(); )
            {
                SummaryElement se = (SummaryElement) j.next();

                Element element = form.getElement( se.getId() );

                w.startElement( "td" );

                try
                {
                    w.writeText( Ognl.getValue( element.getExpression(), item ).toString() );
                }
                catch ( OgnlException e )
                {
                    throw new FormRenderingException( "Error extracting value from " + item + " using the expression " + element.getExpression() );
                }

                w.endElement();
            }

            // ----------------------------------------------------------------------
            // Operations
            // ----------------------------------------------------------------------

            for ( Iterator j = form.getSummary().getOperations().iterator(); j.hasNext(); )
            {
                Operation op = (Operation) j.next();

                w.startElement( "td" );

                w.startElement( "a" );

                String id = null;

                try
                {
                    id = (String) Ognl.getValue( form.getKeyExpression(), item );
                }
                catch ( OgnlException e )
                {
                    e.printStackTrace();
                }

                w.addAttribute( "href", baseUrl + "/" + StringUtils.replace( op.getAction(), "$id$", id ) );

                w.writeText( op.getName() );

                w.endElement();

                w.endElement();
            }

            // ----------------------------------------------------------------------

            w.endElement();

            if ( rowClass.equals( "a" ) )
            {
                rowClass = "b";
            }
            else
            {
                rowClass = "a";
            }
        }

        w.endElement(); //table
    }
}
