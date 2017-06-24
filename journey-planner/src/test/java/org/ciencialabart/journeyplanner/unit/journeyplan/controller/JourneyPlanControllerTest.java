package org.ciencialabart.journeyplanner.unit.journeyplan.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.Optional;

import org.ciencialabart.journeyplanner.exception.http.ResourceNotFoundException;
import org.ciencialabart.journeyplanner.journeyplan.JourneyPlan;
import org.ciencialabart.journeyplanner.journeyplan.controller.JourneyPlanController;
import org.ciencialabart.journeyplanner.journeyplan.service.JourneyPlanService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

public class JourneyPlanControllerTest {
    
    private MockMvc mockMvc;
    
    @Mock
    private JourneyPlanService journeyPlanService;
 
    @InjectMocks
    private JourneyPlanController journeyPlanController;
    
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(journeyPlanController)
                .build();
    }
    
    @Test
    public void should_CreateJourneyPlanAndReturnItsId_ForPostWithNameInParam() throws Exception {
        String newJourneyPlanName = "New journey plan";
        
        when(journeyPlanService.createForName(newJourneyPlanName)).thenReturn(2L);
        
        mockMvc
            .perform(post("/journey-plans")
                    .param("name", newJourneyPlanName))
            .andExpect(status().isOk())
            .andExpect(content().string("2"));
        
        verify(journeyPlanService).createForName(newJourneyPlanName);
    }
    
    @Test
    public void should_ReturnStatusBadRequest_ForPostWithEmptyNameInParam() throws Exception {
        mockMvc
            .perform(post("/journey-plans")
                    .param("name", ""))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    public void should_RenameJourneyPlanAndReturnStatusOk_ForPostWithExistingIdInPathAndNameInParam()
            throws Exception {
        String modifiedJourneyPlanName = "Modified journey plan";
        
        mockMvc.perform(post("/journey-plans/2")
                .param("name", modifiedJourneyPlanName))
            .andExpect(status().isOk());
        
        verify(journeyPlanService).rename(2L, modifiedJourneyPlanName);
    }
    
    @Test
    public void should_ReturnStatusNotFound_ForPostWithNotExistingIdInPathAndNameInParam()
            throws Exception {
        String modifiedJourneyPlanName = "Modified journey plan";
        
        doThrow(new ResourceNotFoundException()).when(journeyPlanService).rename(3L, modifiedJourneyPlanName);
        
        mockMvc.perform(post("/journey-plans/3")
                .param("name", modifiedJourneyPlanName))
            .andExpect(status().isNotFound());
        
        verify(journeyPlanService).rename(3L, modifiedJourneyPlanName);
    }
    
    @Test
    public void should_ReturnStatusBadRequest_ForPostWithIdInPathAndEmptyNameInParam()
            throws Exception {
        mockMvc.perform(post("/journey-plans/2")
                .param("name", ""))
            .andExpect(status().isBadRequest());
    }
    
    @Test
    public void should_ReturnJourneyPlan_ForGetRequestingJsonWithExistingIdInPath() throws Exception {
        long journeyPlanId = 2L;
        String journeyPlanName = "My journey plan";
        JourneyPlan journeyPlan = new JourneyPlan(2L, journeyPlanName);
        
        when(journeyPlanService.get(journeyPlanId)).thenReturn(Optional.of(journeyPlan));
        
        mockMvc.perform(get("/journey-plans/2")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(2)))
            .andExpect(jsonPath("$.name", is(journeyPlanName)));
        
        verify(journeyPlanService).get(journeyPlanId);
    }
    
    @Test
    public void should_ReturnStatusNotFound_ForGetRequestingJsonWithNotExistingIdInPath() throws Exception {
        long journeyPlanId = 3L;
        
        when(journeyPlanService.get(journeyPlanId)).thenThrow(new ResourceNotFoundException());
        
        mockMvc.perform(get("/journey-plans/3")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
        
        verify(journeyPlanService).get(journeyPlanId);
    }
    
    @Test
    public void should_DeleteJourneyPlanAndReturnStatusOk_ForDeleteWithExistingIdInPath() throws Exception {
        mockMvc.perform(delete("/journey-plans/2"))
            .andExpect(status().isOk());
        
        verify(journeyPlanService).delete(2L);
    }
    
    @Test
    public void should_ReturnStatusNotFound_ForDeleteWithNotExistingIdInPath() throws Exception {
        long journeyPlanId = 3L;
        
        doThrow(new ResourceNotFoundException()).when(journeyPlanService).delete(journeyPlanId);
        
        mockMvc.perform(delete("/journey-plans/3"))
            .andExpect(status().isNotFound());
        
        verify(journeyPlanService).delete(journeyPlanId);
    }
    
}
