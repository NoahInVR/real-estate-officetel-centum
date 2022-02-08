package com.busancentum.realestatenoah.service;

import com.busancentum.realestatenoah.dto.PageRequestDTO;
import com.busancentum.realestatenoah.dto.PageResultDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.busancentum.realestatenoah.dto.FreeBoardDTO;
import com.busancentum.realestatenoah.entity.FreeBoard;


@SpringBootTest
public class FreeBoardServiceTests {

    @Autowired
    private FreeBoardService service;

    @Test
    public void testRegister() {

        FreeBoardDTO freeBoardDTO = FreeBoardDTO.builder()
                .title("Sample Title....")
                .content("Sample Content...")
                .writer("user0")
                .build();

        System.out.println(service.register(freeBoardDTO));
    }

    @Test
    public void testList(){

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).
                size(10).build();

        PageResultDTO<FreeBoardDTO, FreeBoard> resultDTO = service.
                getList(pageRequestDTO);

        System.out.println("PREV: " + resultDTO.isPrev());
        System.out.println("NEXT: " + resultDTO.isNext());
        System.out.println("TOTAL: " + resultDTO.getTotalPage());

        System.out.println("--------------------------------------");
        for (FreeBoardDTO freeBoardDTO : resultDTO.getDtoList()) {
            System.out.println(freeBoardDTO);
        }

        System.out.println("=======================================");
        resultDTO.getPageList().forEach(i -> System.out.println(i));
    }

    @Test
    public void testSearch() {

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .type("tc") // 검색 조건 t, c, w, tc, tcw...
                .keyword("한글") //검색 키워드
                .build();

        PageResultDTO<FreeBoardDTO, FreeBoard> resultDTO = service.
                getList(pageRequestDTO);

        System.out.println("PREV: "+resultDTO.isPrev());
        System.out.println("NEXT: "+resultDTO.isNext());
        System.out.println("TOTAL: "+resultDTO.getTotalPage());

        System.out.println("---------------------------------------");
        for (FreeBoardDTO freeBoardDTO : resultDTO.getDtoList()) {
            System.out.println(freeBoardDTO);
        }

        System.out.println("==========================================");
        resultDTO.getPageList().forEach(i -> System.out.println(i));
    }

}
