package org.msa.item;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.msa.item.controller.ItemController;
import org.msa.item.dto.ItemDTO;
import org.msa.item.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class ItemServiceApplicationTests {

    @Autowired
    private ItemService itemService;

    @Autowired
    MockMvc mvc;

    @BeforeEach
    public void setup(){
        mvc = MockMvcBuilders.standaloneSetup(new ItemController(itemService))
            .addFilter(new CharacterEncodingFilter("UTF-8", true))
            .build();
    }

    @Test
    @DisplayName("물품등록 테스트")
    void test() throws Exception{
        try{
            ObjectMapper objectMapper = new ObjectMapper();

            log.info(objectMapper.writeValueAsString(getTestItem()) + "");

            mvc.perform(post("/v1/item/add/C")
                .contentType(MediaType.APPLICATION_JSON)
                .header("accountId", "admin")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(getTestItem())))
                .andDo(print())
                .andExpect(status().isOk());
        } catch (Exception e){
            fail(ExceptionUtils.getStackTrace(e));
        }
    }

    private ItemDTO getTestItem(){
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId("test1");
        itemDTO.setName("상품명 테스트");
        itemDTO.setDescription("상품 설명 테스트");
        itemDTO.setCount(50);
        return itemDTO;
    }
}
