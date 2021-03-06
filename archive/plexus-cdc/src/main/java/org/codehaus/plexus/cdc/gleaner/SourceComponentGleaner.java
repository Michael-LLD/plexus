/*
 * Copyright (C) 2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.codehaus.plexus.cdc.gleaner;

import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaClassCache;
import org.codehaus.plexus.cdc.gleaner.ComponentGleanerException;
import org.codehaus.plexus.component.repository.cdc.ComponentDescriptor;

/**
 * Interface for component gleaners which glean off of source code.
 *
 * @version $Id$
 */
public interface SourceComponentGleaner
{
    String ROLE = SourceComponentGleaner.class.getName();

    ComponentDescriptor glean(JavaClassCache classCache, JavaClass javaClass) throws ComponentGleanerException;
}