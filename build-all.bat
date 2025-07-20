cd ingest-service
mvn clean package

cd ../transaction-service
mvn clean package

cd ../normalizer-service
mvn clean package

cd ../recon-service
mvn clean package
