package example

import org.apache.hadoop.hbase.client.{ConnectionFactory, Put}
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.hbase.{CellUtil, HBaseConfiguration, TableName}
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.client.ColumnFamilyDescriptorBuilder
import org.apache.hadoop.hbase.client.TableDescriptorBuilder
import org.apache.hadoop.hbase.client.Get
import org.apache.hadoop.hbase.client.Result
import org.apache.hadoop.hbase.client.Scan

object HBaseExample extends App {
  println("Starting...")

  val conf : Configuration = HBaseConfiguration.create()
  // If it fails, make if fail faster by uncommenting these lines:
  // conf.set("hbase.client.pause", "1000")
  // conf.set("hbase.client.retries.number", "1")
  // conf.set("zookeeper.recovery.retry", "1")
  val connection = ConnectionFactory.createConnection(conf)

  val admin = connection.getAdmin()
  if (admin.tableExists(TableName.valueOf( Bytes.toBytes("test_table") ))) {
    println("Table exists...")
  } else {
    println("Table does not exist... creating...")
    admin.createTable(
      TableDescriptorBuilder
      .newBuilder(TableName.valueOf( Bytes.toBytes("test_table") ))
      .setColumnFamily(ColumnFamilyDescriptorBuilder.newBuilder(Bytes.toBytes("colfam1")).build())
      .build()
    )
  }

  println("Adding rows to table...")
  val table = connection.getTable(TableName.valueOf( Bytes.toBytes("test_table") ) )
  var put = new Put(Bytes.toBytes("row1"))
  put.addColumn(Bytes.toBytes("colfam1"), Bytes.toBytes("col1"), Bytes.toBytes("value1"))
  put.addColumn(Bytes.toBytes("colfam1"), Bytes.toBytes("col2"), Bytes.toBytes("value2"))
  table.put(put)

  println("Getting row1...")
  var get = new Get(Bytes.toBytes("row1"))
  var result = table.get(get)
  printRow(result)

  println("Scanning the whole table...:")
  var scan = table.getScanner(new Scan())
  scan.forEach(result => {
      printRow(result)
  })

  println("Exiting...")
  connection.close()


  def printRow(result : Result) = {
    val cells = result.rawCells();
    print( Bytes.toString(result.getRow) + ": " )
    for(cell <- cells){
      val col_name = Bytes.toString(CellUtil.cloneQualifier(cell))
      val col_value = Bytes.toString(CellUtil.cloneValue(cell))
      print("(%s,%s) ".format(col_name, col_value))
    }
    println()
  }
}


