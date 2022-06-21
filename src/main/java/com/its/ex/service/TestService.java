package com.its.ex.service;

import com.its.ex.dto.TestDTO;
import com.its.ex.entity.TestEntity;
import com.its.ex.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TestService {

    private final TestRepository testRepository;

    public Long save(TestDTO testDTO) {
        System.out.println("testDTO = " + testDTO);
        //TestDTO 객체에 담긴 값을 TestEntity 객체에 옮겨담기
        TestEntity testEntity = TestEntity.toEntity(testDTO);
        Long id = testRepository.save(testEntity).getId();
        //저장된 id값을 가져올 수 있음
        return id;
    }

    public TestDTO findById(Long id) {
        Optional<TestEntity> optionalTestEntity = testRepository.findById(id);
        if (optionalTestEntity.isPresent()) {
            //조회된 결과가 있으면 optional로 감싸진 메서드를 get메서드로 깜.
            TestEntity testEntity = optionalTestEntity.get();
            //깠으면 entity를 DTO로 변환
//            TestDTO testDTO = TestDTO.toDTO(testEntity);
//            return testDTO;
//            위의 두줄을 밑의 한줄로 변환 가능
            return TestDTO.toDTO(optionalTestEntity.get());
        } else {
            //조회된 결과가 없다.
            return null;
        }
    }

    public List<TestDTO> findAll() {
        List<TestEntity> entityList = testRepository.findAll();
        //entity가 담겨있는 list를 DTO가 담겨있는 list로 변환해라
        List<TestDTO> findList = new ArrayList<>();
        for (TestEntity testEntity : entityList) {
            TestDTO testDTO = TestDTO.toDTO(testEntity);
            findList.add(testDTO);
        }
        return findList;
    }

    public void delete(Long id) {
        testRepository.deleteById(id);
    }

    public Long update(TestDTO testDTO) {
        //save메서드로 update쿼리 가능( 단, id가 같이 가야함)
        TestEntity testEntity = TestEntity.toUpdateEntity(testDTO);
        Long id = testRepository.save(testEntity).getId();
        return id;
    }
}
