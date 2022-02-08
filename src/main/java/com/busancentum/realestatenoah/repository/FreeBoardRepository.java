package com.busancentum.realestatenoah.repository;

import com.busancentum.realestatenoah.entity.FreeBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface FreeBoardRepository extends JpaRepository<FreeBoard,
        Long>, QuerydslPredicateExecutor<FreeBoard> {

}
