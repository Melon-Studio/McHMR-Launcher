package top.dooper.top.auth;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import top.dooper.top.util.HttpApiClient;

import java.io.FileReader;
import java.io.IOException;

import static top.dooper.top.app.CheckHotCoreFolder.getConfigFilePath;

public class CheckServerApi {
    public static boolean checkServerAPI() {
        try {
            JsonObject jsonObject = readJsonFile(getConfigFilePath());

            if (jsonObject.has("url")) {
                String serverValue = jsonObject.get("url").getAsString();
                System.out.println("键“url”存在，对应的值为：" + serverValue);

                try {
                    String resp = HttpApiClient.sendGetRequest(serverValue);
                    Gson gson = new Gson();
                    AuthManagementServer authManagementServer = new AuthManagementServer();
                    AuthManagementServer response = gson.fromJson(resp, authManagementServer.getClass());
                    if (response.getServerName() != null && !response.getServerName().isEmpty()) {
                        System.out.println("正常");
                        return true;
                    }
                    return false;
                } catch (Exception ex) {
                    System.err.println("返回数据不匹配：" + ex.getMessage());
                    return false;
                }
            } else {
                System.out.println("键“url”不存在");
                return false;
            }
        }catch (IOException ex) {
            System.err.println("IO异常：" + ex.getMessage());
            return false;
        }catch (Exception ex) {
            System.err.println("发生异常：" + ex.getMessage());
            return false;
        }
    }


    private static JsonObject readJsonFile(String filePath) throws IOException {
        JsonParser parser = new JsonParser();
        Object obj = parser.parse(new FileReader(filePath));
        return (JsonObject) obj;
    }
}
