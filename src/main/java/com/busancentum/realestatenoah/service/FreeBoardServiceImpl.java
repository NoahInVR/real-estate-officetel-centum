package com.busancentum.realestatenoah.service;

import com.busancentum.realestatenoah.dto.FreeBoardDTO;
import com.busancentum.realestatenoah.dto.PageRequestDTO;
import com.busancentum.realestatenoah.dto.PageResultDTO;
import com.busancentum.realestatenoah.entity.FreeBoard;
import com.busancentum.realestatenoah.entity.QFreeBoard;
import com.busancentum.realestatenoah.repository.FreeBoardRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.el.parser.BooleanNode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor //의존성 자동 주입
public class FreeBoardServiceImpl implements FreeBoardService {

    private final FreeBoardRepository repository; // 반드시 final로 선언

    @Override
    public Long register(FreeBoardDTO dto) {

        log.info("DTO-----------------------");
        log.info(dto);

        FreeBoard entity = dtoToEntity(dto);

        log.info(entity);

        repository.save(entity);

        return entity.getGno();
    }

    @Override
    public FreeBoardDTO read(Long gno) {

        Optional<FreeBoard> result = repository.findById(gno);

        return result.isPresent() ? entityToDto(result.get()) : null;
    }

    @Override
    public PageResultDTO<FreeBoardDTO, FreeBoard> getList(PageRequestDTO
                                                                      requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("gno").
                descending());
        BooleanBuilder booleanBuilder = getSearch(requestDTO); //검색 조건처리

        Page<FreeBoard> result = repository.findAll(booleanBuilder, pageable);
        //Querydsl 사용

        Function<FreeBoard, FreeBoardDTO> fn = (entity -> entityToDto(entity));

        return new PageResultDTO<>(result, fn);
    }

    @Override
    public void remove(Long gno) {

        repository.deleteById(gno);
    }

    @Override
    public void modify(FreeBoardDTO dto)  {

        //업데이트 하는 항목은 '제목', '내용'

        Optional<FreeBoard> result = repository.findById(dto.getGno());

        if (result.isPresent()) {

            FreeBoard entity = result.get();

            entity.changeTitle(dto.getTitle());
            entity.changeContent(dto.getContent());

            repository.save(entity);
        }
    }


    private BooleanBuilder getSearch(PageRequestDTO requestDTO) { //Querydsl 처리

        String type = requestDTO.getType();

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        QFreeBoard qFreeBoard = QFreeBoard.freeBoard;

        String keyeword = requestDTO.getKeyword();

        BooleanExpression expression = qFreeBoard.gno.gt(0L); //gno > 0 조건만 생성

        booleanBuilder.and(expression);

        if (type == null || type.trim().length() == 0) { //검색 조건이 없는  경우
            return booleanBuilder;
        }

        //검색 조건을 작성하기
        BooleanBuilder conditionBuilder = new BooleanBuilder();

        if (type.contains("t")) {
            conditionBuilder.or(qFreeBoard.title.contains(keyeword));
        }

        if(type.contains("c")){
            conditionBuilder.or(qFreeBoard.content.contains(keyeword));
        }

        if(type.contains("w")){
            conditionBuilder.or(qFreeBoard.writer.contains(keyeword));
        }

        //모든 조건 통합
        booleanBuilder.and(conditionBuilder);

        return booleanBuilder;
    }
}