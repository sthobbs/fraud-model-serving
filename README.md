# model-serving
Beam pipeline for low-latency model serving



To Build the docker image
1. open docker desktop, and wait a few minutes

2. on windows/linux, add user to security group to allow docker to interac with regestries
    see https://cloud.google.com/container-registry/docs/advanced-authentication#windows
2.1 (windows) cmd, run "whoami" to get <domain-name>\<username>
    e.g. desktop-cl75tr9\hobbs
2.2 run "net localgroup docker-users <domain-name>\<username> /add" in ADMIN cmd
    net localgroup docker-users hobbs /add

3. download Google cloud CLI, and configure authentication (for docker)
    3.1
    https://cloud.google.com/sdk/docs/install
    https://cloud.google.com/container-registry/docs/advanced-authentication
        gcloud auth activate-service-account ACCOUNT --key-file=KEY-FILE
        gcloud auth activate-service-account service-account1@analog-arbor-367702.iam.gserviceaccount.com --key-file=./service_account_key.json
    3.2 gcloud auth configure-docker
    3.3 in powershell run,
    //Get-Content KEY-FILE | docker login -u KEY-TYPE --password-stdin https://HOSTNAME
    Get-Content ./service_account_key.json | docker login -u _json_key --password-stdin https://gcr.io

4. run commands
export PROJECT=analog-arbor-367702
export REPO=model-serving
export TAG=latest
export IMAGE_URI=gcr.io/$PROJECT/$REPO:$TAG
docker build . --tag $IMAGE_URI
docker push $IMAGE_URI

