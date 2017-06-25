package org.ciencialabart.journeyplanner.unit.place.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.Optional;

import org.ciencialabart.journeyplanner.exception.RemovedResourceInUseException;
import org.ciencialabart.journeyplanner.exception.http.ResourceNotFoundException;
import org.ciencialabart.journeyplanner.place.controller.PlaceController;
import org.ciencialabart.journeyplanner.place.dto.PlaceDTO;
import org.ciencialabart.journeyplanner.place.service.PlaceService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

public class PlaceControllerTest {
    
    private MockMvc mockMvc;
    
    @Mock
    private PlaceService placeService;
 
    @InjectMocks
    private PlaceController placeController;
    
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(placeController)
                .build();
    }
    
    @Test
    public void should_CreatePlaceAndReturnItsId_ForPostWithNameInParam() throws Exception {
        String newPlaceName = "New place";
        
        when(placeService.createForName(newPlaceName)).thenReturn(2L);
        
        mockMvc
            .perform(post("/places")
                    .param("name", newPlaceName))
            .andExpect(status().isOk())
            .andExpect(content().string("2"));
        
        verify(placeService).createForName(newPlaceName);
    }
    
    @Test
    public void should_ReturnStatusBadRequest_ForPostWithEmptyNameInParam() throws Exception {
        mockMvc
            .perform(post("/places")
                    .param("name", ""))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    public void should_RenamePlaceAndReturnStatusOk_ForPostWithExistingIdInPathAndNameInParam()
            throws Exception {
        String modifiedPlaceName = "Modified place";
        
        mockMvc.perform(post("/places/2")
                .param("name", modifiedPlaceName))
            .andExpect(status().isOk());
        
        verify(placeService).rename(2L, modifiedPlaceName);
    }
    
    @Test
    public void should_ReturnStatusNotFound_ForPostWithNotExistingIdInPathAndNameInParam() throws Exception {
        String modifiedPlaceName = "Modified place";
        
        doThrow(new ResourceNotFoundException()).when(placeService).rename(3L, modifiedPlaceName);
        
        mockMvc.perform(post("/places/3")
                .param("name", modifiedPlaceName))
            .andExpect(status().isNotFound());
        
        verify(placeService).rename(3L, modifiedPlaceName);
    }
    
    @Test
    public void should_ReturnStatusBadRequest_ForPostWithIdInPathAndEmptyNameInParam()
            throws Exception {
        mockMvc.perform(post("/places/2")
                .param("name", ""))
            .andExpect(status().isBadRequest());
    }
    
    @Test
    public void should_ReturnPlaceDTO_ForGetRequestingJsonWithExistingIdInPath() throws Exception {
        long placeId = 2L;
        String placeName = "My place";
        PlaceDTO placeDTO = new PlaceDTO(2L, placeName);
        
        when(placeService.get(placeId)).thenReturn(Optional.of(placeDTO));
        
        mockMvc.perform(get("/places/2")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(2)))
            .andExpect(jsonPath("$.name", is(placeName)));
        
        verify(placeService).get(placeId);
    }
    
    @Test
    public void should_ReturnStatusNotFound_ForGetRequestingJsonWithNotExistingIdInPath() throws Exception {
        long placeId = 3L;
        
        when(placeService.get(placeId)).thenThrow(new ResourceNotFoundException());
        
        mockMvc.perform(get("/places/3")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
        
        verify(placeService).get(placeId);
    }
    
    @Test
    public void should_DeletePlaceAndReturnStatusOk_ForDeleteWithExistingNotInUsePlaceIdInPath() throws Exception {
        mockMvc.perform(delete("/places/2"))
            .andExpect(status().isOk());
        
        verify(placeService).delete(2L);
    }
    
    @Test
    public void should_ReturnStatusNotFound_ForDeleteWithNotExistingPlaceIdInPath() throws Exception {
        long placeId = 3L;
        
        doThrow(new ResourceNotFoundException()).when(placeService).delete(placeId);
        
        mockMvc.perform(delete("/places/3"))
            .andExpect(status().isNotFound());
        
        verify(placeService).delete(placeId);
    }
    
    @Test
    public void should_ReturnStatusConflict_ForDeleteWithExistingInUsePlaceIdInPath() throws Exception {
        long placeId = 2L;
        
        doThrow(new RemovedResourceInUseException()).when(placeService).delete(placeId);
        
        mockMvc.perform(delete("/places/2"))
            .andExpect(status().isConflict());
        
        verify(placeService).delete(placeId);
    }
    
}
