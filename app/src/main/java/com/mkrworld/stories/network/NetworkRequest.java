package com.mkrworld.stories.network;

import android.os.AsyncTask;

import com.mkrworld.stories.BuildConfig;
import com.mkrworld.stories.utils.Tracer;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Class actually manipulate the operation of sending the request to the server
 * and receiving the response from the server.<br>
 */
public class NetworkRequest {
    private static final String TAG = BuildConfig.BASE_TAG + ".NetworkRequest";
    private static final int SOCKET_TIME_OUT = 60000;
    private static final int MAX_RETRY = 3;
    private static final int POST = 1;
    private static final int GET = 2;
    private static final int DELETE = 3;

    /**
     * Method to send delete request to the server
     *
     * @param url                      Api on which hit
     * @param jsonObject               json which is send to the server
     * @param onNetworkRequestListener Listen to listen the status of sending the request to the
     *                                 server
     * @param flag                     This flag is returned in response weather success or failure
     */
    public static void sendDeleteRequest(final String url, JSONObject jsonObject, final OnNetworkRequestListener onNetworkRequestListener, final String flag) {
        if (jsonObject != null) {
            sendAsyncJsonRequest(DELETE, url, jsonObject, onNetworkRequestListener, flag);
        }
    }

    /**
     * Method to send post request to the server
     *
     * @param url                      Api on which hit
     * @param jsonObject               json which is send to the server
     * @param onNetworkRequestListener Listen to listen the status of sending the request to the
     *                                 server
     * @param flag                     This flag is returned in response weather success or failure
     */
    public static void sendPostRequest(final String url, JSONObject jsonObject, final OnNetworkRequestListener onNetworkRequestListener, final String flag) {
        if (jsonObject != null) {
            sendAsyncJsonRequest(POST, url, jsonObject, onNetworkRequestListener, flag);
        }
    }

    /**
     * Method to send post request to the server
     *
     * @param url                      Api on which hit
     * @param jsonObject               json which is send to the server
     * @param onNetworkRequestListener Listen to listen the status of sending the request to the
     *                                 server
     * @param flag                     This flag is returned in response weather success or failure
     */
    public static void sendPostRequestBulk(final String url, JSONObject jsonObject, final OnNetworkRequestListener onNetworkRequestListener, final String flag) {
        if (jsonObject != null) {
            sendAsyncJsonRequest(POST, url, jsonObject, onNetworkRequestListener, flag);
        }
    }

    /**
     * Method to send post request to the server
     *
     * @param url                      Api on which hit
     * @param jsonObject               json which is send to the server
     * @param onNetworkRequestListener Listen to listen the status of sending the request to the
     *                                 server
     * @param flag                     This flag is returned in response weather success or failure
     */
    public static void sendGetRequest(final String url, JSONObject jsonObject, final OnNetworkRequestListener onNetworkRequestListener, final String flag) {
        Tracer.debug(TAG, "NetworkRequest.sendGetRequest() URL: " + url);
        sendAsyncJsonRequest(GET, url, jsonObject, onNetworkRequestListener, flag);
    }

    // ==========================================================================================================
    // ==========================================================================================================
    // ==========================================================================================================

