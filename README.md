# rayman-magritte
Repositorio para proyecto de ingeniería del software.
[![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://travis-ci.org/joemccann/dillinger)

## Docker con PostgreSQL

  ### [Crear el container](https://hub.docker.com/_/postgres)
```bash
sudo docker run --name some-postgres -e POSTGRES_PASSWORD=mysecretpassword -d postgres
```

### Ingresar
```bash
sudo docker exec -it some-postgres psql postgres -U postgres
```

### [How to get a Docker container's IP address from the host](https://stackoverflow.com/questions/17157721/how-to-get-a-docker-containers-ip-address-from-the-host)
```bash
docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' container_name_or_id
```
Muestra la ip en la que esta corriendo el container, 
Valores por defecto:
| port | rol | database | scheme |
| ---- | --- | -------- | ------ |
| 5432 | postgres | postgres | postgres |

### Cargar archivos
```bash
sudo docker exec -i some-postgres psql -U postgres postgres < 'file.sql'
```

### [Ejecutar sin root](https://www.digitalocean.com/community/questions/how-to-fix-docker-got-permission-denied-while-trying-to-connect-to-the-docker-daemon-socket)
```bash
sudo usermod -aG docker ${USER}
```
