package stubs;

// Basic MapReduce utility classes
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.hadoop.mapreduce.Job; 

// Classes for File IO
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

// Wrappers for data types
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

// Configurable counters
import org.apache.hadoop.mapreduce.Counters;
import org.apache.hadoop.mapreduce.Counter;

public class WordCount extends Configured implements Tool {
	
	public static void main(String[] args) throws Exception {
    int res = ToolRunner.run(new WordCount(), args);
    System.exit(res);
  }

// The run method configures and starts the MapReduce job.

	public int run(String[] args) throws Exception {
		 Job job = Job.getInstance(getConf(), "wordcount");
		 for (int i = 0; i < args.length; i += 1) {
				
				if ("-skip".equals(args[i])) {
					job.getConfiguration().setBoolean("wordcount.skip.patterns", true);
					i += 1;
					job.addCacheFile(new Path(args[i]).toUri());
				}
				if ("-pos".equals(args[i])) {
					job.getConfiguration().setBoolean("wordcount.pos.patterns", true);
					i += 1;
					job.addCacheFile(new Path(args[i]).toUri());
				}    
				if ("-neg".equals(args[i])) {
					job.getConfiguration().setBoolean("wordcount.neg.patterns", true);
					i += 1;
					job.addCacheFile(new Path(args[i]).toUri());
				}
				}

		job.setJarByClass(WordCount.class);  //the Jar that contains mapper and reducer
		job.setJobName("Word Count");
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		job.setMapperClass(WordCountMapper.class);
		job.setCombinerClass(WordCountReducer.class);
		job.setReducerClass(WordCountReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		int result = job.waitForCompletion(true) ? 0:1;
    
	// Work with the results before returning control to the main method.
 
	// Get the counters from the Map class.
	Counters counters = job.getCounters();
    float good = counters.findCounter("org.myorg.Map$Gauge", "POSITIVE").getValue();
    float bad = counters.findCounter("org.myorg.Map$Gauge", "NEGATIVE").getValue();
    
    if (good + bad > 0) {
	// Calculate the basic sentiment score by dividing the difference 
	// of good and bad words by their sum.
	
		float sentiment = ((good - bad) / (good + bad));
		
  // Calculate the positivity score by dividing good results by the sum of
  // good and bad results. Multiply by 100 and round off to get a percentage.
  // Results 50% and above are more positive, overall.		
		
		float positivity = (good / (good + bad))*100;
		int positivityScore = Math.round(positivity);
		
	// Display the results in the console.
	
		System.out.println("\n\n\n**********\n\n\n");
		System.out.println("Sentiment score = (" + good + " - " + bad + ") / (" + good +
				" + " + bad + ")");
		System.out.println("Sentiment score = " + sentiment);
		System.out.println("\n\n");
		System.out.println("Positivity score = " + good + "/(" + good + "+" + bad + ")");
		System.out.println("Positivity score = " + positivityScore + "%");
		System.out.println("\n\n\n********** \n\n\n\n");
    }
    else {
		System.out.println("\n\n\n**********\n\n\n");
		System.out.println("No positive or negative words found in input data.");
		System.out.println("\n\n\n**********\n\n\n");
    }
	 
    return result;
  }
}
