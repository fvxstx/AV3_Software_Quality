package com.example.fridgeapi.services.impl;

import com.example.fridgeapi.models.FridgeItems;
import com.example.fridgeapi.models.Fridges;
import com.example.fridgeapi.repositories.FridgeItemsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FridgeItemsServiceimplTest {

    @Mock
    private FridgeItemsRepository fridgeItemsRepository;
    @Mock
    private FridgeItemsLogService fridgeItemsLogService;
    @InjectMocks
    private FridgeItemsServiceimpl fridgeItemsService;

    private FridgeItems mockFridgeItem;

    @BeforeEach
    void setUp() {
        mockFridgeItem = new FridgeItems();
        mockFridgeItem.setId(1L);
        mockFridgeItem.setName("Milk");
        mockFridgeItem.setQuantity(1);
        mockFridgeItem.setCreatedAt(LocalDateTime.now().minusDays(1));
    }

    @Test
    void testGetFridgeItem_ShouldReturnItem_WhenFound() {
        when(fridgeItemsRepository.findById(1L)).thenReturn(Optional.of(mockFridgeItem));

        FridgeItems result = fridgeItemsService.getFridgeItem(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Milk", result.getName());

        verify(fridgeItemsRepository, times(1)).findById(1L);
    }

    @Test
    void testGetFridgeItem_ShouldThrowException_WhenNotFound() {

        when(fridgeItemsRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> {
            fridgeItemsService.getFridgeItem(99L);
        });

        verify(fridgeItemsRepository, times(1)).findById(99L);
    }

    @Test
    void testCreateFridgeItem_ShouldSetCreatedAtAndSave() {
        FridgeItems newItem = new FridgeItems();
        newItem.setName("Cheese");

        Fridges fridge = new Fridges();
        fridge.setId(1L);
        newItem.setFridge(fridge);

        ArgumentCaptor<FridgeItems> itemCaptor = ArgumentCaptor.forClass(FridgeItems.class);

        when(fridgeItemsRepository.save(itemCaptor.capture())).thenReturn(mockFridgeItem);

        String result = fridgeItemsService.createFridgeItem(newItem);

        assertEquals("Success", result);

        FridgeItems savedItem = itemCaptor.getValue();

        assertNotNull(savedItem.getCreatedAt());
        assertEquals("Cheese", savedItem.getName());

        verify(fridgeItemsRepository, times(1)).save(any(FridgeItems.class));
    }

    @Test
    void testUpdateFridgeItem_ShouldSetCreatedAtAndSave() {

        mockFridgeItem.setName("Fresh Milk");

        ArgumentCaptor<FridgeItems> itemCaptor = ArgumentCaptor.forClass(FridgeItems.class);

        when(fridgeItemsRepository.findById(mockFridgeItem.getId())).thenReturn(Optional.of(mockFridgeItem));
        when(fridgeItemsRepository.save(itemCaptor.capture())).thenReturn(mockFridgeItem);
        doNothing().when(fridgeItemsLogService.log(mockFridgeItem, "Item alterado da geladeira teste"));

        String result = fridgeItemsService.updateFridgeItem(mockFridgeItem);

        assertEquals("Success", result);

        FridgeItems updatedItem = itemCaptor.getValue();

        assertEquals(mockFridgeItem.getCreatedAt(), updatedItem.getCreatedAt());
        assertEquals("Fresh Milk", updatedItem.getName());
        verify(fridgeItemsRepository, times(1)).findById(mockFridgeItem.getId());
        verify(fridgeItemsRepository, times(1)).save(mockFridgeItem);
        verify(fridgeItemsLogService, times(1)).log(eq(mockFridgeItem));
    }

    @Test
    void testDeleteFridgeItem_ShouldCallDeleteByIdAndReturnSuccess() {
        Long itemId = 2L;

        doNothing().when(fridgeItemsRepository).deleteById(itemId);
        doNothing().when(fridgeItemsLogService.log(mockFridgeItem, "Item deletado da geladeira teste"));

        String result = fridgeItemsService.deleteFridgeItem(itemId);
        assertEquals("Success", result);

        verify(fridgeItemsRepository, times(1)).deleteById(itemId);
        verify(fridgeItemsLogService, times(1)).log(eq(mockFridgeItem));
    }

    @Test
    void testGetAllFridgeItems_ShouldReturnListOfItems() {
        List<FridgeItems> itemList = Collections.singletonList(mockFridgeItem);

        when(fridgeItemsRepository.findAll()).thenReturn(itemList);

        List<FridgeItems> results = fridgeItemsService.getAllFridgeItems();

        assertNotNull(results);
        assertEquals(1, results.size());
        assertEquals("Milk", results.get(0).getName());

        verify(fridgeItemsRepository, times(1)).findAll();
    }
}