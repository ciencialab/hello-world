package org.ciencialabart.journeyplanner.endtoend;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.ciencialabart.journeyplanner.ApplicationConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfiguration.class)
@WebAppConfiguration
public class JourneyPlanLifecycleTest {
    
    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;
    
    @Before
    public void setupMvc() {
        mockMvc = webAppContextSetup(context).build();
    }
    
    @Test
    public void shouldJourneyPlanSupportCRUDOperations() throws Exception {
        long journeyPlanId;
        String newJourneyPlanName = "New journey plan";
        String modifiedJourneyPlanName = "Modified journey plan";
        
        journeyPlanId = createJourneyPlan(newJourneyPlanName);
        assertJourneyPlanNameForId(newJourneyPlanName, journeyPlanId);
        
        renameJourneyPlan(journeyPlanId, modifiedJourneyPlanName);
        assertJourneyPlanNameForId(modifiedJourneyPlanName, journeyPlanId);
        
        deleteJourneyPlan(journeyPlanId);
        assertJourneyPlanMissing(journeyPlanId);
    }
    
    private long createJourneyPlan(String newJourneyPlanName) throws Exception {
        return Long.valueOf(mockMvc
                .perform(post("/journey-plan")
                        .param("name", newJourneyPlanName))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString());
    }
    
    private void renameJourneyPlan(long journeyPlanId, String modifiedJourneyPlanName) throws Exception {
        mockMvc.perform(post("/journey-plan/" + journeyPlanId)
                    .param("name", modifiedJourneyPlanName))
            .andExpect(status().isOk());
    }

    private void assertJourneyPlanNameForId(String journeyPlanName, long id) throws Exception {
        mockMvc.perform(get("/journey-plan/" + id)
                    .accept(MediaType.valueOf("application/json")))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is((int) id)))
            .andExpect(jsonPath("$.name", is(journeyPlanName)));
    }

    private void deleteJourneyPlan(long journeyPlanId) throws Exception {
        mockMvc.perform(delete("/journey-plan/" + journeyPlanId))
            .andExpect(status().isOk());
    }

    private void assertJourneyPlanMissing(long journeyPlanId) throws Exception {
        mockMvc.perform(get("/journey-plan/" + journeyPlanId)
                    .accept(MediaType.valueOf("application/json")))
            .andExpect(status().isOk())
            .andExpect(content().string(""));
    }
    
}
