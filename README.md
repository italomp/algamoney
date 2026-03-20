execute db: <br>
docker run --name mysql-local -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=algamoneyApi -p 3306:3306 -d mysql:8.0 
