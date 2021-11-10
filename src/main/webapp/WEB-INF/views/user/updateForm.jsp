<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="../layout/header.jsp" %>
<div class="container">

    <!-- 회원수정 from --->
    <form >
      <div class="form-group">
        <label for="username">username:</label>
        <input type="username" class="form-control"  id="username" value="${principal.user.username}" readonly>
        <input type="text"  id="id" value="${principal.user.id}" >
        <input type="text"  id="username" value="${principal.user.username}" >
      </div>
      <div class="form-group">
        <label for="password">Password:</label>
        <input type="password" class="form-control" placeholder="Enter Password" value="" id="password">
      </div>
      <div class="form-group">
        <label for="email">Email address:</label>
        <input type="email" class="form-control" placeholder="Enter Email" value="${principal.user.email}" id="email">
      </div>
    </form>
    <button id="btn-update" class="btn btn-primary">수정</button>


</div>
<script type="text/javascript">
    let index = {
        init: function(){
            $("#btn-update").on("click", ()=>{  // ()=>{}는 this를 바인딩하기 위해서!!!
                this.fn_update();
            });
        }
        ,fn_update: function(){
            let data = {
                id: $("#id").val(),
                username: $("#username").val(),
                password: $("#password").val(),
                email: $("#email").val()
            }
            //console.log(data);

            $.ajax({
                type: "PUT",
                url: "/user/updateProc",
                data: JSON.stringify(data), // http body 데이타
                contentType: "application/json; charset=utf-8", // body 데이터 타입(MIME)
                dataType: "json" //서버에서 받은 응답type
            }).done(function(resp){
                alert("수정되었습니다.");
                location.href = "/";
            }).fail(function(error){
                alert(JSON.stringify(error));
            });
        }
    }
    index.init();
</script>
<%@ include file="../layout/footer.jsp" %>

