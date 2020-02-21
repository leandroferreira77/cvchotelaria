package br.com.cvc.corp.hotelaria.configs.logs;

import org.slf4j.MDC;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class StructuredLog {

    public static StructuredLogBuilder builder() {
        return new StructuredLogBuilder();
    }

    public static StructuredLogRemove remove() {
        return new StructuredLogRemove();
    }

    public static class StructuredLogBuilder {

        StructuredLogBuilder() {
        }

        public StructuredLogBuilder exception(final Class<? extends Throwable> ex) {
            MDC.put("exception", ex.getSimpleName());
            return this;
        }

        public StructuredLogBuilder errorField(final String errorField) {
            MDC.put("errorField", errorField);
            return this;
        }

        public StructuredLogBuilder errorValue(final Object errorValue) {
            MDC.put("errorValue", String.valueOf(errorValue));
            return this;
        }

        public StructuredLogBuilder objectRequest(final String objectRequest) {
            MDC.put("objectRequest", objectRequest);
            return this;
        }

        public StructuredLogBuilder objectResponse(final String objectResponse) {
            MDC.put("objectResponse", objectResponse);
            return this;
        }

        public StructuredLogBuilder httpStatus(final int httpStatus) {
            MDC.put("httpStatus", String.valueOf(httpStatus));
            return this;
        }

        public StructuredLogBuilder errorCode(final String errorCode) {
            MDC.put("errorCode", errorCode);
            return this;
        }

        public StructuredLogBuilder errorMessage(final String errorMessage) {
            MDC.put("errorMessage", errorMessage);
            return this;
        }

        public StructuredLogBuilder externalErrorCode(final String externalErrorCode) {
            MDC.put("externalErrorCode", externalErrorCode);
            return this;
        }

        public StructuredLogBuilder externalErrorMessage(final String externalErrorMessage) {
            MDC.put("externalErrorMessage", externalErrorMessage);
            return this;
        }

        public StructuredLogBuilder producedObject(final String producedObject) {
            MDC.put("producedObject", producedObject);
            return this;
        }

        public StructuredLogBuilder response(final String response) {
            MDC.put("response", response);
            return this;
        }

        public StructuredLogBuilder sourceIpList(final List<String> sourceIpList) {
            if (!CollectionUtils.isEmpty(sourceIpList)) {
                final String firstIP[] = sourceIpList.get(0).split(",");
                MDC.put("sourceIpList", String.valueOf(firstIP[0]));
            }
            return this;
        }
    }

    public static class StructuredLogRemove {

        public StructuredLogRemove exception() {
            MDC.remove("exception");
            return this;
        }

        public StructuredLogRemove hystrixFailureType() {
            MDC.remove("hystrixFailureType");
            return this;
        }

        public StructuredLogRemove httpStatus() {
            MDC.remove("httpStatus");
            return this;
        }

        public StructuredLogRemove objectRequest() {
            MDC.remove("objectRequest");
            return this;
        }

        public StructuredLogRemove objectResponse() {
            MDC.remove("objectResponse");
            return this;
        }

        public StructuredLogRemove producedObject() {
            MDC.remove("producedObject");
            return this;
        }

        public StructuredLogRemove response() {
            MDC.remove("response");
            return this;
        }

    }
}
