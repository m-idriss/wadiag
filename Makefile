all : whoami

run_docker:
	docker compose up --build

run_docker_prod:
	docker compose --env-file=.env.prod up --build

prune_docker:
	docker compose down; docker system prune -af

prune_run_docker : prune_docker run_docker

prune_run_docker_prod : prune_docker run_docker_prod


whoami:
	whoami