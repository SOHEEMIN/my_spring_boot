package com.its.ex.test;

import com.its.ex.dto.TestDTO;
import com.its.ex.entity.TestEntity;
import com.its.ex.service.TestService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;
@SpringBootTest
public class TestClass {
    @Autowired
    private TestService testService;

    // testService의 save() 호출
    // 호출 후 리턴값을 print
    @Test
    @Transactional
    @Rollback(value = true)
    public void saveTest(){
        /**
         * 1. 저장할 TestDTO 객체를 만들고 필드값을 저장.
         * 2. DTO객체를 서비스의 save메서드로 전달
         * 3. 전달 후 리턴 값을 받아서(Long)
         * 4. 그 리턴 값으로 DB에서 findById를 하고
         * 5. DB에서 조회된 데이터와 1번에서 저장한 데이터가 일치하는지를 판단하여
         * 6. 일치하면 테스트 통과, 일치하지 않으면 테스트 실패*/

        //1번 저장할 TestDTO 객체를 만들고 필드값을 저장.
        TestDTO testDTO = new TestDTO("테스트데이터999", "테스트데이터999");
        //2번 & 3번 DTO객체를 서비스의 save메서드로 전달 후 리턴 값을 받음(Long)
        Long saveId = testService.save(testDTO);
        //4번 그 리턴 값으로 DB에서 findById를 하고
        TestDTO findDTO = testService.findById(saveId);
        //5번 & 6번 //위처럼 import시 static을 붙이면 앞의 assertion생략 가능
        //DB에서 조회된 데이터와 1번에서 저장한 데이터가 일치하는지를 판단
        assertThat(testDTO.getColumn1()).isEqualTo(findDTO.getColumn1());
    }
    @Test
    @DisplayName("findAll 테스트")
    public void findAllTest(){
        /**
         * 1. 3개의 테스트 데이터 저장
         * 2. findAll 호출
         * 3. 호출한 리스트의 크기가 3인지를 판단
         * 4. 3이면 테스트 통과, 아니면 테스트 실패
         * **/
        //3개의 테스트 데이터를 저장해봅시다. 반복문을 써서
//        for(int i =1; i<=3 ; i++) {
////            testService.save(testDTO);
//            testService.save(new TestDTO("테스트데이터"+i, "테스트데이터"+i));
//        }
        //자바 람다식(화살표 함수), IntStream / rangeClosed: 범위 / i: 반복변수
        IntStream.rangeClosed(1, 3).forEach(i -> {
            testService.save(new TestDTO("데스트데이터"+i, "테스트데이터"+i));
        });
        //findAll 호출해서 리스트 크기가 3과 일치하는지 확인해봅시다.
        List<TestDTO> testDTOList = testService.findAll();
        assertThat(testDTOList.size()).isEqualTo(3); //체이닝메서드(연결해서 조건을 여러가지를 따져줄 수 있음)
    }
    @Test
    @DisplayName("삭제테스트")
    public void deleteTest(){
        //assertThat에서 equalTo 가 아닌 isNull로 사용

        //1. 삭제할 대상을 저장
        TestDTO testDTO = new TestDTO("테스트데이터999", "테스트데이터999");
        //2. 저장된  id값을 가져옴
        Long saveId = testService.save(testDTO);
        //3. 삭제 수행
        testService.delete(saveId);
        TestDTO findDTO = testService.findById(saveId);
        assertThat(testService.findById(saveId)).isNull();
    }

    @Test
    @DisplayName("수정 테스트")
    public void updateTest(){
        /** 수정 테스트를 어떻게 할지 시나리오를 써보고, assertThat().isNotEqualTo() 쓰면 됩니다.**/
        /**
         * 1. 새로운 데이터를 저장
         * 2. 저장한 객체를 조회 (findById)
         * 3. test_column1의 값을 변경하는 수정 처리
         * 4. 수정 후 다시 객체 조회(findById)
         * 5. 2번에서 조회한 값과 5번에서 조회한 값이 같은지를 비교하여(각각의 test_column1)
         * 6. 다르면 테스트 성공, 같다면 테스트 실패
         **/

        TestDTO testDTO = new TestDTO("기존값1", "기존값2");
        Long saveId = testService.save(testDTO);
        TestDTO findDTO = testService.findById(saveId);

        TestDTO alterDTO = new TestDTO("수정값1", "수정값2");
        alterDTO.setId(saveId);
        testService.update(alterDTO);
        TestDTO updateDTO = testService.findById(saveId);
        assertThat(findDTO.getColumn1()).isNotEqualTo(updateDTO.getColumn1());

    }
}