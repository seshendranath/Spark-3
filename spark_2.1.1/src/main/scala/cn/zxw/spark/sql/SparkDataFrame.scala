package cn.zxw.spark.sql

import org.apache.spark.sql.SparkSession

/**
  * Created by zhangxw on 2017/8/16.
  */
object SparkDataFrame {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .master("local")
      .appName("Spark SQL basic example")
      .config("spark.some.config.option", "some-value")
      .getOrCreate()

    // For implicit conversions like converting RDDs to DataFrames
    import spark.implicits._

    val path = "spark_2.1.1/src/main/resources/examples/"
    val people = spark.read.json(path+"people.json")
    people.show()

    people.createOrReplaceTempView("people")
    val sqlDF = spark.sql("SELECT * FROM people")
    sqlDF.show()
    // +----+-------+
    // | age|   name|
    // +----+-------+
    // |null|Michael|
    // |  30|   Andy|
    // |  19| Justin|
    // +----+-------+
    spark.stop()
  }
}
