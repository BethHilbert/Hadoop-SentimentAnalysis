package stubs;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class WordCountReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

  // The reduce method runs once for each key received from shuffle/sort
  @Override
	public void reduce(Text key, Iterable<IntWritable> values, Context context)
			throws IOException, InterruptedException {
		int wordCount = 0;
		
		for (IntWritable value : values) {
		  
			wordCount += value.get();
		}
		context.write(key, new IntWritable(wordCount));
	}
}