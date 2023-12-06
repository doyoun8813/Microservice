package org.msa.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ItemDTO {

    @NotBlank(message = "ID는 필수 입력값 입니다.")
    @Size(max = 10, message = "ID는 10자 이하로 작성 가능 합니다.")
    private String id;

    @Size(max = 20, message = "이름은 20자까지 작성 가능 합니다.")
    private String name;

    @Size(max = 200, message = "설명은 최대 200자까지 작성 가능 합니다.")
    private String description;

    @Positive
    private long count;
    private String regDts;
    private String updDts;
    
}