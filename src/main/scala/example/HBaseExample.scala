package example

import org.apache.hadoop.hbase.client.{ConnectionFactory, Put}
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.hbase.{CellUtil, HBaseConfiguration, TableName}
import org.apache.hadoop.conf.Configuration


object HBaseExample extends App {
  println("Starting...")

  val conf : Configuration = HBaseConfiguration.create()
  conf.set("hbase.zookeeper.quorum", "localhost")
  // conf.set("hbase.zookeeper.property.clientPort", "2181")
  //conf.set("hbase.master", "localhost")
  conf.set("hbase.client.pause", "1000")
  conf.set("hbase.client.retries.number", "2")
  conf.set("zookeeper.recovery.retry", "1")
  // conf.set("zookeeper.znode.parent", "/hbase");
  val connection = ConnectionFactory.createConnection(conf)

  val admin = connection.getAdmin()
  println( admin.tableExists(TableName.valueOf( Bytes.toBytes("test_table") )) )

  // val table = connection.getTable(TableName.valueOf( Bytes.toBytes("test_table") ) )
  // var put = new Put(Bytes.toBytes("row1"))
  // put.addColumn(Bytes.toBytes("colfam1"), Bytes.toBytes("col1"), Bytes.toBytes("value1"))
  // put.addColumn(Bytes.toBytes("colfam1"), Bytes.toBytes("col2"), Bytes.toBytes("value2"))
  // table.put(put)

  println("Finishing...")

}


