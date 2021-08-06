cd ./main-backend
./mvnw clean
./mvnw install
cd ..
docker compose up --build -d