docker run --rm -d \
--name otel-collector \
-p 4317:4317 \
-p 4318:4318 \
-p 55679:55679 \
-p 13133:13133 \
-v $(pwd)/otel-config.yaml:/etc/otel/config.yaml \
otel/opentelemetry-collector-contrib:latest \
--config /etc/otel/config.yaml


docker run \
--name otel-collector \
-p 4317:4317 \
-p 4318:4318 \
-p 55679:55679 \
-p 13133:13133 \
-v $(pwd)/otel-config.yaml:/etc/otel/config.yaml \
otel/opentelemetry-collector-contrib:latest \
--config /etc/otel/config.yaml



http://localhost:13133/health/status
http://192.168.100.10:13133/health/status
