/*
 * This file is part of Some Fine Application.
 *
 * Copyright (C) 2020
 * Caprica Software Limited (capricasoftware.co.uk)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.co.caprica.sfa.webapp;

import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

import static javax.servlet.RequestDispatcher.ERROR_EXCEPTION;
import static javax.servlet.RequestDispatcher.ERROR_STATUS_CODE;

/**
 * Custom error controller to map the error path to the client application so the error may be displayed via client-side routing.
 *
 * FIXME this may not be the best way to do this, but it works
 */
@Controller
@Slf4j
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest req) {
        Integer statusCode = (Integer) req.getAttribute(ERROR_STATUS_CODE);
        Exception exception = (Exception) req.getAttribute(ERROR_EXCEPTION);

        Throwable rootCauseThrowable = Throwables.getRootCause(exception);
        String message = rootCauseThrowable.getMessage();

        log.debug("Redirecting to client for error {}: {}", statusCode, message);

        return String.format("redirect:/system-error/%d", statusCode);
    }

    @Override
    public String getErrorPath() {
        // Unused
        return null;
    }
}
