package org.ciencialabart.journeyplanner.unit.transit.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.ciencialabart.journeyplanner.transit.Transit;
import org.ciencialabart.journeyplanner.transit.TransitAssembler;
import org.ciencialabart.journeyplanner.transit.dto.TransitDTO;
import org.ciencialabart.journeyplanner.transit.dto.TransitDTOAssembler;
import org.ciencialabart.journeyplanner.transit.repository.TransitDAO;
import org.ciencialabart.journeyplanner.transit.service.TransitDefaultService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TransitServiceTest {
    
    @Mock
    private TransitDAO transitDAO;

    @Mock
    private TransitAssembler transitAssembler;
    
    @Mock
    private TransitDTOAssembler transitDTOAssembler;
    
    @InjectMocks
    private TransitDefaultService transitService;
    
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void should_AssembleFromDTOAndCreateTransitReturningItsId() throws Exception {
        TransitDTO transitDTOPassed = mock(TransitDTO.class);
        Transit assembledTransit = mock(Transit.class);
        long expectedNewTransitId = 2L;
        long returnedNewTransitId;
        
        when(transitAssembler.assemble(transitDTOPassed)).thenReturn(assembledTransit);
        when(transitDAO.create(assembledTransit)).thenReturn(expectedNewTransitId);
        
        returnedNewTransitId = transitService.create(transitDTOPassed);
        
        InOrder inOrder = inOrder(transitAssembler, transitDAO);
        inOrder.verify(transitAssembler).assemble(transitDTOPassed);
        inOrder.verify(transitDAO).create(assembledTransit);
        
        assertEquals("Should return new Transit id", expectedNewTransitId, returnedNewTransitId);
    }
    
    @Test
    public void should_GetAssembleToDTOAndReturnExistingOptionalTransit() throws Exception {
        long existingTransitId = 2L;
        Transit existingTransit = mock(Transit.class);
        TransitDTO expectedTransitDTO = mock(TransitDTO.class);
        Optional<TransitDTO> returnedOptionalTransitDTO;
        
        when(transitDAO.get(existingTransitId)).thenReturn(Optional.of(existingTransit));
        when(transitDTOAssembler.assemble(existingTransit)).thenReturn(expectedTransitDTO);
        
        returnedOptionalTransitDTO = transitService.get(existingTransitId);
        
        InOrder inOrder = inOrder(transitDAO, transitDTOAssembler);
        inOrder.verify(transitDAO).get(existingTransitId);
        inOrder.verify(transitDTOAssembler).assemble(existingTransit);
        
        assertEquals("Should return Optional with existing TransitDTO", expectedTransitDTO,
                returnedOptionalTransitDTO.get());
    }
    
    @Test
    public void should_DeleteExistingTransit() throws Exception {
        long existingTransitId = 2L;
        
        transitService.delete(existingTransitId);
        
        verify(transitDAO).delete(existingTransitId);
    }

}
