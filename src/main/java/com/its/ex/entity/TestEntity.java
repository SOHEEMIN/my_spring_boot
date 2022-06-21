package com.its.ex.entity;

import com.its.ex.dto.TestDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "test_table")
public class TestEntity {
    @Id //pk를 지정하는 어노테이션(필수)
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
    @Column(name = "test_id")
    private Long id;

    @Column(name = "test_column1", length = 50)
    private String column1;

    @Column(unique = true)
    private String testColumn2;

    //목적: TestDTO 객체를 받아서 Entity 객체에 옮겨 담은 후 Entity객체로 리턴
    //Service 클래스에서 toEntity 메서드를 호출해서 리턴받은 객체를
    //testRepository의 save 메서드로 전달하세요.
    public static TestEntity toEntity(TestDTO testDTO){
        TestEntity testEntity = new TestEntity();
        testEntity.setColumn1(testDTO.getColumn1());
        testEntity.setTestColumn2(testDTO.getTestColumn2());
        return testEntity;
    }

    public static TestEntity toUpdateEntity(TestDTO testDTO) {
        TestEntity testEntity = new TestEntity();
        testEntity.setId(testDTO.getId());
        testEntity.setColumn1(testDTO.getColumn1());
        testEntity.setTestColumn2(testDTO.getTestColumn2());
        return testEntity;
    }
}
