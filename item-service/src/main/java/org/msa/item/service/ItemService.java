package org.msa.item.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.msa.item.domain.Item;
import org.msa.item.dto.ItemDTO;
import org.msa.item.repository.ItemRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public void insertItem(ItemDTO itemDTO){
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        Item item = Item.builder()
            .id(itemDTO.getId())
            .name(itemDTO.getName())
            .description(itemDTO.getDescription())
            .count(itemDTO.getCount())
            .regDts(date)
            .updDts(date)
            .build();

        itemRepository.save(item);
    }
}
