package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/validation/v2/items")
@RequiredArgsConstructor
public class ValidationItemControllerV2 {

    private final ItemRepository itemRepository;
    private final ItemValidator itemValidator;

    @InitBinder
    public void init(WebDataBinder dataBinder) {
        log.info("init binder {}", dataBinder);
        dataBinder.addValidators(itemValidator);
    }

    @GetMapping
    public String items(Model model) {
        List<Item> Items = itemRepository.findAll();
        model.addAttribute("items", Items);
        return "validation/v2/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item Item = itemRepository.findById(itemId);
        model.addAttribute("item", Item);
        return "validation/v2/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "validation/v2/addForm";
    }

    //    @PostMapping("/add")
    public String addItemV1(@ModelAttribute Item Item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {


        if (!StringUtils.hasText(Item.getItemName())) {
            bindingResult.addError(new FieldError("Item", "itemName", "상품 이름은 필수입니다.")); //item이 필드라 필드에러로 관리 modelattribute에 담기는 걸 ()에 담아주면
        }

        if (Item.getPrice() == null || Item.getPrice() < 1000 || Item.getPrice() > 1_000_000) {
            bindingResult.addError(new FieldError("Item", "price", "상품 가격은 1,000 ~ 1,000,000까지 허용합니다."));

        }

        if (Item.getQuantity() == null || Item.getQuantity() >= 9999) {
            bindingResult.addError(new FieldError("Item", "quantity", "상품 갯수는 9999까지 허용합니다."));

        }


        if (Item.getPrice() != null || Item.getQuantity() != null) {
            int resultPrice = Item.getPrice() * Item.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.addError(new ObjectError("Item", "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 =" + resultPrice));
            }
        }


        Item savedItem = itemRepository.save(Item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

    //    @PostMapping("/add")
    public String addItemV2(@ModelAttribute Item Item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {


        if (!StringUtils.hasText(Item.getItemName())) {
            bindingResult.addError(new FieldError("Item", "itemName", "상품 이름은 필수입니다.")); //item이 필드라 필드에러로 관리 modelattribute에 담기는 걸 ()에 담아주면
        }

        if (Item.getPrice() == null || Item.getPrice() < 1000 || Item.getPrice() > 1_000_000) {
            bindingResult.addError(new FieldError("Item", "price", "상품 가격은 1,000 ~ 1,000,000까지 허용합니다."));

        }

        if (Item.getQuantity() == null || Item.getQuantity() >= 9999) {
            bindingResult.addError(new FieldError("Item", "quantity", "상품 갯수는 9999까지 허용합니다."));

        }


        if (Item.getPrice() != null || Item.getQuantity() != null) {
            int resultPrice = Item.getPrice() * Item.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.addError(new ObjectError("Item", "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 =" + resultPrice));
            }
        }


        Item savedItem = itemRepository.save(Item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

    //    @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item Item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {


        if (!StringUtils.hasText(Item.getItemName())) {
            bindingResult.addError(new FieldError("Item", "itemName", Item.getItemName(), false
                    , new String[]{"required.Item.itemName"}, null, null));
        }

        if (Item.getPrice() == null || Item.getPrice() < 1000 || Item.getPrice() > 1_000_000) {
            bindingResult.addError(new FieldError("Item", "price", Item.getPrice(), false
                    , new String[]{"range.Item.price"}, new Object[]{1000, 1000000}, null));
        }

        if (Item.getQuantity() == null || Item.getQuantity() >= 9999) {
            bindingResult.addError(new FieldError("Item", "quantity", Item.getQuantity(), false, new String[]{"max.Item.quantity"}, new Object[]{9999}, null));
        }


        if (Item.getPrice() != null || Item.getQuantity() != null) {
            int resultPrice = Item.getPrice() * Item.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.addError(new ObjectError("Item", new String[]{"totalPriceMin"}, new Object[]{10000, resultPrice}, null));
            }
        }

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "validation/v2/addForm";
        }


        Item savedItem = itemRepository.save(Item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }


//    @PostMapping("/add")
    public String addItemV4(@ModelAttribute Item Item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {


        if (!StringUtils.hasText(Item.getItemName())) {
            bindingResult.rejectValue("itemName", "required");
        }

        if (Item.getPrice() == null || Item.getPrice() < 1000 || Item.getPrice() > 1_000_000) {
            bindingResult.rejectValue("price", "range", new Object[]{1000, 1000000}, null);
        }

        if (Item.getQuantity() == null || Item.getQuantity() >= 9999) {
            bindingResult.rejectValue("quantity", "max", new Object[]{9999}, null);
        }


        if (Item.getPrice() != null || Item.getQuantity() != null) {
            int resultPrice = Item.getPrice() * Item.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }


        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "validation/v2/addForm";
        }


        Item savedItem = itemRepository.save(Item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

//    @PostMapping("/add")
    public String addItemV5(@ModelAttribute Item Item, BindingResult bindingResult,
                            RedirectAttributes redirectAttributes) {
        itemValidator.validate(Item, bindingResult);
        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "validation/v2/addForm";
        }

        //성공 로직
        Item savedItem = itemRepository.save(Item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

    @PostMapping("/add")
    public String addItemV6(@Validated @ModelAttribute Item Item, BindingResult
            bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "validation/v2/addForm";
        }

        //성공 로직
        Item savedItem = itemRepository.save(Item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }


    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item Item = itemRepository.findById(itemId);
        model.addAttribute("item", Item);
        return "validation/v2/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item Item) {
        itemRepository.update(itemId, Item);
        return "redirect:/validation/v2/items/{itemId}";
    }

}

