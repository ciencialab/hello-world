package org.ciencialabart.journeyplanner.place;

public interface PlaceDAO {

    long create(Place journeyPlan);

    void update(Place journeyPlan);
    
    Place get(long id);

    void delete(long id);
    
}
