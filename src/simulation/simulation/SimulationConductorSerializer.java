package simulation.simulation;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import simulation.agent.Agent;
import simulation.agent.variant.VariantAgent;
import simulation.language.Language;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class SimulationConductorSerializer extends StdSerializer<SimulationConductor> {

    public SimulationConductorSerializer() {
        this(null);
    }

    public SimulationConductorSerializer(Class<SimulationConductor> t) {
        super(t);
    }

    public static void writeToJSON(SimulationConductor conductor, String path) throws IOException {
        Writer writer = new FileWriter(path);

        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        SimpleModule module = new SimpleModule();
        module.addSerializer(SimulationConductor.class, new SimulationConductorSerializer());
        objectMapper.registerModule(module);

        objectMapper.writeValue(writer, conductor);
    }

    @Override
    public void serialize(SimulationConductor conductor, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("number of simulations conducted", conductor.getSimulationsConducted());
        jsonGenerator.writeNumberField("number of simulations not finished", conductor.getUnfinishedSimulations());
        jsonGenerator.writeNumberField("average number of iterations", conductor.getAverageIterations());
        jsonGenerator.writeNumberField("maximum number of iterations", conductor.getMaxIterations());
        jsonGenerator.writeNumberField("objects", conductor.getPatternSimulation().getEnvironment().getThings().size());
        jsonGenerator.writeStringField("population type", conductor.getPatternSimulation().getPopulation().getClass().getSimpleName());

        serializeVariant(conductor, jsonGenerator, serializerProvider);
        serializeAgents(conductor, jsonGenerator, serializerProvider);
        serializeLanguages(conductor, jsonGenerator, serializerProvider);

        jsonGenerator.writeEndObject();
    }

    private void serializeVariant(SimulationConductor conductor, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        VariantAgent variantAgent = conductor.getPatternSimulation().getVariantAgent();
        if(conductor.getPatternSimulation().getVariantAgent() == null) {
            jsonGenerator.writeStringField("variant", "zero");
            jsonGenerator.writeNumberField("variant influence", 0);
            jsonGenerator.writeNumberField("variant language", -1);
        }
        else {
            jsonGenerator.writeStringField("variant", variantAgent.getClass().getSimpleName());
            jsonGenerator.writeNumberField("variant influence", conductor.getPatternSimulation().getVariantInfluence());
            jsonGenerator.writeNumberField("variant language", conductor.getLanguageId(variantAgent.dominatingLanguage(conductor.getPatternSimulation().getLanguages())));
        }
    }

    private void serializeAgents(SimulationConductor conductor, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeArrayFieldStart("agents");
        int i=1;
        for(Agent agent: conductor.getPatternSimulation().getAgentsArray()){
            jsonGenerator.writeStartObject();
            jsonGenerator.writeNumberField("id", i++);
            jsonGenerator.writeStringField("type", agent.getClass().getSimpleName());
            jsonGenerator.writeNumberField("initial language", conductor.getLanguageId(agent.dominatingLanguage(conductor.getPatternSimulation().getLanguages())));
            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndArray();
    }

    private void serializeLanguages(SimulationConductor conductor, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeArrayFieldStart("languages");
        for(Language language: conductor.getLanguagesArray()){
            jsonGenerator.writeStartObject();

            jsonGenerator.writeNumberField("id", conductor.getLanguageId(language));
            jsonGenerator.writeStringField("type", language.getClass().getSimpleName());

            jsonGenerator.writeFieldName("info");
            serializerProvider.defaultSerializeValue(language, jsonGenerator);

            jsonGenerator.writeFieldName("average weight sums history");
            jsonGenerator.writeArray(conductor.getAverageWeightSumsArray(language),0,conductor.getMaxIterations());

            jsonGenerator.writeFieldName("average nums of users history");
            jsonGenerator.writeArray(conductor.getAverageNumsOfUsersArray(language),0,conductor.getMaxIterations());

            jsonGenerator.writeFieldName("average nums of recognized things history");
            jsonGenerator.writeArray(conductor.getAverageNumsOfRecognizedThingsArray(language),0,conductor.getMaxIterations());

            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndArray();
    }

}
