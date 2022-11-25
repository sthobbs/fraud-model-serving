# fraud-model-serving

This repo contains code to implement an XGBoost fraud detection model with a low-latency streaming job on GCP Dataflow using the Apache Beam Java SDK. The model is developed in https://github.com/sthobbs/fraud-model-dev. Once this streaming job is deployed, you can pass data through it using the model-dev repo.


### Prerequisites:
- Run develop-model.py in https://github.com/sthobbs/fraud-model-dev (after prerequisites in fraud-model-dev's README)
    - This should upload side input data to GCS, among other things.
    - Note: if you changed the config in fraud-model-dev, then a different model will be trained. This model should be copied from [fraud-model-dev-repo]/training/results/1.0/model/model.bin to overwrite [fraud-model-serving-repo]/src/main/resources/model.bin
- Set up config:
    - create Pub/Sub topics/subscriptions for input and output, and add them to inputPubsubSubscription and outputPubsubTopic
    - specify path to service key (serviceAccountKeyPath), GCP project (project), storage bucket (bucket), and update the project in sdkContainerImage.
- Build docker image and upload to GCP Container Registry (this is required because XGBoost requires dependencies that workers don't have by default.) (see steps below)


### To Build the docker image and put it on GCP Container Registry:
1. Open docker desktop, and wait a few minutes

2. (if on windows/linux) add user to security group to allow docker to interact with registries (see https://cloud.google.com/container-registry/docs/advanced-authentication#windows)
    1. (on windows) cmd, run "whoami" to get <domain-name>\<username>
    e.g. desktop-cl75tr9\hobbs
    2. run "net localgroup docker-users <username> /add" in ADMIN cmd
    e.g. net localgroup docker-users hobbs /add

3. download Google cloud CLI, and configure authentication (for docker)
    1. run:
            gcloud auth activate-service-account ACCOUNT --key-file=KEY-FILE
            e.g. gcloud auth activate-service-account service-account1@analog-arbor-367702.iam.gserviceaccount.com --key-file=./service_account_key.json
    See https://cloud.google.com/sdk/docs/install, and https://cloud.google.com/container-registry/docs/advanced-authentication)
    
    2. run:
            gcloud auth configure-docker
    3. in powershell, run:
            Get-Content KEY-FILE | docker login -u KEY-TYPE --password-stdin https://HOSTNAME
            e.g. Get-Content ./service_account_key.json | docker login -u _json_key --password-stdin https://gcr.io

4. run commands:
        export PROJECT=analog-arbor-367702
        export REPO=fraud-model-serving
        export TAG=latest
        export IMAGE_URI=gcr.io/$PROJECT/$REPO:$TAG
        docker build . --tag $IMAGE_URI
        docker push $IMAGE_URI
