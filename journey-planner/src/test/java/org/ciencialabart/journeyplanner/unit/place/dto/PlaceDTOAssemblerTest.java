package org.ciencialabart.journeyplanner.unit.place.dto;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import org.ciencialabart.journeyplanner.place.Place;
import org.ciencialabart.journeyplanner.place.dto.PlaceDTO;
import org.ciencialabart.journeyplanner.place.dto.PlaceDTODefaultAssembler;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class PlaceDTOAssemblerTest {
    
    @InjectMocks
    private PlaceDTODefaultAssembler placeDTOAssembler;
    
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void should_AssembleAndReturnPlaceDTO_ForPlaceGiven() throws Exception {
        Long placePassedId = 2L;
        String placePassedName = "Warsaw";
        Place placePassed = mock(Place.class);
        PlaceDTO expectedPlaceDTO = new PlaceDTO(placePassedId, placePassedName);
        PlaceDTO returnedPlaceDTO;
        
        when(placePassed.getId()).thenReturn(placePassedId);
        when(placePassed.getName()).thenReturn(placePassedName);
        
        returnedPlaceDTO = placeDTOAssembler.assemble(placePassed);
        
        verify(placePassed).getId();
        verify(placePassed).getName();
        
        assertEquals("Should return assembled PlaceDTO", expectedPlaceDTO, returnedPlaceDTO);
    }

}
