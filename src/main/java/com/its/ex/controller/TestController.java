package com.its.ex.controller;

import com.its.ex.dto.TestDTO;
import com.its.ex.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class TestController {

    //TestController는 TestService를 호출
    //TestService는 TestRepository를 호출
    //의존 관계를 만들어주세요. (생성자 주입을 이용)
    private final TestService testService;

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/save-form")
    public String save(){
        return "save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute TestDTO testDTO){
        testService.save(testDTO);
        return "index";
    }
    @GetMapping("/findAll")
    public String findAll(Model model){
        List<TestDTO> findList = testService.findAll();
        model.addAttribute("findList", findList);
        return "findAll";
    }
    //요청주소형태(id가 1인 데이터를 삭제하고자 한다면): location:8080/delete/1
    //기존에 배운 query string형식: localhost:8080/delete?id=1
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){ //@RequestParam대신
        testService.delete(id);
        return "index";
    }
    @GetMapping("/update{id}")
    public String updateForm(@PathVariable("id")Long id, Model model){
        TestDTO testDTO = testService.findById(id);
        model.addAttribute("updateDTO", testDTO);
        return "update";
    }
    @PostMapping("/update")
    public String update(@ModelAttribute TestDTO testDTO){
        Long updateId = testService.update(testDTO);
        return "redirect:/"+ updateId;
    }
}
