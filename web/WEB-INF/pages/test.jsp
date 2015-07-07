<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${param.size() > 0}">
    <h1>${param.message}</h1>
</c:if>
<h1>Hello</h1>
