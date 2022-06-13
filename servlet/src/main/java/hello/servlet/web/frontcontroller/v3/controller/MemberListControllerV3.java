package hello.servlet.web.frontcontroller.v3.controller;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import hello.servlet.web.frontcontroller.ModelView;

import java.util.List;
import java.util.Map;

public class MemberListControllerV3 implements ControllerV3{

    MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public ModelView process(Map<String, String> paramMap) {

        List<Member> members = memberRepository.findAll();
        ModelView mv = new ModelView("members"); //member라는 viewName을 가진 모델뷰 인스턴스 생성
        mv.getModel().put("members", members); //위의 모델뷰 인스턴스 안에 있는 모델(=데이터 (Map<String,Object>))에 저장
        return mv;
    }
}
