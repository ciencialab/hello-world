package org.ciencialabart.journeyplanner.unit.place.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.ciencialabart.journeyplanner.place.Place;
import org.ciencialabart.journeyplanner.place.dto.PlaceDTO;
import org.ciencialabart.journeyplanner.place.dto.PlaceDTOAssembler;
import org.ciencialabart.journeyplanner.place.repository.PlaceDAO;
import org.ciencialabart.journeyplanner.place.service.PlaceDefaultService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class PlaceServiceTest {
    
    @Mock
    private PlaceDAO placeDAO;

    @Mock
    private PlaceDTOAssembler placeDTOAssembler;
    
    @InjectMocks
    private PlaceDefaultService placeService;
    
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void should_CreatePlaceAndReturnItsId_ForNameGiven() throws Exception {
        String newPlaceName = "New place";
        ArgumentCaptor<Place> placePassed = ArgumentCaptor.forClass(Place.class);
        long expectedNewPlaceId = 2L;
        long returnedNewPlaceId;
        
        when(placeDAO.create(any())).thenReturn(expectedNewPlaceId);
        
        returnedNewPlaceId = placeService.createForName(newPlaceName);
        
        verify(placeDAO).create(placePassed.capture());
        
        assertEquals(newPlaceName, placePassed.getValue().getName());
        assertEquals("Should return new Place id", expectedNewPlaceId, returnedNewPlaceId);
    }
    
    @Test
    public void should_UpdateExistingPlace_ForNewNameGiven() throws Exception {
        String newPlaceName = "Renamed place";
        ArgumentCaptor<Place> placePassed = ArgumentCaptor.forClass(Place.class);
        Long updatedPlaceId = 2L;
        
        placeService.rename(updatedPlaceId, newPlaceName);
        
        verify(placeDAO).update(placePassed.capture());
        
        assertEquals(updatedPlaceId, placePassed.getValue().getId());
        assertEquals(newPlaceName, placePassed.getValue().getName());
    }
    
    @Test
    public void should_GetAssembleToDTOAndReturnExistingOptionalPlace() throws Exception {
        long existingPlaceId = 2L;
        Place existingPlace = mock(Place.class);
        PlaceDTO expectedPlaceDTO = mock(PlaceDTO.class);
        Optional<PlaceDTO> returnedOptionalPlaceDTO;
        
        when(placeDAO.get(existingPlaceId)).thenReturn(Optional.of(existingPlace));
        when(placeDTOAssembler.assemble(existingPlace)).thenReturn(expectedPlaceDTO);
        
        returnedOptionalPlaceDTO = placeService.get(existingPlaceId);
        
        InOrder inOrder = inOrder(placeDAO, placeDTOAssembler);
        inOrder.verify(placeDAO).get(existingPlaceId);
        inOrder.verify(placeDTOAssembler).assemble(existingPlace);
        
        assertEquals("Should return Optional with existing PlaceDTO", expectedPlaceDTO,
                returnedOptionalPlaceDTO.get());
    }
    
    @Test
    public void should_DeleteExistingPlace() throws Exception {
        long existingPlaceId = 2L;
        
        placeService.delete(existingPlaceId);
        
        verify(placeDAO).delete(existingPlaceId);
    }

}
