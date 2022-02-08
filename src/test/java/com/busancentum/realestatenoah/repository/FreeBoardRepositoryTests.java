package com.busancentum.realestatenoah.repository;

import com.busancentum.realestatenoah.entity.FreeBoard;
import com.busancentum.realestatenoah.entity.QFreeBoard;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class FreeBoardRepositoryTests {

    @Autowired
    private FreeBoardRepository freeBoardRepository;

    @Test
    public void insertDummies() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            FreeBoard freeBoard = FreeBoard.builder()
                    .title("Title..."+ i)
                    .content("Content..." + i)
                    .writer("user" + (i % 10))
                    .build();

            System.out.println(freeBoardRepository.save(freeBoard));
        });
    }

    @Test
    public void updateTest() {
        Optional<FreeBoard> result = freeBoardRepository.findById(100L);
// 존재하는 번호로 테스트
        if (result.isPresent()) {

            FreeBoard freeBoard = result.get();

            freeBoard.changeContent("change Content...");
            freeBoard.changeTitle("Change Title...");

            freeBoardRepository.save(freeBoard);
        }
    }

    @Test
    public void testQuery1() {

        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());

        QFreeBoard qFreeBoard = QFreeBoard.freeBoard; //1

        String keyword = "1";

        BooleanBuilder builder = new BooleanBuilder(); //2

        BooleanExpression expression = qFreeBoard.title.contains(keyword); //3

        builder.and(expression); //4

        Page<FreeBoard> result = freeBoardRepository.findAll(builder, pageable); //5

        result.stream().forEach(freeboard -> {
            System.out.println(freeboard);
        });
    }

    @Test
    public void testQuery2() {

        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());

        QFreeBoard qFreeBoard = QFreeBoard.freeBoard;

        String keyword = "1";

        BooleanBuilder builder = new BooleanBuilder();

        BooleanExpression exTitle = qFreeBoard.title.contains(keyword);

        BooleanExpression exContent = qFreeBoard.content.contains(keyword);

        BooleanExpression exAll = exTitle.or(exContent); // 1-----------------

        builder.and(exAll); // 2---------

        builder.and(qFreeBoard.gno.gt(0L)); //3---------

        Page<FreeBoard> result = freeBoardRepository.findAll(builder,
                pageable);

        result.stream().forEach(freeBoard -> {
            System.out.println(freeBoard);
        });

    }
}
