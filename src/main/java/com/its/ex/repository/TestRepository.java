package com.its.ex.repository;


import com.its.ex.entity.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<TestEntity, Long>{ //대상 entity, pk의 타입

}
