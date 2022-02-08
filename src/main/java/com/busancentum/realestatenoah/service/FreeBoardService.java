package com.busancentum.realestatenoah.service;

import com.busancentum.realestatenoah.dto.FreeBoardDTO;
import com.busancentum.realestatenoah.dto.PageRequestDTO;
import com.busancentum.realestatenoah.dto.PageResultDTO;
import com.busancentum.realestatenoah.entity.FreeBoard;

public interface FreeBoardService {

    Long register(FreeBoardDTO dto);
    FreeBoardDTO read(Long gno);

    PageResultDTO<FreeBoardDTO, FreeBoard> getList(PageRequestDTO
                                                           requestDTO);

    default FreeBoard dtoToEntity(FreeBoardDTO dto){
        FreeBoard entity = FreeBoard.builder()
                .gno(dto.getGno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .build();
        return entity;
    }

    default FreeBoardDTO entityToDto(FreeBoard entity){
        FreeBoardDTO dto = FreeBoardDTO.builder()
                .gno(entity.getGno())
                .title(entity.getTitle())
                .content(entity.getContent())
                .writer(entity.getWriter())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .build();

        return dto;
    }

    void remove(Long gno);

    void modify(FreeBoardDTO dto);
}
