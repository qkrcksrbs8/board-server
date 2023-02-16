package cg.park.boardserver.validator;

import cg.park.boardserver.model.Board;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.thymeleaf.util.StringUtils;

@Component
public class BoardValidator implements Validator {
    @Override
    public boolean supports(Class clazz) {
        return Board.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        if (StringUtils.isEmpty(((Board) obj).getContent()))
            errors.rejectValue("content", "key", "내용을 입력하세요.");
    }
}
