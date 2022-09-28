import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonReader {

    public List<Timeline> getTimelines(String json){
        ObjectMapper mapper = new ObjectMapper();
        List<Timeline> tmList = new ArrayList<>();

        try {
            tmList = Arrays.asList(mapper.readValue(json, Timeline[].class));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return tmList;
    }

}
