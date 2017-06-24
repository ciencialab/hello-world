package org.ciencialabart.journeyplanner.unit.transit.repository;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.ciencialabart.journeyplanner.transit.Transit;
import org.ciencialabart.journeyplanner.transit.repository.TransitJPADAO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TransitDAOTest {
    
    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private TransitJPADAO transitDAO;
    
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void should_PersistTransitAndReturnItsId() throws Exception {
        Transit persistedTransit = mock(Transit.class);
        long expectedNewTransitId = 2L;
        long returnedNewTransitId;
        
        when(persistedTransit.getId()).thenReturn(expectedNewTransitId);
        
        returnedNewTransitId = transitDAO.create(persistedTransit);
        
        InOrder inOrder = inOrder(entityManager, persistedTransit);
        inOrder.verify(entityManager).persist(persistedTransit);
        inOrder.verify(persistedTransit).getId();
        
        assertEquals("Should return persisted Transit id", expectedNewTransitId, returnedNewTransitId);
    }
    
    @Test
    public void should_FindExistingTransitAndReturnItInOptional() throws Exception {
        long existingTransitId = 2L;
        Transit expectedTransit = mock(Transit.class);
        Optional<Transit> returnedOptionalTransit;
        
        when(entityManager.find(Transit.class, existingTransitId)).thenReturn(expectedTransit);
        
        returnedOptionalTransit = transitDAO.get(existingTransitId);
        
        verify(entityManager).find(Transit.class, existingTransitId);
        
        assertEquals("Should return Optional with existing Transit", expectedTransit,
                returnedOptionalTransit.get());
    }
    
    @Test
    public void should_TryToFindNotExistingTransitAndReturnEmptyOptional() throws Exception {
        long notExistingTransitId = 2L;
        Optional<Transit> returnedOptionalTransit;
        
        when(entityManager.find(Transit.class, notExistingTransitId)).thenReturn(null);
        
        returnedOptionalTransit = transitDAO.get(notExistingTransitId);
        
        verify(entityManager).find(Transit.class, notExistingTransitId);
        
        assertFalse("Should return empty Optional", returnedOptionalTransit.isPresent());
    }
    
    @Test
    public void should_RemoveExistingTransit() throws Exception {
        long existingTransitId = 2L;
        Transit existingTransit = mock(Transit.class);
        
        when(entityManager.find(Transit.class, existingTransitId)).thenReturn(existingTransit);
        
        transitDAO.delete(existingTransitId);
        
        InOrder inOrder = inOrder(entityManager);
        inOrder.verify(entityManager).find(Transit.class, existingTransitId);
        inOrder.verify(entityManager).remove(existingTransit);
    }
    
}
