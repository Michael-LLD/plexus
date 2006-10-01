<%--
  ~ Copyright 2005-2006 The Codehaus.
  ~
  ~ Licensed under the Apache License, Version 2.0 (the "License");
  ~ you may not use this file except in compliance with the License.
  ~ You may obtain a copy of the License at
  ~
  ~      http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing, software
  ~ distributed under the License is distributed on an "AS IS" BASIS,
  ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  ~ See the License for the specific language governing permissions and
  ~ limitations under the License.
  --%>

<%@ taglib prefix="ww" uri="/webwork"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h2>Currently Assigned Roles</h2>

<c:choose>
  <c:when test="${!empty assignedRoles}">

    <ww:form action="removeRolesFromUser" namespace="/security" name="removeRoles">
      <ww:hidden name="principal" value="${username}"/>
      <ww:hidden name="removeRolesButton" value="true"/>
      <ww:checkboxlist list="assignedRoles" name="removeSelectedRoles" listValue="name" listKey="name" theme="pss"/>
      <br/>
      <ww:submit value="Remove Selected Roles" name="removeRolesButton" theme="simple" />
    </ww:form>
  </c:when>
  <c:otherwise>
    <p><em>No Roles Assigned (yet)</em></p>
  </c:otherwise>
</c:choose>

<%--<pss:ifAnyAuthorized permissions="grant-roles,remove-roles">--%>
  <h2>Role Management</h2>

<%--  <pss:ifAuthorized permission="grant-roles">--%>

  <h3>Grant</h3>

<c:choose>
  <c:when test="${!empty availableRoles}">
    <ww:form action="addRolesToUser" namespace="/security" name="addRoles">
      <ww:hidden name="principal" value="${username}"/>
      <ww:hidden name="addRolesButton" value="true"/>
      <ww:checkboxlist list="availableRoles" name="addSelectedRoles" listValue="name" listKey="name" theme="pss"/>
      <br/>
      <ww:submit value="Add Selected Roles" name="addRolesButton" theme="simple" />
    </ww:form>
  </c:when>
  <c:otherwise>
    <p><em>No Roles Available to Grant</em></p>
  </c:otherwise>
</c:choose>

<%--  </pss:ifAuthorized> --%>

<%--</pss:ifAnyAuthorized>--%>