package hello.itemservice.validation.validatorFactory;

import hello.itemservice.domain.item.ItemSaveForm;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class BeanValidationTest {
    
    @Test
    void beanValidation() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        
        ItemSaveForm itemSaveForm = new ItemSaveForm();
        itemSaveForm.setItemName(" ");
        itemSaveForm.setPrice(0);
        itemSaveForm.setQuantity(10000);
        
        Set<ConstraintViolation<ItemSaveForm>> violations = validator.validate(itemSaveForm);

        for (ConstraintViolation<ItemSaveForm> violation : violations) {
            System.out.println("violation = " + violation);
            System.out.println("violation.getMessage() = " + violation.getMessage());
        }
    }
}
