/**
 * JobService 
 */
app.factory('JobService',function($http){
	var jobService={}
	var BASE_URL="http://localhost:8085/Project2middleware"
	jobService.addJob=function(job){
		return $http.post(BASE_URL+"/addjob",job)
	}
	
	jobService.getAllJobs=function(){
		return $http.get(BASE_URL+"/getalljobs")
	}
	
	jobService.deleteJob=function(id){
		return $http['delete'](BASE_URL+"/deletejob/"+id)
	}
	
	jobService.updateJobForm=function(id){
		return $http.get(BASE_URL+"/updatejobform/"+id)
	}
	
	jobService.updateJob=function(jobs){
		return $http.put(BASE_URL+"/updatejob",jobs)
	}
	return jobService;
})