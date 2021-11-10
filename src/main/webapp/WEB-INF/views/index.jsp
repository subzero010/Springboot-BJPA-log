<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="layout/header.jsp" %>
<div class="container">

<c:forEach var="list" items="${list.content}"><!-- page로 받을때는 ?.content를 붙여줘야됨 -->

    <div class="card m-2" ><!--m-2 마진 -->
      <div class="card-body">
        <h4 class="card-title">${list.title}<h4>
        <h4 class="card-content">작성자 : ${list.user.username}<h4>
        <a href="/board/${list.id}" class="btn btn-primary">상세보기</a>
      </div>
    </div>
</c:forEach>

    <ul class="pagination justify-content-end"><!-- display:flax 속성을 정렬할때 justify-content-end, center, start -->

      <c:choose>
        <c:when test="${list.first}">
        <li class="page-item disabled"><a class="page-link" href="#">Previous</a></li>
        </c:when>
        <c:otherwise>
        <li class="page-item"><a class="page-link" href="?page=${list.number-1}">Previous</a></li>
        </c:otherwise>
    </c:choose>

      <!-- li class="page-item"><a class="page-link" href="#">1</a></li  -->


      <c:choose>
        <c:when test="${list.last}">
        <li class="page-item disabled"><a class="page-link" href="#">Next</a></li>
        </c:when>
        <c:otherwise>
        <li class="page-item"><a class="page-link" href="?page=${list.number+1}">Next</a></li>
        </c:otherwise>
    </c:choose>
    </ul>
</div>

<script type="text/javascript">

</script>
<%@ include file="layout/footer.jsp" %>