package kr.co.redspring.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
// save 응답할때 사용
public class ResponseDto<T> {  // 38강
    int status;
    T data;
}
