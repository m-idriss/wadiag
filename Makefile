all : whoami

run_docker_prod:
	docker-compose --env-file=.env.prod up --build

prune_docker:
	docker-compose down; docker system prune -af

prune_run_docker_prod : prune_docker run_docker_prod

whoami:
	whoami