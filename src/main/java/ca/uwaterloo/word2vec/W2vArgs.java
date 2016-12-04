package ca.uwaterloo.word2vec;
/**
 * Created by rdsequie on 04/12/16.
 */
import org.kohsuke.args4j.Option;

public class W2vArgs {
    @Option(name = "-file", metaVar = "[file]", required = true, usage = "raw file")
    public String raw;

    //optional arguments
    @Option(name = "-window", metaVar = "[Number]",  usage = "window size")
    public int window = 10;

    @Option(name = "-iter", metaVar = "[Number]",  usage = "number of iterations")
    public int iter = 1;

    @Option(name = "-layer", metaVar = "[Number]",  usage = "layer size")
    public int layer = 100;

    @Option(name = "-min_freq", metaVar = "[Number]", usage = "minimum term frequency")
    public int min_word_freq = 5;

    @Option(name = "-seed", metaVar = "[Number]", usage = "seed size")
    public int seed = 42;

//    @Option(name = "-threads", metaVar = "[Number]",  usage = "number of threads")
//    public int threads;

}
