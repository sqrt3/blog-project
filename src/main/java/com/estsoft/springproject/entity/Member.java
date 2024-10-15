package com.estsoft.springproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;        // DB 테이블의 id와 컬럼 매칭

    @Column(name = "name", nullable = false)
    private String name;    // DB 테이블의 name과 컬럼 매칭
}