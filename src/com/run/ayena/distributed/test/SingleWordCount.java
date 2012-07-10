package com.run.ayena.distributed.test;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

////统计文本中指定某个单词出现的次数



public class SingleWordCount {
	public static class SingleWordCountMapper extends
			Mapper<Object, Text, Text, IntWritable> {

		private final static IntWritable one = new IntWritable(1);
		private Text val = new Text();

		public void map(Object key, Text value, Context context)
				throws IOException, InterruptedException {
			StringTokenizer itr = new StringTokenizer(value.toString());
			String keyword = context.getConfiguration().get("word");
			while (itr.hasMoreTokens()) {
				String nextkey = itr.nextToken();
				if (nextkey.trim().equals(keyword)) {
					val.set(nextkey);
					context.write(val, one);
				} else {
					// do nothing
				}
			}
		}
	}

	public static class SingleWordCountReducer extends
			Reducer<Text,IntWritable,Text,IntWritable> {
		private IntWritable result = new IntWritable();

		public void reduce(Text key, Iterable<IntWritable> values,
				Context context) throws IOException, InterruptedException {
			int sum = 0;
			for (IntWritable val : values) {
				sum += val.get();
			}
			result.set(sum);
			context.write(key, result);
		}
	}

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		String[] otherArgs = new GenericOptionsParser(conf, args)
				.getRemainingArgs();
		if (otherArgs.length != 3) {
			System.err.println("Usage: wordcount  ");
			System.exit(2);
		}


		// 输入指定的单词
		conf.set("word", otherArgs[2]);

		// 指定系统路

		conf.set("mapred.system.dir", "/cygdrive/e/workspace_hadoop/SingleWordCount/");

		// 设置运行的job名称
		Job job = new Job(conf, "word count");

		// 设置运行的job类
		job.setJarByClass(SingleWordCount.class);

		// 设置Mapper
		job.setMapperClass(SingleWordCountMapper.class);

		// 设置本地聚合类，该例本地聚合类同Reduer类
		job.setCombinerClass(SingleWordCountReducer.class);

		// 设置Reduer
		job.setReducerClass(SingleWordCountReducer.class);

		// 设置Map的输出
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);

		// 设置Reducer输出的key类型
		job.setOutputKeyClass(Text.class);
		// 设置Reducer输出的value类型
		job.setOutputValueClass(IntWritable.class);

		// 设置输入和输出的目录
		FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
		FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));

		// 执行，直到结束就退出
		System.exit(job.waitForCompletion(true) ? 0 : 1);



	}
}

