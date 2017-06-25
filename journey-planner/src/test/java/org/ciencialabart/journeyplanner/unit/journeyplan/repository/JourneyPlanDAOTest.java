package org.ciencialabart.journeyplanner.unit.journeyplan.repository;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.ciencialabart.journeyplanner.exception.http.ResourceNotFoundException;
import org.ciencialabart.journeyplanner.journeyplan.JourneyPlan;
import org.ciencialabart.journeyplanner.journeyplan.repository.JourneyPlanJPADAO;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class JourneyPlanDAOTest {
    
    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private JourneyPlanJPADAO journeyPlanDAO;
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void should_PersistJourneyPlanAndReturnItsId() throws Exception {
        JourneyPlan persistedJourneyPlan = mock(JourneyPlan.class);
        long expectedNewJourneyPlanId = 2L;
        long returnedNewJourneyPlanId;
        
        when(persistedJourneyPlan.getId()).thenReturn(expectedNewJourneyPlanId);
        
        returnedNewJourneyPlanId = journeyPlanDAO.create(persistedJourneyPlan);
        
        InOrder inOrder = inOrder(entityManager, persistedJourneyPlan);
        inOrder.verify(entityManager).persist(persistedJourneyPlan);
        inOrder.verify(persistedJourneyPlan).getId();
        
        assertEquals("Should return persisted JourneyPlan id", expectedNewJourneyPlanId, returnedNewJourneyPlanId);
    }
    
    @Test
    public void should_MergeJourneyPlan_IfExist() throws Exception {
        JourneyPlan mergedJourneyPlan = mock(JourneyPlan.class);
        Long mergedJourneyPlanId = 2L;
        
        when(mergedJourneyPlan.getId()).thenReturn(mergedJourneyPlanId);
        when(entityManager.find(JourneyPlan.class, mergedJourneyPlanId)).thenReturn(mock(JourneyPlan.class));
        
        journeyPlanDAO.update(mergedJourneyPlan);
        
        verify(entityManager).merge(mergedJourneyPlan);
    }
    
    @Test
    public void should_ThrowResourceNotFoundException_IfJourneyPlanBeingUpdatedNotExist() throws Exception {
        JourneyPlan mergedJourneyPlan = mock(JourneyPlan.class);
        Long mergedJourneyPlanId = 2L;
        
        when(mergedJourneyPlan.getId()).thenReturn(mergedJourneyPlanId);
        when(entityManager.find(JourneyPlan.class, mergedJourneyPlanId)).thenReturn(null);
        
        thrown.expect(ResourceNotFoundException.class);
        
        journeyPlanDAO.update(mergedJourneyPlan);
    }
    
    @Test
    public void should_FindExistingJourneyPlanAndReturnItInOptional() throws Exception {
        long existingJourneyPlanId = 2L;
        JourneyPlan expectedJourneyPlan = mock(JourneyPlan.class);
        Optional<JourneyPlan> returnedOptionalJourneyPlan;
        
        when(entityManager.find(JourneyPlan.class, existingJourneyPlanId)).thenReturn(expectedJourneyPlan);
        
        returnedOptionalJourneyPlan = journeyPlanDAO.get(existingJourneyPlanId);
        
        verify(entityManager).find(JourneyPlan.class, existingJourneyPlanId);
        
        assertEquals("Should return Optional with existing JourneyPlan", expectedJourneyPlan,
                returnedOptionalJourneyPlan.get());
    }
    
    @Test
    public void should_TryToFindNotExistingJourneyPlanAndReturnEmptyOptional() throws Exception {
        long notExistingJourneyPlanId = 2L;
        Optional<JourneyPlan> returnedOptionalJourneyPlan;
        
        when(entityManager.find(JourneyPlan.class, notExistingJourneyPlanId)).thenReturn(null);
        
        returnedOptionalJourneyPlan = journeyPlanDAO.get(notExistingJourneyPlanId);
        
        verify(entityManager).find(JourneyPlan.class, notExistingJourneyPlanId);
        
        assertFalse("Should return empty Optional", returnedOptionalJourneyPlan.isPresent());
    }
    
    @Test
    public void should_RemoveExistingJourneyPlan_IfExists() throws Exception {
        long existingJourneyPlanId = 2L;
        JourneyPlan existingJourneyPlan = mock(JourneyPlan.class);
        
        when(entityManager.find(JourneyPlan.class, existingJourneyPlanId)).thenReturn(existingJourneyPlan);
        
        journeyPlanDAO.delete(existingJourneyPlanId);
        
        InOrder inOrder = inOrder(entityManager);
        inOrder.verify(entityManager).find(JourneyPlan.class, existingJourneyPlanId);
        inOrder.verify(entityManager).remove(existingJourneyPlan);
    }
    
    @Test
    public void should_ThrowResourceNotFoundException_IfJourneyPlanBeingDeletedNotExist() throws Exception {
        long notExistingJourneyPlanId = 2L;
        
        when(entityManager.find(JourneyPlan.class, notExistingJourneyPlanId)).thenReturn(null);
        
        thrown.expect(ResourceNotFoundException.class);
        
        journeyPlanDAO.delete(notExistingJourneyPlanId);
        
        verify(entityManager).find(JourneyPlan.class, notExistingJourneyPlanId);
    }
    
}
