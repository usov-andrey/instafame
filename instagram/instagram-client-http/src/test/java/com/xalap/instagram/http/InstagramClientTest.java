/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.http;

import com.xalap.framework.json.JsonService;
import org.apache.http.client.fluent.Request;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * @author Usov Andrey
 * @since 2020-08-26
 */
public class InstagramClientTest {

    private HttpInstagramClient instagramClient;

    public void initUseCase() {
        instagramClient = new HttpInstagramClient(new JsonService(), new HttpProxyService());
    }

    @Test
    public void testImport() throws IOException {
        Request request = Request.Get("https://apply.microverse.org/redirect?status=completed_profile");
        System.out.println(request.execute().returnContent().asString());
        /*
        Path file = Paths.get("src", "test", "resources", "instagramTest.html");
        String html = Files.readString(file);
        User user = instagramClient.readUserFromHtml(html);
        assertThat(user.getUserName()).isEqualTo("andreyusov.ru");*/
    }

}
