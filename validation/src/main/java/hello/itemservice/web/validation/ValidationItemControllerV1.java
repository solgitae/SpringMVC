package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/validation/f/items")
@RequiredArgsConstructor
public class ValidationItemControllerV1 {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> Items = itemRepository.findAll();
        model.addAttribute("items", Items);
        return "validation/v1/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item Item = itemRepository.findById(itemId);
        model.addAttribute("item", Item);
        return "validation/v1/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "validation/v1/addForm";
    }

    @PostMapping("/add")
    public String addItem(@ModelAttribute Item Item, RedirectAttributes redirectAttributes, Model model) {

        Map<String, String> errors = new HashMap<>();

        if(!StringUtils.hasText(Item.getItemName())) {
            errors.put("itemName", "상품 이름은 필수입니다.");
        }

        if(Item.getPrice() == null || Item.getPrice() < 1000 || Item.getPrice() > 1_000_000) {
            errors.put("price", "상품 가격은 1,000 ~ 1,000,000까지 허용합니다.");
        }

        if(Item.getQuantity() == null || Item.getQuantity() >= 9999) {
            errors.put("quantity", "상품 갯수는 9999까지 허용합니다.");
        }


        if(Item.getPrice() != null || Item.getQuantity() != null) {
            int resultPrice = Item.getPrice() * Item.getQuantity();
            if (resultPrice < 10000) {
                errors.put("globalError", "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 =" + resultPrice);
            }
        }

        if(!errors.isEmpty()){
            model.addAttribute("errors", errors); //에러 메세지를 출력하기 위해서 모델에 담아 보내는 것
            return "validation/v1/addform";
        }
        Item savedItem = itemRepository.save(Item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v1/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item Item = itemRepository.findById(itemId);
        model.addAttribute("item", Item);
        return "validation/v1/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item Item) {
        itemRepository.update(itemId, Item);
        return "redirect:/validation/v1/items/{itemId}";
    }

}

