package org.ciencialabart.journeyplanner.endtoend;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
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
public class PlaceLifecycleTest {
    
    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;
    
    @Before
    public void setupMvc() {
        mockMvc = webAppContextSetup(context).build();
    }
    
    @Test
    public void shouldPlaceSupportCRUDOperations() throws Exception {
        long placeId;
        String newPlaceName = "New place";
        String modifiedPlaceName = "Modified place";
        
        placeId = createPlace(newPlaceName);
        assertPlaceNameForId(newPlaceName, placeId);
        
        renamePlace(placeId, modifiedPlaceName);
        assertPlaceNameForId(modifiedPlaceName, placeId);
        
        deletePlace(placeId);
        assertPlaceMissing(placeId);
    }
    
    private long createPlace(String newPlaceName) throws Exception {
        return createPlace(mockMvc, newPlaceName);
    }
    
    public static long createPlace(MockMvc mockMvc, String newPlaceName) throws Exception {
        return Long.valueOf(mockMvc
                .perform(post("/places")
                        .param("name", newPlaceName))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString());
    }
    
    private void renamePlace(long placeId, String modifiedPlaceName) throws Exception {
        mockMvc.perform(post("/places/" + placeId)
                    .param("name", modifiedPlaceName))
            .andExpect(status().isOk());
    }

    private void assertPlaceNameForId(String placeName, long id) throws Exception {
        assertPlaceNameForId(mockMvc, placeName, id);
    }

    public static void assertPlaceNameForId(MockMvc mockMvc, String placeName, long id) throws Exception {
        mockMvc.perform(get("/places/" + id)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is((int) id)))
            .andExpect(jsonPath("$.name", is(placeName)));
    }

    private void deletePlace(long placeId) throws Exception {
        deletePlace(mockMvc, placeId);
    }

    public static void deletePlace(MockMvc mockMvc, long placeId) throws Exception {
        mockMvc.perform(delete("/places/" + placeId))
            .andExpect(status().isOk());
    }

    public static void assertPlaceNotRemovable(MockMvc mockMvc, long placeId) throws Exception {
        mockMvc.perform(delete("/places/" + placeId))
            .andExpect(status().isConflict());
    }

    private void assertPlaceMissing(long placeId) throws Exception {
        mockMvc.perform(get("/places/" + placeId)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    public static void assertPlaceExists(MockMvc mockMvc, long placeId) throws Exception {
        mockMvc.perform(get("/places/" + placeId)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
    }
    
}
