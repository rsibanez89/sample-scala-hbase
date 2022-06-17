# Sample Scala app using HBase
This app install HBase in a Docker container and then runs a simple Scala app connecting to HBase.

#### 1- Build the hbase image:
make build

#### 2- Run the container with hbase and start the server:
make run

#### 3- Check the master node is running and region servers are running:
- http://localhost:16010/master-status
- http://localhost:16030/rs-status

#### 4- Check the master node is connected to zookeeper:
- http://localhost:16010/zk.jsp

#### 5- Run the hbase shell:
make run_shell

then try the following commands:
- status
- list
- create 'table_test', 'cf'
- list 'table_test'
- put 'table_test', 'row1', 'cf:a', 'value1'
- put 'table_test', 'row2', 'cf:b', 'value2'
- put 'table_test', 'row3', 'cf:c', 'value3'
- scan 'table_test'
- get 'table_test', 'row1'

#### 6- Run the app from debugger or use the CLI:
- mvn scala:compile
- mvn scala:run -DmainClass=example.HBaseExample

#### 7- Clean docker image and logs
make clean
