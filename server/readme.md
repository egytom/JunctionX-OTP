Existing Repository for the backend: https://github.com/egytom/JunctionX.git  
Existing Repository for the client: https://github.com/Peater68/junctionx-otp-smart-atm.git  
 
 
`minikube start --vm-driver kvm2`

or  

`minikube config set vm-driver kvm2`  
`minikube start`  
`kubectl create clusterrolebinding admin --clusterrole=cluster-admin --serviceaccount=default:default`  

from project root: 

`kubectl apply -f kubernetes/mongodb-configmap.yaml`  
`kubectl apply -f kubernetes/mongodb-secret.yaml`  
`kubectl apply -f kubernetes/mongodb-deployment.yaml`  
`kubectl apply -f kubernetes/gateway-deployment.yaml`  
`kubectl apply -f kubernetes/atm-finder-deployment.yaml`  
`kubectl apply -f kubernetes/ingress.yaml` 

to see your working pods:  

`kubectl get pods`  
or  
`kubectl get all`

some endpoints:  
http://localhost:8081/swagger-ui.html#/   
http://localhost:8081/atm-finder/...  