    /**
     * Method to send Async Request to the server
     *
     * @param URL
     * @param jsonObject
     * @param flag
     */
    private static void sendAsyncJsonRequest(final int requestType, final String URL, final JSONObject jsonObject, final OnNetworkRequestListener onNetworkRequestListener, final String flag) {
        Tracer.debug(TAG, "NetworkRequest.sendAsyncJsonRequest() URL: " + URL + "   " + (flag != null ? flag : ""));
        new AsyncTask<Void, Void, Object>() {
            @Override
            protected Object doInBackground(Void... params) {
                Object response = null;
                int i = 0;
                while (i < MAX_RETRY) {
                    switch (requestType) {
                        case GET:
                            response = excuteGet(URL);
                            break;
                        case DELETE:
                            response = excuteDelete(URL, jsonObject);
                            break;
                        case POST:
                            response = excutePost(URL, jsonObject);
                            break;
                    }
                    if (response instanceof JSONObject) {
                        break;
                    }
                    i++;
                }
                return response;
            }

            protected void onPostExecute(Object result) {
                super.onPostExecute(result);
                if (onNetworkRequestListener != null) {
                    if (result instanceof JSONObject) {
                        onNetworkRequestListener.onNetworkRequestCompleted((JSONObject) result, flag);
                    } else if (result instanceof Error) {
                        onNetworkRequestListener.onNetworkRequestFailed(((Error) result).mErrorCode, ((Error) result).mErrorMessage, flag);
                    } else {
                        onNetworkRequestListener.onNetworkRequestFailed(ErrorConstants.ERROR_CODE_MISCELLANEOUS, ErrorConstants.ERROR_MESSAGE_MISCELLANEOUS, flag);
                    }
                }
            }

            ;

        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    /**
     * Method to send Post Request
     *
     * @param strURL
     * @param jsonObject
     * @return
     */
    private static Object excutePost(String strURL, JSONObject jsonObject) {
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strURL);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0(macintosh;U;Intel Mac 05 X104; en-us; rv:1.9.2.2) Gecko/20100316 Firefox/19.0");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setReadTimeout(SOCKET_TIME_OUT);
            urlConnection.setConnectTimeout(SOCKET_TIME_OUT);
            DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
            wr.writeBytes(jsonObject.toString());
            wr.flush();
            wr.close();
            int responseCode = urlConnection.getResponseCode();

            switch (responseCode) {
                case HttpURLConnection.HTTP_OK:
                case HttpURLConnection.HTTP_ACCEPTED:
                case HttpURLConnection.HTTP_CREATED:
                case HttpURLConnection.HTTP_NO_CONTENT:
                    try {
                        InputStream inputStream = urlConnection.getInputStream();
                        byte data[] = new byte[128];
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        int l = 0;
                        while ((l = inputStream.read(data)) > 0) {
                            byteArrayOutputStream.write(data, 0, l);
                        }
                        String resp = new String(byteArrayOutputStream.toByteArray());
                        return new JSONObject(resp);
                    } catch (JSONException e) {
                        return new Error(ErrorConstants.ERROR_CODE_JSON_FORMAT, ErrorConstants.ERROR_MESSAGE_JSON_FORMAT + ". " + e.getMessage());
                    }
            }
        } catch (UnsupportedEncodingException e) {
            return new Error(ErrorConstants.ERROR_CODE_UNSUPPORTED_ENCODING, ErrorConstants.ERROR_MESSAGE_JSON_FORMAT + ". " + e.getMessage());
        } catch (MalformedURLException e) {
            return new Error(ErrorConstants.ERROR_CODE_MALFORMED_URL_EXCEPTION, ErrorConstants.ERROR_MESSAGE_JSON_FORMAT + ". " + e.getMessage());
        } catch (IOException e) {
            return new Error(ErrorConstants.ERROR_CODE_IO_EXCEPTION, ErrorConstants.ERROR_MESSAGE_JSON_FORMAT + ". " + e.getMessage());
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return new Error(ErrorConstants.ERROR_CODE_NO_NETWORK, ErrorConstants.ERROR_MESSAGE_NO_NETWORK);
    }

    /**
     * Method to send Post Request
     *
     * @param strURL
     * @return
     */
    private static Object excuteGet(String strURL) {
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strURL);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0(macintosh;U;Intel Mac 05 X104; en-us; rv:1.9.2.2) Gecko/20100316 Firefox/19.0");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setReadTimeout(SOCKET_TIME_OUT);
            urlConnection.setConnectTimeout(SOCKET_TIME_OUT);
            int responseCode = urlConnection.getResponseCode();
            switch (responseCode) {
                case HttpURLConnection.HTTP_OK:
                case HttpURLConnection.HTTP_ACCEPTED:
                case HttpURLConnection.HTTP_CREATED:
                case HttpURLConnection.HTTP_NO_CONTENT:
                    try {
                        InputStream inputStream = urlConnection.getInputStream();
                        byte data[] = new byte[128];
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        int l = 0;
                        while ((l = inputStream.read(data)) > 0) {
                            byteArrayOutputStream.write(data, 0, l);
                        }
                        String resp = new String(byteArrayOutputStream.toByteArray());
                        return new JSONObject(resp);
                    } catch (JSONException e) {
                        return new Error(ErrorConstants.ERROR_CODE_JSON_FORMAT, ErrorConstants.ERROR_MESSAGE_JSON_FORMAT + ". " + e.getMessage());
                    }
            }
        } catch (UnsupportedEncodingException e) {
            return new Error(ErrorConstants.ERROR_CODE_UNSUPPORTED_ENCODING, ErrorConstants.ERROR_MESSAGE_JSON_FORMAT + ". " + e.getMessage());
        } catch (MalformedURLException e) {
            return new Error(ErrorConstants.ERROR_CODE_MALFORMED_URL_EXCEPTION, ErrorConstants.ERROR_MESSAGE_JSON_FORMAT + ". " + e.getMessage());
        } catch (IOException e) {
            return new Error(ErrorConstants.ERROR_CODE_IO_EXCEPTION, ErrorConstants.ERROR_MESSAGE_JSON_FORMAT + ". " + e.getMessage());
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return new Error(ErrorConstants.ERROR_CODE_NO_NETWORK, ErrorConstants.ERROR_MESSAGE_NO_NETWORK);
    }

