package no.ssb.avro.j2avro;

import lombok.extern.slf4j.Slf4j;
import org.apache.avro.Schema;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(name = "java2avro",
        versionProvider = Cli.class,
        description = "Convert java pojos to avro schema", mixinStandardHelpOptions = true)
public class Cli implements Runnable, CommandLine.IVersionProvider {

    @CommandLine.Option(names = {"--root-type"},
            description = "Fully qualified class name of the root element for which to generate avro schema", required = true)
    String rootClassName;

    @Override
    public String[] getVersion() {
        return new String[] {String.format("%s (%s)",
                BuildInfo.INSTANCE.getVersion(),
                BuildInfo.INSTANCE.getBuildTimestamp())
        };
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new Cli()).execute(args);
        System.exit(exitCode);
    }

    public void run() {
        PojoTransformer transformer = new PojoTransformer();
        Schema avroSchema = transformer.toAvroSchema(rootClassName);
        System.out.println(avroSchema.toString(true)); // NOSONAR
    }
}
