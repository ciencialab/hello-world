package org.ciencialabart.journeyplanner.unit.journeyplan.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.ciencialabart.journeyplanner.journeyplan.JourneyPlan;
import org.ciencialabart.journeyplanner.journeyplan.repository.JourneyPlanDAO;
import org.ciencialabart.journeyplanner.journeyplan.service.JourneyPlanDefaultService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class JourneyPlanServiceTest {
    
    @Mock
    private JourneyPlanDAO journeyPlanDAO;

    @InjectMocks
    private JourneyPlanDefaultService journeyPlanService;
    
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void should_CreateJourneyPlanAndReturnItsId_ForNameGiven() throws Exception {
        String newJourneyPlanName = "New journey plan";
        ArgumentCaptor<JourneyPlan> journeyPlanPassed = ArgumentCaptor.forClass(JourneyPlan.class);
        long expectedNewJourneyPlanId = 2L;
        long returnedNewJourneyPlanId;
        
        when(journeyPlanDAO.create(any())).thenReturn(expectedNewJourneyPlanId);
        
        returnedNewJourneyPlanId = journeyPlanService.createForName(newJourneyPlanName);
        
        verify(journeyPlanDAO).create(journeyPlanPassed.capture());
        
        assertEquals(newJourneyPlanName, journeyPlanPassed.getValue().getName());
        assertEquals("Should return new JourneyPlan id", expectedNewJourneyPlanId, returnedNewJourneyPlanId);
    }
    
    @Test
    public void should_UpdateExistingJourneyPlan_ForNewNameGiven() throws Exception {
        String newJourneyPlanName = "Renamed journey plan";
        ArgumentCaptor<JourneyPlan> journeyPlanPassed = ArgumentCaptor.forClass(JourneyPlan.class);
        Long updatedJourneyPlanId = 2L;
        
        journeyPlanService.rename(updatedJourneyPlanId, newJourneyPlanName);
        
        verify(journeyPlanDAO).update(journeyPlanPassed.capture());
        
        assertEquals(updatedJourneyPlanId, journeyPlanPassed.getValue().getId());
        assertEquals(newJourneyPlanName, journeyPlanPassed.getValue().getName());
    }
    
    @Test
    public void should_GetAndReturnExistingOptionalJourneyPlan() throws Exception {
        long existingJourneyPlanId = 2L;
        JourneyPlan expectedJourneyPlan = mock(JourneyPlan.class);
        Optional<JourneyPlan> returnedOptionalJourneyPlan;
        
        when(journeyPlanDAO.get(existingJourneyPlanId)).thenReturn(Optional.of(expectedJourneyPlan));
        
        returnedOptionalJourneyPlan = journeyPlanService.get(existingJourneyPlanId);
        
        verify(journeyPlanDAO).get(existingJourneyPlanId);
        
        assertEquals("Should return Optional with existing JourneyPlan", expectedJourneyPlan, returnedOptionalJourneyPlan.get());
    }
    
    @Test
    public void should_DeleteExistingJourneyPlan() throws Exception {
        long existingJourneyPlanId = 2L;
        
        journeyPlanService.delete(existingJourneyPlanId);
        
        verify(journeyPlanDAO).delete(existingJourneyPlanId);
    }

}
