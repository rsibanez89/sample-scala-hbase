clean_build_run: clean build run

build:
	docker build -f Dockerfile.hbase.zk -t hbase:latest .

run:
	docker run \
		-v ${PWD}/logs:/opt/hbase-2.2.7/logs/ \
		-p 2181:2181 \
		-p 16010:16010 \
		-p 16000:16000 \
		-p 16020:16020 \
		-p 16030:16030 \
		--name hbase-1 \
		hbase:latest

clean:
	docker kill hbase-1 || true
	docker rm hbase-1 || true
	rm -rf logs/*

run_shell:
	docker exec -ti hbase-1 ./bin/hbase shell

run_zookeeper_cli:
	docker exec -ti hbase-1 ./bin/hbase zkcli

run_rest:
	docker exec -ti hbase-1 ./bin/hbase rest start

run_start:
	docker exec -ti hbase-1 ./bin/start-hbase.sh

inspect:
	docker exec -ti hbase-1 bash


### Compose
run_compose:
	docker-compose -f docker-compose.yaml up --build | tee docker-compose.log

stop_compose:
	docker-compose -f docker-compose.yaml down
