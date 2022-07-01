package hello.itemservice.web.validation;

import hello.itemservice.domain.item.ItemSaveForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ItemValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return ItemSaveForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ItemSaveForm itemSaveForm = (ItemSaveForm) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "itemName", "required");

        if(itemSaveForm.getPrice() == null || itemSaveForm.getPrice() < 1000 || itemSaveForm.getPrice() > 1000000){
            errors.rejectValue("price", "range", new Object[]{1000,1000000}, null);
        }

        if(itemSaveForm.getQuantity() == null || itemSaveForm.getQuantity() > 10000){
            errors.rejectValue("quantity", "max", new Object[]{9999}, null);
        }

        if(itemSaveForm.getPrice() != null && itemSaveForm.getQuantity() != null){
            int resultPrice = itemSaveForm.getPrice() * itemSaveForm.getQuantity();
            if(resultPrice < 10000) {
                errors.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }


    }




}
