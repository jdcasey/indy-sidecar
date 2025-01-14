/**
 * Copyright (C) 2011-2021 Red Hat, Inc. (https://github.com/Commonjava/indy-sidecar)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.commonjava.util.sidecar.jaxrs;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import org.commonjava.util.sidecar.jaxrs.mock.MockTestProfile;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.OK;

@QuarkusTest
@TestProfile( MockTestProfile.class )
public class NPMDownloadFromArchiveTest
{

    @Test
    public void testMetadataDownload()
    {
        // do npm metadata proxy no matter it exists locally or not
        given().when()
               .get( "/api/folo/track/2021/npm/group/npmjs/@babel/code-frame" )
               .then()
               .statusCode( NOT_FOUND.getStatusCode() );
    }

    @Test
    public void testDownloadSuccess()
    {
        given().when()
               .get( "/api/folo/track/2021/npm/group/npmjs/@babel/code-frame/-/code-frame-7.tgz" )
               .then()
               .statusCode( OK.getStatusCode() );
    }

    @Test
    public void testDownloadNotFound()
    {
        given().when()
               .get( "/api/folo/track/2021/npm/group/npmjs/@babel/code-frame/-/code-frame-8.tgz" )
               .then()
               .statusCode( NOT_FOUND.getStatusCode() );
    }
}
