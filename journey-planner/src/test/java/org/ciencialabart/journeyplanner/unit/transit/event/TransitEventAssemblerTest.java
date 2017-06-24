package org.ciencialabart.journeyplanner.unit.transit.event;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.ciencialabart.journeyplanner.place.Place;
import org.ciencialabart.journeyplanner.transit.event.TransitEvent;
import org.ciencialabart.journeyplanner.transit.event.TransitEventDefaultAssembler;
import org.ciencialabart.journeyplanner.transit.event.dto.TransitEventDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class TransitEventAssemblerTest {
    
    @InjectMocks
    private TransitEventDefaultAssembler transitEventDefaultAssembler;
    
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void should_AssembleAndReturnTransitEvent_ForTransitEventDTOGiven() throws Exception {
        Long transitEventDTOPassedId = 2L;
        OffsetDateTime transitEventDTOPassedTime = OffsetDateTime.of(
                LocalDateTime.of(2017, 6, 16, 12, 00),
                ZoneOffset.ofHours(2));
        TransitEventDTO transitEventDTOPassed = mock(TransitEventDTO.class);
        TransitEvent expectedTransitEvent =
                new TransitEvent(new Place(transitEventDTOPassedId), transitEventDTOPassedTime);
        TransitEvent returnedTransitEvent;
        
        when(transitEventDTOPassed.getPlaceId()).thenReturn(transitEventDTOPassedId);
        when(transitEventDTOPassed.getTime()).thenReturn(transitEventDTOPassedTime);
        
        returnedTransitEvent = transitEventDefaultAssembler.assemble(transitEventDTOPassed);
        
        verify(transitEventDTOPassed).getPlaceId();
        verify(transitEventDTOPassed).getTime();
        
        assertEquals("Should return assembled TransitEvent", expectedTransitEvent, returnedTransitEvent);
    }

}
