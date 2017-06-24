package org.ciencialabart.journeyplanner.unit.transit.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import org.ciencialabart.journeyplanner.exception.http.ResourceNotFoundException;
import org.ciencialabart.journeyplanner.transit.controller.TransitController;
import org.ciencialabart.journeyplanner.transit.dto.TransitDTO;
import org.ciencialabart.journeyplanner.transit.event.dto.TransitEventDTO;
import org.ciencialabart.journeyplanner.transit.service.TransitService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TransitControllerTest {
    
    private MockMvc mockMvc;
    
    @Mock
    private TransitService transitService;
 
    @InjectMocks
    private TransitController transitController;
    
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(transitController)
                .build();
    }
    
    @Test
    public void should_CreateTransitAndReturnItsId_ForPostSendingTransitDTOInJson() throws Exception {
        ZoneOffset localTimeZoneOffset = ZoneOffset.ofHours(2);
        TransitDTO newTransitDTO = new TransitDTO(
                new TransitEventDTO(
                        3L,
                        OffsetDateTime.of(LocalDateTime.of(2017, 6, 16, 12, 00), localTimeZoneOffset)),
                new TransitEventDTO(
                        4L,
                        OffsetDateTime.of(LocalDateTime.of(2017, 6, 16, 15, 30), localTimeZoneOffset)));
        
        when(transitService.create(newTransitDTO)).thenReturn(2L);
        
        ObjectMapper mapper = new ObjectMapper();
        mockMvc
            .perform(post("/transits")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(newTransitDTO)))
            .andExpect(status().isOk())
            .andExpect(content().string("2"));
        
        verify(transitService).create(newTransitDTO);
    }
    
    @Test
    public void should_ReturnTransitDTO_ForGetRequestingJsonWithExistingIdInPath() throws Exception {
        long transitId = 2L;
        ZoneOffset localTimeZoneOffset = ZoneOffset.ofHours(2);
        TransitDTO transitDTO = new TransitDTO(
                transitId,
                new TransitEventDTO(
                        3L,
                        OffsetDateTime.of(LocalDateTime.of(2017, 6, 16, 12, 00), localTimeZoneOffset)),
                new TransitEventDTO(
                        4L,
                        OffsetDateTime.of(LocalDateTime.of(2017, 6, 16, 15, 30), localTimeZoneOffset)));
        
        when(transitService.get(transitId)).thenReturn(Optional.of(transitDTO));
        
        mockMvc.perform(get("/transits/2")
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(2)))
        .andExpect(jsonPath("$.departureEvent.placeId", is((int) transitDTO.getDepartureEvent().getPlaceId())))
        .andExpect(jsonPath("$.departureEvent.time", is(
                transitDTO.getDepartureEvent().getTime().toString())))
        .andExpect(jsonPath("$.arrivalEvent.placeId", is((int) transitDTO.getArrivalEvent().getPlaceId())))
        .andExpect(jsonPath("$.arrivalEvent.time", is(
                transitDTO.getArrivalEvent().getTime().toString())));
        
        verify(transitService).get(transitId);
    }
    
    @Test
    public void should_ReturnStatusNotFound_ForGetRequestingJsonWithNotExistingIdInPath() throws Exception {
        long transitId = 3L;
        
        when(transitService.get(transitId)).thenThrow(new ResourceNotFoundException());
        
        mockMvc.perform(get("/transits/3")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
        
        verify(transitService).get(transitId);
    }
    
    @Test
    public void should_DeleteTransitAndReturnStatusOk_ForDeleteWithExistingIdInPath() throws Exception {
        mockMvc.perform(delete("/transits/2"))
            .andExpect(status().isOk());
        
        verify(transitService).delete(2L);
    }
    
}
