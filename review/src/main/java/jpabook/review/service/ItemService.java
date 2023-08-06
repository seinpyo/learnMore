package jpabook.review.service;

import jpabook.review.domain.Item.Book;
import jpabook.review.domain.Item.Item;
import jpabook.review.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    public final ItemRepository itemRepository;

    @Transactional  //readOnly = true 면 저장안됨
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    @Transactional
    public Item updateItem(Long itemId, Book param) {
        Item findItem = itemRepository.findOne(itemId); //트렌잭션 안에서 엔티티를 다시 조회 (영속성 컨텍스트에 올라감)
        findItem.setPrice(param.getPrice());
        findItem.setName(param.getName());
        findItem.setStockQuantity(param.getStockQuantity());

        //트랜잭션 커밋 시점에 변경 감지에 의해 update Query 실행
        return findItem;
    }

    @Transactional
    public Item updateItem(Long itemId, String name, int price, int stockQuantity) {   //식별자와 변경할 데이터를 명확하게 전달하도록 한다(파라미터 or DTO)
        Item findItem = itemRepository.findOne(itemId);
        findItem.setPrice(price);  //setter 대신 추적 가능한 메서드를 만드는 것이 좋음
        findItem.setName(name);
        findItem.setStockQuantity(stockQuantity);

        return findItem;
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }
}
