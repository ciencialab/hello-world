package org.ciencialabart.journeyplanner.unit.transit.dto;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import org.ciencialabart.journeyplanner.transit.Transit;
import org.ciencialabart.journeyplanner.transit.dto.TransitDTO;
import org.ciencialabart.journeyplanner.transit.dto.TransitDTODefaultAssembler;
import org.ciencialabart.journeyplanner.transit.event.TransitEvent;
import org.ciencialabart.journeyplanner.transit.event.dto.TransitEventDTO;
import org.ciencialabart.journeyplanner.transit.event.dto.TransitEventDTOAssembler;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TransitDTOAssemblerTest {
    
    @Mock
    private TransitEventDTOAssembler transitEventDTOAssembler;
    
    @InjectMocks
    private TransitDTODefaultAssembler transitDTOAssembler;
    
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void should_AssembleAndReturnTransitDTO_ForTransitGiven() throws Exception {
        Long transitId = 2L;
        Transit transitPassed = mock(Transit.class);
        TransitEvent departureEvent = mock(TransitEvent.class);
        TransitEvent arrivalEvent = mock(TransitEvent.class);
        TransitEventDTO departureEventDTO = mock(TransitEventDTO.class);
        TransitEventDTO arrivalEventDTO = mock(TransitEventDTO.class);
        TransitDTO expectedTransitDTO = new TransitDTO(transitId, departureEventDTO, arrivalEventDTO);
        TransitDTO returnedTransitDTO;
        
        when(transitPassed.getId()).thenReturn(transitId);
        when(transitPassed.getDepartureEvent()).thenReturn(departureEvent);
        when(transitPassed.getArrivalEvent()).thenReturn(arrivalEvent);
        when(transitEventDTOAssembler.assemble(departureEvent)).thenReturn(departureEventDTO);
        when(transitEventDTOAssembler.assemble(arrivalEvent)).thenReturn(arrivalEventDTO);
        
        returnedTransitDTO = transitDTOAssembler.assemble(transitPassed);
        
        verify(transitPassed).getId();
        
        InOrder departureEventInOrder = inOrder(transitPassed, transitEventDTOAssembler);
        departureEventInOrder.verify(transitPassed).getDepartureEvent();
        departureEventInOrder.verify(transitEventDTOAssembler).assemble(departureEvent);
        
        InOrder arrivalEventInOrder = inOrder(transitPassed, transitEventDTOAssembler);
        arrivalEventInOrder.verify(transitPassed).getArrivalEvent();
        arrivalEventInOrder.verify(transitEventDTOAssembler).assemble(arrivalEvent);
        
        assertEquals("Should return assembled TransitDTO", expectedTransitDTO, returnedTransitDTO);
    }

}
