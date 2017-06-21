#!/bin/bash

set -x


oc delete job jenkins

oc create -f ./jenkins-job.yaml
