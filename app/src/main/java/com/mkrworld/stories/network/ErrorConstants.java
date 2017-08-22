package com.mkrworld.stories.network;

/**
 * Created by A1ZFKXA3 on 8/22/2017.
 */

public interface ErrorConstants {
    int ERROR_CODE_MISCELLANEOUS  = 99;
    String ERROR_MESSAGE_MISCELLANEOUS = "MISCELLANEOUS ERROR";

    int ERROR_CODE_NO_NETWORK = 100;
    String ERROR_MESSAGE_NO_NETWORK = "No Network Connection";

    int ERROR_CODE_UNSUPPORTED_ENCODING = 100;
    String ERROR_MESSAGE_UNSUPPORTED_ENCODING = "Network Unsupported Exception";

    int ERROR_CODE_MALFORMED_URL_EXCEPTION = 100;
    String ERROR_MESSAGE_MALFORMED_URL_EXCEPTION = "Network Malformed Url Exception";

    int ERROR_CODE_IO_EXCEPTION = 100;
    String ERROR_MESSAGE_IO_EXCEPTION = "Network Input/Output Exception";

    int ERROR_CODE_JSON_FORMAT = 100;
    String ERROR_MESSAGE_JSON_FORMAT = "Json format exception";

}
