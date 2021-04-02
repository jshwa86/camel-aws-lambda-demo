package helloworld;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TodoModelTest {

    @Test
    public void canDeserialize() throws JsonProcessingException {
        String json = "{\n" +
                "  \"userId\": 1,\n" +
                "  \"id\": 1,\n" +
                "  \"title\": \"delectus aut autem\",\n" +
                "  \"completed\": false\n" +
                "}";

        TodoModel todoModel = new ObjectMapper().readValue(json,TodoModel.class);
        assertEquals("1",todoModel.getUserId());
        assertEquals(1,todoModel.getId());
        assertEquals("delectus aut autem",todoModel.getTitle());
        assertFalse(todoModel.isCompleted());

    }

}