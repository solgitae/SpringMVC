package hello.servlet.web.frontcontroller.v4.controller;

import java.util.Map;

public class MemberFormControllerV4 implements ControllerV4 {
    /**
     * @param paramMap
     * @param model
     * @return String(MyView가 아님)
     */

    @Override
    public String process(Map<String, String> paramMap, Map<String, Object>
            model) {
        return "new-form";
    }
}
