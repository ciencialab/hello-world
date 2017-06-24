package org.ciencialabart.journeyplanner.exception.http;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ResourceInConflictingStateException extends RuntimeException {

    private static final long serialVersionUID = 7678847897156033457L;

}
