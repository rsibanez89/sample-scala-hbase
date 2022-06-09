build:
	docker build -f Dockerfile.hbase -t hbase:latest .

run:
	docker run \
		-v ${PWD}/logs:/opt/hbase-2.4.12/logs/ \
		-p 2181:2181 \
		-p 16010:16010 \
		-p 16000:16000 \
		-p 16020:16020 \
		-p 16030:16030 \
		-p 8080:8080 \
		--name hbase-1 \
		hbase:latest

run_client:
	docker exec -ti hbase-1 ./bin/hbase shell

run_rest:
	docker exec -ti hbase-1 ./bin/hbase rest start

run_start:
	docker exec -ti hbase-1 ./bin/start-hbase.sh

inspect:
	docker exec -ti hbase-1 bash

clean:
	docker kill hbase-1
	docker rm hbase-1
	rm logs/*

