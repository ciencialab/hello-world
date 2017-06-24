package org.ciencialabart.journeyplanner.place.dto;

public class PlaceDTO {
    
    private Long id;
    private String name;
    
    public PlaceDTO() {}

    public PlaceDTO(String name) {
        this.name = name;
    }

    public PlaceDTO(long id, String name) {
        this(name);
        
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof PlaceDTO) {
            PlaceDTO otherPlaceDTO = (PlaceDTO) other;
            
            return id == null ? otherPlaceDTO.id == null : id.equals(otherPlaceDTO.id) &&
                    name.equals(otherPlaceDTO.name);
        }
        
        return false;
    }
    
    @Override
    public int hashCode() {
        int result = id == null ? 0 : id.hashCode();
        
        result = 31 * result + name.hashCode();
        
        return result;
    }
    
}
