package com.rajendra;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import java.util.Optional;

/**
 * Azure Functions with HTTP Trigger.
 */
public class Function {
    /**
     * This function listens at endpoint "/api/HttpExample". Two ways to invoke it using "curl" command in bash:
     * 1. curl -d "HTTP Body" {your host}/api/HttpExample
     * 2. curl "{your host}/api/HttpExample?name=HTTP%20Query"
     */
    @FunctionName("HttpExample")
    public HttpResponseMessage run(
            @HttpTrigger(
                name = "req",
                methods = {HttpMethod.GET, HttpMethod.POST},
                authLevel = AuthorizationLevel.ANONYMOUS)
                HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {
        context.getLogger().info("Java HTTP trigger processed a request.");

        // Parse query parameter
        final String queryP = request.getQueryParameters().get("principal ");
        final String principal = request.getBody().orElse(queryP);
        final String queryR = request.getQueryParameters().get("rate");
        final String rate = request.getBody().orElse(queryR);
        final String queryT = request.getQueryParameters().get("time");
        final String time = request.getBody().orElse(queryT);

        if (principal == null || rate == null || time == null) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Please pass Principal, Rate & Time").build();
        } else {
        	int p = Integer.parseInt(principal);
        	int r = Integer.parseInt(rate);
        	int t = Integer.parseInt(time);
        	int si = (p * r * t / 100  );
            return request.createResponseBuilder(HttpStatus.OK).body("SI:  " +  si).build();
        }
    }
}
