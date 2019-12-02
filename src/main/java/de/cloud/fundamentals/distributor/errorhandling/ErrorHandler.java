package de.cloud.fundamentals.distributor.errorhandling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class ErrorHandler implements ErrorController {

    private static final String ERROR_PATH = "/error";
    private final Logger LOGGER = LoggerFactory.getLogger(ErrorHandler.class);
    private final ErrorAttributes errorAttributes;
    @Value("${debug}")
    private boolean debug;


    @Autowired
    public ErrorHandler(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @ResponseBody
    @RequestMapping(value = ERROR_PATH)
    public ResponseEntity<ErrorJSON> handleError(WebRequest request, HttpServletResponse response) {
        ErrorJSON errorDetails = new ErrorJSON(response.getStatus(), getErrorAttributes(request, debug));
        LOGGER.error(errorDetails.toString());
        return ResponseEntity
                .status(response.getStatus())
                .body(errorDetails);
    }

    private Map<String, Object> getErrorAttributes(WebRequest request, boolean includeStackTrace) {
        return errorAttributes.getErrorAttributes(request, includeStackTrace);
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
