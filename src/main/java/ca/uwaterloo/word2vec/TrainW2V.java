package ca.uwaterloo.word2vec;

import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.text.sentenceiterator.BasicLineIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionHandlerFilter;
import org.kohsuke.args4j.ParserProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public class TrainW2V {

    private static Logger log = LoggerFactory.getLogger(TrainW2V.class);

    public static void main(String[] args) throws Exception {
        W2vArgs w2vArgs = new W2vArgs();
        CmdLineParser parser = new CmdLineParser(w2vArgs, ParserProperties.defaults().withUsageWidth(90));

        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            parser.printUsage(System.err);
            System.err.println("Example: "+ TrainW2V.class.getSimpleName() + parser.printExample(OptionHandlerFilter.REQUIRED));
            return;
        }


        log.info("Load & Vectorize Sentences....");
        // Strip white space before and after for each line
        SentenceIterator sent_iter = new BasicLineIterator(w2vArgs.raw);

         // Use the Lucene Standard Tokenizer here
        TokenizerFactory t = new DefaultTokenizerFactory();
        t.setTokenPreProcessor(new CommonPreprocessor());

        log.info("Building model....");
        Word2Vec vec = new Word2Vec.Builder()
                .minWordFrequency(w2vArgs.min_word_freq)
                .iterations(w2vArgs.iter)
                .layerSize(w2vArgs.layer)
                .seed(w2vArgs.seed)
                .windowSize(w2vArgs.window)
                .iterate(sent_iter)
                .tokenizerFactory(t)
                .build();

        log.info("Fitting Word2Vec model....");
        vec.fit();

        log.info("Writing word vectors to text file....");

        // Write word vectors
        WordVectorSerializer.writeWordVectors(vec, "pathToWriteto.txt");

        log.info("Closest Words:");
        Collection<String> lst = vec.wordsNearest("day", 10);
        System.out.println(lst);
    }
}