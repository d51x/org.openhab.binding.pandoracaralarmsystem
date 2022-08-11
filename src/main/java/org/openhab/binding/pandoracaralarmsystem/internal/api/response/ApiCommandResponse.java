package org.openhab.binding.pandoracaralarmsystem.internal.api.response;

import com.google.gson.annotations.SerializedName;

public class ApiCommandResponse extends ApiSuccessResponse {
    @SerializedName("action_result")
    public ActionResult actionResult;

    private class ActionResult {
        public String deviceId;
        public String replay;
    }
}
