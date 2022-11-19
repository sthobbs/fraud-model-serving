# XGBoost requires dependencies that are not available in the default

# java version must match
FROM apache/beam_java17_sdk:2.42.0

# Install XGBoost dependencies
RUN apt-get update -y
RUN apt-get install -y gcc
RUN apt-get install -y libgomp1

# CMD ["sudo", "apt-get", "update", "-y"]
# CMD ["sudo", "apt-get", "install", "-y", "gcc"]
# CMD ["sudo", "apt-get", "install", "-y", "libgomp1"]
