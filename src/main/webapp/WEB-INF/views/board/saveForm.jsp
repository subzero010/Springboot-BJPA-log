<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="../layout/header.jsp" %>
<div class="container">

<!--글쓰기 화면-->
    <form >
      <div class="form-group">
        <label for="title">Title:</label>
        <input type="text" class="form-control" placeholder="Enter Title" id="title" name="title">
      </div>

      <div class="form-group">
        <label for="content">Content:</label><!-- summer note 적용 -->
        <textarea class="form-control summernote" rows="5" id="content" name="content"></textarea>
      </div>
    </form>
    <button id="btn-save" class="btn btn-primary">글쓰기</button>

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
            $("#btn-save").on("click", ()=>{  // ()=>{}는 this를 바인딩하기 위해서!!!
                this.save();
            });
        }
        ,save: function(){
            //alert("save 호출!!");
            let data = {
                title: $("#title").val(),
                content: $("#content").val()
            }

            $.ajax({
                type: "POST",
                url: "/api/board",
                data: JSON.stringify(data), // http body 데이타
                contentType: "application/json; charset=utf-8", // body 데이터 타입(MIME)
                dataType: "json" //서버에서 받은 응답type
            }).done(function(resp){
                alert("저장되었습니다.");
                location.href = "/";
            }).fail(function(error){
                alert(JSON.stringify(error));
            });
        }
    }
    index.init();
</script>
<%@ include file="../layout/footer.jsp" %>