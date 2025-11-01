package com.example.fridgeapi.services.impl;

import com.example.fridgeapi.models.Fridges;
import com.example.fridgeapi.repositories.FridgesRepository;
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
class FridgesServiceimplTest {

    @Mock
    private FridgesRepository fridgesRepository;

    @InjectMocks
    private FridgesServiceimpl fridgesService;

    private Fridges mockFridge;

    @BeforeEach
    void setUp() {
        mockFridge = new Fridges(true, "4°C");
        mockFridge.setId(1L);
        mockFridge.setCreatedAt(LocalDateTime.now());
    }

    @Test
    void testGetFridge_ShouldReturnFridge_WhenFound() {

        when(fridgesRepository.findById(1L)).thenReturn(Optional.of(mockFridge));

        Fridges result = fridgesService.getFridge(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("4°C", result.getTemperature());

        verify(fridgesRepository, times(1)).findById(1L);
    }

    @Test
    void testGetFridge_ShouldThrowException_WhenNotFound() {

        when(fridgesRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            fridgesService.getFridge(99L);
        });

        verify(fridgesRepository, times(1)).findById(99L);
    }

    @Test
    void testCreateFridge_ShouldSaveAndReturnSuccess() {
        Fridges newFridge = new Fridges(true, "5°C"); // Sem ID, sem createdAt

        ArgumentCaptor<Fridges> fridgeCaptor = ArgumentCaptor.forClass(Fridges.class);

        when(fridgesRepository.save(fridgeCaptor.capture())).thenReturn(newFridge);

        String result = fridgesService.createFridge(newFridge);

        assertEquals("Success", result);

        verify(fridgesRepository, times(1)).save(any(Fridges.class));

        Fridges savedFridge = fridgeCaptor.getValue();

        assertNotNull(savedFridge.getCreatedAt());
        assertEquals("5°C", savedFridge.getTemperature());
    }

    @Test
    void testUpdateFridge_ShouldSaveAndReturnSuccess() {

        when(fridgesRepository.save(any(Fridges.class))).thenReturn(mockFridge);

        String result = fridgesService.updateFridge(mockFridge);

        assertEquals("Success", result);
        verify(fridgesRepository, times(1)).save(mockFridge);
    }

    @Test
    void testDeleteFridge_ShouldCallDeleteByIdAndReturnSuccess() {
        Long fridgeId = 1L;

        doNothing().when(fridgesRepository).deleteById(fridgeId);

        String result = fridgesService.deleteFridge(fridgeId);

        assertEquals("Success", result);

        verify(fridgesRepository, times(1)).deleteById(fridgeId);
    }

    @Test
    void testGetAllFridges_ShouldReturnListOfFridges() {

        List<Fridges> fridgeList = Collections.singletonList(mockFridge);

        when(fridgesRepository.findAll()).thenReturn(fridgeList);

        List<Fridges> results = fridgesService.getAllFridges();

        assertNotNull(results);
        assertEquals(1, results.size());
        assertEquals(1L, results.get(0).getId());
        verify(fridgesRepository, times(1)).findAll();
    }

    @Test
    void testGetAllFridges_ShouldReturnEmptyList_WhenNoFridges() {

        when(fridgesRepository.findAll()).thenReturn(Collections.emptyList());

        List<Fridges> results = fridgesService.getAllFridges();

        assertNotNull(results);
        assertTrue(results.isEmpty());
        verify(fridgesRepository, times(1)).findAll();
    }
}