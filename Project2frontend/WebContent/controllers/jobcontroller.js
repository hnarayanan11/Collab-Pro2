/**
 * JobCtrl
 */
app.controller('JobCtrl',function($scope,JobService,$location){
	$scope.showJob=false;
	//Data is from View to Controller
	$scope.addJob=function(job){
		JobService.addJob(job).then(function(response){
			alert('Job Details insertted successfully...')
			$location.path('/getalljobs')
		},function(response){
			if(response.data.errorCode==4)
				$location.path('/login')
			$scope.error=response.data
		})
	}
	
	//Data is from Controller to View
	//Statement which will get executed always
	JobService.getAllJobs().then(function(response){
		$scope.jobs=response.data
	},function(response){
		if(response.status==401)
			$location.path('/login')		
	})
	
	$scope.showJobDetails=function(){
		$scope.showJob= !$scope.showJob/*!$scope.showJob*/
	}
})