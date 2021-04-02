package helloworld;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;

public class HelloWorldRoute extends RouteBuilder {
    @Override
    public void configure() {
        from("direct:helloWorld").id("helloWorld")
                .to("https://jsonplaceholder.typicode.com/todos/1?CamelJacksonUnmarshalType")
        .unmarshal(new JacksonDataFormat(TodoModel.class));
    }
}
