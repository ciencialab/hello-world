package org.ciencialabart.journeyplanner.endtoend;

import static org.ciencialabart.journeyplanner.endtoend.PlaceLifecycleTest.*;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.ciencialabart.journeyplanner.ApplicationConfiguration;
import org.ciencialabart.journeyplanner.transit.dto.TransitDTO;
import org.ciencialabart.journeyplanner.transit.event.dto.TransitEventDTO;
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

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfiguration.class)
@WebAppConfiguration
public class TransitLifecycleTest {
    
    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;
    
    @Before
    public void setupMvc() {
        mockMvc = webAppContextSetup(context).build();
    }
    
    @Test
    public void shouldTransitSupportNotAlteringCRUDOperations() throws Exception {
        long transitId;
        long sourcePlaceId;
        long destinationPlaceId;
        String sourcePlaceName = "Warsaw";
        String destinationPlaceName = "Krakow";
        ZoneOffset localTimeZoneOffset = ZoneOffset.ofHours(2);
        
        sourcePlaceId = PlaceLifecycleTest.createPlace(mockMvc, sourcePlaceName);
        destinationPlaceId = PlaceLifecycleTest.createPlace(mockMvc, destinationPlaceName);
        
        TransitDTO transitDTO = new TransitDTO(
                new TransitEventDTO(
                        sourcePlaceId,
                        OffsetDateTime.of(LocalDateTime.of(2017, 6, 16, 12, 00), localTimeZoneOffset)),
                new TransitEventDTO(
                        destinationPlaceId,
                        OffsetDateTime.of(LocalDateTime.of(2017, 6, 16, 15, 30), localTimeZoneOffset)));
        
        transitId = createTransit(transitDTO);
        
        assertTransitForId(transitDTO, transitId);
        assertPlaceNotRemovable(mockMvc, sourcePlaceId);
        assertPlaceNotRemovable(mockMvc, destinationPlaceId);
        
        deleteTransit(transitId);
        
        assertTransitMissing(transitId);
        assertPlaceExists(mockMvc, sourcePlaceId);
        assertPlaceExists(mockMvc, destinationPlaceId);
        
        deletePlace(mockMvc, sourcePlaceId);
        deletePlace(mockMvc, destinationPlaceId);
    }
    
    private long createTransit(TransitDTO transit) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        
        return Long.valueOf(mockMvc
                .perform(post("/transits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(transit)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString());
    }

    private void assertTransitForId(TransitDTO transit, long id) throws Exception {
        mockMvc.perform(get("/transits/" + id)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is((int) id)))
            .andExpect(jsonPath("$.departureEvent.placeId", is((int) transit.getDepartureEvent().getPlaceId())))
            .andExpect(jsonPath("$.departureEvent.time", is(
                    transit.getDepartureEvent().getTime().toString())))
            .andExpect(jsonPath("$.arrivalEvent.placeId", is((int) transit.getArrivalEvent().getPlaceId())))
            .andExpect(jsonPath("$.arrivalEvent.time", is(
                    transit.getArrivalEvent().getTime().toString())));
    }

    private void deleteTransit(long transitId) throws Exception {
        mockMvc.perform(delete("/transits/" + transitId))
            .andExpect(status().isOk());
    }

    private void assertTransitMissing(long transitId) throws Exception {
        mockMvc.perform(get("/transits/" + transitId)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }
    
}
