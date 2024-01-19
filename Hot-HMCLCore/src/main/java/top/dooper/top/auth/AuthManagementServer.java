package top.dooper.top.auth;

import com.google.gson.Gson;
import top.dooper.top.exception.ServerErrorException;
import top.dooper.top.util.HttpApiClient;

public class AuthManagementServer{
    private String url;
    private String serverName;
    public static AuthManagementServer locateServer(String url) throws Exception {
        AuthManagementServer authManagementServer = new AuthManagementServer();
        authManagementServer.setUrl(url);
        String resp = HttpApiClient.sendGetRequest(url);

        Gson gson = new Gson();
        AuthManagementServer response = gson.fromJson(resp, authManagementServer.getClass());
        if (response != null) {
            authManagementServer.setServerName(response.getServerName());
            return authManagementServer;
        }
        throw new ServerErrorException();
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }
}
