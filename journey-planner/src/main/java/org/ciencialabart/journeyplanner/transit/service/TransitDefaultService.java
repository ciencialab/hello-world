package org.ciencialabart.journeyplanner.transit.service;

import java.util.Optional;

import org.ciencialabart.journeyplanner.transit.Transit;
import org.ciencialabart.journeyplanner.transit.TransitAssembler;
import org.ciencialabart.journeyplanner.transit.dto.TransitDTO;
import org.ciencialabart.journeyplanner.transit.dto.TransitDTOAssembler;
import org.ciencialabart.journeyplanner.transit.repository.TransitDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransitDefaultService implements TransitService {
    
    private TransitDAO transitDAO;
    private TransitAssembler transitAssembler;
    private TransitDTOAssembler transitDTOAssembler;

    @Autowired
    public TransitDefaultService(TransitDAO transitDAO, 
            TransitAssembler transitAssembler, 
            TransitDTOAssembler transitDTOAssembler) {
        this.transitDAO = transitDAO;
        this.transitAssembler = transitAssembler;
        this.transitDTOAssembler = transitDTOAssembler;
    }

    @Override
    public long create(TransitDTO transitDTO) {
        return transitDAO.create(transitAssembler.assemble(transitDTO));
    }

    @Override
    public Optional<TransitDTO> get(long id) {
        Optional<Transit> optionalTransit = transitDAO.get(id);
        
        if (optionalTransit.isPresent()) {
            return Optional.of(transitDTOAssembler.assemble(optionalTransit.get()));
        }
        
        return Optional.empty();
    }

    @Override
    public void delete(long id) {
        transitDAO.delete(id);
    }

}