    /**
     * Method to send Post Request
     *
     * @param strURL
     * @param jsonObject
     * @return
     */
    private static Object excuteDelete(String strURL, JSONObject jsonObject) {
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strURL);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("DELETE");
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0(macintosh;U;Intel Mac 05 X104; en-us; rv:1.9.2.2) Gecko/20100316 Firefox/19.0");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setReadTimeout(SOCKET_TIME_OUT);
            urlConnection.setConnectTimeout(SOCKET_TIME_OUT);
            DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
            wr.writeBytes(jsonObject.toString());
            wr.flush();
            wr.close();
            int responseCode = urlConnection.getResponseCode();
            switch (responseCode) {
                case HttpURLConnection.HTTP_OK:
                case HttpURLConnection.HTTP_ACCEPTED:
                case HttpURLConnection.HTTP_CREATED:
                case HttpURLConnection.HTTP_NO_CONTENT:
                    try {
                        InputStream inputStream = urlConnection.getInputStream();
                        byte data[] = new byte[128];
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        int l = 0;
                        while ((l = inputStream.read(data)) > 0) {
                            byteArrayOutputStream.write(data, 0, l);
                        }
                        String resp = new String(byteArrayOutputStream.toByteArray());
                        return new JSONObject(resp);
                    } catch (JSONException e) {
                        return new Error(ErrorConstants.ERROR_CODE_JSON_FORMAT, ErrorConstants.ERROR_MESSAGE_JSON_FORMAT + ". " + e.getMessage());
                    }
            }
        } catch (UnsupportedEncodingException e) {
            return new Error(ErrorConstants.ERROR_CODE_UNSUPPORTED_ENCODING, ErrorConstants.ERROR_MESSAGE_JSON_FORMAT + ". " + e.getMessage());
        } catch (MalformedURLException e) {
            return new Error(ErrorConstants.ERROR_CODE_MALFORMED_URL_EXCEPTION, ErrorConstants.ERROR_MESSAGE_JSON_FORMAT + ". " + e.getMessage());
        } catch (IOException e) {
            return new Error(ErrorConstants.ERROR_CODE_IO_EXCEPTION, ErrorConstants.ERROR_MESSAGE_JSON_FORMAT + ". " + e.getMessage());
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return new Error(ErrorConstants.ERROR_CODE_NO_NETWORK, ErrorConstants.ERROR_MESSAGE_NO_NETWORK);
    }

    // ==========================================================================================================
    // ==========================================================================================================
    // ==========================================================================================================

    /**
     * Class to handel the error response
     */
    private static class Error {
        private int mErrorCode;
        private String mErrorMessage;

        Error(int errorCode, String errorMesage) {
            mErrorCode = errorCode;
            mErrorMessage = errorMesage;
        }
    }

    /**
     * This listener is used to get the status at the time of made any request
     * to the server<br>
     * If the request is successfully made to the server then
     * <b>onNetworkRequestCompleted(JSONObject json)</b> is invoke, where the
     * <b>json</b> is the response send by the server in respect of the request.<br>
     * If there is an error occur such as No-Network-Connection then
     * <b>onNetworkRequestFailed(Error error)</b> is invoke.<br>
     **/
    public static interface OnNetworkRequestListener {

        /**
         * Callback to notify that the request is successfully granted by the
         * server and response is returned from the server
         *
         * @param json response return from the server in the form of json
         * @param flag Flag that associate with an request
         */
        public void onNetworkRequestCompleted(JSONObject json, String flag);

        /**
         * Callback to notify that there is some sort of error occur at the time
         * of requesting to the server
         *
         * @param errorCode    Error occur at the time of network calling
         * @param errorMessage Error occur at the time of network calling
         * @param flag         Flag that associate with an request
         */
        public void onNetworkRequestFailed(int errorCode, String errorMessage, String flag);
    }
}
