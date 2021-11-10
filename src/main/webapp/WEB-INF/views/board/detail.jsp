<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="../layout/header.jsp" %>
<div class="container">

<!--글쓰기 화면-->
  <form name="frm">
    <input type="text" id="id" value="${board.id}" />
      <div >
        <label for="title"></label>
        글번호: <span><i>${board.id}</i></span>
        작성자: <span><i>${board.user.username}</i></span>
        작성일시: <span><i>${board.createDate}</i></span>
      </div>
      <div class="form-group">
        <label for="title">Title:</label>
        <h3>${board.title}</h3>
      </div>
      <hr/>
      <div class="form-group">
        <label for="content">Content:</label>
        <div>${board.content}</div>
      </div>

      <!-- 댓글 67강(무한 참조(반복) 문제!!)--------->
      <div class="card">
        <div class="card-body"><textarea id="reply-content" rows="1" class="form-control"></textarea></div>
        <div class="card-footer">
            <button type="button" id="btn-reply-save" class="btn btn-primary" >등록</button>
        </div>
      </div>
      <br/>
      <!-- 댓글 목록 ----->
      <div class="card">
        <div class="card-header">댓글 리스트</div>
        <ul id="reply-box" class="list-group">
            <c:forEach var="reply" items="${board.replys}">
            <li id="reply-${reply.id}" class="list-group-item d-flex justify-content-between">
                <div>${reply.content}</div>
                <div class="d-flex">
                    <div class="font-italic">작성자 : ${reply.user.username}&nbsp;</div>
                    <c:if test="${reply.user.id==principal.user.id}">
                        <button type="button" class="btn btn-outline-secondary btn-sm" onClick="index.replyDelete(${board.id}, ${reply.id});">삭제</button>
                    </c:if>
                </div>
            </li>
            </c:forEach>
        </ul>
      </div>


  </form>

  <div >
      <button id="btn-list"  class="btn btn-secondary">목록</button>
      <c:if test="${board.user.id == principal.user.id}">
        <button id="btn-uptForm" class="btn btn-warning">수정</button>
        <button id="btn-delete" class="btn btn-danger">삭제</button>
      </c:if>
  </div>

</div>
<script type="text/javascript">
    let index = {
        init: function(){
            $("#btn-list").on("click", ()=>{  // ()=>{}는 this를 바인딩하기 위해서!!!
                this.fn_goList();
            });
            $("#btn-uptForm").on("click", ()=>{
                this.fn_goDetail();
            });
            $("#btn-delete").on("click", ()=>{
                this.fn_delete();
            });
            $("#btn-reply-save").on("click", ()=>{
                this.fn_replySave();
            });
        }
        ,fn_goList: function(){
            location.href="/";
        }
        ,fn_goDetail: function(){
            location.href = "<c:url value='/board/' />" + "${board.id}" + "/updateForm";
            //var frm = document.frm;
            //frm.method = "GET";
            //frm.action = "<c:url value='/board/' />" + "${board.id}" + "/updateForm";
            //frm.submit();
        }
        ,fn_delete: function(){
            $.ajax({
                type: "DELETE",
                url: "<c:url value='/api/board/' />"+ $("#id").val(),
                //contentType: "application/json; charset=utf-8", // body 데이터 타입(MIME)
                dataType: "json" //서버에서 받은 응답type
            }).done(function(resp){
                alert("삭제되었습니다.");
                location.href = "<c:url value='/' />";
            }).fail(function(error){
                alert(JSON.stringify(error));
            });
        }
        ,fn_replySave: function(){  //69강 dto 사용한 예제
            let data = {
                userId: ${principal.user.id},
                boardId: $("#id").val(),
                content: $("#reply-content").val()
            }
            $.ajax({
                type: "POST",
                //url: `/api/board/${data.boardId}/reply_dto`,  //${data.boardId}값이 set안됨 ㅠㅠ
                url: "/api/board/"+data.boardId+"/reply_dto",
                contentType: "application/json; charset=utf-8", // body 데이터 타입(MIME)
                data: JSON.stringify(data), // http body 데이타
                dataType: "json" //서버에서 받은 응답type
            }).done(function(resp){
                if(resp.status === 500){
                    alert("실패했습니다."+resp.status);
                }
                else{
                    alert("등록되었습니다."+resp.status);
                    location.href = "/board/"+data.boardId;
                }
            }).fail(function(error){
                alert(JSON.stringify(error));
            });
        }
        ,__fn_replySave: function(){//68강 dto 사용안한예제
            //let boardId = $("#id").val();
            let data = {
                content: $("#reply-content").val()
            }
            //console.log(">> boardId : "+ boardId);
            console.log(data);
            $.ajax({
                type: "POST",
                //url: `/api/board/${boardId}/reply`,  // (`)javascript변수값이 문자열로 들어간다
                url: "<c:url value='/api/board/' />"+ $("#id").val() + "/reply",
                contentType: "application/json; charset=utf-8", // body 데이터 타입(MIME)
                data: JSON.stringify(data), // http body 데이타
                dataType: "json" //서버에서 받은 응답type
            }).done(function(resp){
                alert("답글 등록되었습니다.");
                location.href = "<c:url value='/board/' />"+ $("#id").val();
            }).fail(function(error){
                alert(JSON.stringify(error));
            });
        }
        ,replyDelete: function(boardId, replyId){//73강 댓글삭제
            $.ajax({
                type: "DELETE",
                url: "/api/board/"+boardId+"/reply/"+replyId,  // (`)javascript변수값이 문자열로 들어간다
                contentType: "application/json; charset=utf-8", // body 데이터 타입(MIME)
                //data: JSON.stringify(data), // http body 데이타
                dataType: "json" //서버에서 받은 응답type
            }).done(function(resp){
                if(resp.status === 500){
                    alert("실패했습니다."+resp.status);
                }
                else{
                    alert("답글 삭제되었습니다."+resp.status);
                    location.href = "<c:url value='/board/' />"+ boardId;
                }
            }).fail(function(error){
                alert(JSON.stringify(error));
            });
        }
    }
    index.init();
</script>
<%@ include file="../layout/footer.jsp" %>