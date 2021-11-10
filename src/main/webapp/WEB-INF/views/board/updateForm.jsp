<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="../layout/header.jsp" %>
<div class="container">

<!--글쓰기 수정 화면-->
    <form >
      <div class="form-group">
        <label for="title">Title:</label>
        <input type="text" class="form-control" placeholder="Enter Title" id="title" name="title" value="${board.title}">
      </div>

      <div class="form-group">
        <label for="content">Content:</label><!-- summer note 적용 -->
        <textarea class="form-control summernote" rows="5" id="content" name="content">${board.content}</textarea>
      </div>
      <input type="text" id="id" name="id" value="${board.id}" />
    </form>
    <button id="btn-update" class="btn btn-primary">글쓰기 수정</button>

</div>
<script type="text/javascript">
      $('.summernote').summernote({
        tabsize: 2,
        height: 300,
        toolbar: [
          ['style', ['style']],
          ['font', ['bold', 'underline', 'clear']],
          ['color', ['color']],
          ['para', ['ul', 'ol', 'paragraph']],
          ['table', ['table']],
          ['insert', ['link', 'picture', 'video']],
          ['view', ['fullscreen', 'codeview', 'help']]
        ]
      });
    let index = {
        init: function(){
            $("#btn-update").on("click", ()=>{  // ()=>{}는 this를 바인딩하기 위해서!!!
                this.fn_update();
            });
        }
        ,fn_update: function(){
            let id = $("#id").val()
            let data = {
                title: $("#title").val(),
                content: $("#content").val()
            }

            $.ajax({
                type: "PUT",
                url: "/api/board/"+id,
                data: JSON.stringify(data), // http body 데이타
                contentType: "application/json; charset=utf-8", // body 데이터 타입(MIME)
                dataType: "json" //서버에서 받은 응답type
            }).done(function(resp){
                alert("수정되었습니다.");
                location.href = "<c:url value='/board/' />" + "${board.id}";
            }).fail(function(error){
                alert(JSON.stringify(error));
            });
        }
    }
    index.init();
</script>
<%@ include file="../layout/footer.jsp" %>