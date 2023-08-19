package com.pavel.socialweb.Exception;

import com.pavel.socialweb.Model.ErrorModel;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@Controller
public class ErrorControllerImpl implements ErrorController {
    private final ErrorAttributes errorAttributes;

    private static final String ERROR = "/error";

    public ErrorControllerImpl(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @RequestMapping(ERROR)
    public ResponseEntity<ErrorModel> Errors(WebRequest webRequest,
                                             ErrorAttributeOptions errorAttributeOptions){
        Map<String, Object> errors = errorAttributes.getErrorAttributes(webRequest, ErrorAttributeOptions.of(ErrorAttributeOptions.Include.MESSAGE, ErrorAttributeOptions.Include.EXCEPTION));
        return ResponseEntity
                .status((Integer) errors.get("status"))
                .body(
                        ErrorModel
                                .builder()
                                .error((String) errors.get("error"))
                                .error_description((String) errors.get("message"))
                                .build()
                );
    }
}
