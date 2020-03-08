package com.partharaj;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.partharaj.App;
import com.partharaj.Logger;

import java.util.HashMap;
import java.util.Map;

public class NetRequestManager {

    public final static String HOST = "https://example.com/api";

    NetRequestManager netRequestManager;
    private String TAG = getClass().getSimpleName();
    private String endPoint;
    private boolean useCookie;
    private Map<String, String> headers = new HashMap<>();
    private Map<String, String> params = new HashMap<>();
    private OnResponse onResponse;
    private int responseCode = 0;

    public static class Builder {

        NetRequestManager netRequestManager;

        public Builder() {
            netRequestManager = new NetRequestManager();
        }

        public Builder setEndPoint(String endPoint) {
            netRequestManager.endPoint = endPoint;
            return this;
        }

        public Builder setHeader(Map<String, String> headers) {
            netRequestManager.headers = headers;
            return this;
        }

        public Builder setParam(Map<String, String> params) {
            netRequestManager.params = params;
            return this;
        }

        public Builder setOnResponse(OnResponse onResponse) {
            netRequestManager.onResponse = onResponse;
            return this;
        }

        public Builder useCookie(boolean useCookie) {
            netRequestManager.useCookie = useCookie;
            return this;
        }

        public NetRequestManager build() {
            return netRequestManager;
        }

    }

    public void post() {
        sendRequest(Request.Method.POST);
    }

    public void get() {
        sendRequest(Request.Method.GET);
    }

    private void sendRequest(int method) {
        Response.Listener onSuccess = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Logger.log(TAG, "Success Response for: " + endPoint);
                Logger.log(TAG, "Response body: " + response);

                if (onResponse != null) {
                    onResponse.onSuccess(response);
                }
            }
        };

        Response.ErrorListener onError = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse != null) {
                    responseCode = error.networkResponse.statusCode;
                    try {
                        Logger.log(TAG, "Error response for: " + endPoint);
                        Logger.log(TAG, "onErrorResponse: " + error.getLocalizedMessage() + " netcode: " + error.networkResponse.statusCode);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                if (onResponse != null) onResponse.onError(error);
            }
        };

        StringRequest request = new StringRequest(method, endPoint, onSuccess, onError) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                if (params == null) return super.getParams();
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return headers;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                responseCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }
        };
        App.getApp().addToRequestQueue(request);
    }

    public int getResponseCode() {
        return responseCode;
    }

    public interface OnResponse{
        void onSuccess(String response);
        void onError(VolleyError error);
    }
}
