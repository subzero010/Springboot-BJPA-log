<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="../layout/header.jsp" %>
<div class="container">

    <form >
      <div class="form-group">
        <label for="username">username:</label>
        <input type="username" class="form-control" placeholder="Enter Username" id="username">
      </div>
      <div class="form-group">
        <label for="email">Email address:</label>
        <input type="email" class="form-control" placeholder="Enter Email" id="email">
      </div>
      <div class="form-group">
        <label for="password">Password:</label>
        <input type="password" class="form-control" placeholder="Enter password" id="password">
      </div>
    </form>
    <button id="btn-save" class="btn btn-primary">회원가입</button>


</div>
<script type="text/javascript">
    let index = {
        //init안의 this값이 ()=>{}를 사용했기 때문에.. 여기 this값과 동일함.. 만약  function(){}를 사용하면 ..밖에있는 this값은 윈도우this를 뜻하므로..
        /*
        let _this = this;//function(){}를 사용했을때!!!
        init: function(){
            $("#btn-save").on("click", function(){  // function(){}을 사용할 때는 꼭!! _this를 해야됨(밖에서 정의!!!)
                _this.save();
            });
        }
        */


        init: function(){
            $("#btn-save").on("click", ()=>{  // ()=>{}는 this를 바인딩하기 위해서!!!
                this.save();
            });
        }
        ,save: function(){
            //alert("save 호출!!");
            let data = {
                username: $("#username").val(),
                password: $("#password").val(),
                email: $("#email").val()
            }
            //console.log(data);

            $.ajax({
                type: "POST",
                url: "/auth/joinProc",
                data: JSON.stringify(data), // http body 데이타
                contentType: "application/json; charset=utf-8", // body 데이터 타입(MIME)
                dataType: "json" //서버에서 받은 응답type
            }).done(function(resp){
                if(resp.status === 500){
                    alert("회원가입 실패하였습니다.");
                }
                else{
                    alert("회원가입 완료되었습니다.");
                    location.href = "/";
                }
            }).fail(function(error){
                alert(JSON.stringify(error));
            });
        }
    }
    index.init();
</script>
<%@ include file="../layout/footer.jsp" %>

