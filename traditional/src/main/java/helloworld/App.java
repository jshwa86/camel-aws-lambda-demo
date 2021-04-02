package helloworld;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.engine.DefaultFluentProducerTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Handler for requests to Lambda function.
 */
public class App implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private CamelContext camelContext;

    public App(){
        initCamel();
    }

    private void initCamel(){
        camelContext = new DefaultCamelContext();
        try {
            camelContext.addRoutes(new RouteBuilder() {
                @Override
                public void configure() throws Exception {
                    from("direct:helloWorld").id("helloWorld")
                            .to("https://jsonplaceholder.typicode.com/todos/1?CamelJacksonUnmarshalType")
                    .unmarshal(new JacksonDataFormat(TodoModel.class));
                }
            });
            camelContext.start();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input, final Context context) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Custom-Header", "application/json");

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent()
                .withHeaders(headers);
        TodoModel output =
            DefaultFluentProducerTemplate.on(camelContext)
                    .to("direct:helloWorld").request(TodoModel.class);

            return response
                    .withStatusCode(200)
                    .withBody(output.getTitle());
    }

}