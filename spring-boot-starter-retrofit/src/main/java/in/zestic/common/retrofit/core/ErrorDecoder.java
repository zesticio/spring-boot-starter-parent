package in.zestic.common.retrofit.core;

import in.zestic.common.retrofit.exception.RetrofitException;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * When an exception occurs in the request or an invalid response result is received, the HTTP related information is decoded into the exception,
 * and the invalid response is determined by the business itself.
 */
public interface ErrorDecoder {

    /**
     * @param request  request
     * @param response response
     * @return If it returns null, the processing is ignored and the processing continues with the original response.
     */
    default RuntimeException invalidRespDecode(Request request, Response response) {
        if (!response.isSuccessful()) {
            throw RetrofitException.errorStatus(request, response);
        }
        return null;
    }


    /**
     * @param request request
     * @param cause   IOException
     * @return RuntimeException
     */
    default RuntimeException ioExceptionDecode(Request request, IOException cause) {
        return RetrofitException.errorExecuting(request, cause);
    }

    /**
     * @param request request
     * @param cause   Exception
     * @return RuntimeException
     */
    default RuntimeException exceptionDecode(Request request, Exception cause) {
        return RetrofitException.errorUnknown(request, cause);
    }
}
