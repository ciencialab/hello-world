package org.ciencialabart.journeyplanner.unit.place.repository;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityManager;

import org.ciencialabart.journeyplanner.exception.RemovedResourceInUseException;
import org.ciencialabart.journeyplanner.exception.http.ResourceNotFoundException;
import org.ciencialabart.journeyplanner.place.Place;
import org.ciencialabart.journeyplanner.place.repository.PlaceJPADAO;
import org.ciencialabart.journeyplanner.transit.Transit;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class PlaceDAOTest {
    
    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private PlaceJPADAO placeDAO;
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void should_PersistPlaceAndReturnItsId() throws Exception {
        Place persistedPlace = mock(Place.class);
        long expectedNewPlaceId = 2L;
        long returnedNewPlaceId;
        
        when(persistedPlace.getId()).thenReturn(expectedNewPlaceId);
        
        returnedNewPlaceId = placeDAO.create(persistedPlace);
        
        InOrder inOrder = inOrder(entityManager, persistedPlace);
        inOrder.verify(entityManager).persist(persistedPlace);
        inOrder.verify(persistedPlace).getId();
        
        assertEquals("Should return persisted Place id", expectedNewPlaceId, returnedNewPlaceId);
    }
    
    @Test
    public void should_MergePlace_IfExist() throws Exception {
        Place mergedPlace = mock(Place.class);
        Long mergedPlaceId = 2L;
        
        when(mergedPlace.getId()).thenReturn(mergedPlaceId);
        when(entityManager.find(Place.class, mergedPlaceId)).thenReturn(mock(Place.class));
        
        placeDAO.update(mergedPlace);
        
        verify(entityManager).merge(mergedPlace);
    }
    
    @Test
    public void should_throwResourceNotFoundException_IfPlaceBeingUpdatedNotExist() throws Exception {
        Place mergedPlace = mock(Place.class);
        Long mergedPlaceId = 2L;
        
        when(mergedPlace.getId()).thenReturn(mergedPlaceId);
        when(entityManager.find(Place.class, mergedPlaceId)).thenReturn(null);
        
        thrown.expect(ResourceNotFoundException.class);
        
        placeDAO.update(mergedPlace);
    }
    
    @Test
    public void should_FindExistingPlaceAndReturnItInOptional() throws Exception {
        long existingPlaceId = 2L;
        Place expectedPlace = mock(Place.class);
        Optional<Place> returnedOptionalPlace;
        
        when(entityManager.find(Place.class, existingPlaceId)).thenReturn(expectedPlace);
        
        returnedOptionalPlace = placeDAO.get(existingPlaceId);
        
        verify(entityManager).find(Place.class, existingPlaceId);
        
        assertEquals("Should return Optional with existing Place", expectedPlace,
                returnedOptionalPlace.get());
    }
    
    @Test
    public void should_TryToFindNotExistingPlaceAndReturnEmptyOptional() throws Exception {
        long notExistingPlaceId = 2L;
        Optional<Place> returnedOptionalPlace;
        
        when(entityManager.find(Place.class, notExistingPlaceId)).thenReturn(null);
        
        returnedOptionalPlace = placeDAO.get(notExistingPlaceId);
        
        verify(entityManager).find(Place.class, notExistingPlaceId);
        
        assertFalse("Should return empty Optional", returnedOptionalPlace.isPresent());
    }
    
    @Test
    public void should_RemoveExistingPlace_When_NotInUse() throws Exception {
        long existingPlaceId = 2L;
        Place existingPlace = mock(Place.class);
        Set<Transit> existingPlaceDepartureTransits = Collections.emptySet();
        Set<Transit> existingPlaceArrivalTransits = Collections.emptySet();
        
        when(entityManager.find(Place.class, existingPlaceId)).thenReturn(existingPlace);
        when(existingPlace.getDepartureTransits()).thenReturn(existingPlaceDepartureTransits);
        when(existingPlace.getArrivalTransits()).thenReturn(existingPlaceArrivalTransits);
        
        placeDAO.delete(existingPlaceId);
        
        InOrder inOrder = inOrder(entityManager);
        inOrder.verify(entityManager).find(Place.class, existingPlaceId);
        inOrder.verify(entityManager).remove(existingPlace);
    }
    
    @Test
    public void should_ThrowResourceNotFoundException_IfPlaceBeingDeletedNotExist() throws Exception {
        long notExistingPlaceId = 2L;
        
        when(entityManager.find(Place.class, notExistingPlaceId)).thenReturn(null);
        
        thrown.expect(ResourceNotFoundException.class);
        
        placeDAO.delete(notExistingPlaceId);
        
        verify(entityManager).find(Place.class, notExistingPlaceId);
    }
    
    @Test
    public void should_ThrowRemovedResourceInUseException_When_InUseByDepartureTransit() throws Exception {
        long existingPlaceId = 2L;
        Place existingPlace = mock(Place.class);
        Set<Transit> existingPlaceDepartureTransits = Collections.singleton(mock(Transit.class));
        Set<Transit> existingPlaceArrivalTransits = Collections.emptySet();
        
        when(entityManager.find(Place.class, existingPlaceId)).thenReturn(existingPlace);
        when(existingPlace.getDepartureTransits()).thenReturn(existingPlaceDepartureTransits);
        when(existingPlace.getArrivalTransits()).thenReturn(existingPlaceArrivalTransits);
        
        thrown.expect(RemovedResourceInUseException.class);
        
        placeDAO.delete(existingPlaceId);
        
        verify(entityManager).find(Place.class, existingPlaceId);
    }
    
    @Test
    public void should_ThrowRemovedResourceInUseException_When_InUseByArrivalTransit() throws Exception {
        long existingPlaceId = 2L;
        Place existingPlace = mock(Place.class);
        Set<Transit> existingPlaceDepartureTransits = Collections.emptySet();
        Set<Transit> existingPlaceArrivalTransits = Collections.singleton(mock(Transit.class));
        
        when(entityManager.find(Place.class, existingPlaceId)).thenReturn(existingPlace);
        when(existingPlace.getDepartureTransits()).thenReturn(existingPlaceDepartureTransits);
        when(existingPlace.getArrivalTransits()).thenReturn(existingPlaceArrivalTransits);
        
        thrown.expect(RemovedResourceInUseException.class);
        
        placeDAO.delete(existingPlaceId);
        
        verify(entityManager).find(Place.class, existingPlaceId);
    }
    
}
