package cn.zxw.spark.streaming.input

import org.apache.spark.streaming.StreamingContext
import org.apache.spark.SparkConf
import org.apache.spark.streaming.Seconds
import org.apache.spark.storage.StorageLevel

/**
 * @author zhangxw
 */
object SourceSocketTextStream {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[2]").setAppName("SourceSocketTextStream")
    val ssc = new StreamingContext(sparkConf,Seconds(5))
    
    //MySocketServer
    val lines = ssc.socketTextStream("localhost", 9999, StorageLevel.MEMORY_ONLY)
    
    val words = lines.flatMap { x => x.split("\\s") }
    val pairs = words.map { x => (x,1) }
    val wordcount = pairs.reduceByKey( _+_ )
    
    wordcount.print()
    
    ssc.start()
    ssc.awaitTermination()
  }
}