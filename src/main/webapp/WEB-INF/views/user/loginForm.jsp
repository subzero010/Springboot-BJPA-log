<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="../layout/header.jsp" %>
<div class="container">

<!-- ------------------------  -->
<!--  spring security 사용  -->

    <form action="/auth/loginProc" method="post">
      <div class="form-group">
        <label for="username">username:</label>
        <input type="username" class="form-control" placeholder="Enter Username" id="username" name="username">
      </div>
      <div class="form-group">
        <label for="password">Password:</label>
        <input type="password" class="form-control" placeholder="Enter password" id="password" name="password">
      </div>
      <!-- div class="form-group form-check">
        <label class="form-check-label">
          <input class="form-check-input" type="checkbox" name="remember"> Remember me
        </label>
      </div -->

        <button id="btn-login" class="btn btn-primary">로그인</button>
    </form>

</div>

<script type="text/javascript">
/* spring security 사용으로 인해 주석처리
    let index = {
        init: function(){
            $("#btn-login").on("click", ()=>{  // ()=>{}는 this를 바인딩하기 위해서!!!
                this.login();
            });
        }
        ,login: function(){
            let data = {
                username: $("#username").val(),
                password: $("#password").val(),
            }
            //console.log(data);

            $.ajax({
                type: "POST",
                url: "/api/login",
                data: JSON.stringify(data), // http body 데이타
                contentType: "application/json; charset=utf-8", // body 데이터 타입(MIME)
                dataType: "json" //서버에서 받은 응답type
            }).done(function(resp){
                location.href = "/";
            }).fail(function(error){
                alert(JSON.stringify(error));
            });
        }
    }
    index.init();
    */
</script>
<%@ include file="../layout/footer.jsp" %>