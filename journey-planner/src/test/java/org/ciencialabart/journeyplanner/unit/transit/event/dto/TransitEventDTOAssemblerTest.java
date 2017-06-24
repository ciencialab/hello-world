package org.ciencialabart.journeyplanner.unit.transit.event.dto;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.ciencialabart.journeyplanner.place.Place;
import org.ciencialabart.journeyplanner.transit.event.TransitEvent;
import org.ciencialabart.journeyplanner.transit.event.dto.TransitEventDTO;
import org.ciencialabart.journeyplanner.transit.event.dto.TransitEventDTODefaultAssembler;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class TransitEventDTOAssemblerTest {
    
    @InjectMocks
    private TransitEventDTODefaultAssembler transitEventDTODefaultAssembler;
    
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void should_AssembleAndReturnTransitEventDTO_ForTransitEventGiven() throws Exception {
        Long transitEventPassedPlaceId = 2L;
        Place transitEventPassedPlace = mock(Place.class);
        OffsetDateTime transitEventPassedTime = OffsetDateTime.of(
                LocalDateTime.of(2017, 6, 16, 12, 00),
                ZoneOffset.ofHours(2));
        TransitEvent transitEventPassed = mock(TransitEvent.class);
        TransitEventDTO expectedTransitEventDTO = new TransitEventDTO(transitEventPassedPlaceId, transitEventPassedTime);
        TransitEventDTO returnedTransitEventDTO;
        
        when(transitEventPassed.getPlace()).thenReturn(transitEventPassedPlace);
        when(transitEventPassedPlace.getId()).thenReturn(transitEventPassedPlaceId);
        when(transitEventPassed.getTime()).thenReturn(transitEventPassedTime);
        
        returnedTransitEventDTO = transitEventDTODefaultAssembler.assemble(transitEventPassed);
        
        InOrder inOrder = inOrder(transitEventPassed, transitEventPassedPlace);
        inOrder.verify(transitEventPassed).getPlace();
        inOrder.verify(transitEventPassedPlace).getId();
        
        verify(transitEventPassed).getTime();
        
        assertEquals("Should return assembled TransitEventDTO", expectedTransitEventDTO, returnedTransitEventDTO);
    }

}
