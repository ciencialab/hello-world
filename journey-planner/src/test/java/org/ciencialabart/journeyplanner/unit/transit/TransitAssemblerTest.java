package org.ciencialabart.journeyplanner.unit.transit;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import org.ciencialabart.journeyplanner.transit.Transit;
import org.ciencialabart.journeyplanner.transit.TransitDefaultAssembler;
import org.ciencialabart.journeyplanner.transit.dto.TransitDTO;
import org.ciencialabart.journeyplanner.transit.event.TransitEvent;
import org.ciencialabart.journeyplanner.transit.event.TransitEventAssembler;
import org.ciencialabart.journeyplanner.transit.event.dto.TransitEventDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TransitAssemblerTest {
    
    @Mock
    private TransitEventAssembler transitEventAssembler;
    
    @InjectMocks
    private TransitDefaultAssembler transitAssembler;
    
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void should_AssembleAndReturnTransit_ForTransitDTOGiven() throws Exception {
        TransitDTO transitDTOPassed = mock(TransitDTO.class);
        TransitEventDTO departureEventDTO = mock(TransitEventDTO.class);
        TransitEventDTO arrivalEventDTO = mock(TransitEventDTO.class);
        TransitEvent departureEvent = mock(TransitEvent.class);
        TransitEvent arrivalEvent = mock(TransitEvent.class);
        Transit expectedTransit = new Transit(departureEvent, arrivalEvent);
        Transit returnedTransit;
        
        when(transitDTOPassed.getDepartureEvent()).thenReturn(departureEventDTO);
        when(transitDTOPassed.getArrivalEvent()).thenReturn(arrivalEventDTO);
        when(transitEventAssembler.assemble(departureEventDTO)).thenReturn(departureEvent);
        when(transitEventAssembler.assemble(arrivalEventDTO)).thenReturn(arrivalEvent);
        
        returnedTransit = transitAssembler.assemble(transitDTOPassed);
        
        InOrder departureEventInOrder = inOrder(transitDTOPassed, transitEventAssembler);
        departureEventInOrder.verify(transitDTOPassed).getDepartureEvent();
        departureEventInOrder.verify(transitEventAssembler).assemble(departureEventDTO);
        
        InOrder arrivalEventInOrder = inOrder(transitDTOPassed, transitEventAssembler);
        arrivalEventInOrder.verify(transitDTOPassed).getArrivalEvent();
        arrivalEventInOrder.verify(transitEventAssembler).assemble(arrivalEventDTO);
        
        assertEquals("Should return assembled Transit", expectedTransit, returnedTransit);
    }

}
