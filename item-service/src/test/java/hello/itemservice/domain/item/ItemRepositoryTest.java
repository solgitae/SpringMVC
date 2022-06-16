package hello.itemservice.domain.item;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class ItemRepositoryTest {

    ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    void afterEach(){
        itemRepository.clearStore();
    }


    @Test
    void save(){
        //given
        Item item = new Item("A", 10_000, 10);
        //when
        itemRepository.save(item);
        //then
        Item findItem = itemRepository.findById(item.getId());
        assertThat(findItem).isEqualTo(item);
    }


    @Test
    void findAll(){
        //given
        Item itemA = new Item("A", 10_000, 10);
        Item itemB = new Item("B", 20_000, 20);
        Item itemC = new Item("C", 30_000, 30);
        itemRepository.save(itemA);
        itemRepository.save(itemB);
        itemRepository.save(itemC);
        //when
        List<Item> result = itemRepository.findAll();
        //then
        assertThat(result.size()).isEqualTo(3);
        assertThat(result).contains(itemA, itemB, itemC);

    }

    @Test
    void updateItem(){
        //given
        Item item = new Item("A", 10_000, 10);
        Item savedItem = itemRepository.save(item);
        Long itemId = savedItem.getId(); //저장된 아이템의 ID값
        //when
        Item updateParam = new Item("item2", 20_000, 30);
        itemRepository.update(itemId, updateParam); //A의 ID(순번)에 B를 끼움
        Item findItem = itemRepository.findById(itemId); //B가 담겨야 됨
        //then
        assertThat(findItem.getItemName()).isEqualTo(updateParam.getItemName());
        assertThat(findItem.getPrice()).isEqualTo(updateParam.getPrice());

    }

}
