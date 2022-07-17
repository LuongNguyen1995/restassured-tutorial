package test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.RequestCapability;
import org.apache.commons.codec.binary.Base64;


import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
public class JiraIssueTypes implements RequestCapability {
    public static void main(String[] args) {
        String baseUri = "https://tester2205e.atlassian.net";
        String path = "/rest/api/3/project/tester2205e";

        String email = "luong@gmail.com";
        String apiToken = "vhjcslxkhgvx";
        String cred = email.concat(":").concat(apiToken);
        byte[] encodeCred = Base64.encodeBase64(cred.getBytes());
        String encodedCredStr = new String(encodeCred);

        RequestSpecification request = given();
        request.baseUri(baseUri);
        request.header(defaultHeader);
        request.header(getAuthenticatedHeader.apply(encodedCredStr));

        Response response = request.get(path);
        response.prettyPrint();

        Map<String, List<Map<String, String>>> projectInfo = JsonPath.from(response.asString()).get();
        List<Map<String, String>> issueTypes = projectInfo.get("issueTypes");
        for (Map<String, String> issueType : issueTypes){
            System.out.println(issueType.get("id"));
            System.out.println(issueType.get("name"));
            System.out.println("----------------");
        }
    }

    static String getIssueTypeId(String issueTypeStr){

    }


}
