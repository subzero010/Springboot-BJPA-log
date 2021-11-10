package kr.co.redspring.blog.test;

import org.springframework.web.bind.annotation.*;

// 사용자가 요청 -> 응답(html파일)
// @Controller

// 사용자가 요청 -> 응답(Data)
@RestController
public class HttpTestController {

    private static final String TAG = "HttpControllerTest : ";
    @GetMapping("/http/lombok")
    public String lombokTest(){
        Member m = Member.builder().username("parkdr").password("1234").email("parkdr@naver.com").build();
        System.out.println(TAG + "getter : "+ m.getId());
        m.setId(100);
        System.out.println(TAG + "getter : "+ m.getId());
        return "lombok builder() TEST OK!";
    }

    //인터넷 브라우져 요청은 get요청만 할 수 있다.
    //http://localhost:8080/http/get (select)
    @GetMapping("/http/get")
    public String getTest(@RequestParam int id, Member m){
        return "get 요청 .. @RequestParam.id: "+ id + ", m.username: "+m.getUsername();
    }
    //http://localhost:8080/http/post (inset)  .. error
    @PostMapping("/http/post")
    public String postTest(@RequestBody Member m){//@RequestBody String text,
        //return "post 요청 .. @RequestBody.text: "+text;
        return "post 요청 .. m.username: "+m.getUsername();
    }




}
